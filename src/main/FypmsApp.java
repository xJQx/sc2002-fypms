package main;

import controllers.AuthController;
import controllers.FYPCoordinatorController;
import controllers.StudentController;
import controllers.SupervisorController;
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
			switch (user.getRole()) {
				case STUDENT:
					new StudentController().start();
					break;
				case SUPERVISOR:
					new SupervisorController().start();
					break;
				case FYPCOORDINATOR:
					new FYPCoordinatorController().start();
					break;
			}
		} while (true);
	}
}
