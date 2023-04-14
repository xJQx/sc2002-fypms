package controllers;

import java.util.ArrayList;
import java.util.Scanner;

import enums.RequestType;
import interfaces.IProjectStudentService;
import interfaces.IProjectView;
import interfaces.IRequestStudentService;
import interfaces.IRequestView;
import models.Project;
import models.Request;
import models.Student;

import services.ProjectStudentService;
import services.RequestStudentService;
import stores.AuthStore;
import stores.DataStore;
import utils.SelectorUtils;
import utils.TextDecorationUtils;
import views.ProjectAvailableView;
import views.RequestAllocateProjectView;
import views.RequestChangeProjectTitleView;
import views.RequestDeregisterProjectView;
import views.CommonView;
import views.ProjectAllocatedView;

/**
 * The {@link StudentController} class is responsible for handling the
 * student-specific user interface and user interactions. It extends the
 * {@link UserController} class and provides functionality for students to
 * view available projects, view allocated projects, send project allocation
 * requests, request project title changes, and request FYP deregistration.
 * This class utilizes {@link IProjectStudentService} and
 * {@link IRequestStudentService} to interact with the underlying data and
 * perform necessary operations.
 */
public class StudentController extends UserController {
    /**
     * {@link Scanner} object to get input from the user.
     */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * The {@link IProjectStudentService} instance used for accessing project data
     * and services for students.
     */
    private static final IProjectStudentService projectStudentService = new ProjectStudentService();

    /**
     * The {@link IRequestStudentService} instance used for accessing request data
     * and services for students.
     */
    private static final IRequestStudentService requestStudentService = new RequestStudentService();

    /**
     * The project view used to display project information.
     */
    protected static IRequestView requestView;

    /**
     * Constructs an instance of the {@link StudentController} class.
     */
    public StudentController() {
    };

    /**
     * Starts the student menu and prompts the user to select an action.
     * The method loops until the user chooses to exit the student menu.
     */
    public void start() {
        IProjectView projectView;
        int choice;

        do {
            CommonView.printNavbar("FYPMS > Student Menu");
            System.out.println(TextDecorationUtils.underlineText("SETTINGS"));
            System.out.println("1. Change password");
            
            System.out.println(TextDecorationUtils.underlineText("\nPROJECTS"));
            System.out.println("2. View available projects");
            System.out.println("3. View allocated projects");
            
            System.out.println(TextDecorationUtils.underlineText("\nREQUESTS"));
            System.out.println("4. View requests status and history");
            System.out.println("5. Request project allocation");
            System.out.println("6. Request project title change");
            System.out.println("7. Request FYP deregistration");

            System.out.println("\n8. Exit");

            choice = sc.nextInt();
            sc.nextLine(); // consume the remaining newline character

            switch (choice) {
                case 1:
                    CommonView.printNavbar("FYPMS > Student Menu > Change Password");
                    if (changePassword()) {
                        // Restart session by logging out
                        AuthController.endSession();
                        return;
                    }
                    break;
                case 2:
                    CommonView.printNavbar("FYPMS > Student Menu > Available Projects");
                    projectView = new ProjectAvailableView();
                    viewAvailableProject(projectView);
                    break;
                case 3:
                    CommonView.printNavbar("FYPMS > Student Menu > Allocated Project");
                    projectView = new ProjectAllocatedView();
                    viewAllocatedProject(projectView);
                    break;
                case 4:
                    CommonView.printNavbar("FYPMS > Student Menu > Requests");
                    viewRequests();
                    break;
                case 5:
                    CommonView.printNavbar("FYPMS > Student Menu > Send Project to Coordinator for Allocation");
                    sendProjectToCoordinator();
                    break;
                case 6:
                    CommonView.printNavbar("FYPMS > Student Menu > Request to Change Project Title");
                    requestTitleChange();
                    break;
                case 7:
                    CommonView.printNavbar("FYPMS > Student Menu > Request to Deregister from FYP");
                    requestFYPDeregistration();
                    break;
                case 8:
                    System.out.println("Exiting student menu");
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
    /**
     * Displays the allocated project for the current student.
     *
     * @param projectView the {@link IProjectView} instance for displaying project
     *                    information
     */
    private void viewAllocatedProject(IProjectView projectView) {
        Project project = projectStudentService.getAllocatedProject(AuthStore.getCurrentUser().getUserID());

        if (project == null) {
            System.out.println("You have not registered a project.");
        } else {
            projectView.displayProjectInfo(project);
        }
    }

    /**
     * Displays the list of available projects for the current student.
     *
     * @param projectView the {@link IProjectView} instance for displaying project
     *                    information
     */
    private void viewAvailableProject(IProjectView projectView) {
        Student student = (Student) AuthStore.getCurrentUser();
        if (student.getIsDeregistered()) {
            System.out.println("You are not allowed to make selection again as you deregistered your FYP.");
            return;
        }

        Project allocatedProject = projectStudentService.getAllocatedProject(student.getStudentID());
        ArrayList<Project> availableProjects = projectStudentService.getAvailableProjects();

        if (allocatedProject != null) {
            String message = "You are currently allocated to a FYP and do not have access to available project list.";
            System.out.println(message);
        } else {
            for (Project project : availableProjects) {
                projectView.displayProjectInfo(project);
                System.out.println();
            }
        }
    }

    /**
     * Displays the request status and history for the current student.
     */
    private void viewRequests() {
        System.out.println("Displaying all student requests");
        ArrayList<Request> requests = requestStudentService.getStudentRequests(AuthStore.getCurrentUser().getUserID());

        for (Request request : requests) {
            if (request.getType() == RequestType.ALLOCATE_PROJECT) {
                requestView = new RequestAllocateProjectView();
            } else if (request.getType() == RequestType.CHANGE_PROJECT_TITLE) {
                requestView = new RequestChangeProjectTitleView();
            } else if (request.getType() == RequestType.DEREGISTER_PROJECT) {
                requestView = new RequestDeregisterProjectView();
            } else {
                continue;
            }
            requestView.displayRequestInfo(request);
        }
    }

    /**
     * Sends a project allocation request to the FYP coordinator.
     */
    private void sendProjectToCoordinator() {
        String studentID = AuthStore.getCurrentUser().getUserID();
        Project reservedProject = projectStudentService.getReservedProject(studentID);
        Project allocatedProject = projectStudentService.getAllocatedProject(studentID);

        if (reservedProject != null) {
            System.out.println("You have already reserved a project! You are not allowed to register another project!");
            return;
        }

        if (allocatedProject != null) {
            System.out.println("You are already allocated to a FYP.");
            return;
        }

        ArrayList<Project> projects = projectStudentService.getAvailableProjects();
        Project project = SelectorUtils.projectSelector(projects);

        if (project == null) {
            return;
        }

        String coordinatorID = DataStore.getFYPCoordinatorsData().keySet().iterator().next();
        requestStudentService.createAllocateProjectRequest(studentID, coordinatorID, project.getProjectID());

        System.out.println("Request sent to FYP coordinator successfully!");
    }

    /**
     * Sends a request for a project title change.
     */
    private void requestTitleChange() {
        String studentID = AuthStore.getCurrentUser().getUserID();
        Project project = projectStudentService.getAllocatedProject(studentID);

        if (project == null) {
            System.out.println("You have not registered a project.");
            return;
        }

        String supervisorID = project.getSupervisor().getUserID();
        int projectID = project.getProjectID();

        System.out.print("Enter new project title: ");
        String newTitle = sc.nextLine();

        requestStudentService.createChangeProjectTitleRequest(
                studentID,
                supervisorID,
                projectID,
                newTitle);

        System.out.println("Title request sent successfully!");
    }

    /**
     * Sends a request for FYP deregistration.
     */
    private void requestFYPDeregistration() {
        String studentID = AuthStore.getCurrentUser().getUserID();
        String fypCoordinatorID = DataStore.getFYPCoordinatorsData().keySet().iterator().next();
        Project project = projectStudentService.getAllocatedProject(studentID);

        if (project == null) {
            System.out.println("Deregistration not allowed! You have not registered a project.");
            return;
        }

        ProjectAllocatedView projectView = new ProjectAllocatedView();
        projectView.displayProjectInfo(project);

        String option;

        do {
            System.out.println("\nAre you sure you want to deregister this project?");
            System.out.printf("Enter Y to confirm or N to cancel: ");
            option = sc.next();
            sc.nextLine();

        } while (!option.equalsIgnoreCase("y") && !option.equalsIgnoreCase("n"));

        if (option.equalsIgnoreCase("n")) {
            System.out.println("FYP deregistration request cancelled.");
            return;
        }

        requestStudentService.createDeregisterProjectRequest(studentID, fypCoordinatorID, project.getProjectID());
        System.out.println("FYP deregistration request sent successfully!");
    }
}
