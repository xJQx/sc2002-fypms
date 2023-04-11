package controllers;

import java.util.Scanner;

import interfaces.IAuthService;

import services.AuthStudentService;
import services.AuthSupervisorService;
import services.AuthFYPCoordinatorService;

public class AuthController {
    private static final Scanner sc = new Scanner(System.in);
    private static IAuthService authService;

    public static void startSession() {
        int choice = 0;
        boolean authenticated = false;

        do {

            do {
                System.out.println("<Enter 0 to shutdown system>\n");
                System.out.println("Login as:");
                System.out.println("1. Student");
                System.out.println("2. Supervisor");
                System.out.println("3. FYP Coordinator");

                choice = sc.nextInt();
            } while (choice < 0 || choice > 3);

            switch (choice) {
            	case 0:
            		System.out.println("Shutting down FYPMS...");
            		return;
                case 1:
                    authService = new AuthStudentService();
                    break;
                case 2:
                    authService = new AuthSupervisorService();
                    break;
                case 3:
                    authService = new AuthFYPCoordinatorService();
                    break;
            }

            String userID, password;

            System.out.print("UserID: ");
            userID = sc.next();

            System.out.print("Password: ");
            password = sc.next();

            authenticated = authService.login(userID, password);
            if (!authenticated) {
                // We do not specify whether the userID or password is incorrect to make it more
                // secure
                System.out.println("Credentials invalid! Note that UserID and Password are case-sensitive.\n");
            }
        } while (!authenticated);
    }

    public static void endSession() {
        authService.logout();
        System.out.println("User logged out!");
    }
}
