package org.jitsi.moderated.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.hash.Hashing;
import org.jitsi.moderated.Config;
import org.jitsi.moderated.Constants;
import org.jitsi.moderated.model.JoinInfo;
import org.jitsi.moderated.model.ModeratedRoom;

import java.net.MalformedURLException;
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
        String roomName = Hashing.sha256().hashString(room.getMeetingId(), StandardCharsets.UTF_8).toString();
        Map<String, String> context = new HashMap<>();
        String tenant = Config.getTargetTenant();
        context.put(Constants.JWT_CLAIM_CONTEXT_GROUP, tenant);

        String token = JWT.create()
                .withIssuer(Constants.JWT_ISSUER)
                .withSubject(tenant)
                .withAudience(Constants.JWT_AUDIENCE)
                .withKeyId(Config.getPrivateKeyId())
                .withClaim(Constants.JWT_CLAIM_ROOM, roomName)
                .withClaim(Constants.JWT_CLAIM_CONTEXT, context)
                .sign(this.algorithm);

        StringBuffer baseUrl = new StringBuffer(Config.getDeploymentUrl())
                .append(tenant)
                .append("/")
                .append(roomName);

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
