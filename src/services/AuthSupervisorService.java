package services;

import java.util.Map;

import models.Supervisor;
import stores.AuthStore;
import stores.DataStore;

/**
 * The {@link AuthSupervisorService} class extends {@link AuthService} and
 * provides the login functionality for supervisors.
 */
public class AuthSupervisorService extends AuthService {
    @Override
    public boolean login(String userID, String password) {
        Map<String, Supervisor> supervisorData = DataStore.getSupervisorsData();

        Supervisor supervisor = supervisorData.get(userID);

        if (authenticate(supervisor, password) == false)
            return false;

        AuthStore.setCurrentUser(supervisor);
        return true;
    }

}
