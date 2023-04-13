package views;

import models.DeregisterProjectRequest;
import models.Project;
import models.Request;
import utils.RequestViewUtils;
import enums.RequestType;
import interfaces.IRequestView;

/**
 * The {@link RequestDeregisterProjectView} class implements
 * {@link IRequestView} and provides methods for displaying 
 * request information related to deregistering a student
 * from a project.
 */
public class RequestDeregisterProjectView implements IRequestView{

	@Override
	public void displayRequestInfo(Request request) {
		if (!(request.getType() == RequestType.DEREGISTER_PROJECT)) return;
		String studentName = ((DeregisterProjectRequest) request).getSender().getName();
		Project project = request.getProject();
		
		// ----- Display ----- //
		RequestViewUtils.printRequestHeader(request, "Request to Deregister Student From Project");
		RequestViewUtils.printSenderInfo(request.getSender());
		RequestViewUtils.printReceiverInfo(request.getReceiver());
		
		RequestViewUtils.printSubHeader("Details");
		System.out.println("Project:");
		System.out.println(" > ProjectID: " + project.getProjectID());
		System.out.println(" > Title: " + project.getTitle());
		System.out.println(" > Supervisor: " + project.getSupervisor().getName());
		System.out.println(" > Student (to be deregistered): " + studentName);
		
		RequestViewUtils.printRequestHistory(request);
		
		System.out.println();
	}

}
