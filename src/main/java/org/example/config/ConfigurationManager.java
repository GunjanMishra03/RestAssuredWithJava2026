package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;

public final class ConfigurationManager {
    private static final String ENVIRONMENT = System.getProperty("test.environment", "qa")
            .trim().toLowerCase(Locale.ROOT);
    private static final Properties PROPERTIES = loadProperties();

    private ConfigurationManager() {
    }

    public static String baseUrl() {
        String override = System.getProperty("api.baseUrl");
        if (StringUtils.isBlank(override)) {
            override = System.getenv("REST_BASE_URL");
        }
        String baseUrl = StringUtils.defaultIfBlank(override, PROPERTIES.getProperty("api.baseUrl"));
        if (StringUtils.isBlank(baseUrl)) {
            throw new IllegalStateException("api.baseUrl is missing for environment '" + ENVIRONMENT + "'");
        }
        return baseUrl.trim();
    }

    public static long responseTimeThresholdMillis() {
        String value = PROPERTIES.getProperty("api.responseTimeThresholdMs");
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException exception) {
            throw new IllegalStateException("Invalid api.responseTimeThresholdMs for environment '"
                    + ENVIRONMENT + "': " + value, exception);
        }
    }

    private static Properties loadProperties() {
        String resource = "config/" + ENVIRONMENT + ".properties";
        Properties properties = new Properties();
        try (InputStream input = ConfigurationManager.class.getClassLoader().getResourceAsStream(resource)) {
            if (input == null) {
                throw new IllegalStateException("Configuration file not found: " + resource);
            }
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read configuration file: " + resource, exception);
        }
    }
}
