package services;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import interfaces.IRequestSupervisorService;
import models.Request;
import models.Student;
import models.TransferStudentRequest;
import store.DataStore;

public class RequestSupervisorService implements IRequestSupervisorService {

	// View pending requests from students
	@Override
	public ArrayList<Request> getStudentPendingRequests(String supervisorID) {
		Map<Integer, Request> requestsData = DataStore.getRequestsData();
		
		ArrayList<Request> studentPendingRequests = requestsData.values().stream()
				.filter(request -> request.getReceiver().getUserID().equals(supervisorID) &&
						request.getSender() instanceof Student)
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