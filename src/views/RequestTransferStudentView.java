package views;

import models.Project;
import models.Request;
import models.TransferStudentRequest;
import store.DataStore;
import utils.RequestViewUtils;
import interfaces.IRequestView;

public class RequestTransferStudentView implements IRequestView{

	@Override
	public void displayRequestInfo(Request request) {
		if (!(request instanceof TransferStudentRequest)) return;
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
		System.out.println(" > Student: " + project.getStudent().getName());
		System.out.println();
		System.out.println("Requested Changes:");
		System.out.println(" > Supervisor (Replacement): " + replacementSupervisorName);
		
		RequestViewUtils.printRequestHistory(request);
		
		System.out.println();
	}

}
