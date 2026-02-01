package org.learn.di;

import org.learn.di.error.ApplicationPropertiesLoadError;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApplicationProperties {
    private final HashMap<String, String> properties;

    private ApplicationProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }

    public String get(String value) {
        return this.properties.get(value);
    }

    static ApplicationProperties load() {
        List<String> lines = readLinesFromResourceFile("app.properties");
        HashMap<String, String> properties = parsLines(lines);
        return new ApplicationProperties(properties);
    }

    private static HashMap<String, String> parsLines(List<String> lines) {
        HashMap<String, String> properties = new HashMap<>();
        if (lines.isEmpty()) {
            System.out.println("No properties found in app.properties");
            return properties;
        }

        lines.stream()
                .map(line -> line.trim())
                .filter(line -> isValidLine(line))
                .forEach(line -> setProperties(line, properties));

        return properties;
    }

    private static void setProperties(String line, HashMap<String, String> properties) {
        String[] split = line.split("=");
        if (split.length != 2) {
            throw new ApplicationPropertiesLoadError("Invalid Property format : " + line);
        }

        String key = split[0].trim();
        String value = split[1].trim();

        if (key.isEmpty() || value.isEmpty()) {
            throw new ApplicationPropertiesLoadError("Invalid Property format : " + line);
        }

        properties.put(key, value);
    }

    private static boolean isValidLine(String line) {
        return !(line.isEmpty() || line.startsWith("#"));
    }

    private static List<String> readLinesFromResourceFile(String source) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(source);
        if (resource == null) {
            System.out.println("Unable to find resource file " + source);
            return new ArrayList<>();
        }

        try {
            Path path = Paths.get(resource.toURI());
            return Files.readAllLines(path);
        } catch (URISyntaxException | IOException e) {
            System.out.println("Unable to read resource file " + source + " : " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
