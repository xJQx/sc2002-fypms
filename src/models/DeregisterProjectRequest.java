package models;

import java.util.ArrayList;

import enums.ProjectStatus;
import enums.RequestStatus;
import enums.RequestType;
import stores.DataStore;

/**
 * The {@link DeregisterProjectRequest} class represents a request made by a
 * student to deregister a project. It is a subclass of the {@link Request}
 * class and includes methods to approve and update the status of the request.
 */
public class DeregisterProjectRequest extends Request {
	/**
	 * The ID of the student sending the deregistration request.
	 */
	private String studentID;

	// ---------- Constructor ---------- //
	/**
	 * This constructor initializes an existing {@link DeregisterProjectRequest}
	 * object.
	 * 
	 * @param senderID   the ID of the sender of the request
	 * @param receiverID the ID of the receiver of the request
	 * @param projectID  the ID of the project associated with the request
	 * @param requestID  the ID of the request
	 * @param status     the current status of the request
	 * @param history    the list of statuses that the request went through
	 */
	public DeregisterProjectRequest(String senderID, String receiverID, int projectID, int requestID,
			RequestStatus status, ArrayList<String> history) {
		super(senderID, receiverID, projectID, requestID, status, history);
		super.setType(RequestType.DEREGISTER_PROJECT);
		this.studentID = senderID;
	}

	/**
	 * This constructor creates a new {@link DeregisterProjectRequest} object.
	 * 
	 * @param senderID   the ID of the sender of the request
	 * @param receiverID the ID of the receiver of the request
	 * @param projectID  the ID of the project associated with the request
	 */
	public DeregisterProjectRequest(String senderID, String receiverID, int projectID) {
		super(senderID, receiverID, projectID);
		super.setType(RequestType.DEREGISTER_PROJECT);
		this.studentID = senderID;
	}

	// ---------- Methods ---------- //
	/**
	 * This method approves the {@link DeregisterProjectRequest}. It updates the
	 * status of the request, the status and availability of the project, and the
	 * number of projects supervised by the project's supervisor.
	 * 
	 * @return true if data is successfully saved to CSV, false otherwise
	 */
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
		supervisor.setNumOfProjects(supervisor.getNumOfProjects() - 1);

		// Update Student - Deregister the student (i.e.: student cannot select projects anymore)
		DataStore.getStudentsData().get(studentID).setIsDeregistered(true);

		// Save to CSV
		return DataStore.saveData();
	}

	// ---------- Getters ----- //
	/**
	 * This method returns the {@link Student} object associated with the student
	 * ID of the {@link DeregisterProjectRequest}.
	 * 
	 * @return the {@link Student} object associated with the student ID
	 */
	public Student getStudent() {
		return DataStore.getStudentsData().get(this.studentID);
	}
}
