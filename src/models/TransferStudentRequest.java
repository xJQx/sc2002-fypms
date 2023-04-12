package models;

import java.util.ArrayList;

import enums.RequestStatus;
import enums.RequestType;
import stores.DataStore;

public class TransferStudentRequest extends Request {
	private String replacementSupervisorID; 
	
	// ---------- Constructor ---------- //
	// For initializing existing requests
	public TransferStudentRequest(String senderID, String receiverID, int projectID, int requestID, RequestStatus status, ArrayList<String> history, String replacementSupervisorID) {
		super(senderID, receiverID, projectID, requestID, status, history);
		super.setType(RequestType.TRANSFER_STUDENT);
		this.replacementSupervisorID = replacementSupervisorID;
	}
	
	// For creating new requests
	public TransferStudentRequest(String senderID, String receiverID, int projectID, String replacementSupervisorID) {
		super(senderID, receiverID, projectID);
		super.setType(RequestType.TRANSFER_STUDENT);
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
		
		Supervisor supervisor = project.getSupervisor();
  		Supervisor replacementSupervisor = DataStore.getSupervisorsData().get(replacementSupervisorID);

		// Validation - Supervisors can only supervisor at most 2 projects
		if (replacementSupervisor.getNumOfProjects() >= 2) {
			System.out.println(
					"Cannot approve request. Replacement supervisor has reached the maximum number of projects he/she can supervise!");
			return false;
		}
		
		// Update Request
		super.approve();
		
		// Update Project's Supervisor
		project.setSupervisor(this.replacementSupervisorID);

		// Update Supervisor
		supervisor.setNumOfProjects(supervisor.getNumOfProjects() - 1);
		replacementSupervisor.setNumOfProjects(replacementSupervisor.getNumOfProjects() + 1);
		
		// Save to CSV
		return DataStore.saveData();
	}
	
	// ---------- Getters and Setters ---------- //
	public String getReplacementSupervisorID() {
		return this.replacementSupervisorID;
	}
}
