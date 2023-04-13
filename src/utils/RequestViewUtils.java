package utils;

import enums.UserRole;
import models.Request;
import models.User;

/**
 * The {@link RequestViewUtils} class provides utility methods for displaying
 * request-related information, such as request header, sender/receiver info,
 * and request history.
 */
public class RequestViewUtils {
	/**
	 * Prints the header for a request, including its ID, type, and status.
	 *
	 * @param request           the request object to be displayed
	 * @param requestTypeString a string representation of the request type
	 */
	public static void printRequestHeader(Request request, String requestTypeString) {
		System.out.println("-------------------- <Request " + request.getRequestID() + "> --------------------");
		System.out.println("Type: " + requestTypeString);
		System.out.println("Status: " + request.getStatus());
	}

	/**
	 * Prints a subheader for a specific section of the request display.
	 *
	 * @param subHeader the title of the subheader
	 */
	public static void printSubHeader(String subHeader) {
		System.out.println();
		System.out.println("\t<" + subHeader + ">");
	}

	/**
	 * Prints information about the sender of the request, including their
	 * user ID, name, and email.
	 *
	 * @param sender the sender user object
	 */
	public static void printSenderInfo(User sender) {
		RequestViewUtils.printSubHeader("Sender");

		if (sender.getRole() == UserRole.STUDENT)
			System.out.println("StudentID: " + sender.getUserID());
		else if (sender.getRole() == UserRole.SUPERVISOR)
			System.out.println("SupervisorID: " + sender.getUserID());
		else if (sender.getRole() == UserRole.FYPCOORDINATOR)
			System.out.println("FYPCoordinatorID: " + sender.getUserID());

		System.out.println("Name: " + sender.getName());
		System.out.println("Email: " + sender.getEmail());
	}

	/**
	 * Prints information about the receiver of the request, including their
	 * user ID, name, and email.
	 *
	 * @param receiver the receiver user object
	 */
	public static void printReceiverInfo(User receiver) {
		RequestViewUtils.printSubHeader("Receiver");

		if (receiver.getRole() == UserRole.STUDENT)
			System.out.println("StudentID: " + receiver.getUserID());
		else if (receiver.getRole() == UserRole.SUPERVISOR)
			System.out.println("SupervisorID: " + receiver.getUserID());
		else if (receiver.getRole() == UserRole.FYPCOORDINATOR)
			System.out.println("FYPCoordinatorID: " + receiver.getUserID());

		System.out.println("Name: " + receiver.getName());
		System.out.println("Email: " + receiver.getEmail());
	}

	/**
	 * Prints the history of a request.
	 *
	 * @param request the request object with its history
	 */
	public static void printRequestHistory(Request request) {
		RequestViewUtils.printSubHeader("History");

		int count = 1;
		for (String history : request.getHistory()) {
			System.out.println(count++ + ". " + history);
		}
	}
}
