package services;

import interfaces.IUserService;
import models.User;
import stores.AuthStore;
import stores.DataStore;

public class UserService implements IUserService {

    @Override
    public boolean changePassword(String oldPassword, String newPassword) {
        User user = AuthStore.getCurrentUser();
        if (!user.setPassword(oldPassword, newPassword))
            return false;
        
        DataStore.saveData(); // save new password to database
        
        return true;
    }
}
