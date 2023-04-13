package views;

import java.util.Scanner;

/**
 * The {@link CommonView} class provides utility methods for displaying user
 * interface components in the console.
 */
public class CommonView {
    private static final Scanner sc = new Scanner(System.in);
    
	/**
	 * Prints the splash screen for the Final Year Project Management System
	 * (FYPMS).
	 */
	public static void printSplashScreen() {
		System.out.println("\u250F" + "\u2501".repeat(98) + "\u2513");
		System.out.println("\u2503                           Final Year Project Management System (FYPMS)                           \u2503");
		System.out.println("\u2517" + "\u2501".repeat(98) + "\u251B");
	}
	
	/**
	 * Prints the navigation bar with the given path.
	 *
	 * @param path the path to be displayed in the navigation bar
	 */
	public static void printNavbar(String path) {
		String spaces = String.format("%" + (97 - path.length()) + "s", "");
		
		// Display
        System.out.println();
        System.out.println();
        
		System.out.println("\u250F" + "\u2501".repeat(98) + "\u2513");
		System.out.println("\u2503 " + path + spaces + "\u2503");
		System.out.println("\u2517" + "\u2501".repeat(98) + "\u251B");
	}
	
	/**
	 * Prompts the user to press the "Enter" key to continue with the application.
	 */
	public static void pressEnterToContinue() {
	    System.out.println("Press Enter key to continue...");
	    sc.nextLine();
	}
}