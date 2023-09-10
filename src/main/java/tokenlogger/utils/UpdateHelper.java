package tokenlogger.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import static tokenlogger.config.Config.VERSION_INFO_URL;
import static tokenlogger.config.Config.VERSION_NUMBER;

public class UpdateHelper {
    public static boolean checkForUpdates() {
        try {
            return getUpdateInfo().get("version").asInt() > VERSION_NUMBER;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static JsonNode getUpdateInfo() {
        try {
            return Json.getObjectMapper().readTree(Requests.get(VERSION_INFO_URL, null));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonNodeFactory.instance.nullNode();
        }
    }
}
