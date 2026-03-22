package ca.admin.delivermore.collector.version;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class CollectorVersionInfo {

    private static final String VERSION = buildVersion();

    private CollectorVersionInfo() {
    }

    public static String getVersion() {
        return VERSION;
    }

    private static String buildVersion() {
        String major = readProperty("collector-version.properties", "version.major", "0");
        String minor = readProperty("collector-version.properties", "version.minor", "0");
        String micro = readProperty("collector-git.properties", "git.total.commit.count", "0");

        return major + "." + minor + "." + normalizeMicro(micro);
    }

    private static String normalizeMicro(String micro) {
        return micro != null && micro.matches("\\d+") ? micro : "0";
    }

    private static String readProperty(String resourceName, String key, String defaultValue) {
        Properties properties = new Properties();
        try (InputStream in = CollectorVersionInfo.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null) {
                return defaultValue;
            }
            properties.load(in);
            return properties.getProperty(key, defaultValue);
        } catch (IOException ex) {
            return defaultValue;
        }
    }
}
