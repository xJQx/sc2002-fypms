package services;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import enums.UserRole;
import interfaces.IRequestFYPCoordinatorService;
import models.Request;
import store.DataStore;

public class RequestFYPCoordinatorService extends RequestSupervisorService implements IRequestFYPCoordinatorService {

	// Get All Requests
	@Override
	public ArrayList<Request> getAllRequests() {
		return DataStore.getRequestsData().values().stream().collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public ArrayList<Request> getSupervisorPendingRequests(String fypCoordinatorID) {
		Map<Integer, Request> requestsData = DataStore.getRequestsData();
		
		ArrayList<Request> supervisorPendingRequests = requestsData.values().stream()
				.filter(request -> request.getReceiver().getUserID().equals(fypCoordinatorID) &&
						request.getSender().getRole() == UserRole.SUPERVISOR)
				.collect(Collectors.toCollection(ArrayList::new));
		
		return supervisorPendingRequests;
	}

}
