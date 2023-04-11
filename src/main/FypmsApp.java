package main;

import controllers.AuthController;
import controllers.FYPCoordinatorController;
import controllers.StudentController;
import controllers.SupervisorController;
import enums.UserRole;
import models.User;
import store.AuthStore;
import store.DataStore;
import views.CommonView;

public class FypmsApp {
	public static void main(String[] args) {
		do {
			// Initialize DataStore
			DataStore.initDataStore();
			
			// Display Splash Screen
			CommonView.printSplashScreen();
			
			// Authentication - Log In
			AuthController.startSession();
			if(!AuthStore.isLoggedIn()) return;
			
			// Start session
			User user = AuthStore.getCurrentUser();
			if (user.getRole() == UserRole.STUDENT) {
				new StudentController().start();
			} else if (user.getRole() == UserRole.SUPERVISOR) {
				new SupervisorController().start();
			} else if (user.getRole() == UserRole.FYPCOORDINATOR) {
				new FYPCoordinatorController().start();
			}
		} while (true);
	}
}