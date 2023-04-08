package models;

import java.util.ArrayList;

import enums.ProjectStatus;
import enums.RequestStatus;
import store.AppStore;

public class DeregisterProjectRequest extends Request {

	// ---------- Constructor ---------- //
	// For initializing existing requests
	public DeregisterProjectRequest(String senderID, String receiverID, int projectID, int requestID, RequestStatus status, ArrayList<String> history) {
		super(senderID, receiverID, projectID, requestID, status, history);
	}
	
	// For creating new requests
	public DeregisterProjectRequest(String senderID, String receiverID, int projectID) {
		super(senderID, receiverID, projectID);
	}
	
	// ---------- Methods ---------- //
	@Override
	public boolean approve() {
		String studentID = this.getSender().getUserID();
		Project project = super.getProject();
		Supervisor supervisor = project.getSupervisor();
		
		// Update Request
		super.approve();
		
		// Update Project
		project.setStudent(null); 
		project.setStatus(ProjectStatus.AVAILABLE);
		
		// Update Supervisor
		supervisor.setNumOfProjects(supervisor.getNumOfProjects()-1);
		
		// Update Student - Deregister the student (i.e.: student cannot select projects anymore)
		AppStore.getStudentsData().get(studentID).setIsDeregistered(true);
		
		// Save to CSV
		return AppStore.saveData();
	}
}
