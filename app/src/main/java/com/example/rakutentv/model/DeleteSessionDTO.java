package com.example.rakutentv.model;

import com.google.gson.annotations.SerializedName;

public class DeleteSessionDTO {

    @SerializedName("session_id")
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
