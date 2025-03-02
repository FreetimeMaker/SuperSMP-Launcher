package com.freetime.launcher;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LauncherGUI {
    private static JTextField modpackIdField;
    private static JTextField modpackPathField;
    private static JTextField apiKeyField;
    private static JTextArea outputArea;

    public static void launch() {
        JFrame frame = new JFrame("Minecraft Modpack Launcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Modpack ID:"));
        modpackIdField = new JTextField();
        panel.add(modpackIdField);

        panel.add(new JLabel("Modpack Path:"));
        modpackPathField = new JTextField();
        panel.add(modpackPathField);

        panel.add(new JLabel("Modrinth API Key:"));
        apiKeyField = new JTextField();
        panel.add(apiKeyField);

        JButton downloadButton = new JButton("Download and Install");
        downloadButton.addActionListener(e -> downloadAndInstallModpack());
        panel.add(downloadButton);

        frame.add(panel, BorderLayout.CENTER);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        frame.setVisible(true);

        loadConfig();
    }

    private static void loadConfig() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
            modpackIdField.setText(properties.getProperty("modpack.id"));
            modpackPathField.setText(properties.getProperty("modpack.path"));
            apiKeyField.setText(properties.getProperty("modrinth.api.key"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void log(String message) {
        outputArea.append(message + "\n");
    }

    private static void downloadAndInstallModpack() {
        String modpackId = modpackIdField.getText();
        String modpackPath = modpackPathField.getText();
        String apiKey = apiKeyField.getText();

        Properties properties = new Properties();
        properties.setProperty("modpack.id", modpackId);
        properties.setProperty("modpack.path", modpackPath);
        properties.setProperty("modrinth.api.key", apiKey);

        try {
            properties.store(new FileOutputStream("src/main/resources/config.properties"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String userHome = System.getProperty("user.home");
        String minecraftPath = userHome + File.separator + "AppData" + File.separator + "Roaming" + File.separator + ".minecraft";

        log("Downloading modpack...");
        ModrinthApi.downloadModpack(modpackId, modpackPath);
        log("Installing modpack...");
        ModpackInstaller.installModpack(modpackPath, minecraftPath);
        log("Modpack installation complete.");
    }
}