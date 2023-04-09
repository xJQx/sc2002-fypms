package interfaces;

import java.util.ArrayList;

import models.Request;

public interface IRequestFYPCoordinatorService extends IRequestSupervisorService {
	public ArrayList<Request> getAllRequests();

	public ArrayList<Request> getSupervisorPendingRequests(String fypCoordinatorID);
}
