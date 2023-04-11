package controllers;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import enums.ProjectStatus;
import interfaces.IProjectFYPCoordinatorService;
import interfaces.IProjectView;
import interfaces.IRequestFYPCoordinatorService;
import models.AllocateProjectRequest;
import models.DeregisterProjectRequest;
import models.Project;
import models.Request;
import models.Supervisor;
import models.TransferStudentRequest;
import services.ProjectFYPCoordinatorService;
import services.RequestFYPCoordinatorService;
import store.AuthStore;
import store.DataStore;
import utils.SelectorUtils;
import views.CommonView;
import views.SubmittedProjectView;

public class FYPCoordinatorController extends SupervisorController {
    private static final Scanner sc = new Scanner(System.in);

    private static final IRequestFYPCoordinatorService requestFYPCoordinatorService = new RequestFYPCoordinatorService();
    private static final IProjectFYPCoordinatorService projectFYPCoordinatorService = new ProjectFYPCoordinatorService();

    @Override
    public void start() {
        int choice;

        do {
        	CommonView.printNavbar("FYPMS > FYP Coordinator Menu");
            System.out.println("1. Change password");
            System.out.println("2. Create projects");
            System.out.println("3. Update project");
            System.out.println("4. View projects");
            System.out.println("5. View projects by filters");
            System.out.println("6. View/Approve/Reject pending requests");
            System.out.println("7. View request history");
            System.out.println("8. Request student transfer");
            System.out.println("9. Exit");

            choice = sc.nextInt();
            sc.nextLine(); // consume the remaining newline character

            switch (choice) {
                case 1:
                	CommonView.printNavbar("FYPMS > FYP Coordinator Menu > Change Password");
                    if (changePassword()) {
                    	// Restart session by logging out
                        AuthController.endSession();
                        return;
                    }
                    break;
                case 2:
                	CommonView.printNavbar("FYPMS > FYP Coordinator Menu > Create Projects");
                    createProjects();
                    break;
                case 3:
                	CommonView.printNavbar("FYPMS > FYP Coordinator Menu > Update Submitted Project");
                    updateProject();
                    break;
                case 4:
                	CommonView.printNavbar("FYPMS > FYP Coordinator Menu > View Projects");
                    viewProjects();
                    break;
                case 5:
                	CommonView.printNavbar("FYPMS > FYP Coordinator Menu > View Projects (with filtering)");
                    viewProjectsByFilter();
                    break;
                case 6:
                	CommonView.printNavbar("FYPMS > FYP Coordinator Menu > Pending Requests (View/Approve/Reject)");
                    viewApproveRejectPendingRequest();
                    break;
                case 7:
                	CommonView.printNavbar("FYPMS > FYP Coordinator Menu > View Requests");
                    viewRequests();
                    break;
                case 8:
                	CommonView.printNavbar("FYPMS > FYP Coordinator Menu > Request to Transfer Student");
                    requestStudentTransfer();
                    break;
                case 9:
                    System.out.println("Exiting FYP coordinator menu");
                    AuthController.endSession();
                    return;
                default:
                    System.out.println("Invalid choice. Please select a number from 1 to 9.");
                    break;
            }

            if (2 <= choice && choice <= 8) {
            	CommonView.pressEnterToContinue();
            }
        } while (true);
    }
    
    // ---------- Helper Methods ---------- //
    private void viewProjectsByFilter() {
        System.out.println("\nSelect project by filter menu");
        System.out.println("Choose an option");
        System.out.println("1. Filter by project status");
        System.out.println("2. Filter by supervisorID");
        System.out.println("(To quit, do not enter 1 or 2)");

        if (!sc.hasNextInt()) {
            sc.nextLine();
            return;
        }

        int option = sc.nextInt();
        sc.nextLine();

        if (option != 1 && option != 2) {
            return;
        }

        ArrayList<Project> projects;
        IProjectView projectView = new SubmittedProjectView();

        switch (option) {
            case 1:
                ProjectStatus projectStatus = SelectorUtils.projectStatusSelector();
                if (projectStatus == null) {
                    return;
                }
                projects = projectFYPCoordinatorService.getAllProjectsByStatus(projectStatus);
                break;
            case 2:
                Supervisor supervisor = SelectorUtils.supervisorSelector(DataStore.getSupervisorsData());
                if (supervisor == null) {
                    return;
                }
                projects = projectFYPCoordinatorService.getAllProjectsBySupervisor(supervisor.getSupervisorID());
                break;
            default:
                projects = projectFYPCoordinatorService.getAllProjects();
        }

        projects.forEach(project -> {
        	projectView.displayProjectInfo(project);
        	System.out.println();
        });
    }

    @Override
    protected void viewApproveRejectPendingRequest() {
        String fypCoordinatorID = AuthStore.getCurrentUser().getUserID();

        while (true) {
            System.out.println("Select request option (Enter non-int to exit):");
            System.out.println("1. Supervisor requests");
            System.out.println("2. Allocation requests");
            System.out.println("3. Deallocation requests");

            if (!sc.hasNextInt()) {
                sc.nextLine();
                return;
            }

            int option = sc.nextInt();
            sc.nextLine();

            if (option < 1 || option > 3) {
                System.out.println("Invalid option!");
                continue;
            }
            ArrayList<Request> requests;

            switch (option) {
                case 1:
                    requests = requestFYPCoordinatorService
                            .getSupervisorPendingRequests(fypCoordinatorID);
                    break;
                case 2:
                    requests = requestFYPCoordinatorService.getIncomingRequests(fypCoordinatorID).stream()
                            .filter(request -> request instanceof AllocateProjectRequest)
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;
                case 3:
                    requests = requestFYPCoordinatorService.getIncomingRequests(fypCoordinatorID).stream()
                            .filter(request -> request instanceof DeregisterProjectRequest)
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;
                default:
                    requests = null;
            }

            Request request = SelectorUtils.requestSelector(requests);
            if (request == null) {
                return;
            }

            while (true) {
                System.out.println("Select approve/reject (Enter non-int to exit)");
                System.out.println("1. Approve");
                System.out.println("2. Reject");

                if (!sc.hasNextInt()) {
                    sc.nextLine();
                    return;
                }

                int choice = sc.nextInt();
                sc.nextLine();

                if (choice < 1 || choice > 2) {
                    System.out.println("Invalid option!");
                    continue;
                }

                switch (choice) {
                    case 1:
                        if (request.approve()) {
                            System.out.println("Request approved!");
                            updateSupervisorProjectCount(request);
                        } else {
                            System.out.println("Approval failed!");
                        }
                        break;
                    case 2:
                        if (request.reject()) {
                            System.out.println("Request rejected!");
                        } else {
                            System.out.println("Approval failed!");
                        }
                        break;
                }
                return;
            }
        }
    }

    private void updateSupervisorProjectCount(Request request) {
        if (request instanceof TransferStudentRequest) {
            TransferStudentRequest transferRequest = (TransferStudentRequest) request;
            Supervisor supervisor = (Supervisor) transferRequest.getSender();

            logSupervisorStatus(supervisor.getName(),
                    supervisor.getSupervisorID(),
                    supervisor.getNumOfProjects());

            if (supervisor.getNumOfProjects() < Supervisor.MAX_PROJECTS) {
                projectSupervisorService.updateUnavailableProjectsToAvailable(supervisor.getSupervisorID());
                logMaxCapacityUnreached();
            }

            String replacementSupervisorID = transferRequest.getReplacementSupervisorID();
            Supervisor replacementSupervisor = DataStore.getSupervisorsData().get(replacementSupervisorID);

            if (replacementSupervisor == null) {
                return;
            }

            logSupervisorStatus(replacementSupervisor.getName(),
                    replacementSupervisor.getSupervisorID(),
                    replacementSupervisor.getNumOfProjects());

            if (replacementSupervisor.getNumOfProjects() >= Supervisor.MAX_PROJECTS) {
                projectSupervisorService.updateRemainingProjectsToUnavailable(replacementSupervisorID);
                logMaxCapacityReached();
            }
        } else if (request instanceof AllocateProjectRequest) {
            AllocateProjectRequest allocateRequest = (AllocateProjectRequest) request;
            Supervisor supervisor = allocateRequest.getProject().getSupervisor();
            String supervisorID = supervisor.getSupervisorID();

            logSupervisorStatus(supervisor.getName(),
                    supervisor.getSupervisorID(),
                    supervisor.getNumOfProjects());

            if (supervisor.getNumOfProjects() >= Supervisor.MAX_PROJECTS) {
                projectSupervisorService.updateRemainingProjectsToUnavailable(supervisorID);
                logMaxCapacityReached();
            }
        } else if (request instanceof DeregisterProjectRequest) {
            DeregisterProjectRequest deregisterRequest = (DeregisterProjectRequest) request;
            Supervisor supervisor = deregisterRequest.getProject().getSupervisor();
            String supervisorID = supervisor.getSupervisorID();

            logSupervisorStatus(supervisor.getName(),
                    supervisor.getSupervisorID(),
                    supervisor.getNumOfProjects());

            if (supervisor.getNumOfProjects() < Supervisor.MAX_PROJECTS) {
                projectSupervisorService.updateUnavailableProjectsToAvailable(supervisorID);
                logMaxCapacityUnreached();
            }
        }
    }

    private void logSupervisorStatus(String name, String id, int numOfProjects) {
        System.out.printf("\n%s (%s) is now supervising %d projects.\n", name, id, numOfProjects);
    }

    private void logMaxCapacityReached() {
        System.out.println("Max supervising projects reached!");
        System.out.println("Remaining available projects are now unavailable.");
    }

    private void logMaxCapacityUnreached() {
        System.out.println("Unavailable projects are now available.");
    }

    @Override
    protected void viewProjects() {
        ArrayList<Request> allRequests = requestFYPCoordinatorService.getAllRequests();
        System.out.println("Displaying all requests:\n");
        displayRequests(allRequests);
        System.out.println("\nDisplayed all requests:");
    }
}
