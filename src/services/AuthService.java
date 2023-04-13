package services;

import interfaces.IAuthService;
import models.User;
import stores.AuthStore;

/**
 * The {@link AuthService} class is an abstract class that implements the
 * {@link IAuthService} interface.
 * It provides basic authentication functionalities for user login and logout.
 */
public abstract class AuthService implements IAuthService {
    public abstract boolean login(String userID, String password);

    /**
     * Constructs an instance of the {@link AuthService} class.
     */
    public AuthService() {
    };

    @Override
    public boolean logout() {
        AuthStore.setCurrentUser(null);
        return true;
    };

    /**
     * Authenticates the given user with the given password.
     * 
     * @param user     the user to be authenticated
     * @param password the password to be used for authentication
     * @return true if the user is authenticated successfully, false otherwise
     */
    protected boolean authenticate(User user, String password) {
        if (user == null)
            return false;
        if (!user.getPassword().equals(password))
            return false;
        return true;
    }
}
