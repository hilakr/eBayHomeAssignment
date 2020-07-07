package com.ebay.hw.Model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

public class Meeting {
    String fromTime;
    String toTime;
    String titleMeeting;
    String status;

    public Meeting(String fromTime, String toTime, String titleMeeting) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        if (titleMeeting == null)
            this.titleMeeting = "no title";
        else this.titleMeeting = titleMeeting;
        this.status = "no status";

    }

    public String getFromTime() {
        return fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public String getTitleMeeting() {
        return titleMeeting;
    }

    public String getStatus() {
        return status;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public void setTitleMeeting(String titleMeeting) {
        this.titleMeeting = titleMeeting;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
