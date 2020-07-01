package org.jitsi.moderated;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class Config {

    public static String getDeploymentUrl() throws MalformedURLException {
        return new URL(System.getenv("DEPLOYMENT_URL")).toString();
    }

    public static String getPrivateKeyFileName() {
        return System.getenv("PRIVATE_KEY_FILE");
    }

    public static String getPrivateKeyId() {
        return System.getenv("PRIVATE_KEY_ID");
    }

    public static int getServerPort() {
        return Integer.parseInt(System.getenv("PORT"));
    }

    public static String getTargetTenant() {
        return System.getenv("TARGET_TENANT");
    }
}
