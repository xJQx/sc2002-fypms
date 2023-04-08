package models;

import enums.ProjectStatus;

public class Project {
	private static int lastProjectID = 0;
	private int projectID;
	private String title;
	private Student student;
	private Supervisor supervisor;
	private ProjectStatus status;
	
	// For Initializing Existing Projects
	public Project(int projectID, String title, Supervisor supervisor, Student student, ProjectStatus status) {
		this.title = title;
		this.status = status;
		this.student = student;
		this.supervisor = supervisor;
		this.projectID = projectID;
		
		// update lastProjectID
		if (projectID > Project.lastProjectID) Project.lastProjectID = projectID;
	}
	
	// Constructor for Creating New Projects
	public Project(String title, Supervisor supervisor, Student student, ProjectStatus status) {
		this.title = title;
		this.status = status;
		this.student = student;
		this.supervisor = supervisor;
		this.projectID = ++Project.lastProjectID;
	}
	
	
	// ---------- Getter and Setter Methods ---------- //
	public int getProjectID() {
		return this.projectID;
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
	
	public Supervisor getSupervisor() {
		return this.supervisor;
	}
	
	public boolean setStudent(Student student) {
		this.student = student;
		return true;
	}
	
	public Student getStudent() {
		return this.student;
	}
}
