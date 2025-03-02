package com.example.launcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
            String modpackSource = properties.getProperty("modpack.source");
            String modpackId = properties.getProperty("modpack.id");
            String modpackPath = properties.getProperty("modpack.path");

            if (modpackSource.equals("curseforge")) {
                String apiKey = properties.getProperty("curseforge.api.key");
                CurseForgeApi.downloadModpack(apiKey, modpackId, modpackPath);
            } else if (modpackSource.equals("modrinth")) {
                ModrinthApi.downloadModpack(modpackId, modpackPath);
            }

            ModpackInstaller.installModpack(modpackPath, properties.getProperty("minecraft.path"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}