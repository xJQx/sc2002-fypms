package models;

import java.util.ArrayList;

import enums.RequestStatus;
import enums.RequestType;
import stores.DataStore;

/**
 * The {@link ChangeProjectTitleRequest} class represents a request from a
 * supervisor to change the title of a project. It is a subclass of the
 * {@link Request} class.
 */
public class ChangeProjectTitleRequest extends Request {

	/**
	 * The new title for the project.
	 */
	private String newTitle;

	/**
	 * Constructs a {@link ChangeProjectTitleRequest} object to initialize an
	 * existing request.
	 * 
	 * @param senderID   the ID of the user who sent the request
	 * @param receiverID the ID of the user who receives the request
	 * @param projectID  the ID of the project related to the request
	 * @param requestID  the ID of the request
	 * @param status     the current status of the request
	 * @param history    the history of the request
	 * @param newTitle   the new title for the project
	 */
	public ChangeProjectTitleRequest(String senderID, String receiverID, int projectID, int requestID,
			RequestStatus status, ArrayList<String> history, String newTitle) {
		super(senderID, receiverID, projectID, requestID, status, history);
		super.setType(RequestType.CHANGE_PROJECT_TITLE);
		this.newTitle = newTitle;
	}

	/**
	 * Constructs a {@link ChangeProjectTitleRequest} object to create a new
	 * request.
	 * 
	 * @param senderID   the ID of the user who sent the request
	 * @param receiverID the ID of the user who receives the request
	 * @param projectID  the ID of the project related to the request
	 * @param newTitle   the new title for the project
	 */
	public ChangeProjectTitleRequest(String senderID, String receiverID, int projectID, String newTitle) {
		super(senderID, receiverID, projectID);
		super.setType(RequestType.CHANGE_PROJECT_TITLE);
		this.newTitle = newTitle;
	}

	/**
	 * Approves the request to change the project title and updates the project's
	 * title.
	 *
	 * @return true if the request is approved and the data is saved successfully,
	 *         false if the receiver of the request is not the supervisor who
	 *         submitted the project or when data is not saved successfully.
	 */
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

	/**
	 * Returns the new title for the project in the request.
	 * 
	 * @return the new title
	 */
	public String getNewTitle() {
		return this.newTitle;
	}
}
