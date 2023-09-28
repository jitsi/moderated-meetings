/*
 * Moderated meetings.
 *
 * Copyright @ 2023 - present 8x8, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jitsi.moderated;

import org.springframework.boot.json.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

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
        String port = System.getenv("PORT");
        return port != null ? Integer.parseInt(port) : 8080;
    }

    public static String getTargetTenant() {
        return System.getenv("TARGET_TENANT");
    }

    public static String getJwtKeysCacheUrl() {
        return System.getenv("JWT_PUB_KEYS_CACHE_URL");
    }

    public static String getTokenAuthUrl() {
        return System.getenv("TOKEN_AUTH_URL");
    }

    public static Map<String, Object> getJwtVerify() {
        String verify = System.getenv("JWT_VERIFY");
        if (verify != null) {
            return JsonParserFactory.getJsonParser().parseMap(System.getenv("JWT_VERIFY"));
        }

        return new HashMap<>();
    }
}
