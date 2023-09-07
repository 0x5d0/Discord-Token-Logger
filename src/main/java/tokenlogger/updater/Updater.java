package tokenlogger.updater;

import tokenlogger.utils.FileHelper;
import tokenlogger.utils.Requests;
import tokenlogger.utils.UpdateHelper;

import static tokenlogger.config.Config.APP_SAVE_PATH;

public class Updater {
    public static void main(String[] args) {
        FileHelper.delete(APP_SAVE_PATH);
        Requests.download(UpdateHelper.getUpdateInfo().get("link").asText(), APP_SAVE_PATH);
        FileHelper.launch(APP_SAVE_PATH);
        System.exit(0);
    }
}
