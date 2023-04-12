package utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import enums.ProjectStatus;
import enums.RequestType;
import interfaces.IRequestView;
import models.Project;
import models.Request;
import models.Supervisor;
import views.RequestAllocateProjectView;
import views.RequestChangeProjectTitleView;
import views.RequestDeregisterProjectView;
import views.RequestTransferStudentView;

public class SelectorUtils {
    private static final Scanner sc = new Scanner(System.in);

    public static Project projectSelector(ArrayList<Project> projects) {
        while (true) {
            System.out.println("projectID\t\tTitle");
            projects.forEach(project -> System.out.printf("%-20d\t%s\n", project.getProjectID(), project.getTitle()));

            System.out.printf("\nSelect projectID (Enter to return): ");
            String input = sc.nextLine();
            int projectID;

            if (input.isEmpty()) { // If the input is empty (user pressed Enter), return
                return null;
            } else if (input.matches("[0-9]+")) { // If the input is an integer, proceed with the code
                projectID = Integer.parseInt(input);
            } else { // If the input is not empty and not an integer, prompt the user to enter again
                System.out.println("Invalid input. Please enter a projectID or press Enter to return.\n");
                continue;
            }

            Optional<Project> optionalSelectedProject = projects.stream()
                    .filter(project -> project.getProjectID() == projectID)
                    .findFirst();

            if (optionalSelectedProject.isPresent()) {
                return optionalSelectedProject.get();
            } else {
                System.out.println("Invalid project ID!");
            }
        }
    }

    public static Supervisor supervisorSelector(Map<String, Supervisor> supervisors) {
        while (true) {
            System.out.println("supervisorID\t\tName");
            supervisors.values().forEach(supervisor -> {
                System.out.printf("%-20s\t%s\n", supervisor.getSupervisorID(), supervisor.getName());
            });

            System.out.printf("\nSelect supervisorID (Enter to return): ");
            String supervisorID = sc.nextLine().trim();

            if (supervisorID.isEmpty()) {
                return null;
            }
            Supervisor selectedSupervisor = supervisors.get(supervisorID);

            if (selectedSupervisor != null) {
                return selectedSupervisor;
            } else {
                System.out.println("Invalid supervisorID!");
            }

            sc.nextLine();
        }
    }

    public static ProjectStatus projectStatusSelector() {
        while (true) {
            System.out.println("Project Status");
            System.out.println("1. AVAILABLE");
            System.out.println("2. RESERVED");
            System.out.println("3. UNAVAILABLE");
            System.out.println("4. ALLOCATED");
            System.out.printf("Select Project Status (Enter to return): ");

            String input = sc.nextLine();
            int option;

            if (input.isEmpty()) { // If the input is empty (user pressed Enter), return
                return null;
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

            return ProjectStatus.values()[option - 1];
        }
    }

    public static Request requestSelector(ArrayList<Request> requests) {
        if (requests == null) {
            return null;
        }

        IRequestView requestView;

        while (true) {
            for (Request request : requests) {
                if (request.getType() == RequestType.ALLOCATE_PROJECT) {
                    requestView = new RequestAllocateProjectView();
                } else if (request.getType() == RequestType.CHANGE_PROJECT_TITLE) {
                    requestView = new RequestChangeProjectTitleView();
                } else if (request.getType() == RequestType.DEREGISTER_PROJECT) {
                    requestView = new RequestDeregisterProjectView();
                } else if (request.getType() == RequestType.TRANSFER_STUDENT) {
                    requestView = new RequestTransferStudentView();
                } else {
                    continue;
                }
                requestView.displayRequestInfo(request);
            }

            System.out.printf("Select request ID (Enter to return): ");

            String input = sc.nextLine();
            int requestID;

            if (input.isEmpty()) { // If the input is empty (user pressed Enter), return
                return null;
            } else if (input.matches("[0-9]+")) { // If the input is an integer, proceed with the code
                requestID = Integer.parseInt(input);
            } else { // If the input is not empty and not an integer, prompt the user to enter again
                System.out.println("Invalid input. Please enter a requestID or press Enter to return.\n");
                continue;
            }

            Optional<Request> optionalSelectedRequest = requests.stream()
                    .filter(request -> request.getRequestID() == requestID)
                    .findFirst();

            if (optionalSelectedRequest.isPresent()) {
                return optionalSelectedRequest.get();
            } else {
                System.out.println("Invalid requestID!");
            }
        }
    }
}
