package org.jitsi.moderated.model;

public class ModeratedRoom {
    private final String meetingId;

    public ModeratedRoom(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingId() {
        return meetingId;
    }
}
