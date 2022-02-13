package com.georgeciachir.appforcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toMap;

@RestController
@RequestMapping
public class ConfigController {

    private static final Map<String, String> SETTINGS = new HashMap<>();
    private static final Map<String, String> SECRETS = new HashMap<>();

    private static final String APP_SETTINGS_PREFIX = "APP_SETTINGS";
    private static final String CONFIG_MAP_SETTINGS_PREFIX = "CONFIG_MAP_SETTINGS_";
    private static final String SECRETS_PREFIX = "SECRET_";

    @Value("${htmlLocation}")
    private String htmlLocation;

    @Value("${applicationSettingsLocation}")
    private String applicationSettingsLocation;

    @Value("${applicationSecretsLocation}")
    private String applicationSecretsLocation;

    @Value("${appDBPassword}")
    private String appDBPassword;

    @PostConstruct
    public void setUp() {
        SETTINGS.put("HTML_LOCATION", htmlLocation);
        SETTINGS.put("APP_SETTINGS_LOCATION", applicationSettingsLocation);
        SECRETS.put("APP_DB_PASSWORD", appDBPassword);
    }

    @GetMapping("/settings/{readMode}")
    public Map<String, String> displayAppSettings(@PathVariable ReadMode readMode) {
        switch (readMode) {
            case STATIC:
                return SETTINGS;
            case DYNAMIC:
                return readFromEnvironment(value -> value.startsWith(APP_SETTINGS_PREFIX) || value.startsWith(CONFIG_MAP_SETTINGS_PREFIX));
            case VOLUME:
                return readFromSettingsVolume(applicationSettingsLocation);
            default:
                throw new IllegalArgumentException(String.format("Settings read mode [%s] not implemented", readMode));
        }
    }

    @GetMapping("/secrets/{readMode}")
    public Map<String, String> displayAppSecrets(@PathVariable ReadMode readMode) {
        switch (readMode) {
            case STATIC:
                return SECRETS;
            case DYNAMIC:
                return readFromEnvironment(value -> value.startsWith(SECRETS_PREFIX));
            case VOLUME:
                return readFromSettingsVolume(applicationSecretsLocation);
            default:
                throw new IllegalArgumentException(String.format("Secrets read mode [%s] not implemented", readMode));
        }
    }

    private Map<String, String> readFromEnvironment(Predicate<String> filter) {
        return System.getenv().entrySet().stream()
                .filter(entry -> filter.test(entry.getKey()))
                .collect(toMap(Entry::getKey, Entry::getValue));
    }

    private Map<String, String> readFromSettingsVolume(String location) {
        Path settingsVolumeLocation = Paths.get(location);
        try {
            return Files.list(settingsVolumeLocation)
                    .filter(not(Files::isDirectory))
                    .collect(toMap(path -> path.getFileName().toString(), ConfigController::getFileContent));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFileContent(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    enum ReadMode {
        STATIC, DYNAMIC, VOLUME
    }
}
