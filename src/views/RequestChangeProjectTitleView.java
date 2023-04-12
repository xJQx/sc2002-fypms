package views;

import models.ChangeProjectTitleRequest;
import models.Project;
import models.Request;
import utils.RequestViewUtils;
import enums.RequestType;
import interfaces.IRequestView;


public class RequestChangeProjectTitleView implements IRequestView {

	@Override
	public void displayRequestInfo(Request request) {
		if (!(request.getType() == RequestType.CHANGE_PROJECT_TITLE)) return;
		String newTitle = ((ChangeProjectTitleRequest) request).getNewTitle();
		Project project = request.getProject();
		
		// ----- Display ----- //
		RequestViewUtils.printRequestHeader(request, "Request to Change Project Title");
		RequestViewUtils.printSenderInfo(request.getSender());
		RequestViewUtils.printReceiverInfo(request.getReceiver());
		
		RequestViewUtils.printSubHeader("Details");
		System.out.println("Original Project:");
		System.out.println(" > ProjectID: " + project.getProjectID());
		System.out.println(" > Title: " + project.getTitle());
		System.out.println(" > Supervisor: " + project.getSupervisor().getName());
		System.out.println(" > Student: " + project.getStudent().getName());
		System.out.println();
		System.out.println("Requested Changes:");
		System.out.println(" > Title (New): " + newTitle);
		
		RequestViewUtils.printRequestHistory(request);
		
		System.out.println();
	}

}
