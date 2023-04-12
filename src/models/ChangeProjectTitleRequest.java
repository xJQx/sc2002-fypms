package models;

import java.util.ArrayList;

import enums.RequestStatus;
import stores.DataStore;

public class ChangeProjectTitleRequest extends Request {
	private String newTitle;

	// ---------- Constructor ---------- //
	// For initializing existing requests
	public ChangeProjectTitleRequest(String senderID, String receiverID, int projectID, int requestID, RequestStatus status, ArrayList<String> history, String newTitle) {
		super(senderID, receiverID, projectID, requestID, status, history);
		this.newTitle = newTitle;
	}
	
	// For creating new requests
	public ChangeProjectTitleRequest(String senderID, String receiverID, int projectID, String newTitle) {
		super(senderID, receiverID, projectID);
		this.newTitle = newTitle;
	}

	// ---------- Methods ---------- //
	@Override
	public boolean approve() {
		Project project = super.getProject();
		
		// Check if the recipient is the one who submitted the project
		if (!project.getSupervisor().getSupervisorID().equals(super.getReceiver().getUserID())) {
			System.out.println("Request cannot be approved. Invalid receiver!");
			return false;
		}
		
		// Update Request
		super.approve();
		
		// Update Project's Title
		project.setTitle(this.newTitle);
		
		// Save to CSV
		return DataStore.saveData();
	}
	
	// ---------- Getters and Setters ---------- //
	public String getNewTitle() {
		return this.newTitle;
	}
}
