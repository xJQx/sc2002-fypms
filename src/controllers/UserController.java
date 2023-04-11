package controllers;

import java.util.Scanner;

import interfaces.IUserService;
import services.UserService;

public class UserController {
    private static final Scanner sc = new Scanner(System.in);

    protected boolean changePassword() {
        String oldPassword, newPassword;
        boolean success;
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
            success = userService.changePassword(oldPassword, newPassword);

            if (!success) {
                System.out.println("Old password does not match!");
            }
        } while (!success);

        System.out.println("Password changed!");
        return true;
    }
}
