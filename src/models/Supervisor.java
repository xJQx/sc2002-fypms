package models;

public class Supervisor extends User {
	private String supervisorID;
	private int numOfProjects;
	public static final int MAX_PROJECTS = 2;
	
	public Supervisor(String supervisorID, String name, String email, String password, int numOfProjects) {
		super(supervisorID, name, email, password);
		this.supervisorID = supervisorID;
		this.numOfProjects = numOfProjects;
	}
	
	public String getSupervisorID() {
		return this.supervisorID;
	}
	
	public int getNumOfProjects() {
		return this.numOfProjects;
	}
	
	public Boolean setNumOfProjects(int numOfProjects) {
		if (numOfProjects > Supervisor.MAX_PROJECTS) return false;
		this.numOfProjects = numOfProjects;
		return true;
	}
}
