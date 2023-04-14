package models;

import java.util.ArrayList;

import enums.ProjectStatus;
import enums.RequestStatus;
import enums.RequestType;
import stores.DataStore;

/**
 * The {@link AllocateProjectRequest} class represents a request to allocate a
 * project to a student. It is a subclass of the {@link Request} class.
 */
public class AllocateProjectRequest extends Request {

	/**
	 * Constructs an {@link AllocateProjectRequest} object for an existing request.
	 * 
	 * @param senderID   the user ID of the sender
	 * @param receiverID the user ID of the receiver
	 * @param projectID  the ID of the project
	 * @param requestID  the ID of the request
	 * @param status     the current status of the request
	 * @param history    the history of the request
	 */
	public AllocateProjectRequest(String senderID, String receiverID, int projectID, int requestID,
			RequestStatus status, ArrayList<String> history) {
		super(senderID, receiverID, projectID, requestID, status, history);
		super.setType(RequestType.ALLOCATE_PROJECT);
	}

	/**
	 * Constructs an {@link AllocateProjectRequest} object for a new request.
	 * 
	 * @param senderID   the user ID of the sender
	 * @param receiverID the user ID of the receiver
	 * @param projectID  the ID of the project
	 */
	public AllocateProjectRequest(String senderID, String receiverID, int projectID) {
		super(senderID, receiverID, projectID);
		super.setType(RequestType.ALLOCATE_PROJECT);
		super.getProject().setStatus(ProjectStatus.RESERVED);
		super.getProject().setStudent(senderID);
		DataStore.saveData();
	}

	/**
	 * Approves the request to allocate the project to the student.
	 * 
	 * @return true if the request is approved and data is saved successfully,
	 *         false if the supervisor has already reached the maximum number of
	 *         projects that they can supervise ({@link Supervisor#MAX_PROJECTS}) or
	 *         when data is not saved successfully.
	 */
	@Override
	public boolean approve() {
		String studentID = this.getSender().getUserID();
		Project project = super.getProject();
		Supervisor supervisor = project.getSupervisor();

		// Validation - Supervisors can only supervise at most 2 projects
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
		supervisor.setNumOfProjects(supervisor.getNumOfProjects() + 1);

		// Save to CSV
		return DataStore.saveData();
	}

	/**
	 * Rejects the request to allocate the project to the student.
	 * 
	 * @return true if the request is rejected and data is saved successfully,
	 *         false otherwise
	 */
	@Override
	public boolean reject() {
		// Update Request
		super.reject();

		// Update Project
		Project project = super.getProject();
		if (project.getSupervisor().getNumOfProjects() >= Supervisor.MAX_PROJECTS) {
			project.setStatus(ProjectStatus.UNAVAILABLE);
		} else {
			project.setStatus(ProjectStatus.AVAILABLE);
		}

		// Save to CSV
		return DataStore.saveData();
	}
}
