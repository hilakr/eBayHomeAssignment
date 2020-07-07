package com.ebay.hw;

import com.ebay.hw.Model.DayMeetings;
import com.ebay.hw.Model.MeetingCoordinator;
import com.ebay.hw.Model.NewMeeting;
import com.ebay.hw.Storage.MeetingStoreImpl;
import com.ebay.hw.Utils.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MeetingStoreImplTest {
    public static HashMap<LocalDate, DayMeetings> calender = new HashMap<>();
    public static MeetingCoordinator coordinate = new MeetingCoordinator(calender);
    public static MeetingStoreImpl store =  new MeetingStoreImpl(coordinate);
    public static Utils utils;


    @BeforeAll
    public static void init (){
        NewMeeting meeting1 = new NewMeeting("06/07/2020 02:00", "06/07/2020 04:00", "meeting");
        NewMeeting meeting2 = new NewMeeting("06/07/2020 04:00", "06/07/2020 06:00", "meeting");
        NewMeeting meeting3 = new NewMeeting("06/07/2020 10:00", "06/07/2020 11:00", "meeting3");
        ArrayList<NewMeeting> listOfMeeting = new ArrayList<>();
        listOfMeeting.add(meeting1);
        listOfMeeting.add(meeting2);
        listOfMeeting.add(meeting3);
        coordinate.getCalender().put(LocalDate.of(2020,7,6),new DayMeetings(5, listOfMeeting));

    }
    @Test
    void save() {
        store.save(new NewMeeting("07/07/2020 08:00", "07/07/2020 10:00", "meeting4"));

        assertEquals (coordinate.getCalender().size(), 2);
        assertEquals (coordinate.getCalender().get(LocalDate.of(2020,07,07)).getMeetings().size(), 1);
        assertEquals (coordinate.getCalender().get(LocalDate.of(2020,07,07)).getTotalDurations(), 2.0);
    }

    @Test
    void removeMeetingByTime() {
        store.removeMeetingByTime("07/07/2020 08:00");

        assertEquals (coordinate.getCalender().size(), 2);
        assertEquals (coordinate.getCalender().get(LocalDate.of(2020,07,07)).getMeetings().size(), 0);
        assertEquals (coordinate.getCalender().get(LocalDate.of(2020,07,07)).getTotalDurations(), 0.0);


        String test = store.removeMeetingByTime("08/07/2020 04:00");

        assertEquals(test, "The meeting does not exist in the calendar, meeting was not removed.");
    }

    @Test
    void removeMeetingByTitle() {
        String count = store.removeMeetingByTitle("meeting");

        assert(count.contains("2"));
        assertEquals(coordinate.getCalender().get(LocalDate.of(2020,07,06)).getMeetings().size(), 1);
        assertEquals(coordinate.getCalender().get(LocalDate.of(2020,07,06)).getMeetings().get(0).getTitleMeeting(), "meeting3");
        assertEquals(coordinate.getCalender().get(LocalDate.of(2020,07,06)).getTotalDurations(), 1.0);

    }

    @Test
    void getNextMeeting() {
        NewMeeting meeting = new NewMeeting("07/07/2020 08:00", "07/07/2020 10:00", "meeting4");
        store.save(meeting);

        NewMeeting nextMeetingTest = store.getNextMeeting();

        assertEquals(nextMeetingTest.getTitleMeeting(), "meeting4");
    }


}