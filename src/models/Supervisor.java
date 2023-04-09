package models;

import enums.UserRole;

public class Supervisor extends User {
	private String supervisorID;
	private int numOfProjects;
	public static final int MAX_PROJECTS = 2;
	
	public Supervisor(String supervisorID, String name, String email, String password, int numOfProjects) {
		super(supervisorID, name, email, password);
		super.setRole(UserRole.SUPERVISOR);
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
		// TODO - if numOfProjects exceed the max, update the remaining submitted projects to UNAVAILABLE
		if (numOfProjects > Supervisor.MAX_PROJECTS) {
			System.out.println("MAX number of projects reached!");
			return false;
		}
		this.numOfProjects = numOfProjects;
		return true;
	}
}
