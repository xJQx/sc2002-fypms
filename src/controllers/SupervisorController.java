package controllers;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import enums.ProjectStatus;
import interfaces.IProjectSupervisorService;
import interfaces.IProjectView;
import models.Project;
import services.ProjectSupervisorService;
import store.AuthStore;
import views.SubmittedProjectView;

public class SupervisorController extends UserController {
    private static final Scanner sc = new Scanner(System.in);

    private static final IProjectSupervisorService projectSupervisorService = new ProjectSupervisorService();

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

        while (true) {
            System.out.println("projectID\tTitle");
            projects.forEach(project -> System.out.printf("%d\t%s\n", project.getProjectID(), project.getTitle()));

            System.out.println("Select projectID (Enter non-int to exit)");
            if (!sc.hasNextInt()) {
                sc.nextLine();
                return;
            }
            int projectID = sc.nextInt();
            sc.nextLine();

            Optional<Project> optionalSelectedProject = projects.stream()
                    .filter(project -> project.getProjectID() == projectID)
                    .findFirst();

            if (optionalSelectedProject.isPresent()) {
                Project selectedProject = optionalSelectedProject.get();
                System.out.printf("Editing project %d - %s\n", projectID, selectedProject.getTitle());
                System.out.print("Enter new title: ");
                String newTitle = sc.nextLine();
                boolean success = projectSupervisorService.updateProjectTitle(selectedProject, newTitle, supervisorID);
                System.out.println(success ? "Project updated successfully!" : "Project update fail!");
                return;
            } else {
                System.out.println("Invalid project ID!");
            }
        }
    }

    protected void viewProjects() {
        IProjectView projectView = new SubmittedProjectView();
        String supervisorID = AuthStore.getCurrentUser().getUserID();
        ArrayList<Project> projects = projectSupervisorService.getSubmittedProjects(supervisorID);

        System.out.println("Projects:\n");
        for (Project project : projects) {
            projectView.displayProjectInfo(project);
            System.out.println();
        }
    }

    protected void viewApproveRejectPendingRequest() {
        
    }

    public void start() {
        int choice;

        do {
            System.out.println("Supervisor Menu");
            System.out.println("1. Change password");
            System.out.println("2. Create projects");
            System.out.println("3. Update project");
            System.out.println("4. View projects");
            System.out.println("5. View/Approve/Reject pending requests");
            System.out.println("6. View request history");
            System.out.println("7. Request student transfer");
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
                    break;
                case 6:
                    break;
                case 7:
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
