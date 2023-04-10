package controllers;

import java.util.ArrayList;
import java.util.Scanner;

import enums.ProjectStatus;
import interfaces.IProjectFYPCoordinatorService;
import interfaces.IProjectView;
import interfaces.IRequestFYPCoordinatorService;
import models.Project;
import models.Supervisor;
import services.ProjectFYPCoordinatorService;
import services.RequestFYPCoordinatorService;
import store.DataStore;
import utils.SelectorUtils;
import views.SubmittedProjectView;

public class FYPCoordinatorController extends SupervisorController {
    private static final Scanner sc = new Scanner(System.in);

    private static final IRequestFYPCoordinatorService requestFYPCoordinatorService = new RequestFYPCoordinatorService();
    private static final IProjectFYPCoordinatorService projectFYPCoordinatorService = new ProjectFYPCoordinatorService();

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

        projects.forEach(project -> projectView.displayProjectInfo(project));
    }

    @Override
    public void start() {
        int choice;

        do {
            System.out.println("FYP Coordinator Menu");
            System.out.println("1. Change password");
            System.out.println("2. Create projects");
            System.out.println("3. Update project");
            System.out.println("4. View projects");
            System.out.println("5. View/Approve/Reject pending requests");
            System.out.println("6. View request history");
            System.out.println("7. Request student transfer");
            System.out.println("8. View projects by filters");
            System.out.println("8. Exit");

            choice = sc.nextInt();
            sc.nextLine(); // consume the remaining newline character

            switch (choice) {
                case 1:
                    if (changePassword()) {
                        // TODO: reset session
                    }
                    break;
                case 2:
                    createProjects();
                    break;
                case 3:
                    updateProject();
                    break;
                case 4:
                    viewProjects();
                    break;
                case 5:
                    viewApproveRejectPendingRequest();
                    break;
                case 6:
                    viewRequests();
                    break;
                case 7:
                    requestStudentTransfer();
                    break;
                case 8:
                    System.out.println("Exiting supervisor menu");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a number from 1 to 8.");
                    break;
            }
        } while (true);
    }

}
