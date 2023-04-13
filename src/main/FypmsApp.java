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

/**
 * The main class responsible for running the FYPMS application.
 * 
 * <p>
 * This class handles initializing of the data store, authentication for users
 * to log in, and starting the appropriate session based on the role of the
 * logged-in user.
 * </p>
 */
public class FypmsApp {
	/**
	 * The entry point for the FYPMS application. This method is responsible for
	 * running an infinite loop to allow multiple users to operate the application.
	 * 
	 * @param args an array of String arguments passed to this method
	 */
	public static void main(String[] args) {
		try {
			do {
				// Initialize DataStore
				DataStore.initDataStore(new CsvDataService(), FilePathsUtils.csvFilePaths());

				// Display Splash Screen
				CommonView.printSplashScreen();

				// Authentication - Log In
				AuthController.startSession();
				if (!AuthStore.isLoggedIn())
					return;

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
