package com.ebay.hw.Model;

public class MeetingResponse {
    String status;

    public MeetingResponse(String description) {
        this.status = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
