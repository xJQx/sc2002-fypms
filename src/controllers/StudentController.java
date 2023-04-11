package controllers;

import java.util.ArrayList;
import java.util.Scanner;

import interfaces.IProjectStudentService;
import interfaces.IProjectView;
import interfaces.IRequestStudentService;
import interfaces.IRequestView;
import models.AllocateProjectRequest;
import models.ChangeProjectTitleRequest;
import models.DeregisterProjectRequest;
import models.Project;
import models.Request;
import models.Student;

import services.ProjectStudentService;
import services.RequestStudentService;

import store.AuthStore;
import store.DataStore;
import utils.SelectorUtils;
import views.AvailableProjectView;
import views.RequestAllocateProjectView;
import views.RequestChangeProjectTitleView;
import views.RequestDeregisterProjectView;
import views.AllocatedProjectView;

public class StudentController extends UserController {
    private static final Scanner sc = new Scanner(System.in);
    private static final IProjectStudentService projectStudentService = new ProjectStudentService();
    private static final IRequestStudentService requestStudentService = new RequestStudentService();
    protected static IRequestView requestView;

    public void start() {
        IProjectView projectView;
        int choice;

        do {
            System.out.println("Menu");
            System.out.println("1. Change password");
            System.out.println("2. View available projects");
            System.out.println("3. View allocated projects");
            System.out.println("4. View requests status and history");
            System.out.println("5. Send project to coordinator");
            System.out.println("6. Request project title change");
            System.out.println("7. Request FYP deregistration");
            System.out.println("8. Exit");

            choice = sc.nextInt();
            sc.nextLine(); // consume the remaining newline character

            switch (choice) {
                case 1:
                    if (changePassword()) {
                        // Restart session by logging out
                        AuthController.endSession();
                        return;
                    }
                    break;
                case 2:
                    projectView = new AvailableProjectView();
                    viewAvailableProject(projectView);
                    break;
                case 3:
                    projectView = new AllocatedProjectView();
                    viewAllocatedProject(projectView);
                    break;
                case 4:
                    viewRequests();
                    break;
                case 5:
                    sendProjectToCoordinator();
                    break;
                case 6:
                    requestTitleChange();
                    break;
                case 7:
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
        } while (true);
    }

    // ---------- Helper Methods ---------- //
    private void viewAllocatedProject(IProjectView projectView) {
        Project project = projectStudentService.getAllocatedProject(AuthStore.getCurrentUser().getUserID());

        if (project == null) {
            System.out.println("You have not registered a project.");
        } else {
            projectView.displayProjectInfo(project);
        }
    }

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

    private void viewRequests() {
        System.out.println("Displaying all student requests");
        ArrayList<Request> requests = requestStudentService.getStudentRequests(AuthStore.getCurrentUser().getUserID());

        for (Request request : requests) {
            if (request instanceof AllocateProjectRequest) {
                requestView = new RequestAllocateProjectView();
            } else if (request instanceof ChangeProjectTitleRequest) {
                requestView = new RequestChangeProjectTitleView();
            } else if (request instanceof DeregisterProjectRequest) {
                requestView = new RequestDeregisterProjectView();
            } else {
                continue;
            }
            requestView.displayRequestInfo(request);
        }
    }

    private void sendProjectToCoordinator() {
        String studentID = AuthStore.getCurrentUser().getUserID();
        ArrayList<Project> projects = projectStudentService.getAvailableProjects();
        Project project = SelectorUtils.projectSelector(projects);

        if (project == null) {
            return;
        }

        String coordinatorID = DataStore.getFYPCoordinatorsData().keySet().iterator().next();
        requestStudentService.createAllocateProjectRequest(studentID, coordinatorID, project.getProjectID());

        System.out.println("Request sent to FYP coordinator successfully!");
    }

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

    private void requestFYPDeregistration() {
        String studentID = AuthStore.getCurrentUser().getUserID();
        String fypCoordinatorID = DataStore.getFYPCoordinatorsData().keySet().iterator().next();
        Project project = projectStudentService.getAllocatedProject(studentID);

        if (project == null) {
            System.out.println("Deregistration not allowed! You have not registered a project.");
            return;
        }

        AllocatedProjectView projectView = new AllocatedProjectView();
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
