package views;

public class CommonView {
	public static void printSplashScreen() {
		System.out.println("====================================================================================================");
		System.out.println("|                           Final Year Project Management System (FYPMS)                           |");
		System.out.println("====================================================================================================");
	}
	
	public static void printNavbar(String path) {
		String spaces = String.format("%" + (97 - path.length()) + "s", "");
		
		// Display
        System.out.println();
        System.out.println();
		System.out.println("====================================================================================================");
		System.out.println("| " + path + spaces + "|");
		System.out.println("====================================================================================================");
	}
}