package com.ebay.hw.MeetingService;

import com.ebay.hw.MeetingService.MeetingService;
import com.ebay.hw.Model.DayMeetings;
import com.ebay.hw.Model.Meeting;
import com.ebay.hw.Model.MeetingCoordinator;
import com.ebay.hw.Model.NewMeeting;
import com.ebay.hw.Storage.MeetingStoreImpl;
import com.ebay.hw.Utils.Converter;
import com.ebay.hw.Utils.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

class MeetingServiceTest {
    public static HashMap<LocalDate, DayMeetings> calender = new HashMap<>();
    public static MeetingCoordinator coordinate = new MeetingCoordinator(calender);
    public static MeetingStoreImpl store = new MeetingStoreImpl(coordinate);
    public static Utils utils = new Utils();
    public static Converter converter = new Converter();
    public static MeetingService meetingService = new MeetingService(store, coordinate, converter);


    @BeforeAll
    static void init() {
        NewMeeting meeting1 = new NewMeeting("06/07/2020 02:00", "06/07/2020 04:00", "meeting");
        NewMeeting meeting2 = new NewMeeting("06/07/2020 04:00", "06/07/2020 06:00", "meeting");
        NewMeeting meeting3 = new NewMeeting("06/07/2020 10:00", "06/07/2020 11:00", "meeting3");
        ArrayList<NewMeeting> listOfMeeting = new ArrayList<>();
        listOfMeeting.add(meeting1);
        listOfMeeting.add(meeting2);
        listOfMeeting.add(meeting3);
        coordinate.getCalender().put(LocalDate.of(2020,7,6), new DayMeetings(5, listOfMeeting));
    }

    @Test
    void isOverlapping() {
        Meeting meeting = new Meeting("06/07/2020 02:00", "06/07/2020 03:00", "test");
        assert(meetingService.isOverlapping(meeting, utils.convertStringToDate(meeting.getFromTime()), utils.convertStringToDate(meeting.getToTime())));

    }

    @Test
    void isExceeded() {
        NewMeeting meeting4 = new NewMeeting("06/07/2020 11:00", "06/07/2020 13:00", "test");
        NewMeeting meeting5 = new NewMeeting("06/07/2020 13:00", "06/07/2020 15:00", "test");
        store.save(meeting4);
        store.save(meeting5);
        Meeting meeting = new Meeting("06/07/2020 15:00", "06/07/2020 17:00", "test");

        assert(meetingService.isExceeded(meeting, utils.convertStringToDate(meeting.getFromTime()), utils.convertStringToDate(meeting.getToTime())));

        store.save(converter.convertToNewMeeting(meeting));

        assert(meetingService.isExceeded(meeting, utils.convertStringToDate(meeting.getFromTime()), utils.convertStringToDate(meeting.getToTime())));


    }
}