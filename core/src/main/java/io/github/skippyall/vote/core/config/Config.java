package io.github.skippyall.vote.core.config;

import io.github.skippyall.vote.core.VoteCore;
import io.github.skippyall.vote.core.misc.EnvironmentVars;
import io.github.skippyall.vote.core.storage.StorageType;

import java.io.*;
import java.util.Properties;
import java.util.function.Function;

public class Config {
    //Config values
    private static StorageType STORAGE_TYPE;

    public static StorageType getStorageType() {
        return STORAGE_TYPE;
    }

    private static String HOST;

    public static String getHost() {
        return HOST;
    }

    private static int PORT;

    public static int getPort() {
        return PORT;
    }
    //Config values end

    private static final Properties defaults = new Properties();
    private static final Properties properties = new Properties(defaults);
    private static final File FILE = new File(EnvironmentVars.dataFolder, "config.properties");
    public static void load() {

        try (InputStream defaultStream = Config.class.getClassLoader().getResourceAsStream("default.properties")) {
            defaults.load(defaultStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load default config", e);
        }

        if(!FILE.exists()) {
            try(FileOutputStream writer = new FileOutputStream(FILE);
                    InputStream defaultStream = Config.class.getClassLoader().getResourceAsStream("default.properties")
            ) {
                defaultStream.transferTo(writer);
                writer.flush();
                System.out.println("Created config");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try(FileReader reader = new FileReader(FILE)) {
            properties.load(reader);
        } catch (IOException e) {
            VoteCore.LOGGER.warn("Failed to load config", e);
        }

        defaults.forEach(properties::putIfAbsent);

        STORAGE_TYPE = tryGetOrDefault(StorageType::valueOf, "storage_type", "storage_type must be SQLite, MySQL or Synced");

        HOST = properties.getProperty("host");
        PORT = tryGetOrDefault(Integer::parseInt, "port", "port must be an integer");
    }

    public static void save() {
        /*try(FileWriter writer = new FileWriter(FILE)) {
            properties.store(writer, "VoteCore Configuration");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }

    public static <T> T tryGetOrDefault(Function<String, T> toTry, String key, String warnMessage){
        try {
            return toTry.apply(properties.getProperty(key));
        } catch (Exception e) {
            if(warnMessage != null) {
                VoteCore.LOGGER.warn(warnMessage, e);
            }
            return toTry.apply(defaults.getProperty(key));
        }
    }
}
