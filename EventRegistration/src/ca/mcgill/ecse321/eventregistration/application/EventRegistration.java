package ca.mcgill.ecse321.eventregistration.application;

import ca.mcgill.ecse321.eventregistration.view.EventRegistrationPage;

public class EventRegistration {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new EventRegistrationPage().setVisible(true);
			}
		});
	}
}


