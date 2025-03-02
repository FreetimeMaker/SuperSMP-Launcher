package com.example.launcher;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ModpackDownloader {
    public static void downloadModpack(String modpackUrl, String targetPath) {
        try {
            FileUtils.copyURLToFile(new URL(modpackUrl), new File(targetPath));
            System.out.println("Modpack downloaded to " + targetPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}