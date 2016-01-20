package ca.mcgill.ecse321.eventregistration.controller;

import static org.junit.Assert.*;

import java.io.File;

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

	private void checkResultsParticipant(String name, RegistrationManager rm2) {
		assertEquals(1, rm2.getParticipant().size());
		assertEquals(name, rm2.getParticipant(0).getName());
		assertEquals(0, rm2.getEvent().size());
		assertEquals(0, rm2.getRegistrations().size());
	}

}
