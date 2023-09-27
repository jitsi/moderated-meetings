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
package org.jitsi.moderated.model;

public class JoinInfo {

    private final String joinUrl;
    private final String moderatorUrl;
    private final String roomName;

    public JoinInfo(String roomName, String joinUrl, String moderatorUrl) {
        this.roomName = roomName;
        this.joinUrl = joinUrl;
        this.moderatorUrl = moderatorUrl;
    }

    public String getJoinUrl() {
        return joinUrl;
    }

    public String getModeratorUrl() {
        return moderatorUrl;
    }

    public String getRoomName() {
        return roomName;
    }
}
