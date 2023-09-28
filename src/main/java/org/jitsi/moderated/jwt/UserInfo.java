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

/**
 * Extracts user info from the token.
 */
public class UserInfo {
    private final String uid;
    private final String name;
    private final String picture;
    private final String email;

    public UserInfo(DecodedJWT jwt) {
        this.uid = jwt.getClaim("user_id").asString();
        this.name = jwt.getClaim("name").asString();
        this.picture = jwt.getClaim("picture").asString();
        this.email = jwt.getClaim("email").asString();
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getEmail() {
        return email;
    }
}
