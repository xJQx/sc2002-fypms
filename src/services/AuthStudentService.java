package services;

import java.util.Map;

import models.Student;

import store.AuthStore;
import store.DataStore;

public class AuthStudentService extends AuthService {
    @Override
    public boolean login(String userID, String password) {
        Map<String, Student> studentData = DataStore.getStudentsData();

        Student student = studentData.get(userID);

        if (authenticate(student, password) == false)
            return false;

        AuthStore.setCurrentUser(student);
        return true;
    }

}
