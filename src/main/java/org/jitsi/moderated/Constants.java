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

public abstract class Constants {
    // JWT segments
    public static final String JWT_AUDIENCE = "jitsi";
    public static final String JWT_CLAIM_CONTEXT = "context";
    public static final String JWT_CLAIM_CONTEXT_GROUP = "group";
    public static final String JWT_CLAIM_USER_ID = "user_id";
    public static final String JWT_CLAIM_ROOM = "room";
    public static final String JWT_CLAIM_NAME = "name";
    public static final String JWT_CLAIM_PICTURE= "picture";
    public static final String JWT_CLAIM_EMAIL = "email";
    public static final String JWT_ISSUER = "jitsi";

    // URL param for token
    public static final String JWT_URL_PARAM_NAME = "jwt";
}
