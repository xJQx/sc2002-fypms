package services;

import java.util.Map;

import models.FYPCoordinator;
import stores.AuthStore;
import stores.DataStore;

/**
 * The {@link AuthFYPCoordinatorService} class extends {@link AuthService} and
 * provides the login functionality for FYP Coordinators.
 */
public class AuthFYPCoordinatorService extends AuthService {
    @Override
    public boolean login(String userID, String password) {
        Map<String, FYPCoordinator> fypCoordinatorData = DataStore.getFYPCoordinatorsData();

        FYPCoordinator fypCoordinator = fypCoordinatorData.get(userID);

        if (authenticate(fypCoordinator, password) == false)
            return false;

        AuthStore.setCurrentUser(fypCoordinator);
        return true;
    }

}
