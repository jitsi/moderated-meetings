package org.jitsi.moderated;

public abstract class Constants {
    // JWT segments
    public static final String JWT_AUDIENCE = "jitsi";
    public static final String JWT_CLAIM_CONTEXT = "context";
    public static final String JWT_CLAIM_CONTEXT_GROUP = "group";
    public static final String JWT_CLAIM_ROOM = "room";
    public static final String JWT_ISSUER = "jitsi";

    // URL param for token
    public static final String JWT_URL_PARAM_NAME = "jwt";
}
