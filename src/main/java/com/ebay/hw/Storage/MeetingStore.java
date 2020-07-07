package com.ebay.hw.Storage;

import com.ebay.hw.Model.NewMeeting;

public interface MeetingStore {

    public String save(NewMeeting meeting);
    public String removeMeetingByTime(String fromTime);
    public String removeMeetingByTitle(String title);
    public NewMeeting getNextMeeting();
}
