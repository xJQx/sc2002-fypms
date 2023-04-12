package controllers;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import enums.ProjectStatus;
import interfaces.IProjectSupervisorService;
import interfaces.IProjectView;
import interfaces.IRequestSupervisorService;
import interfaces.IRequestView;
import models.AllocateProjectRequest;
import models.ChangeProjectTitleRequest;
import models.DeregisterProjectRequest;
import models.Project;
import models.Request;
import models.Supervisor;
import models.TransferStudentRequest;
import services.ProjectSupervisorService;
import services.RequestSupervisorService;
import stores.AuthStore;
import stores.DataStore;
import utils.SelectorUtils;
import views.CommonView;
import views.RequestAllocateProjectView;
import views.RequestChangeProjectTitleView;
import views.RequestDeregisterProjectView;
import views.RequestTransferStudentView;
import views.ProjectSubmittedView;

public class SupervisorController extends UserController {
    private static final Scanner sc = new Scanner(System.in);

    protected static final IProjectSupervisorService projectSupervisorService = new ProjectSupervisorService();
    protected static final IRequestSupervisorService requestSupervisorService = new RequestSupervisorService();
    protected static IProjectView projectView;
    protected static IRequestView requestView;

    public void start() {
        int choice;

        do {
        	CommonView.printNavbar("FYPMS > Supervisor Menu");
            System.out.println("1. Change password");
            System.out.println("2. Create projects");
            System.out.println("3. Update submitted project");
            System.out.println("4. View submitted projects");
            System.out.println("5. View/Approve/Reject pending requests");
            System.out.println("6. View request history");
            System.out.println("7. Request student transfer");
            System.out.println("8. Exit");

            choice = sc.nextInt();
            sc.nextLine(); // consume the remaining newline character

            switch (choice) {
                case 1:
                	CommonView.printNavbar("FYPMS > Supervisor Menu > Change Password");
                    if (changePassword()) {
                    	// Restart session by logging out
                        AuthController.endSession();
                        return;
                    }
                    break;
                case 2:
                	CommonView.printNavbar("FYPMS > Supervisor Menu > Create Projects");
                    createProjects();
                    break;
                case 3:
                	CommonView.printNavbar("FYPMS > Supervisor Menu > Update Submitted Project");
                    updateProject();
                    break;
                case 4:
                	CommonView.printNavbar("FYPMS > Supervisor Menu > View Submitted Projects");
                    viewProjects();
                    break;
                case 5:
                	CommonView.printNavbar("FYPMS > Supervisor Menu > Pending Requests (View/Approve/Reject)");
                    viewApproveRejectPendingRequest();
                    break;
                case 6:
                	CommonView.printNavbar("FYPMS > Supervisor Menu > View Requests");
                    viewRequests();
                    break;
                case 7:
                	CommonView.printNavbar("FYPMS > Supervisor Menu > Request to Transfer Student");
                    requestStudentTransfer();
                    break;
                case 8:
                    System.out.println("Exiting supervisor menu");
                    AuthController.endSession();
                    return;
                default:
                    System.out.println("Invalid choice. Please select a number from 1 to 8.");
                    break;
            }

            if (2 <= choice && choice <= 7) {
            	CommonView.pressEnterToContinue();
            }
        } while (true);
    }
    
    // ---------- Helper Methods ---------- //
    protected void createProjects() {
        System.out.print("Enter the number of projects to create: ");
        int projectCount = sc.nextInt();
        sc.nextLine();

        String supervisorID = AuthStore.getCurrentUser().getUserID();
        ArrayList<Project> projects = new ArrayList<Project>();

        for (int i = 0; i < projectCount; i++) {
            System.out.printf("Creating project %d\n", i);
            System.out.print("Project Title: ");
            String title = sc.nextLine();
            Project project = new Project(title, supervisorID, null, ProjectStatus.AVAILABLE);
            projects.add(project);
        }

        projectSupervisorService.createProjects(projects);
    }

    protected void updateProject() {
        String supervisorID = AuthStore.getCurrentUser().getUserID();
        ArrayList<Project> projects = projectSupervisorService.getSubmittedProjects(supervisorID);

        Project selectedProject = SelectorUtils.projectSelector(projects);
        if (selectedProject != null) {
            System.out.printf("Editing project %d - %s\n", selectedProject.getProjectID(), selectedProject.getTitle());
            System.out.print("Enter new title: ");
            String newTitle = sc.nextLine();
            boolean success = projectSupervisorService.updateProjectTitle(selectedProject, newTitle, supervisorID);
            System.out.println(success ? "Project updated successfully!" : "Project update fail!");
        }
    }

    protected void viewProjects() {
        projectView = new ProjectSubmittedView();
        String supervisorID = AuthStore.getCurrentUser().getUserID();
        ArrayList<Project> projects = projectSupervisorService.getSubmittedProjects(supervisorID);

        for (Project project : projects) {
            projectView.displayProjectInfo(project);
            System.out.println();
        }
    }

    protected void viewApproveRejectPendingRequest() {
        String supervisorID = AuthStore.getCurrentUser().getUserID();
        requestView = new RequestChangeProjectTitleView();
        ArrayList<Request> requests = requestSupervisorService.getStudentPendingRequests(supervisorID);

        while (true) {
            System.out.println("requestID\tTitle");
            requests.forEach(request -> requestView.displayRequestInfo(request));

            System.out.println("Select requestID to approve/reject (Enter non-int to exit)");
            if (!sc.hasNextInt()) {
                sc.nextLine();
                return;
            }
            int requestID = sc.nextInt();
            sc.nextLine();

            Optional<Request> optionalSelectedRequest = requests.stream()
                    .filter(request -> request.getRequestID() == requestID)
                    .findFirst();

            if (optionalSelectedRequest.isPresent()) {
                Request selectedRequest = optionalSelectedRequest.get();
                System.out.println("\nSelect option (Enter non-int to exit):");
                System.out.println("1. Approve");
                System.out.println("2. Reject");

                if (!sc.hasNextInt()) {
                    sc.nextLine();
                    return;
                }
                int option = sc.nextInt();
                sc.nextLine();

                switch (option) {
                    case 1:
                        if (selectedRequest.approve()) {
                            System.out.println("Request approved!");
                        } else {
                            System.out.println("Approval failed!");
                        }
                        break;
                    case 2:
                        if (selectedRequest.reject()) {
                            System.out.println("Request rejected!");
                        } else {
                            System.out.println("Rejection failed!");
                        }
                        break;
                }
                return;
            } else {
                System.out.println("Invalid requestID!");
            }
        }
    }

    protected void viewRequests() {
        String supervisorID = AuthStore.getCurrentUser().getUserID();
        ArrayList<Request> incomingRequests = requestSupervisorService.getIncomingRequests(supervisorID);
        ArrayList<Request> outgoingRequests = requestSupervisorService.getOutgoingRequests(supervisorID);

        System.out.println("View incoming/outgoing requests");
        System.out.println("1. Incoming requests");
        System.out.println("2. Outgoing requests");
        System.out.println("(Enter non-int to exit)");

        if (!sc.hasNextInt()) {
            sc.nextLine();
            return;
        }

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1:
                displayRequests(incomingRequests);
                break;
            case 2:
                displayRequests(outgoingRequests);
                break;
        }
    }

    protected void displayRequests(ArrayList<Request> requests) {
        for (Request request : requests) {
            if (request instanceof AllocateProjectRequest) {
                requestView = new RequestAllocateProjectView();
            } else if (request instanceof ChangeProjectTitleRequest) {
                requestView = new RequestChangeProjectTitleView();
            } else if (request instanceof DeregisterProjectRequest) {
                requestView = new RequestDeregisterProjectView();
            } else if (request instanceof TransferStudentRequest) {
                requestView = new RequestTransferStudentView();
            } else {
                continue;
            }

            requestView.displayRequestInfo(request);
        }
    }

    protected void requestStudentTransfer() {
        String supervisorID = AuthStore.getCurrentUser().getUserID();
        String coordinatorID = DataStore.getFYPCoordinatorsData().keySet().iterator().next();
        ArrayList<Project> projects = projectSupervisorService.getSubmittedProjects(supervisorID);
        Project selectedProject = SelectorUtils.projectSelector(projects);

        if (selectedProject == null) {
            return;
        }

        System.out.printf("Selected project %d - %s\n", selectedProject.getProjectID(), selectedProject.getTitle());
        System.out.println("Select replacement supervisor\n");
        Supervisor replacementSupervisor = SelectorUtils.supervisorSelector(DataStore.getSupervisorsData());

        if (replacementSupervisor == null) {
            return;
        }

        System.out.printf("Selected supervisor %s - %s\n",
                replacementSupervisor.getSupervisorID(), replacementSupervisor.getName());
        requestSupervisorService.createTransferStudentRequest(supervisorID, coordinatorID,
                selectedProject.getProjectID(),
                replacementSupervisor.getSupervisorID());
        System.out.println("Transfer request made successfully!");
    }
}
