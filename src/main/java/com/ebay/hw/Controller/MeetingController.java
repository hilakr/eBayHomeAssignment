package com.ebay.hw.Controller;

import com.ebay.hw.Model.Meeting;
import com.ebay.hw.Model.MeetingResponse;
import com.ebay.hw.MeetingService.MeetingService;
import com.ebay.hw.Model.NewMeeting;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController

public class MeetingController {
    MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping(value = "/setMeeting", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public NewMeeting setMeeting(@RequestBody Meeting meeting) {
        return meetingService.setMeeting(meeting);
    }

    @PostMapping(value = "/removeMeetingByTime")
    public MeetingResponse removeMeetingByTime(@RequestParam String fromTime){
        return meetingService.removeMeetingByTime(fromTime);
    }

    @DeleteMapping(value = "/remove/{title}")
    public MeetingResponse remove(@PathVariable(value = "title") String title){
        return meetingService.removeMeetingByTitle(title);
    }

    @GetMapping(value = "/getNextMeeting")
    public NewMeeting getNextMeeting(){
        return meetingService.getNextMeeting();
    }

    @GetMapping(value = "/print")
    public ArrayList<NewMeeting>  print() {
        return meetingService.print();
    }

}

