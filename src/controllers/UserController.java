package controllers;

import java.util.Scanner;

import interfaces.IUserService;
import services.UserService;

public class UserController {
    private static final Scanner sc = new Scanner(System.in);

    protected boolean changePassword() {
        String oldPassword, newPassword;
        boolean success = false;
        IUserService userService = new UserService();

        System.out.println("Changing Password...");

        do {
            System.out.print("Enter old password (Enter X to quit): ");
            oldPassword = sc.next();
            if (oldPassword.equalsIgnoreCase("X")) {
                System.out.println("Exiting change password...");
                return false;
            }

            System.out.print("Enter new password: ");
            newPassword = sc.next();
            
            // New password must be different from old password
            if (newPassword.equals(oldPassword)) {
            	System.out.println("New password cannot match old password!");
            	continue;
            }
            
            // Change password
            success = userService.changePassword(oldPassword, newPassword);

            if (!success) {
                System.out.println("Old password does not match!");
            }
        } while (!success);

        System.out.println("Password changed!");
        return true;
    }
}
