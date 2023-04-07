package models;

public class Student extends User {
	private String studentID;
	private Boolean isRegistered;

	public Student(String studentID, String name, String email, String password, boolean isRegistered) {
		super(studentID, name, email, password);
		this.isRegistered = isRegistered;
		this.studentID = studentID;
	}
	
	public Boolean getIsRegistered() {
		return this.isRegistered;
	}
	
	public void setIsRegistered(Boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
	
	public String getStudentID() {
		return this.studentID;
	}
}
