package models;

import enums.UserRole;

/**
 * The {@link Supervisor} class represents a user with the role of supervisor.
 * It is a subclass of the {@link User} class and includes additional properties
 * specific to a supervisor, such as a supervisor ID and the number of projects
 * supervised.
 */
public class Supervisor extends User {
	/**
	 * The ID of the supervisor.
	 */
	private String supervisorID;

	/**
	 * The number of projects supervised by the supervisor.
	 */
	private int numOfProjects;

	/**
	 * The maximum number of projects that can be supervised by a
	 * {@link Supervisor}.
	 */
	public static final int MAX_PROJECTS = 2;

	/**
	 * Constructs a {@link Supervisor} object with given supervisor ID, name, email,
	 * password, and number of projects.
	 * 
	 * @param supervisorID  the ID of the supervisor
	 * @param name          the name of the supervisor
	 * @param email         the email address of the supervisor
	 * @param password      the password of the supervisor
	 * @param numOfProjects the number of projects supervised by the supervisor
	 */
	public Supervisor(String supervisorID, String name, String email, String password, int numOfProjects) {
		super(supervisorID, name, email, password);
		super.setRole(UserRole.SUPERVISOR);
		this.supervisorID = supervisorID;
		this.numOfProjects = numOfProjects;
	}

	/**
	 * Returns the supervisor ID of the Supervisor.
	 * 
	 * @return the supervisor ID
	 */
	public String getSupervisorID() {
		return this.supervisorID;
	}

	/**
	 * Returns the number of projects supervised by the Supervisor.
	 * 
	 * @return the number of projects supervised
	 */
	public int getNumOfProjects() {
		return this.numOfProjects;
	}

	/**
	 * Sets the number of projects supervised by the Supervisor.
	 * 
	 * @param numOfProjects the number of projects to set
	 * @return true if number of projects is set successfully, false if the number
	 *         of projects exceeds the maximum number
	 *         ({@link Supervisor#MAX_PROJECTS}) of
	 *         projects allowed
	 */
	public Boolean setNumOfProjects(int numOfProjects) {
		if (numOfProjects > Supervisor.MAX_PROJECTS) {
			System.out.println("MAX number of projects reached!");
			return false;
		}
		this.numOfProjects = numOfProjects;
		return true;
	}
}
