package io.github.skippyall.vote.core.config;

import io.github.skippyall.vote.core.VoteServer;
import io.github.skippyall.vote.core.misc.EnvironmentVars;
import io.github.skippyall.vote.core.storage.StorageType;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Function;

public class Config {
    //Config values
    private static StorageType STORAGE_TYPE;

    public static StorageType getStorageType() {
        return STORAGE_TYPE;
    }


    public static boolean CENTRAL_SERVER_ENABLED;

    private static String HOST;

    private static String getHost() {
        return HOST;
    }

    private static int PORT;

    private static int getPort() {
        return PORT;
    }
    //Config values end

    private static final Properties defaults = new Properties();
    private static final Properties properties = new Properties(defaults);
    private static final File FILE = new File(EnvironmentVars.dataFolder, "config.properties");
    public static void load() {
        try {
            defaults.load(Config.class.getClassLoader().getResourceAsStream("default.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load default config", e);
        }
        try(FileReader reader = new FileReader(FILE)) {
            properties.load(reader);
        } catch (IOException e) {
            VoteServer.LOGGER.warn("Failed to load config", e);
        }

        defaults.forEach(properties::putIfAbsent);

        STORAGE_TYPE = tryGetOrDefault(StorageType::valueOf, "storage_type", "storage_type must be SQLite or MySQL");

        CENTRAL_SERVER_ENABLED = tryGetOrDefault(Boolean::getBoolean, "enable_central", "enable_central must be true or false");

        HOST = properties.getProperty("host");
        PORT = tryGetOrDefault(Integer::getInteger, "port", "port must be an integer");
    }

    public static void save() {
        try(FileWriter writer = new FileWriter(FILE)) {
            properties.store(writer, "Vote Configuration");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T tryGetOrDefault(Function<String, T> toTry, String key, String warnMessage){
        try {
            return toTry.apply(properties.getProperty(key));
        } catch (Exception e) {
            if(warnMessage != null) {
                VoteServer.LOGGER.warn(warnMessage, e);
            }
            return toTry.apply(defaults.getProperty(key));
        }
    }
}
