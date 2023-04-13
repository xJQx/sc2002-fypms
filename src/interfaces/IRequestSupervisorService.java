package interfaces;

import java.util.ArrayList;

import models.Request;

/**
 * The {@link IRequestSupervisorService} interface defines a contract for
 * managing supervisor-related request services.
 */
public interface IRequestSupervisorService {
	/**
	 * Retrieves a list of pending requests made by the students of the supervisor
	 * with the specified ID.
	 *
	 * @param supervisorID the ID of the supervisor
	 * @return an {@link ArrayList} of {@link Request} objects representing the
	 *         pending requests made by the students of the supervisor
	 */
	public ArrayList<Request> getStudentPendingRequests(String supervisorID);

	/**
	 * Retrieves a list of incoming requests sent to the supervisor with the
	 * specified ID.
	 *
	 * @param supervisorID the ID of the supervisor
	 * @return an {@link ArrayList} of {@link Request} objects representing the
	 *         incoming requests sent to the supervisor
	 */
	public ArrayList<Request> getIncomingRequests(String supervisorID);

	/**
	 * Retrieves a list of outgoing requests sent by the supervisor with the
	 * specified ID.
	 *
	 * @param supervisorID the ID of the supervisor
	 * @return an {@link ArrayList} of {@link Request} objects representing the
	 *         outgoing requests sent by the supervisor
	 */
	public ArrayList<Request> getOutgoingRequests(String supervisorID);

	/**
	 * Approves the specified project request.
	 *
	 * @param request the {@link Request} object to be approved
	 * @return true if the request was approved successfully, false otherwise
	 */
	public boolean approveProject(Request request);

	/**
	 * Rejects the specified project request.
	 *
	 * @param request the {@link Request} object to be rejected
	 * @return true if the request was rejected successfully, false otherwise
	 */
	public boolean rejectProject(Request request);

	/**
	 * Creates a new transfer student request with the specified sender ID,
	 * receiver ID, project ID, and replacement supervisor ID.
	 *
	 * @param senderID                the ID of the sender of the request
	 * @param receiverID              the ID of the receiver of the request
	 * @param projectID               the ID of the project associated with the
	 *                                request
	 * @param replacementSupervisorID the ID of the supervisor who will be the new
	 *                                supervisor for the transferred student
	 * @return true if the request was created successfully, false otherwise
	 */
	public boolean createTransferStudentRequest(String senderID, String receiverID, int projectID,
			String replacementSupervisorID);
}
