package controllers;

import java.util.Scanner;

import interfaces.IUserService;
import services.UserService;

/**
 * The {@link UserController} class is responsible for handling user-related
 * actions, such as changing the user's password. This
 * class serves as a base class for more specific user types like
 * {@link StudentController} or {@link SupervisorController} or
 * {@link FYPCoordinatorController}.
 */
public class UserController {
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Changes the user's password by prompting for their old and new passwords.
     * If the old password matches the current one and the new password is
     * different,
     * the change is successful.
     *
     * The password change fails in the following scenarios:
     * <ol>
     * <li>The old password does not match the current password</li>
     * <li>The new password is the same as the old password</li>
     * <li>The user decides to quit the process by entering "X"</li>
     * </ol>
     *
     * @return true if the password change is successful, false otherwise
     */
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
