package interfaces;

import java.util.ArrayList;

import models.Request;

/**
 * The {@link IRequestStudentService} interface defines a contract for managing
 * student-related request services.
 */
public interface IRequestStudentService {
	/**
	 * Retrieves a list of requests made by the student with the specified ID.
	 *
	 * @param studentID the ID of the student
	 * @return an {@link ArrayList} of {@link Request} objects representing the
	 *         requests made by the student
	 */
	public ArrayList<Request> getStudentRequests(String studentID);

	/**
	 * Creates a new allocate project request with the specified sender ID, receiver
	 * ID, and project ID.
	 * 
	 * @param senderID   the ID of the sender of the request
	 * @param receiverID the ID of the receiver of the request
	 * @param projectID  the ID of the project associated with the request
	 * @return true if the request was created successfully, false otherwise
	 */
	public boolean createAllocateProjectRequest(String senderID, String receiverID, int projectID);

	/**
	 * Creates a new deregister project request with the specified sender ID,
	 * receiver ID, and project ID.
	 *
	 * @param senderID   the ID of the sender of the request
	 * @param receiverID the ID of the receiver of the request
	 * @param projectID  the ID of the project associated with the request
	 * @return true if the request was created successfully, false otherwise
	 */
	public boolean createDeregisterProjectRequest(String senderID, String receiverID, int projectID);

	/**
	 * Creates a new change project title request with the specified sender ID,
	 * receiver ID, project ID, and new title.
	 *
	 * @param senderID   the ID of the sender of the request
	 * @param receiverID the ID of the receiver of the request
	 * @param projectID  the ID of the project associated with the request
	 * @param newTitle   the new title of the project
	 * @return true if the request was created successfully, false otherwise
	 */
	public boolean createChangeProjectTitleRequest(String senderID, String receiverID, int projectID, String newTitle);
}
