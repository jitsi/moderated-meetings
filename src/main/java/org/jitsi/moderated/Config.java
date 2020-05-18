package org.jitsi.moderated;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

public class Config {
    private static final Config INSTANCE = new Config();
    private final Properties properties = new Properties();

    private final static Logger logger = Logger.getLogger(Config.class.getName());

    private Config() {
        try (InputStream is = Config.class.getResourceAsStream("/config.properties")) {
            this.properties.load(is);
        } catch (IOException e) {
            logger.severe("Error loading config. Maybe you forgot to create a config file in resources/config.properties.");
            throw new RuntimeException(e);
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public String getDeploymentUrl() throws MalformedURLException {
        return new URL(this.properties.getProperty("deployment.url")).toString();
    }

    public String getPrivateKeyFileName() {
        return this.properties.getProperty("private.key.file");
    }

    public String getPrivateKeyId() {
        return this.properties.getProperty("private.key.id");
    }

    public int getServerport() {
        return Integer.parseInt(this.properties.getProperty("server.port", "8080"));
    }

    public String getTargetTenant() {
        return this.properties.getProperty("target.tenant");
    }
}
