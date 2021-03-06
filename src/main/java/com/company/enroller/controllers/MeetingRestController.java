package com.company.enroller.controllers;

import java.net.URISyntaxException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

	@Autowired
	MeetingService meetingService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetings() {
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
	     Meeting meeting = meetingService.findById(id);
	     if (meeting == null) {
	         return new ResponseEntity(HttpStatus.NOT_FOUND);
	     }
	     return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	 }
		
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {
        Meeting addedMeeting = meetingService.findByTitle(meeting.getTitle());
        if (addedMeeting != null) {
            return new ResponseEntity<>(
            		"Unable to create. A meeting named " + 
            meeting.getTitle() + 
            " already exist.", 
            HttpStatus.CONFLICT);
        }

        meetingService.add(meeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
    }
	 
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<?> deleteMeeting(@PathVariable("id") long id) throws URISyntaxException {
		     Meeting meeting = meetingService.findById(id);
		     if (meeting == null) {
		         return new ResponseEntity(HttpStatus.NOT_FOUND);
		     }
		     meetingService.delete(meeting);
		     return new ResponseEntity(HttpStatus.GONE);
		 }

//	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
//	public ResponseEntity<?> updateMeeting(@PathVariable("id") long id, @RequestBody Meeting dmeeting) throws URISyntaxException {
//	     Meeting meeting = meetingService.findById(id);
//	     if (meeting == null) {
//	         return new ResponseEntity(HttpStatus.NOT_FOUND);
//	     }
//	     meetingService.update(meeting);
//	     return new ResponseEntity(HttpStatus.OK);
//	 }

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> addMeetingParticipant(@PathVariable("id") long id,
			@RequestBody Participant participant) throws URISyntaxException {
	     Meeting meeting = meetingService.findById(id);
	     if (meeting == null) {
	         return new ResponseEntity(HttpStatus.NOT_FOUND);
	     }
	     meeting.addParticipant(participant);
	     meetingService.update(meeting);
	     return new ResponseEntity(HttpStatus.OK);
	 }	
	
    public Collection<Participant> getMeetingParticipants(long id) {
	    return meetingService.findById(id).getParticipants();
    }

}
