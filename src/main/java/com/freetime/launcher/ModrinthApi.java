package com.freetime.launcher;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ModrinthApi {
    private static final String API_URL = "https://api.modrinth.com/v2/project/";

    public static void downloadModpack(String modpackId, String targetPath) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
            String apiKey = properties.getProperty("modrinth.api.key");

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpGet request = new HttpGet(API_URL + modpackId + "/version");
                request.addHeader("Authorization", "Bearer " + apiKey);

                HttpResponse response = httpClient.execute(request);
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JSONArray versionsArray = new JSONArray(jsonResponse);

                if (versionsArray.length() > 0) {
                    JSONObject latestVersion = versionsArray.getJSONObject(0);
                    String downloadUrl = latestVersion.getJSONArray("files").getJSONObject(0).getString("url");

                    try (InputStream in = new URL(downloadUrl).openStream();
                         FileOutputStream out = new FileOutputStream(targetPath)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = in.read(buffer)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }
                        System.out.println("Modpack downloaded to " + targetPath);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}