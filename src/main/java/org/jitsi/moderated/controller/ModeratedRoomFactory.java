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
package org.jitsi.moderated.controller;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.hash.Hashing;
import org.jitsi.moderated.Config;
import org.jitsi.moderated.Constants;
import org.jitsi.moderated.jwt.*;
import org.jitsi.moderated.model.JoinInfo;
import org.jitsi.moderated.model.ModeratedRoom;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ModeratedRoomFactory {
    private static final ModeratedRoomFactory FACTORY = new ModeratedRoomFactory();
    private final Algorithm algorithm;

    private ModeratedRoomFactory() {
        this.algorithm = this.initAlgorithm();
    }

    public static ModeratedRoomFactory factory() {
        return FACTORY;
    }

    public JoinInfo getJoinInfo(ModeratedRoom room) throws NoSuchAlgorithmException, MalformedURLException {
        String deployment = Config.getDeploymentUrl();
        String deploymentHost = new URL(deployment).getHost();

        String roomName = Hashing.sha256().hashString(room.getMeetingId(), StandardCharsets.UTF_8).toString();
        String tenant = Config.getTargetTenant();

        Map<String, Object> context = new HashMap<>();

        JWTCreator.Builder builder = JWT.create()
            .withIssuer(Constants.JWT_ISSUER)
            .withSubject(tenant != null ? tenant : deploymentHost)
            .withAudience(Constants.JWT_AUDIENCE)
            .withKeyId(Config.getPrivateKeyId())
            .withClaim(Constants.JWT_CLAIM_ROOM, roomName);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserInfo) {
            UserInfo info = (UserInfo) auth.getPrincipal();

            builder
                .withClaim(Constants.JWT_CLAIM_USER_ID, info.getUid())
                .withClaim(Constants.JWT_CLAIM_NAME, info.getName())
                .withClaim(Constants.JWT_CLAIM_EMAIL, info.getEmail())
                .withClaim(Constants.JWT_CLAIM_PICTURE, info.getPicture());
        } else {
            if (tenant != null) {
                context.put(Constants.JWT_CLAIM_CONTEXT_GROUP, tenant);
            }
        }

        if (!context.isEmpty()) {
            builder.withClaim(Constants.JWT_CLAIM_CONTEXT, context);
        }

        String token = builder.sign(this.algorithm);

        StringBuilder baseUrl = new StringBuilder(deployment);

        if (!deployment.endsWith("/")) {
            baseUrl.append("/");
        }

        if (tenant != null) {
            baseUrl.append(tenant).append("/");
        }

        baseUrl.append(roomName);

        return new JoinInfo(roomName, baseUrl.toString(), baseUrl
                .append("?")
                .append(Constants.JWT_URL_PARAM_NAME)
                .append("=")
                .append(token)
                .toString());
    }

    public ModeratedRoom getModeratedRoom() {
        // We use double uuid for aesthetics reasons: to make it 64 byte long, same as the sha hash of the
        // join link.
        String meetingId = new StringBuilder(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString()
                .replaceAll("-", "");

        return new ModeratedRoom(meetingId);
    }

    private Algorithm initAlgorithm() {
        try {
            PKCS8EncodedKeySpec spec =  new PKCS8EncodedKeySpec(
                    Files.readAllBytes(Paths.get(Config.getPrivateKeyFileName()))
            );
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey)keyFactory.generatePrivate(spec);
            return Algorithm.RSA256(null, privateKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
