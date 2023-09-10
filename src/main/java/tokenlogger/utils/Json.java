package tokenlogger.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
