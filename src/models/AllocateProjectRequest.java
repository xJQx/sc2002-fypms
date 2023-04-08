package models;

import java.util.ArrayList;

import enums.ProjectStatus;
import enums.RequestStatus;
import store.AppStore;

public class AllocateProjectRequest extends Request {

	// ---------- Constructor ---------- //
	// For initializing existing requests
	public AllocateProjectRequest(String senderID, String receiverID, int projectID, int requestID, RequestStatus status, ArrayList<String> history) {
		super(senderID, receiverID, projectID, requestID, status, history);
	}
	
	// For creating new requests
	public AllocateProjectRequest(String senderID, String receiverID, int projectID) {
		super(senderID, receiverID, projectID);
	}

	// ---------- Methods ---------- //
	@Override
	public boolean approve() {
		String studentID = this.getSender().getUserID();
		Project project = super.getProject();
		Supervisor supervisor = project.getSupervisor();
		
		// Validation - Supervisors can only supervisor at most 2 projects
		if (supervisor.getNumOfProjects() >= 2) {
			System.out.println("Cannot approve request. Supervisor has reached the maximum number of projects he/she can supervise!");
			return false;
		}
		
		// Update Request
		super.approve();
		
		// Update Project
		project.setStudent(studentID); 
		project.setStatus(ProjectStatus.ALLOCATED);
		
		// Update Supervisor
		supervisor.setNumOfProjects(supervisor.getNumOfProjects()+1);
		
		// Save to CSV
		return AppStore.saveData();
	}
	
	@Override
	public boolean reject() {
		// Update Request
		super.reject();
		
		// Update Project
		Project project = super.getProject();
		project.setStatus(ProjectStatus.AVAILABLE);
		
		// Save to CSV
		return AppStore.saveData();
	}
}
