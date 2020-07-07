package com.ebay.hw.MeetingService;

import com.ebay.hw.Model.MeetingCoordinator;
import com.ebay.hw.Storage.MeetingStore;
import com.ebay.hw.Model.DayMeetings;
import com.ebay.hw.Model.Meeting;
import com.ebay.hw.Model.MeetingResponse;
import com.ebay.hw.Model.NewMeeting;
import com.ebay.hw.Utils.Converter;
import com.ebay.hw.Utils.Utils;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.logging.Logger;

import static java.time.DayOfWeek.SATURDAY;

@Service
public class MeetingService {

    private final static Logger logger = Logger.getLogger(MeetingService.class.getName());

    private final MeetingStore meetingStore;
    public final MeetingCoordinator coordinate;
    private final Converter converter;
    private Utils utils = new Utils();

    int HOURS_PER_DAY = 10;
    int HOURS_PER_WEEK = 40;

    public MeetingService(MeetingStore meetingStore, MeetingCoordinator coordinate, Converter converter) {
        this.meetingStore = meetingStore;
        this.coordinate = coordinate;
        this.converter = converter;
    }


    public NewMeeting setMeeting(Meeting meeting) {
        try {
            LocalDateTime fromTime = convertStringToDate(meeting.getFromTime());
            LocalDateTime toTime = convertStringToDate(meeting.getToTime());
            if (!isValidMeeting(meeting, fromTime, toTime))
                return converter.convertToNewMeeting(meeting);
            meetingStore.save(converter.convertToNewMeeting(meeting));
            meeting.setStatus("valid meeting created");
            return converter.convertToNewMeeting(meeting);
        } catch (DateTimeException e) {
            meeting.setStatus("Invalid date or time. please enter in the following format: dd/MM/yyyy HH:mm  - meeting declined");
            return converter.convertToNewMeeting(meeting);

        }
    }

    private Boolean isValidMeeting(Meeting meeting, LocalDateTime fromTime, LocalDateTime toTime) {
        if (isDayMeetingIsSaturday(meeting, fromTime, toTime)) return false;
        if (isMeetingDurationNotValid(meeting, fromTime, toTime)) return false;
        if (isOverlapping(meeting, fromTime, toTime)) return false;
        if (isExceeded(meeting, fromTime, toTime)) return false;
        return true;
    }

    private boolean isMeetingDurationNotValid(Meeting meeting, LocalDateTime fromTime, LocalDateTime toTime) {
        if (fromTime.getHour() == toTime.getHour()) {
            if (Math.abs(fromTime.getMinute() - toTime.getMinute()) < 15) {
                meeting.setStatus("A meeting cannot last for less than 15 minutes");
                return true;
            }
        } else {
            if (Math.abs(fromTime.getHour() - toTime.getHour()) > 2) {
                meeting.setStatus("A meeting cannot last for more than 2 hours");
                return true;
            }
        }
        return false;
    }

    private boolean isDayMeetingIsSaturday(Meeting meeting, LocalDateTime fromTime, LocalDateTime toTime) {
        if (DayOfWeek.of(fromTime.get(ChronoField.DAY_OF_WEEK)) == SATURDAY || DayOfWeek.of(toTime.get(ChronoField.DAY_OF_WEEK)) == SATURDAY) {
            meeting.setStatus("No meetings on Saturdays");
            return true;
        }
        return false;
    }

    LocalDateTime convertStringToDate(String myDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return LocalDateTime.parse(myDate, formatter);

    }

    public boolean isOverlapping(Meeting meeting, LocalDateTime fromTime, LocalDateTime toTime) {
        if (this.coordinate.getCalender().size() > 0) {
            if (this.coordinate.getCalender().containsKey(utils.getDate(meeting.getFromTime()))) {
                ArrayList<NewMeeting> dayMeetings = this.coordinate.getCalender().get(utils.getDate(meeting.getFromTime())).getMeetings();
                for (NewMeeting dayMeeting : dayMeetings)
                    if (fromTime.isBefore(convertStringToDate(dayMeeting.getToTime())) && convertStringToDate(dayMeeting.getFromTime()).isBefore(toTime)) {
                        meeting.setStatus("Two meetings cannot overlap");
                        return true;
                    }
            }
        }
        return false;
    }

    public boolean isExceeded(Meeting meeting, LocalDateTime fromTime, LocalDateTime toTime) {
        double count = 0;
        if (this.coordinate.getCalender().size() > 0) {
            if (this.coordinate.getCalender().containsKey(utils.getDate(meeting.getFromTime()))) {
                if (this.coordinate.getCalender().get(utils.getDate(meeting.getFromTime())).getTotalDurations() >= HOURS_PER_DAY) {
                    meeting.setStatus("meeting for the same day exceeding 10h");
                    return true;
                } else if (this.coordinate.getCalender().get(utils.getDate(meeting.getFromTime())).getTotalDurations()
                        + utils.getDurationOfDatesInHours(fromTime, toTime) > HOURS_PER_DAY) {
                    meeting.setStatus("meeting for the same day exceeding 10h");
                    return true;
                }
            }
        }
        for (DayMeetings dayMeetings : this.coordinate.getCalender().values()) {
            count += dayMeetings.getTotalDurations();
            if (count >= 40) {
                meeting.setStatus("meeting for the same week exceeding 40h");
                return true;
            }
        }
        if (count + utils.getDurationOfDatesInHours(fromTime, toTime) > HOURS_PER_WEEK) {
            meeting.setStatus("meeting for the same week exceeding 40h");
            return true;
        }
        return false;
    }

    public MeetingResponse removeMeetingByTime(String fromTime) {

        return new MeetingResponse(meetingStore.removeMeetingByTime(fromTime));
    }

    public MeetingResponse removeMeetingByTitle(String title) {

        return new MeetingResponse(meetingStore.removeMeetingByTitle(title));
    }

    public NewMeeting getNextMeeting() {
        return meetingStore.getNextMeeting();
    }

    public ArrayList<NewMeeting> print() {
        ArrayList<NewMeeting> allMeetings = new ArrayList<>();
        for (LocalDate localDate : this.coordinate.getCalender().keySet()) {
            for (NewMeeting meeting : this.coordinate.getCalender().get(localDate).getMeetings())
                allMeetings.add(meeting);
        }
        return allMeetings;
    }
}

