package com.nc.utils;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties configProp = new Properties();
    private static Properties envProp = new Properties();
    private static Properties locatorProp = new Properties();

    static {
        try {
            // Load config.properties dari classpath
            InputStream configFis = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
            InputStream envFis = ConfigReader.class.getClassLoader().getResourceAsStream("env.properties");
            InputStream locatorFis = ConfigReader.class.getClassLoader().getResourceAsStream("locators.properties");

            if (configFis == null) {
                throw new RuntimeException("❌ File config.properties tidak ditemukan di classpath.");
            }

            if (envFis == null) {
                throw new RuntimeException("❌ File env.properties tidak ditemukan di classpath.");
            }

            configProp.load(configFis);
            envProp.load(envFis);
            if (locatorFis != null) {
                locatorProp.load(locatorFis);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return configProp.getProperty(key);
    }

    public static String getBaseUrl() {
        String env = getProperty("env");
        return envProp.getProperty(env + ".url");
    }

    public static String getBrowser() {
        return System.getProperty("browser", configProp.getProperty("browser"));
    }

    public static String getLocator(String key) {
        return locatorProp.getProperty(key);
    }
}
