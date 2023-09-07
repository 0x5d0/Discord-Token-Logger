package tokenlogger.utils;

import java.io.*;
import java.net.URL;

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

        try {
            Response response = client.newCall(request).execute();
            int responseCode = response.code();
            System.out.println("POST Response Code: " + responseCode);
        } catch (Exception ignored) {}
    }

    public static String get(String urlString, String token) {
        OkHttpClient client = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder()
                .url(urlString);

        if (token != null) {
            requestBuilder.header("Authorization", token);
        }

        try {
            Response response = client.newCall(requestBuilder.build()).execute();
            int responseCode = response.code();
            System.out.println("GET Response Code: " + responseCode);

            if (responseCode == 200) {
                return response.body().string();
            }
        } catch (Exception e) {}

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
}
