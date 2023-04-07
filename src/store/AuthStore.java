package store;
import enums.UserRole;
import models.FYPCoordinator;
import models.Student;
import models.Supervisor;
import models.User;

public class AuthStore {
	private static User currentUser;
	private static UserRole role;
	
	private AuthStore() {}
	
	public static void setCurrentUser(User currentUser) {
		AuthStore.currentUser = currentUser;
		
		// User Role
		if (currentUser instanceof Student) AuthStore.role = UserRole.STUDENT;
		else if (currentUser instanceof Supervisor) AuthStore.role = UserRole.SUPERVISOR;
		else if (currentUser instanceof FYPCoordinator) AuthStore.role = UserRole.FYPCOORDINATOR;
		else AuthStore.role = null; // No User
	}
	
	public static boolean isLoggedIn() {
		return currentUser != null;
	}
	
	// ---------- Get Methods ---------- //
	
	public static User getCurrentUser() {
		return AuthStore.currentUser;
	}
	
	public static UserRole getUserRole() {
		return AuthStore.role;
	}
}
