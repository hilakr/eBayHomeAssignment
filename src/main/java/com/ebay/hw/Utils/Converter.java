package com.ebay.hw.Utils;

import com.ebay.hw.Model.Meeting;
import com.ebay.hw.Model.NewMeeting;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public NewMeeting convertToNewMeeting (Meeting meeting){
        return new NewMeeting(meeting.getFromTime(), meeting.getToTime(), meeting.getTitleMeeting(), meeting.getStatus());
    }
}
