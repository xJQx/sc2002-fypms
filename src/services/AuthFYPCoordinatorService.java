package services;

import java.util.Map;

import models.FYPCoordinator;

import store.DataStore;
import store.AuthStore;

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
