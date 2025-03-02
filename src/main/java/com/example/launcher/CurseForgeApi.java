package com.example.launcher;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CurseForgeApi {
    private static final String API_URL = "https://api.curseforge.com/v1/mods/";

    public static void downloadModpack(String apiKey, String modpackId, String targetPath) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(API_URL + modpackId + "/files");
            request.addHeader("x-api-key", apiKey);

            HttpResponse response = httpClient.execute(request);
            String jsonResponse = EntityUtils.toString(response.getEntity());
            JSONArray filesArray = new JSONObject(jsonResponse).getJSONArray("data");

            if (filesArray.length() > 0) {
                JSONObject latestFile = filesArray.getJSONObject(0);
                String downloadUrl = latestFile.getString("downloadUrl");
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
    }
}