package services;

import java.util.Map;

import models.Student;

import store.AuthStore;
import store.AppStore;

public class AuthStudentService extends AuthService {
    @Override
    public boolean login(String userID, String password) {
        Map<String, Student> studentData = AppStore.getStudentsData();

        Student student = studentData.get(userID);

        if (authenticate(student, password) == false)
            return false;

        AuthStore.setCurrentUser(student);
        return true;
    }

}
