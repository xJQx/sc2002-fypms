package main;

import controllers.AuthController;
import controllers.FYPCoordinatorController;
import controllers.StudentController;
import controllers.SupervisorController;
import models.User;
import services.CsvDataService;
import stores.AuthStore;
import stores.DataStore;
import utils.FilePathsUtils;
import views.CommonView;

public class FypmsApp {
	public static void main(String[] args) {
		try {
			do {
				// Initialize DataStore
				DataStore.initDataStore(new CsvDataService(), FilePathsUtils.csvFilePaths());
				
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
		} catch (Exception e) {
			// Save Data and Logout user
			DataStore.saveData();
			AuthController.endSession();
			
			// Print message
			System.out.println("FYPMS crashed. Please restart the system.");
			System.out.println("Error: " + e.getMessage());
		}
	}
}
