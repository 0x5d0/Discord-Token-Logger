package tokenlogger.utils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import okhttp3.*;

public class Requests {
    public static void post(String jsonPayload, String url) {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(jsonPayload, JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("POST: " + responseCode);
        } catch (Exception ignored) {}
    }

    public static String get(String urlString, String token) {
        OkHttpClient client = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder()
                .url(urlString);

        if (token != null) requestBuilder.header("Authorization", token);

        try (Response response = client.newCall(requestBuilder.build()).execute()) {
            int responseCode = response.code();
            if (responseCode == 200) return response.body().string();
            System.out.println("GET: " + responseCode);
        } catch (Exception ignored) {}

        return null;
    }

    public static void download(String fileUrl, String savePath) {
        try (InputStream in = new URL(fileUrl).openStream()) {
            Files.copy(in, Paths.get(savePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ignored) {}
    }
}
