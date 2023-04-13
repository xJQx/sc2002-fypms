package models;

import enums.UserRole;

public class User {
	private String userID;
	private String password;
	private String name;
	private String email;
	private UserRole role;

	/**
	 * Constructs a {@link User} object with given user ID, name, email, and password.
	 * 
	 * @param userID   the ID of the user
	 * @param name     the name of the user
	 * @param email    the email address of the user
	 * @param password the password of the user
	 */
	public User(String userID, String name, String email, String password) {
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	/**
	 * Returns the user ID of the User.
	 * 
	 * @return the user ID
	 */
	public String getUserID() {
		return this.userID;
	}

	/**
	 * Returns the password of the User.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Returns the name of the User.
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the email address of the User.
	 * 
	 * @return the email address
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Returns the UserRole of the User.
	 * 
	 * @return the {@link UserRole}
	 */
	public UserRole getRole() {
		return this.role;
	}

	/**
	 * Sets the UserRole of the User.
	 * 
	 * @param role the UserRole to set
	 * @return true if UserRole is set successfully
	 */
	public Boolean setRole(UserRole role) {
		this.role = role;
		return true;
	}

	/**
	 * Sets the password of the user.
	 * Validates if the oldPassword matches the currentPassword
	 * 
	 * @param oldPassword the old password to confirm the change
	 * @param newPassword the new password to set
	 * @return true if password change is successful, false otherwise
	 */
	public Boolean setPassword(String oldPassword, String newPassword) {
		if (!oldPassword.equals(this.password)) return false;
		this.password = newPassword;
		return true;
	}
}
