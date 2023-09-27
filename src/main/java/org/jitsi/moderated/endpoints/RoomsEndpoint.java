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
package org.jitsi.moderated.endpoints;

import org.jitsi.moderated.controller.ModeratedRoomFactory;
import org.jitsi.moderated.model.JoinInfo;
import org.jitsi.moderated.model.ModeratedRoom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;

@RestController()
@RequestMapping("/rest/rooms")
public class RoomsEndpoint {

    @GetMapping()
    public ModeratedRoom getModeratedRoom() {
        return ModeratedRoomFactory.factory().getModeratedRoom();
    }

    @GetMapping("{meetingId}")
    public JoinInfo getJoinInfo(@PathVariable String meetingId) throws NoSuchAlgorithmException, MalformedURLException {
        return ModeratedRoomFactory.factory().getJoinInfo(new ModeratedRoom(meetingId));
    }
}
