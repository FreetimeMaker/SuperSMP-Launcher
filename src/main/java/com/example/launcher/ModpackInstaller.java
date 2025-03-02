package com.example.launcher;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;

public class ModpackInstaller {
    public static void installModpack(String modpackPath, String minecraftPath) {
        try (InputStream is = new FileInputStream(modpackPath);
             ArchiveInputStream ais = new ArchiveStreamFactory().createArchiveInputStream("tar", is)) {
            TarArchiveEntry entry;
            while ((entry = (TarArchiveEntry) ais.getNextEntry()) != null) {
                File outputFile = new File(minecraftPath, entry.getName());
                if (entry.isDirectory()) {
                    if (!outputFile.exists() && !outputFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + outputFile);
                    }
                } else {
                    try (OutputStream os = new FileOutputStream(outputFile)) {
                        IOUtils.copy(ais, os);
                    }
                }
            }
            System.out.println("Modpack installed to " + minecraftPath);
        } catch (IOException | ArchiveException e) {
            e.printStackTrace();
        }
    }
}