package ca.mcgill.ecse321.eventregistration.view;

import javax.activity.InvalidActivityException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationController;

public class EventRegistrationPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8062635784771606869L;
	
	//UI element
	private JTextField participantNameTextField;
	private JLabel participantNameLabel;
	private JButton addParticipantButton;
	
	public EventRegistrationPage() {
		initComponents();
		refreshData();
	}

	private void initComponents() {
		// TODO Auto-generated method stub
		participantNameTextField = new JTextField();
		participantNameLabel = new JLabel();
		addParticipantButton = new JButton();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Event Registration");
		
		participantNameLabel.setText("Name:");
		addParticipantButton.setText("Add Participant");
		addParticipantButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					addParticipantButtonActionPerformed(evt);
				} catch (InvalidActivityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addComponent(participantNameLabel)
				.addGroup(layout.createParallelGroup()
						.addComponent(participantNameTextField, 200, 200, 400)
						.addComponent(addParticipantButton))
				);
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {addParticipantButton, participantNameTextField});
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(participantNameLabel)
						.addComponent(participantNameTextField))
				.addComponent(addParticipantButton)
				);
		pack();
	}
	
	private void refreshData() {
		participantNameTextField.setText("");
	}
	
	private void addParticipantButtonActionPerformed(java.awt.event.ActionEvent evt) throws InvalidActivityException {
		EventRegistrationController erc = new EventRegistrationController();
		erc.createParticipant(participantNameTextField.getText());
		refreshData();
		}
		

	

	}


