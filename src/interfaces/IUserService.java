package interfaces;

/**
 * The {@link IUserService} interface defines a contract for user services.
 */
public interface IUserService {
    /**
     * Changes the password of a user if the provided old password matches the
     * current password.
     *
     * @param oldPassword the current password of the user
     * @param newPassword the new password the user wants to set
     * @return true if the password was changed successfully, false otherwise
     */
    public boolean changePassword(String oldPassword, String newPassword);
}
