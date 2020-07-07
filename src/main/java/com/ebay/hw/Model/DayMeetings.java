package com.ebay.hw.Model;

import java.util.ArrayList;

public class DayMeetings {
    ArrayList<NewMeeting> meetings;
    double totalDurations;

    public DayMeetings() {
        this.meetings = new ArrayList<>();
        this.totalDurations = 0;
    }

    public DayMeetings(int total, ArrayList<NewMeeting> listOfMeeting) {
        this.totalDurations = total;
        this.meetings = listOfMeeting;
    }

    public ArrayList<NewMeeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(ArrayList<NewMeeting> meetings) {
        this.meetings = meetings;
    }

    public double getTotalDurations() {
        return totalDurations;
    }

    public void setTotalDurations(double totalDurations) {
        this.totalDurations = totalDurations;
    }
}
