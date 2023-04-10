package utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import enums.ProjectStatus;
import models.Project;
import models.Supervisor;

public class SelectorUtils {
    private static final Scanner sc = new Scanner(System.in);

    public static Project projectSelector(ArrayList<Project> projects) {
        while (true) {
            System.out.println("projectID\tTitle");
            projects.forEach(project -> System.out.printf("%d\t%s\n", project.getProjectID(), project.getTitle()));

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
            System.out.println("supervisorID\tName");
            supervisors.values().forEach(supervisor -> {
                System.out.printf("%s\t%s\n", supervisor.getSupervisorID(), supervisor.getName());
            });

            System.out.println("Select supervisorID (Enter to return)");
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
}
