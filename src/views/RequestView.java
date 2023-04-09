package view;
import models.Request;
import interfaces.IRequestView;

public class RequestView implements IRequestView{

	public void displayRequestInfo(Request request) {
		if (request.approve()) {
			System.out.println("Your request has been approved.");
		}
		
		else if (request.reject()) {
			System.out.println("Your request has been rejected.");
		}
		
		else {
			System.out.println("Your request is pending.");
		}
		
	}
	
}
