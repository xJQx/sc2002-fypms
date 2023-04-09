package view;

import java.util.ArrayList;

import models.Request;
import enums.RequestStatus;
import interfaces.IRequestView;

public class RequestDeregisterProjectView implements IRequestView{

	public void displayRequestInfo(Request request) {
		System.out.println("RequestID: " + request.getRequestID());
		System.out.println("Request to Deregister Project.");
		
		System.out.println("ProjectID: " + request.getProject().getProjectID());
		System.out.println("StudentID: " + request.getSender.getUserID());
		
		System.out.println("History and Status: ");
		
		for (String hist : request.getHistory()) {
			System.out.println(hist);
		}
		
		
	}

}
