package views;

import models.Project;
import models.Request;
import models.TransferStudentRequest;
import stores.DataStore;
import utils.RequestViewUtils;
import enums.RequestType;
import interfaces.IRequestView;

/**
 * The {@link RequestTransferStudentView} class implements {@link IRequestView}
 * and provides methods for displaying request information related
 * to transferring a student to a replacement supervisor.
 */
public class RequestTransferStudentView implements IRequestView{

	/**
	 * Constructs an instance of the {@link RequestTransferStudentView} class.
	 */
	public RequestTransferStudentView() {
	};

	@Override
	public void displayRequestInfo(Request request) {
		if (!(request.getType() == RequestType.TRANSFER_STUDENT)) return;
		String replacementSupervisorID = ((TransferStudentRequest) request).getReplacementSupervisorID();
		String replacementSupervisorName = DataStore.getSupervisorsData().containsKey(replacementSupervisorID) ?
				DataStore.getSupervisorsData().get(replacementSupervisorID).getName() :
				DataStore.getFYPCoordinatorsData().get(replacementSupervisorID).getName();
		Project project = request.getProject();
		
		// ----- Display ----- //
		RequestViewUtils.printRequestHeader(request, "Request to Transfer Student to a Replacement Supervisor");
		RequestViewUtils.printSenderInfo(request.getSender());
		RequestViewUtils.printReceiverInfo(request.getReceiver());
		
		RequestViewUtils.printSubHeader("Details");
		System.out.println("Project:");
		System.out.println(" > ProjectID: " + project.getProjectID());
		System.out.println(" > Title: " + project.getTitle());
		System.out.println(" > Supervisor: " + project.getSupervisor().getName());
		System.out.println(" > Student: " + (project.getStudent() != null ? project.getStudent().getName() : "Deregistered"));
		System.out.println();
		System.out.println("Requested Changes:");
		System.out.println(" > Supervisor (Replacement): " + replacementSupervisorName);
		
		RequestViewUtils.printRequestHistory(request);
		
		System.out.println();
	}

}
