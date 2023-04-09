package models;

import enums.ProjectStatus;
import store.DataStore;

public class Project {
	private static int lastProjectID = 0;
	private int projectID;
	private String title;
	private String studentID;
	private String supervisorID;
	private ProjectStatus status;
	
	// For Initializing Existing Projects
	public Project(int projectID, String title, String supervisorID, String studentID, ProjectStatus status) {
		this.title = title;
		this.status = status;
		this.studentID = studentID;
		this.supervisorID = supervisorID;
		this.projectID = projectID;
		
		// update lastProjectID
		if (projectID > Project.lastProjectID) Project.lastProjectID = projectID;
	}
	
	// Constructor for Creating New Projects
	public Project(String title, String supervisorID, String studentID, ProjectStatus status) {
		this.title = title;
		this.status = status;
		this.studentID = studentID;
		this.supervisorID = supervisorID;
		this.projectID = ++Project.lastProjectID;
	}
	
	
	// ---------- Getter and Setter Methods ---------- //
	public int getProjectID() {
		return this.projectID;
	}
	
	public String getTitle() {
		return title;
	}
	
	public boolean setTitle(String title) {
		this.title = title;
		return true;
	}
	
	public ProjectStatus getStatus() {
		return status;
	}
	
	public boolean setStatus(ProjectStatus status) {
		this.status = status;
		return true;
	}
	
	public Supervisor getSupervisor() {
		if (DataStore.getSupervisorsData().containsKey(this.supervisorID)) {
			return DataStore.getSupervisorsData().get(this.supervisorID);
		} else if (DataStore.getFYPCoordinatorsData().containsKey(this.supervisorID)) {
			return DataStore.getFYPCoordinatorsData().get(this.supervisorID);
		}
		
		return null;
	}
	
	public boolean setSupervisor(String supervisorID) {
		this.supervisorID = supervisorID;
		return true;
	}
	
	public Student getStudent() {
		return DataStore.getStudentsData().get(this.studentID);
	}
	
	public boolean setStudent(String studentID) {
		this.studentID = studentID;
		return true;
	}
}
