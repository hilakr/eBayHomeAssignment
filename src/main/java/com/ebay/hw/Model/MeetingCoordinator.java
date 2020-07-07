package com.ebay.hw.Model;

import com.ebay.hw.Model.DayMeetings;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;

@Component
public class MeetingCoordinator {
    HashMap<LocalDate, DayMeetings> calender;

    public MeetingCoordinator(HashMap<LocalDate, DayMeetings> calender) {
        this.calender = calender;
    }


    public HashMap<LocalDate, DayMeetings> getCalender() {
        return calender;
    }

    public void setCalender(HashMap<LocalDate, DayMeetings> calender) {
        this.calender = calender;
    }
}
