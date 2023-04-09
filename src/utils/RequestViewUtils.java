package utils;

import enums.UserRole;
import models.Request;
import models.User;

public class RequestViewUtils {
	public static void printRequestHeader(Request request, String requestTypeString) {
		System.out.println("-------------------- <Request " + request.getRequestID() + "> --------------------");
		System.out.println("Type: " + requestTypeString);
		System.out.println("Status: " + request.getStatus());
	}
	
	public static void printSubHeader(String subHeader) {
		System.out.println();
		System.out.println("\t<"+ subHeader + ">");
	}
	
	public static void printSenderInfo(User sender) {
		RequestViewUtils.printSubHeader("Sender");
		
		if (sender.getRole() == UserRole.STUDENT) System.out.println("StudentID: " + sender.getUserID());
		else if (sender.getRole() == UserRole.SUPERVISOR) System.out.println("SupervisorID: " + sender.getUserID());
		else if (sender.getRole() == UserRole.FYPCOORDINATOR) System.out.println("FYPCoordinatorID: " + sender.getUserID());
		
		System.out.println("Name: " + sender.getName());
		System.out.println("Email: " + sender.getEmail());
	}
	
	public static void printReceiverInfo(User receiver) {
		RequestViewUtils.printSubHeader("Receiver");
		
		if (receiver.getRole() == UserRole.STUDENT) System.out.println("StudentID: " + receiver.getUserID());
		else if (receiver.getRole() == UserRole.SUPERVISOR) System.out.println("SupervisorID: " + receiver.getUserID());
		else if (receiver.getRole() == UserRole.FYPCOORDINATOR) System.out.println("FYPCoordinatorID: " + receiver.getUserID());
		
		System.out.println("Name: " + receiver.getName());
		System.out.println("Email: " + receiver.getEmail());
	}
	
	public static void printRequestHistory(Request request) {
		RequestViewUtils.printSubHeader("History");
		
		int count = 1;
		for (String history : request.getHistory()) {
			System.out.println(count++ + ". " + history);
		}
	}
}
