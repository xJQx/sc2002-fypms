package views;

import models.Project;
import models.Request;
import utils.RequestViewUtils;
import interfaces.IRequestView;

public class RequestDeregisterProjectView implements IRequestView{

	@Override
	public void displayRequestInfo(Request request) {
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
		System.out.println(" > Student (to be deregistered): " + project.getStudent().getName());
		
		RequestViewUtils.printRequestHistory(request);
		
		System.out.println();
	}

}
