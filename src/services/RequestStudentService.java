package services;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import interfaces.IRequestStudentService;
import models.AllocateProjectRequest;
import models.ChangeProjectTitleRequest;
import models.DeregisterProjectRequest;
import models.Request;
import stores.DataStore;

/**
 * The {@link RequestStudentService} class implements
 * {@link IRequestStudentService},
 * providing request-related functionalities for students.
 */
public class RequestStudentService implements IRequestStudentService {

	/**
	 * Constructs an instance of the {@link RequestStudentService} class.
	 */
	public RequestStudentService() {
	};

	// Get Requests sent by Students themselves
	@Override
	public ArrayList<Request> getStudentRequests(String studentID) {
		Map<Integer, Request> requestsData = DataStore.getRequestsData();

		ArrayList<Request> studentRequests = requestsData.values().stream()
				.filter(request -> request.getSender().getUserID().equals(studentID))
				.collect(Collectors.toCollection(ArrayList::new));

		return studentRequests;
	}

	@Override
	public boolean createAllocateProjectRequest(String senderID, String receiverID, int projectID) {
		Request request = new AllocateProjectRequest(senderID, receiverID, projectID);

		Map<Integer, Request> requestsData = DataStore.getRequestsData();
		requestsData.put(request.getRequestID(), request);
		DataStore.setRequestsData(requestsData);

		return true;
	}

	@Override
	public boolean createDeregisterProjectRequest(String senderID, String receiverID, int projectID) {
		Request request = new DeregisterProjectRequest(senderID, receiverID, projectID);

		Map<Integer, Request> requestsData = DataStore.getRequestsData();
		requestsData.put(request.getRequestID(), request);
		DataStore.setRequestsData(requestsData);

		return true;
	}

	@Override
	public boolean createChangeProjectTitleRequest(String senderID, String receiverID, int projectID, String newTitle) {
		Request request = new ChangeProjectTitleRequest(senderID, receiverID, projectID, newTitle);

		Map<Integer, Request> requestsData = DataStore.getRequestsData();
		requestsData.put(request.getRequestID(), request);
		DataStore.setRequestsData(requestsData);

		return true;
	}

}
