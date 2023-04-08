package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import enums.RequestStatus;
import store.AppStore;

public abstract class Request {
	private static int lastRequestID = 0;
	private int requestID;
	private RequestStatus status;
	private ArrayList<String> history; // <DateTime1 - Status1>; <DateTime2 - Status2>;
	private String senderID;
	private String receiverID;
	private int projectID;
	
	// ---------- Constructor ---------- //
	// For initializing existing requests
	public Request(String senderID, String receiverID, int projectID, int requestID, RequestStatus status, ArrayList<String> history) {
		this.requestID = requestID;
		this.status = status;
		this.history = history;
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.projectID = projectID;
		
		if (requestID > Request.lastRequestID) Request.lastRequestID = requestID;
	}
	
	// For creating new requests
	public Request(String senderID, String receiverID, int projectID) {
		this.requestID = ++Request.lastRequestID;
		this.status = RequestStatus.PENDING;
		this.history = new ArrayList<String>();
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.projectID = projectID;
	}
	
	// ---------- Methods ---------- //
	public abstract boolean approve();
	
	public boolean reject() {
		this.status = RequestStatus.REJECTED;
		this.history.add(this.formatHistory(new Date(), this.status));
		
		return true;
	}
	
	// ---------- Getter and Setters ---------- //
	public int getRequestID() {
		return this.requestID;
	}
	
	public RequestStatus getStatus() {
		return this.status;
	}
	
	public boolean setStatus(RequestStatus status) {
		this.status = status;
		return true;
	}
	
	public User getSender() {
		Map<String, Student> studentsMap = AppStore.getStudentsData();
		if (studentsMap.containsKey(this.senderID)) return studentsMap.get(this.senderID);
		
		Map<String, Supervisor> supervisorsMap = AppStore.getSupervisorsData();
		if (supervisorsMap.containsKey(this.senderID)) return supervisorsMap.get(this.senderID);
		
		return null;
	}
	
	public User getReceiver() {
		Map<String, FYPCoordinator> fypcoordinatorsMap = AppStore.getFYPCoordinatorsData();
		if (fypcoordinatorsMap.containsKey(this.receiverID)) return fypcoordinatorsMap.get(this.receiverID);
		
		Map<String, Supervisor> supervisorsMap = AppStore.getSupervisorsData();
		if (supervisorsMap.containsKey(this.receiverID)) return supervisorsMap.get(this.receiverID);
		
		return null;
	}
	
	public Project getProject() {
		Map<Integer, Project> projectsMap = AppStore.getProjectsData();
		return projectsMap.get(this.projectID);
	}
	
	public ArrayList<String> getHistory() {
		return this.history;
	}
	
	public boolean setHistory(ArrayList<String> history) {
		this.history = history;
		return true;
	}
	
	public boolean addHistory(Date date, RequestStatus status) {
		return this.history.add(this.formatHistory(date, status));
	}
	
	// ---------- Helper Methods ---------- //
	private String formatHistory(Date date, RequestStatus status) {
		return String.format("%s - %s", date.toString(), status);
	}
}
