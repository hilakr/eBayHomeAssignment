package com.ebay.hw.Storage;

import com.ebay.hw.Model.DayMeetings;
import com.ebay.hw.Model.MeetingCoordinator;
import com.ebay.hw.Model.NewMeeting;
import com.ebay.hw.Utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;

@Component
public class MeetingStoreImpl implements MeetingStore {

    private static final Logger logger = LoggerFactory.getLogger(MeetingStoreImpl.class);

    MeetingCoordinator coordinate;
    Utils utils;

    public MeetingStoreImpl(MeetingCoordinator coordinator) {
        this.coordinate = coordinator;
        this.utils = new Utils();
    }


    public String save(NewMeeting meeting) {
        try {
            if (!coordinate.getCalender().containsKey(utils.getDate(meeting.getFromTime()))) {
                this.coordinate.getCalender().put(utils.getDate(meeting.getFromTime()), new DayMeetings());
            }
            this.coordinate.getCalender().get(utils.getDate(meeting.getFromTime())).getMeetings().add(meeting);
            increaseDurationMeetings(meeting);
            return "Meeting successfully added";
        } catch (Exception e) {
            logger.error("There was an error and the meeting was not saved. error message: " + e.getMessage());
            return "There was an error and the meeting was not saved";
        }
    }

    private void increaseDurationMeetings(NewMeeting meeting) {
        this.coordinate.getCalender().get(utils.getDate(meeting.getFromTime())).
                setTotalDurations(utils.getDurationOfDatesInHours(utils.convertStringToDate(meeting.getFromTime()),
                        utils.convertStringToDate(meeting.getToTime())) + this.coordinate.getCalender().get(utils.getDate(meeting.getFromTime())).getTotalDurations());
    }

    private void decreaseDurationMeetings(NewMeeting meeting) {
        this.coordinate.getCalender().get(utils.getDate(meeting.getFromTime())).
                setTotalDurations(utils.getDurationOfDatesInHours(utils.convertStringToDate(meeting.getFromTime()),
                        utils.convertStringToDate(meeting.getToTime())) - this.coordinate.getCalender().get(utils.getDate(meeting.getFromTime())).getTotalDurations());
    }

    public String removeMeetingByTime(String fromTime) {
        try {
            if (this.coordinate.getCalender().size() > 0) {
                if (this.coordinate.getCalender().containsKey(utils.getDate(fromTime))) {
                    for (NewMeeting meeting : this.coordinate.getCalender().get(utils.getDate(fromTime)).getMeetings()) {
                        if (meeting.getFromTime().equals(fromTime)) {
                            this.coordinate.getCalender().get(utils.getDate(fromTime)).getMeetings().remove(meeting);
                            decreaseDurationMeetings(meeting);
                            return "Meeting successfully removed";
                        }
                    }
                }
            }
            return "The meeting does not exist in the calendar, meeting was not removed.";
        } catch (Exception e) {
            return "There was an error and the meeting was not removed. error message: " + e.getMessage();
        }

    }

    @Override
    public NewMeeting getNextMeeting() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate fromNow = now.toLocalDate();
        double nextMeetingTime = 40;
        NewMeeting nextMeeting = null;
        try {
            if (this.coordinate.getCalender().size() > 0) {
                for (LocalDate localDate : this.coordinate.getCalender().keySet()) {
                    if (localDate.isEqual(fromNow) || localDate.isAfter(fromNow)) {
                        for (NewMeeting meeting : this.coordinate.getCalender().get(localDate).getMeetings()) {
                            if (utils.convertStringToDate(meeting.getFromTime()).isAfter(now)) {
                                if (utils.getDurationOfDatesInHours(utils.convertStringToDate(meeting.getFromTime()), now) < nextMeetingTime)
                                    nextMeetingTime = utils.getDurationOfDatesInHours(utils.convertStringToDate(meeting.getFromTime()), now);
                                nextMeeting = meeting;
                            }
                        }

                    }
                }

            }
            if (nextMeeting != null)
                return nextMeeting;
            else
                return new NewMeeting("", "", "", "No next meeting found");
        } catch (Exception e) {
            logger.error("An error occurred while trying to receive the next meeting. error message: " + e.getMessage());
            return new NewMeeting("", "", "", "An error occurred while trying to receive the next meeting");
        }
    }

    public String removeMeetingByTitle(String title) {
        int count = 0;
        try {
            if (this.coordinate.getCalender().size() > 0) {
                for (DayMeetings dayMeetings : this.coordinate.getCalender().values()) {
                    Iterator ite = dayMeetings.getMeetings().iterator();
                    while (ite.hasNext()) {
                        NewMeeting meeting = (NewMeeting) ite.next();
                        if (meeting.getTitleMeeting().equals(title)) {
                            count++;
                            ite.remove();
                            decreaseDurationMeetings(meeting);
                        }
                    }
                }
            }
            return "meetings with title: " + title + " successfully removed";
        } catch (Exception e) {
            logger.error("An error occurred while trying to remove meetings by title. error message: " + e.getMessage());
            return "An error occurred while trying to remove meetings by title. error message";
        }
    }

}
