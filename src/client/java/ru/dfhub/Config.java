package ru.dfhub;




import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {

    private static JSONObject config = new JSONObject();

    public static void reload() {
        try {
            checkExists();
            config = new JSONObject(readConfig());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject getConfig() {
        return config;
    }

    private static void checkExists() throws IOException {
        File file = new File("config/mc-uptime.json");
        if (!file.exists()) {
            file.createNewFile();
            Files.writeString(file.toPath(), getDefaultConfig());
        }
    }

    private static String readConfig() throws IOException {
        return Files.readString(Path.of("config/mc-uptime.json"));
    }

    private static String getDefaultConfig() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(Config.class.getClassLoader().getResourceAsStream("config.json")));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
