package com.ebay.hw.Model;

import java.time.LocalDateTime;

public class NewMeeting {
    String fromTime;
    String toTime;
    String titleMeeting;
    String status;



    public NewMeeting(String fromTime, String toTime, String titleMeeting, String status) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.titleMeeting = titleMeeting;
        this.status = status;
    }

    public NewMeeting(Meeting  meeting) {
        this.fromTime = meeting.fromTime;
        this.toTime = meeting.toTime;
        this.titleMeeting = meeting.titleMeeting;
        this.status = "no status";
    }

    public NewMeeting(String fromTime, String toTime, String titleMeeting) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.titleMeeting = titleMeeting;
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
}
