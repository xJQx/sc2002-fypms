package services;

import java.util.Map;

import models.Student;
import stores.AuthStore;
import stores.DataStore;

/**
 * The {@link AuthStudentService} class extends {@link AuthService} and
 * provides the login functionality for students.
 */
public class AuthStudentService extends AuthService {
    /**
     * Constructs an instance of the {@link AuthStudentService} class.
     */
    public AuthStudentService() {
    };

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
