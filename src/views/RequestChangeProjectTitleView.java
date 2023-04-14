package views;

import models.ChangeProjectTitleRequest;
import models.Project;
import models.Request;
import utils.RequestViewUtils;
import enums.RequestType;
import interfaces.IRequestView;

/**
 * The {@link RequestChangeProjectTitleView} class implements
 * {@link IRequestView} and provides methods for displaying 
 * request information related to changing a project's title.
 */
public class RequestChangeProjectTitleView implements IRequestView {

	/**
	 * Constructs an instance of the {@link RequestChangeProjectTitleView} class.
	 */
	public RequestChangeProjectTitleView() {
	};

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
		System.out.println(" > Student: " + (project.getStudent() != null ? project.getStudent().getName() : "Deregistered"));
		System.out.println();
		System.out.println("Requested Changes:");
		System.out.println(" > Title (New): " + newTitle);
		
		RequestViewUtils.printRequestHistory(request);
		
		System.out.println();
	}

}
