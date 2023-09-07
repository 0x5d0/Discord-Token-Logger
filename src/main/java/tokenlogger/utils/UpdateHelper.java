package tokenlogger.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static tokenlogger.config.Config.VERSION_INFO_URL;
import static tokenlogger.config.Config.VERSION_NUMBER;

public class UpdateHelper {
    public static boolean checkForUpdates() {
        return getUpdateInfo().get("version").asInt() > VERSION_NUMBER;
    }

    public static JsonNode getUpdateInfo() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(Requests.get(VERSION_INFO_URL, null));
        } catch (Exception ignored) {}
        return null;
    }
}
