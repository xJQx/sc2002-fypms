package models;

import java.util.ArrayList;

import enums.ProjectStatus;
import enums.RequestStatus;
import enums.RequestType;
import stores.DataStore;

/**
 * The {@link TransferStudentRequest} class represents a request made to
 * transfer a student from one supervisor to another. It is a subclass of the
 * {@link Request} class
 */
public class TransferStudentRequest extends Request {
	/**
	 * The ID of the supervisor who will be the new supervisor for the transferred
	 * student.
	 */
	private String replacementSupervisorID;

	// ---------- Constructor ---------- //
	/**
	 * Constructs a {@link TransferStudentRequest} object with the given sender ID,
	 * receiver ID, project ID, request ID, request status, history, and replacement
	 * supervisorID.
	 * This constructor is used for initializing existing requests.
	 *
	 * @param senderID                the ID of the sender of the request
	 * @param receiverID              the ID of the receiver of the request
	 * @param projectID               the ID of the project associated with the
	 *                                request
	 * @param requestID               the ID of the request
	 * @param status                  the status of the request
	 * @param history                 the history of the request
	 * @param replacementSupervisorID the ID of the supervisor who will be the new
	 *                                supervisor for the transferred student
	 */
	public TransferStudentRequest(String senderID, String receiverID, int projectID, int requestID,
			RequestStatus status, ArrayList<String> history, String replacementSupervisorID) {
		super(senderID, receiverID, projectID, requestID, status, history);
		super.setType(RequestType.TRANSFER_STUDENT);
		this.replacementSupervisorID = replacementSupervisorID;
	}

	/**
	 * Constructs a {@link TransferStudentRequest} object with the given sender ID,
	 * receiver ID, project ID, and replacement supervisor ID.
	 * This constructor is used for creating new requests.
	 * 
	 * @param senderID                the ID of the sender of the request
	 * @param receiverID              the ID of the receiver of the request
	 * @param projectID               the ID of the project associated with the
	 *                                request
	 * @param replacementSupervisorID the ID of the supervisor who will be the new
	 *                                supervisor for the transferred student
	 */
	public TransferStudentRequest(String senderID, String receiverID, int projectID, String replacementSupervisorID) {
		super(senderID, receiverID, projectID);
		super.setType(RequestType.TRANSFER_STUDENT);
		this.replacementSupervisorID = replacementSupervisorID;
	}

	// ---------- Methods ---------- //
	/**
	 * Approves the {@link TransferStudentRequest} object.
	 * This method updates the supervisor and project information, and
	 * saves the data to the CSV file.
	 * 
	 * @return true if the request is approved successfully, false if one of the
	 *         following
	 *         conditions is true:
	 *         <ul>
	 *         <li>The project is not submitted by the sender/supervisor</li>
	 *         <li>The replacement supervisor has reached the maximum number of
	 *         projects he/she can supervise</li>
	 *         </ul>
	 */
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
		if (project.getStatus() == ProjectStatus.ALLOCATED) {
			supervisor.setNumOfProjects(supervisor.getNumOfProjects() - 1);
		}
		replacementSupervisor.setNumOfProjects(replacementSupervisor.getNumOfProjects() + 1);

		// Save to CSV
		return DataStore.saveData();
	}

	// ---------- Getters and Setters ---------- //
	/**
	 * Returns the ID of the supervisor who will be the new supervisor for the
	 * transferred student.
	 * 
	 * @return the ID of the replacement supervisor
	 */
	public String getReplacementSupervisorID() {
		return this.replacementSupervisorID;
	}
}
