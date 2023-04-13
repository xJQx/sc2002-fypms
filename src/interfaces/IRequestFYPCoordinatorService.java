package interfaces;

import java.util.ArrayList;

import models.Request;

/**
 * The {@link IRequestFYPCoordinatorService} interface defines a contract for
 * managing FYP Coordinator-related request services.
 */
public interface IRequestFYPCoordinatorService extends IRequestSupervisorService {
	/**
	 * Retrieves a list of all requests.
	 *
	 * @return an {@link ArrayList} of {@link Request} objects representing all
	 *         the requests
	 */
	public ArrayList<Request> getAllRequests();

	/**
	 * Retrieves a list of pending requests made to the FYP Coordinator with the
	 * specified ID.
	 *
	 * @param fypCoordinatorID the ID of the FYP Coordinator
	 * @return an {@link ArrayList} of {@link Request} objects representing the
	 *         pending requests made to the FYP Coordinator
	 */
	public ArrayList<Request> getSupervisorPendingRequests(String fypCoordinatorID);
}
