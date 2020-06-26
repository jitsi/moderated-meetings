package org.jitsi.moderated.model;

import com.google.common.base.CaseFormat;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

public class ClientConfig {

    private final static Logger LOGGER = Logger.getLogger(ClientConfig.class.getName());
    private static ClientConfig INSTANCE;

    // Analytics
    private String amplitudeKey;

    // Social
    private String fbLink;
    private String githubLink;
    private String linkedInLink;
    private String twitterLink;

    // Stores
    private String appStoreLink;
    private String fdriodLink;
    private String playStoreLink;

    private ClientConfig() { }

    public String getAmplitudeKey() {
        return amplitudeKey;
    }

    public String getFbLink() {
        return fbLink;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public String getLinkedInLink() {
        return linkedInLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public String getAppStoreLink() {
        return appStoreLink;
    }

    public String getFdriodLink() {
        return fdriodLink;
    }

    public String getPlayStoreLink() {
        return playStoreLink;
    }

    /**
     * Initializer method for the singleton client config. This method tries to read all required env vars and assigns
     * them to fields in this class by doing proper camel/underscore case conversion (e.g. reads FB_LINK and
     * assigns to fbLink).
     *
     * @return The initiated instance of ClientConfig.
     */
    public static ClientConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientConfig();
            Field[] declaredFields = ClientConfig.class.getDeclaredFields();
            for (Field field: declaredFields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    String envVarName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, field.getName());
                    String envVarValue = System.getenv(envVarName);

                    if (envVarValue != null) {
                        try {
                            switch (field.getType().getName()) {
                                case "java.lang.Integer":
                                    field.set(INSTANCE, Integer.parseInt(envVarValue));
                                    break;
                                case "java.lang.Double":
                                    field.set(INSTANCE, Double.parseDouble(envVarValue));
                                    break;
                                default:
                                    field.set(INSTANCE, envVarValue);
                            }
                        } catch (IllegalAccessException e) {
                            LOGGER.severe("Error setting client config value: " + envVarName + "->" + field.getName());
                        }
                    } else {
                        LOGGER.warning("No value for client config " + field.getName() + " (" + envVarName + ")");
                    }
                }
            }
        }

        return INSTANCE;
    }
}