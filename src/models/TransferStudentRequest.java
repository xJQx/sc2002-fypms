package models;

import java.util.ArrayList;

import enums.RequestStatus;
import store.DataStore;

public class TransferStudentRequest extends Request {
	private String replacementSupervisorID; 
	
	// ---------- Constructor ---------- //
	// For initializing existing requests
	public TransferStudentRequest(String senderID, String receiverID, int projectID, int requestID, RequestStatus status, ArrayList<String> history, String replacementSupervisorID) {
		super(senderID, receiverID, projectID, requestID, status, history);
		this.replacementSupervisorID = replacementSupervisorID;
	}
	
	// For creating new requests
	public TransferStudentRequest(String senderID, String receiverID, int projectID, String replacementSupervisorID) {
		super(senderID, receiverID, projectID);
		this.replacementSupervisorID = replacementSupervisorID;
	}

	// ---------- Methods ---------- //
	@Override
	public boolean approve() {
		Project project = super.getProject();
		
		// Check if the project is submitted by the sender/supervisor
		if (!project.getSupervisor().getSupervisorID().equals(super.getSender().getUserID())) {
			System.out.println("Request cannot be approved. Invalid sender!");
			return false;
		}
		
		// Update Request
		super.approve();
		
		// Update Project's Supervisor
		project.setSupervisor(this.replacementSupervisorID);
		
		// Save to CSV
		return DataStore.saveData();
	}
	
	// ---------- Getters and Setters ---------- //
	public String getReplacementSupervisorID() {
		return this.replacementSupervisorID;
	}
}
