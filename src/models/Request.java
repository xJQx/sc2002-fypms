package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import enums.RequestStatus;
import enums.RequestType;
import stores.DataStore;

/**
 * The abstract class {@link Request} represents a request made by a user to a
 * supervisor or FYP coordinator to perform a certain action on a project.
 * 
 * Each request has a unique ID, type, status, sender and receiver IDs, project
 * ID, and a history of status changes.
 * 
 * This is an abstract class and therefore cannot be instantiated directly.
 * Subclasses that represent specific types of requests must be created.
 */
public abstract class Request {
	/**
	 * The ID of the last request created.
	 */
	private static int lastRequestID = 0;

	/**
	 * The ID of the request.
	 */
	private int requestID;

	/**
	 * The type of request.
	 */
	private RequestType type;

	/**
	 * The status of the request.
	 */
	private RequestStatus status;

	/**
	 * The history of the status changes of the request.
	 */
	private ArrayList<String> history;

	/**
	 * The ID of the sender making the request.
	 */
	private String senderID;

	/**
	 * The ID of the receiver of the request.
	 */
	private String receiverID;

	/**
	 * The ID of the project associated with the request.
	 */
	private int projectID;

	// ---------- Constructor ---------- //

	/**
	 * Constructs a {@link Request} object with existing request ID, status,
	 * history, sender ID, receiver ID, and project ID. This is for initializing
	 * existing requests
	 * 
	 * @param senderID   the ID of the sender making the request
	 * @param receiverID the ID of the receiver of the request
	 * @param projectID  the ID of the project associated with the request
	 * @param requestID  the ID of the request itself
	 * @param status     the status of the request
	 * @param history    the history of the status changes of the request
	 */
	public Request(String senderID, String receiverID, int projectID, int requestID, RequestStatus status,
			ArrayList<String> history) {
		this.requestID = requestID;
		this.status = status;
		this.history = history;
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.projectID = projectID;

		if (requestID > Request.lastRequestID)
			Request.lastRequestID = requestID;
	}

	/**
	 * Constructs a new {@link Request} object with a new request ID, pending
	 * status, empty history, sender ID, receiver ID, and project ID. This is for
	 * creating new requests
	 * 
	 * @param senderID   the ID of the sender making the request
	 * @param receiverID the ID of the receiver of the request
	 * @param projectID  the ID of the project associated with the request
	 */
	public Request(String senderID, String receiverID, int projectID) {
		this.requestID = ++Request.lastRequestID;
		this.status = RequestStatus.PENDING;
		this.history = new ArrayList<String>();
		this.history.add(this.formatHistory(new Date(), this.status));
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.projectID = projectID;
	}

	// ---------- Methods ---------- //

	/**
	 * Changes the status of the request to APPROVED and updates the request
	 * history.
	 * 
	 * @return true if the status of the request is updated successfully in the
	 *         data store, false otherwise
	 */
	public boolean approve() {
		// Update Request's status and history
		this.setStatus(RequestStatus.APPROVED);
		this.addHistory(new Date(), RequestStatus.APPROVED);

		// Save to CSV
		return DataStore.saveData();
	}

	/**
	 * Changes the status of the request to REJECTED and updates the request
	 * history.
	 * 
	 * @return true if the status of the request is updated successfully in the
	 *         data store, false otherwise
	 */
	public boolean reject() {
		// Update Request's status and history
		this.setStatus(RequestStatus.REJECTED);
		this.addHistory(new Date(), this.status);

		// Save to CSV
		return DataStore.saveData();
	}

	// ---------- Getter and Setters ---------- //

	/**
	 * Returns the ID of the request.
	 * 
	 * @return the request ID
	 */
	public int getRequestID() {
		return this.requestID;
	}

	/**
	 * Returns the type of the request.
	 * 
	 * @return the request type
	 */
	public RequestType getType() {
		return this.type;
	}

	/**
	 * Sets the type of the request.
	 * 
	 * @param type the {@link RequestType} to set
	 * @return true if the request type is set successfully, false otherwise
	 */
	public boolean setType(RequestType type) {
		this.type = type;
		return true;
	}

	/**
	 * Returns the status of the request.
	 * 
	 * @return the {@link RequestStatus}
	 */
	public RequestStatus getStatus() {
		return this.status;
	}

	/**
	 * Sets the status of the request.
	 * 
	 * @param status the {@link RequestStatus} to set
	 * @return true if the request status is set successfully, false otherwise
	 */
	public boolean setStatus(RequestStatus status) {
		this.status = status;
		return true;
	}

	/**
	 * Returns the sender of the request.
	 * 
	 * @return the sender of the request as a {@link User} object, null if the
	 *         sender ID is invalid or not found
	 */
	public User getSender() {
		Map<String, Student> studentsMap = DataStore.getStudentsData();
		if (studentsMap.containsKey(this.senderID))
			return studentsMap.get(this.senderID);

		Map<String, Supervisor> supervisorsMap = DataStore.getSupervisorsData();
		if (supervisorsMap.containsKey(this.senderID))
			return supervisorsMap.get(this.senderID);

		Map<String, FYPCoordinator> fypCoordinatorsMap = DataStore.getFYPCoordinatorsData();
		if (fypCoordinatorsMap.containsKey(this.senderID))
			return fypCoordinatorsMap.get(this.senderID);

		return null;
	}

	/**
	 * Returns the receiver of the request.
	 * 
	 * @return the receiver of the request as a {@link User} object, null if the
	 *         receiver ID is invalid or not found
	 */
	public User getReceiver() {
		Map<String, FYPCoordinator> fypcoordinatorsMap = DataStore.getFYPCoordinatorsData();
		if (fypcoordinatorsMap.containsKey(this.receiverID))
			return fypcoordinatorsMap.get(this.receiverID);

		Map<String, Supervisor> supervisorsMap = DataStore.getSupervisorsData();
		if (supervisorsMap.containsKey(this.receiverID))
			return supervisorsMap.get(this.receiverID);

		return null;
	}

	/**
	 * Returns the project associated with the request.
	 * 
	 * @return the project of the request as a {@link Project} object, null if the
	 *         project ID is invalid or not found
	 */
	public Project getProject() {
		Map<Integer, Project> projectsMap = DataStore.getProjectsData();
		return projectsMap.get(this.projectID);
	}

	/**
	 * Returns the history of status changes of the request.
	 * 
	 * @return the history of the request
	 */
	public ArrayList<String> getHistory() {
		return this.history;
	}

	/**
	 * Sets the history of status changes of the request.
	 * 
	 * @param history the history of the request to set
	 * @return true if the history of the request is set successfully, false
	 *         otherwise
	 */
	public boolean setHistory(ArrayList<String> history) {
		this.history = history;
		return true;
	}

	/**
	 * Adds a status change to the history of the request.
	 * 
	 * @param date   the date of the status change
	 * @param status the status after the change
	 * @return true if the status change is added successfully to the history, false
	 *         otherwise
	 */
	public boolean addHistory(Date date, RequestStatus status) {
		return this.history.add(this.formatHistory(date, status));
	}

	// ---------- Helper Methods ---------- //

	/**
	 * Returns a string representation of the status change with the given date and
	 * status.
	 * 
	 * @param date   the date of the status change
	 * @param status the status after the change
	 * @return a formatted string with the date and status
	 */
	private String formatHistory(Date date, RequestStatus status) {
		return String.format("%s - %s", date.toString(), status);
	}
}
