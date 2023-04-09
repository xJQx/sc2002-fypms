package controllers;

import java.util.ArrayList;
import java.util.Scanner;

import interfaces.IProjectStudentService;
import interfaces.IProjectView;

import models.Project;
import models.Student;
import services.ProjectStudentService;

import store.AuthStore;

import views.AvailableProjectView;
import views.AllocatedProjectView;

public class StudentController extends UserController {
    private static final Scanner sc = new Scanner(System.in);
    private static final IProjectStudentService studentService = new ProjectStudentService();

    private void viewAllocatedProject(IProjectView projectView) {
        Project project = studentService.getAllocatedProject(AuthStore.getCurrentUser().getUserID());

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

        Project allocatedProject = studentService.getAllocatedProject(student.getStudentID());
        ArrayList<Project> availableProjects = studentService.getAvailableProjects();

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

            switch (choice) {
                case 1:
                    if (changePassword()) {
                        // reset session
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
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    System.out.println("Exiting student menu");
                    return;
            }
        } while (choice < 1 || choice > 3);

    }
}
