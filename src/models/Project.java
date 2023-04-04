package models;

public class Project {
	public static int lastProjectID;
	private int projectID;
	private String title;
	private ProjectStatus status;
	private Supervisor supervisor;
	private Student student;
	
	public Project(String title, Supervisor supervisor, Student student) {
		this.title = title;
		this.student = student;
		this.supervisor = supervisor;
		this.projectID = ++lastProjectID;
		
	}
	public boolean setTitle(String title) {
		this.title = title;
		return true;
	}
	
	public String getTitle() {
		return title;
	}
	public boolean setStatus(ProjectStatus status) {
		this.status = status;
		return true;
	}
	public ProjectStatus getStatus() {
		return status;
	}
	
	public boolean setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
		return true;
	}
	public Supervisor getSupervsior() {
		return supervsior;
	}
	
	public boolean setStudent(Student student) {
		this.student = student;
		return true;
	}
	public Student getStudent() {
		return student;
	}
}
