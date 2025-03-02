package com.freetime.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
            String modpackId = properties.getProperty("modpack.id");
            String modpackPath = properties.getProperty("modpack.path");

            // Dynamisch den Minecraft-Pfad ermitteln
            String userHome = System.getProperty("user.home");
            String minecraftPath = userHome + File.separator + "AppData" + File.separator + "Roaming" + File.separator + ".minecraft";

            ModrinthApi.downloadModpack(modpackId, modpackPath);
            ModpackInstaller.installModpack(modpackPath, minecraftPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}