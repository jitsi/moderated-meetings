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
