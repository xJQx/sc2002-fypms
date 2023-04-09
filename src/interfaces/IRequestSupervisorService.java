package interfaces;

import java.util.ArrayList;

import models.Request;

public interface IRequestSupervisorService {
	public ArrayList<Request> getStudentPendingRequests(String supervisorID);
	
	public ArrayList<Request> getIncomingRequests(String supervisorID);
	
	public ArrayList<Request> getOutgoingRequests(String supervisorID);
	
	public boolean approveProject(Request request);
	
	public boolean rejectProject(Request request);
	
	public boolean createTransferStudentRequest(String senderID, String receiverID, int projectID, String replacementSupervisorID);
}
