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
package org.jitsi.moderated.jwt;

import com.auth0.jwt.interfaces.*;
import org.apache.commons.logging.*;
import org.jitsi.moderated.*;
import org.springframework.boot.json.*;
import org.springframework.util.*;

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.nio.charset.*;
import java.security.cert.*;
import java.security.interfaces.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Downloads a list of public files from a URL that will return json file with a mapping between kids and the keys.
 * If the response Cache-Control header exists we will respect it and refresh it.
 */
public class JsonKeyProvider implements RSAKeyProvider {
    private final Log logger = LogFactory.getLog(this.getClass());

    private Map<String, RSAPublicKey> keys = new HashMap<>();

    ScheduledExecutorService updater = Executors.newScheduledThreadPool(1);

    public JsonKeyProvider() {
        update();
    }

    private void update() {
        try {
            String url = Config.getJwtKeysCacheUrl();

            if (url == null) {
                return;
            }

            logger.info("Updating keys from: " + url);

            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(20))
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String cacheHeader = response.headers().firstValue("cache-control").orElse(null);
            String maxAge = "";
            if (cacheHeader != null) {
                maxAge = Arrays.stream(cacheHeader.split(",")).filter(p -> p.trim().startsWith("max-age"))
                        .findFirst().orElse("");
                maxAge = maxAge.replace("max-age=", "").trim();
            }

            long updateInSec = -1;
            if (StringUtils.hasText(maxAge)) {
                updateInSec = Long.parseLong(maxAge);

                // let's schedule new update 60 seconds before the cache expiring
                if (updateInSec > 60) {
                    logger.info("Scheduling update of keys for " + (updateInSec - 60) + " seconds.");
                    updater.schedule(this::update, updateInSec - 60, TimeUnit.SECONDS);
                }
            }

            if (response.statusCode() == 200) {
                JsonParserFactory.getJsonParser().parseMap(response.body())
                        .forEach((k,v) -> {
                            try {
                                CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
                                InputStream in = new ByteArrayInputStream(((String)v).getBytes(StandardCharsets.UTF_8));
                                X509Certificate certificate = (X509Certificate)certFactory.generateCertificate(in);

                                keys.put(k, (RSAPublicKey) certificate.getPublicKey());
                            } catch (Exception e) {
                                logger.error("Error parsing public key", e);
                            }
                        });
            }
        } catch (IOException|InterruptedException e) {
            logger.error("Error obtaining public keys", e);
        }
    }

    @Override
    public RSAPublicKey getPublicKeyById(String keyId) {

        return keys.get(keyId);
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return null;
    }

    @Override
    public String getPrivateKeyId() {
        return null;
    }
}
