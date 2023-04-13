package models;

import enums.UserRole;

/**
 * The {@link Student} class represents a student user in the system.
 * It extends the {@link User} class, which contains the basic user information.
 */
public class Student extends User {
	/**
	 * The ID of the student.
	 */
	private String studentID;

	/**
	 * The registration status of the student.
	 */
	private Boolean isDeregistered;

	/**
	 * Constructs a {@link Student} object with given student ID, name, email,
	 * password, and registration status.
	 * 
	 * @param studentID      the ID of the student
	 * @param name           the name of the student
	 * @param email          the email address of the student
	 * @param password       the password of the student
	 * @param isDeregistered the registration status of the student
	 */
	public Student(String studentID, String name, String email, String password, boolean isDeregistered) {
		super(studentID, name, email, password);
		super.setRole(UserRole.STUDENT);
		this.isDeregistered = isDeregistered;
		this.studentID = studentID;
	}

	/**
	 * Returns whether a student is deregistered.
	 * 
	 * @return the isDeregistered
	 */
	public Boolean getIsDeregistered() {
		return this.isDeregistered;
	}

	/**
	 * Sets the isDeregistered status of the Student.
	 * 
	 * @param isDeregistered the isDeregistered status to set
	 */
	public void setIsDeregistered(Boolean isDeregistered) {
		this.isDeregistered = isDeregistered;
	}

	/**
	 * Returns the student ID of the Student.
	 * 
	 * @return the student ID
	 */
	public String getStudentID() {
		return this.studentID;
	}
}
