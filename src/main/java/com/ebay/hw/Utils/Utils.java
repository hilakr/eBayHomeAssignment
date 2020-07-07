package com.ebay.hw.Utils;

import com.ebay.hw.Model.NewMeeting;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Utils {

    public LocalDateTime convertStringToDate(String myDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return LocalDateTime.parse(myDate, formatter);

    }
    public LocalDate getDate(String fromTime) {
        return convertStringToDate(fromTime).toLocalDate();
    }


    public String meetingToString(NewMeeting meeting) {
        return "Meeting{" +
                "fromTime='" + meeting.getFromTime() + '\'' +
                ", toTime='" + meeting.getToTime() + '\'' +
                ", titleMeeting='" + meeting.getTitleMeeting() + '\'' +
                ", status='" + meeting.getStatus() + '\'' +
                '}';
    }

    public double getDurationOfDatesInHours(LocalDateTime fromTime, LocalDateTime toTime) {
        return (double) (Math.abs((ChronoUnit.MINUTES.between(fromTime, toTime))/ 60));
    }

    public long getTimeInMilli(LocalDateTime fromTime) {
        return fromTime.atZone(ZoneId.of("Asia/Jerusalem")).toInstant().toEpochMilli();
    }

}
