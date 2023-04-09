package models;

import enums.UserRole;

public class User {
	private String userID;
	private String password;
	private String name;
	private String email;
	private UserRole role;
	
	
	public User(String userID, String name, String email, String password) {
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public String getUserID() {
		return this.userID;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public UserRole getRole() {
		return this.role;
	}
	
	public Boolean setRole(UserRole role) {
		this.role = role;
		return true;
	}
	
	public Boolean setPassword(String oldPassword, String newPassword) {
		if (!oldPassword.equals(this.password)) return false;
		this.password = newPassword;
		return true;
	}
}
