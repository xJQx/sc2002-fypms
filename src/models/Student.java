package models;

public class Student extends User {
	private String studentID;
	private Boolean isDeregistered;

	public Student(String studentID, String name, String email, String password, boolean isDeregistered) {
		super(studentID, name, email, password);
		this.isDeregistered = isDeregistered;
		this.studentID = studentID;
	}
	
	public Boolean getIsDeregistered() {
		return this.isDeregistered;
	}
	
	public void setIsDeregistered(Boolean isDeregistered) {
		this.isDeregistered = isDeregistered;
	}
	
	public String getStudentID() {
		return this.studentID;
	}
}
