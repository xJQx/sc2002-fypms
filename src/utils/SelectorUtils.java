package utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import enums.ProjectStatus;
import interfaces.IRequestView;
import models.AllocateProjectRequest;
import models.ChangeProjectTitleRequest;
import models.DeregisterProjectRequest;
import models.Project;
import models.Request;
import models.Supervisor;
import models.TransferStudentRequest;
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

            System.out.println("Select projectID (Enter non-int to exit)");
            if (!sc.hasNextInt()) {
                sc.nextLine();
                return null;
            }
            int projectID = sc.nextInt();
            sc.nextLine();

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

            System.out.println("\nSelect supervisorID (Enter to return)");
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
            System.out.println("Select Project Status (Enter non-int to return)");
            System.out.println("1. AVAILABLE");
            System.out.println("2. RESERVED");
            System.out.println("3. UNAVAILABLE");
            System.out.println("4. ALLOCATED");

            if (!sc.hasNextInt()) {
                sc.nextLine();
                return null;
            }

            int option = sc.nextInt();
            sc.nextLine();

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

            System.out.println("Select request ID (Enter non-int to exit):");
            if (!sc.hasNextInt()) {
                sc.nextLine();
                return null;
            }
            int requestID = sc.nextInt();
            sc.nextLine();

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
