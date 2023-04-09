package interfaces;

import java.util.ArrayList;

import models.Request;

public interface IRequestStudentService {
	public ArrayList<Request> getStudentRequests(String studentID);
	
	public boolean createAllocateProjectRequest(String senderID, String receiverID, int projectID);
	
	public boolean createDeregisterProjectRequest(String senderID, String receiverID, int projectID);
	
	public boolean createChangeProjectTitleRequest(String senderID, String receiverID, int projectID, String newTitle);
}
