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
