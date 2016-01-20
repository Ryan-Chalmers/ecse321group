package ca.mcgill.ecse321.eventregistration.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.Registration;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXStream;

public class TestEventRegistrationController {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.setFilename("test"+File.separator+"ca"+File.separator+"mcgill"+File.separator+"ecse321"+File.separator+"eventRegistration"+File.separator+"persistence"+File.separator+"data.xml");
		PersistenceXStream.setAlias("event", Event.class);
		PersistenceXStream.setAlias("participant", Participant.class);
		PersistenceXStream.setAlias("registraion", Registration.class);
		PersistenceXStream.setAlias("manager", Registration.class);
	}

	

	@After
	public void tearDown() throws Exception {
		// clear all registrations
		RegistrationManager rm = RegistrationManager.getInstance();
		rm.delete();
	}

	@Test
	public void testCreateParticipant() {
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0,rm.getParticipant().size());
		
		String name = "Oscar";
		
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createParticipant(name);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		checkResultsParticipant(name, rm);
		
		RegistrationManager rm2 = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();
		
		//check file contents
		checkResultsParticipant(name, rm2);
		

	}
	
	
	@Test
	public void testCreateParticipantNull() {
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getParticipant().size());
		
		String name = null;
		
		String error = null;
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createParticipant(name);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			
		}
		//check error
		assertEquals("Participant name cannot be empty!", error);
		
		//check no change in memory
		assertEquals(0, rm.getParticipant().size());
		
	}
	
	@Test
	public void testCreateParticipantEmpty() {
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getParticipant().size());
		
		String name = "";
		
		String error = null;
		EventRegistrationController erc = new EventRegistrationController();
		try { 
			erc.createParticipant(name);
		}catch (InvalidInputException e) {
			error = e.getMessage();
		}
		//check error
		assertEquals("Participant name cannot be empty!", error);
		
		//check no change in memory
		assertEquals(0, rm.getParticipant().size());
	}
	
	@Test
	public void testCreateParticipantSpace() {
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getParticipant().size());
		
		String name = "";
		
		String error = null;
		EventRegistrationController erc = new EventRegistrationController();
		try { 
			erc.createParticipant(name);
		}catch (InvalidInputException e) {
			error = e.getMessage();
		}
		//check error
		assertEquals("Participant name cannot be empty!", error);
				
		//check no change in memory
		assertEquals(0, rm.getParticipant().size());
	}
	
	@Test
	public void testCreateEvent() {
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getEvent().size());
		
		String name = "Soccer Game";
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.OCTOBER,16,9,00,0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2016, Calendar.OCTOBER,16,10,30,0);
		Time endTime = new Time(c.getTimeInMillis());
		
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.createEvent(name, eventDate, startTime, endTime);
		} catch (InvalidInputException e) {
			//check that no error occured
			fail();
		}
		//check model in memory
		checkResultEvent(name, eventDate, startTime, endTime, rm);
		
		RegistrationManager rm2 = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();
		
		//check file contents
		checkResultEvent(name, eventDate, startTime, endTime, rm2);
		
	}
	
	@Test
	public void testRegister() {
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0,rm.getRegistrations().size());
		
		String nameP = "Oscar";
		Participant participant = new Participant(nameP);
		rm.addParticipant(participant);
		assertEquals(1, rm.getParticipant().size());
		
		String nameE = "Soccer Game";
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.OCTOBER,16,9,00,0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2016, Calendar.OCTOBER,16,10,30,0);
		Time endTime = new Time(c.getTimeInMillis());
		Event event = new Event(nameE, eventDate, startTime, endTime);
		rm.addEvent(event);
		assertEquals(1, rm.getEvent().size());
		
		EventRegistrationController erc = new EventRegistrationController();
		try {
			erc.register(participant, event);
		} catch (InvalidInputException e) {
			//check that no error occured
			fail();
		}
		//check model in memory
		checkResultRegister(nameP,nameE, eventDate, startTime, endTime, rm);
				
		RegistrationManager rm2 = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();
				
		//check file contents
		checkResultRegister(nameP,nameE, eventDate, startTime, endTime, rm);
	}

	private void checkResultsParticipant(String name, RegistrationManager rm2) {
		assertEquals(1, rm2.getParticipant().size());
		assertEquals(name, rm2.getParticipant(0).getName());
		assertEquals(0, rm2.getEvent().size());
		assertEquals(0, rm2.getRegistrations().size());
	}
	
	private void checkResultEvent(String name, Date eventDate, Time startTime, Time endTime, RegistrationManager rm2) {
		
		assertEquals(0, rm2.getParticipant().size());
		assertEquals(1, rm2.getEvent().size());
		assertEquals(name, rm2.getEvent(0).getName());
		assertEquals(eventDate.toString(), rm2.getEvent(0).getEventDate().toString());
		assertEquals(startTime.toString(), rm2.getEvent(0).getStartTime().toString());
		assertEquals(endTime.toString(), rm2.getEvent(0).getEndTime().toString());
		assertEquals(0, rm2.getRegistrations().size());
		
		
	}
	
	private void checkResultRegister(String nameP, String nameE, Date eventDate, Time startTime, Time endTime,RegistrationManager rm2)
	{
		assertEquals(1, rm2.getParticipant().size());
		assertEquals(nameP, rm2.getParticipant(0).getName());
		assertEquals(1, rm2.getEvent().size());
		assertEquals(nameE, rm2.getEvent(0).getName());
		assertEquals(eventDate.toString(), rm2.getEvent(0).getEventDate().toString());
		assertEquals(startTime.toString(), rm2.getEvent(0).getStartTime().toString());
		assertEquals(endTime.toString(), rm2.getEvent(0).getEndTime().toString());
		assertEquals(1, rm2.getRegistrations().size());
		assertEquals(rm2.getEvent(0), rm2.getRegistration(0).getEvent());
		assertEquals(rm2.getParticipant(0), rm2.getRegistration(0).getParticipant());
	}

}
