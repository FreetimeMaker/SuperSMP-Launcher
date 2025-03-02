import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MinecraftLauncher {
    public static void main(String[] args) {
        try {
            // Pfad zu Ihrer Minecraft-Installation
            String minecraftPath = "C:\\Pfad\\zu\\Minecraft";

            // Erstellen Sie die Befehlsliste
            List<String> command = new ArrayList<>();
            command.add("java");
            command.add("-Xmx1024M"); // Maximale RAM-Nutzung
            command.add("-Xms512M");  // Minimale RAM-Nutzung
            command.add("-jar");
            command.add(minecraftPath + "\\minecraft_server.jar");
            command.add("nogui");

            // Befehl ausführen
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(new java.io.File(minecraftPath));
            Process process = processBuilder.start();

            // Ausgabe des Prozesses lesen
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Prozess beenden
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}