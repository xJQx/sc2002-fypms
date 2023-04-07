package store;

import java.util.ArrayList;
import java.util.List;

import models.User;

public class AppStore {
	private static List<User> userData = new ArrayList<User>();
	
	private AppStore() {}
	
	public static boolean initAppStore() {
		return true;
	}
	
	public static List<User> getUserData() {
		return userData;
	}
	
	public static void setUserData(List<User> userData) {
		AppStore.userData = userData;
	}
}
