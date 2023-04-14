package controllers;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import enums.ProjectStatus;
import enums.RequestStatus;
import enums.RequestType;
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
import stores.AuthStore;
import stores.DataStore;
import utils.SelectorUtils;
import utils.TextDecorationUtils;
import views.CommonView;
import views.ProjectSubmittedView;

/**
 * The {@link FYPCoordinatorController} class extends
 * {@link SupervisorController} and provides utility methods for managing the
 * FYP Coordinator user role within the application.
 * This class utilizes {@link IRequestFYPCoordinatorService} and
 * {@link IProjectFYPCoordinatorService} to interact with the underlying data
 * and perform necessary operations.
 */
public class FYPCoordinatorController extends SupervisorController {
    /**
     * {@link Scanner} object to get input from the user.
     */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * An instance of the {@link IRequestFYPCoordinatorService} interface
     * used to perform operations related to requests in the FYPMS system.
     */
    private static final IRequestFYPCoordinatorService requestFYPCoordinatorService = new RequestFYPCoordinatorService();

    /**
     * An instance of the {@link IProjectFYPCoordinatorService} interface
     * used to perform operations related to projects in the FYPMS system.
     */
    private static final IProjectFYPCoordinatorService projectFYPCoordinatorService = new ProjectFYPCoordinatorService();

    /**
     * Constructs an instance of the {@link FYPCoordinatorController} class.
     */
    public FYPCoordinatorController() {
    };

    /**
     * Starts the FYP Coordinator menu and prompts the user to select an action.
     * The method loops until the user chooses to exit the FYP Coordinator menu.
     */
    @Override
    public void start() {
        int choice;

        do {
            // Check if fyp coordinator has pending requests
            boolean hasPendingRequest = 
                requestFYPCoordinatorService.getStudentPendingRequests(AuthStore.getCurrentUser().getUserID()).size() +
                requestFYPCoordinatorService.getSupervisorPendingRequests(AuthStore.getCurrentUser().getUserID()).size() > 0;

            // Options for user
            CommonView.printNavbar("FYPMS > FYP Coordinator Menu");
            System.out.println(TextDecorationUtils.underlineText("SETTINGS"));
            System.out.println("1. Change password");
            
            System.out.println(TextDecorationUtils.underlineText("\nPROJECTS"));
            System.out.println("2. Create projects");
            System.out.println("3. Update project");
            System.out.println("4. View all projects");
            System.out.println("5. View all projects by filters");
            
            System.out.println(TextDecorationUtils.underlineText("\nREQUESTS"));
            System.out.println("6. View/Approve/Reject " + TextDecorationUtils.boldText("PENDING") + " requests " + (hasPendingRequest ? TextDecorationUtils.italicText("(NEW)"): ""));
            System.out.println("7. View all request history");
            System.out.println("8. Request student transfer");
            
            System.out.println("\n9. Exit");

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
                    CommonView.printNavbar("FYPMS > FYP Coordinator Menu > All View Projects");
                    viewProjects();
                    break;
                case 5:
                    CommonView.printNavbar("FYPMS > FYP Coordinator Menu > All View Projects (with filtering)");
                    viewProjectsByFilter();
                    break;
                case 6:
                    CommonView.printNavbar("FYPMS > FYP Coordinator Menu > Pending Requests (View/Approve/Reject)");
                    viewApproveRejectPendingRequest();
                    break;
                case 7:
                    CommonView.printNavbar("FYPMS > FYP Coordinator Menu > View All Requests");
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
    /**
     * Displays all projects with filters applied.
     */
    private void viewProjectsByFilter() {
        System.out.println("\nSelect project by filter menu");
        System.out.println("1. Filter by project status");
        System.out.println("2. Filter by supervisorID");
        System.out.printf("Select option (Enter to return): ");

        int option;

        while (true) {
            String input = sc.nextLine();

            if (input.isEmpty()) { // If the input is empty (user pressed Enter), return
                return;
            } else if (input.matches("[0-9]+")) { // If the input is an integer, proceed with the code
                option = Integer.parseInt(input);
                break;
            } else { // If the input is not empty and not an integer, prompt the user to enter again
                System.out.println("Invalid input. Please enter an option or press Enter to return.\n");
                continue;
            }
        }

        ArrayList<Project> projects;
        IProjectView projectView = new ProjectSubmittedView();

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

    /**
     * Overrides the {@link SupervisorController}'s method for viewing, approving,
     * and rejecting pending requests. This implementation is tailored for the FYP
     * Coordinator role, handling additional request types and updating project
     * availability when necessary.
     */
    @Override
    protected void viewApproveRejectPendingRequest() {
        String fypCoordinatorID = AuthStore.getCurrentUser().getUserID();

        while (true) {
            // Retrieve all the different requests
            ArrayList<Request> supervisorPendingRequests = requestFYPCoordinatorService.getSupervisorPendingRequests(fypCoordinatorID);
            ArrayList<Request> allocationPendingRequests = requestFYPCoordinatorService.getIncomingRequests(fypCoordinatorID).stream()
                                                .filter(request -> request.getType() == RequestType.ALLOCATE_PROJECT &&
                                                        request.getStatus() == RequestStatus.PENDING)
                                                .collect(Collectors.toCollection(ArrayList::new));
            ArrayList<Request> deallocationPendingRequests = requestFYPCoordinatorService.getIncomingRequests(fypCoordinatorID).stream()
                                                .filter(request -> request.getType() == RequestType.DEREGISTER_PROJECT &&
                                                        request.getStatus() == RequestStatus.PENDING)
                                                .collect(Collectors.toCollection(ArrayList::new));
            ArrayList<Request> changeProjectTitlePendingReqeusts = requestFYPCoordinatorService.getIncomingRequests(fypCoordinatorID).stream()
                                                .filter(request -> request.getType() == RequestType.CHANGE_PROJECT_TITLE &&
                                                        request.getStatus() == RequestStatus.PENDING)
                                                .collect(Collectors.toCollection(ArrayList::new));

            // Display Options
            System.out.println("1. Supervisor requests " + (supervisorPendingRequests.size() > 0 ? TextDecorationUtils.italicText("(NEW)"): ""));
            System.out.println("2. Allocation requests " + (allocationPendingRequests.size() > 0 ? TextDecorationUtils.italicText("(NEW)"): ""));
            System.out.println("3. Deallocation requests " + (deallocationPendingRequests.size() > 0 ? TextDecorationUtils.italicText("(NEW)"): ""));
            System.out.println("4. Change Project Title requests " + (changeProjectTitlePendingReqeusts.size() > 0 ? TextDecorationUtils.italicText("(NEW)"): ""));
            System.out.printf("Select request option (Enter to return): ");

            int option;
            String input = sc.nextLine();

            if (input.isEmpty()) { // If the input is empty (user pressed Enter), return
                return;
            } else if (input.matches("[0-9]+")) { // If the input is an integer, proceed with the code
                option = Integer.parseInt(input);
            } else { // If the input is not empty and not an integer, prompt the user to enter again
                System.out.println("Invalid input. Please enter an option or press Enter to return.\n");
                continue;
            }

            if (option < 1 || option > 4) {
                System.out.println("Invalid option!");
                continue;
            }
            ArrayList<Request> requests;

            switch (option) {
                case 1:
                    requests = supervisorPendingRequests;
                    break;
                case 2:
                    requests = allocationPendingRequests;
                    break;
                case 3:
                    requests = deallocationPendingRequests;
                    break;
                case 4:
                    requests = changeProjectTitlePendingReqeusts;
                    break;
                default:
                    requests = null;
            }

            // Select a Request
            Request request = SelectorUtils.requestSelector(requests);
            if (request == null) {
                return;
            }

            while (true) {
                // Show warning when the supervisor already supervising the maximum allowable number of projects for allocate project requests
                Supervisor supervisor = request.getProject().getSupervisor();
                if (request.getType() == RequestType.ALLOCATE_PROJECT && supervisor.getNumOfProjects() >= Supervisor.MAX_PROJECTS) {
                    System.out.printf("Warning: %s already is supervising %d projects.\n", supervisor.getName(), Supervisor.MAX_PROJECTS);
                }

                // Approve or Reject
                System.out.println("1. Approve");
                System.out.println("2. Reject");
                System.out.printf("Select approve/reject (Enter to return): ");

                int choice;
                input = sc.nextLine();

                if (input.isEmpty()) { // If the input is empty (user pressed Enter), return
                    return;
                } else if (input.matches("[0-9]+")) { // If the input is an integer, proceed with the code
                    choice = Integer.parseInt(input);
                } else { // If the input is not empty and not an integer, prompt the user to enter again
                    System.out.println("Invalid input. Please enter an option or press Enter to return.\n");
                    continue;
                }

                if (choice < 1 || choice > 2) {
                    System.out.println("Invalid option!");
                    continue;
                }

                switch (choice) {
                    case 1:
                        if (request.approve()) {
                            System.out.println("Request approved!");
                            updateAvailabilityOfProjects(request);
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

    /**
     * A utility function that updates the availability of projects after a request
     * (allocate, transfer, deregister) has been completed.
     *
     * @param request A {@link Request} object representing the request.
     */
    private void updateAvailabilityOfProjects(Request request) {
        if (request.getType() == RequestType.TRANSFER_STUDENT) {
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
        } else if (request.getType() == RequestType.ALLOCATE_PROJECT) {
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
        } else if (request.getType() == RequestType.DEREGISTER_PROJECT) {
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

    /**
     * Logs the status of the supervisor, including their name, ID, and the number
     * of projects they are currently supervising.
     *
     * @param name          The supervisor's name.
     * @param id            The supervisor's ID.
     * @param numOfProjects The number of projects the supervisor is currently
     *                      supervising.
     */
    private void logSupervisorStatus(String name, String id, int numOfProjects) {
        System.out.printf("\n%s (%s) is now supervising %d projects.\n", name, id, numOfProjects);
    }

    /**
     * Logs a message indicating that a supervisor's maximum project capacity has
     * been reached adn that remaining available projects are now unavailable.
     */
    private void logMaxCapacityReached() {
        System.out.println("Max supervising projects reached!");
        System.out.println("Remaining available projects are now unavailable.");
    }

    /**
     * Logs a message indicating that a supervisor's maximum project capacity has
     * not been reached and that unavailable projects are not available.
     */
    private void logMaxCapacityUnreached() {
        System.out.println("Unavailable projects are now available.");
    }

    /**
     * Displays all projects
     */
    @Override
    protected void viewProjects() {
        ArrayList<Project> allProjects = projectFYPCoordinatorService.getAllProjects();
        System.out.println("Displaying all projects:\n");
        projectView = new ProjectSubmittedView();
        allProjects.forEach(project -> {
            projectView.displayProjectInfo(project);
            System.out.println();
        });
        System.out.println("\nDisplayed all projects.");
    }

    /**
     * Displays all requests
     */
    @Override
    protected void viewRequests() {
        ArrayList<Request> allRequests = requestFYPCoordinatorService.getAllRequests();
        System.out.println("Displaying all requests:\n");
        displayRequests(allRequests);
        System.out.println("\nDisplayed all requests.");
    }
}
