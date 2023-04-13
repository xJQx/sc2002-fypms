package services;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import enums.UserRole;
import interfaces.IRequestSupervisorService;
import models.Request;
import models.TransferStudentRequest;
import stores.DataStore;

/**
 * The {@link RequestSupervisorService} class implements
 * {@link IRequestSupervisorService},
 * providing request-related functionalities for supervisors.
 */
public class RequestSupervisorService implements IRequestSupervisorService {

	/**
	 * Constructs an instance of the {@link RequestSupervisorService} class.
	 */
	public RequestSupervisorService() {
	};

	// View pending requests from students
	@Override
	public ArrayList<Request> getStudentPendingRequests(String supervisorID) {
		Map<Integer, Request> requestsData = DataStore.getRequestsData();

		ArrayList<Request> studentPendingRequests = requestsData.values().stream()
				.filter(request -> request.getReceiver().getUserID().equals(supervisorID) &&
						request.getSender().getRole() == UserRole.STUDENT)
				.collect(Collectors.toCollection(ArrayList::new));

		return studentPendingRequests;
	}

	@Override
	public ArrayList<Request> getIncomingRequests(String supervisorID) {
		Map<Integer, Request> requestsData = DataStore.getRequestsData();

		ArrayList<Request> incomingRequests = requestsData.values().stream()
				.filter(request -> request.getReceiver().getUserID().equals(supervisorID))
				.collect(Collectors.toCollection(ArrayList::new));

		return incomingRequests;
	}

	@Override
	public ArrayList<Request> getOutgoingRequests(String supervisorID) {
		Map<Integer, Request> requestsData = DataStore.getRequestsData();

		ArrayList<Request> outgoingRequests = requestsData.values().stream()
				.filter(request -> request.getSender().getUserID().equals(supervisorID))
				.collect(Collectors.toCollection(ArrayList::new));

		return outgoingRequests;
	}

	@Override
	public boolean approveProject(Request request) {
		return request.approve();
	}

	@Override
	public boolean rejectProject(Request request) {
		return request.reject();
	}

	@Override
	public boolean createTransferStudentRequest(String senderID, String receiverID, int projectID,
			String replacementSupervisorID) {
		Request request = new TransferStudentRequest(senderID, receiverID, projectID, replacementSupervisorID);

		Map<Integer, Request> requestsData = DataStore.getRequestsData();
		requestsData.put(request.getRequestID(), request);
		DataStore.setRequestsData(requestsData);

		return true;
	}

}
