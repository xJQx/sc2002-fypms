package models;

import enums.ProjectStatus;
import stores.DataStore;

/**
 * The {@link Project} class represents a project that a supervisor has created
 */
public class Project {
	/**
	 * The ID of the last project that was created.
	 */
	private static int lastProjectID = 0;

	/**
	 * The ID of the project.
	 */
	private int projectID;

	/**
	 * The title of the project.
	 */
	private String title;

	/**
	 * The ID of the student who is working on this project.
	 */
	private String studentID;

	/**
	 * The ID of the supervisor assigned to supervise this project.
	 */
	private String supervisorID;

	/**
	 * The status of the project.
	 */
	private ProjectStatus status;

	/**
	 * Constructs a {@link Project} object for an existing project
	 * 
	 * @param projectID    the ID of the project to initialize
	 * @param title        the title of the project
	 * @param supervisorID the ID of the supervisor assigned to the project
	 * @param studentID    the ID of the student working on the project
	 * @param status       the status of the project
	 */
	public Project(int projectID, String title, String supervisorID, String studentID, ProjectStatus status) {
		this.title = title;
		this.status = status;
		this.studentID = studentID;
		this.supervisorID = supervisorID;
		this.projectID = projectID;

		// update lastProjectID
		if (projectID > Project.lastProjectID)
			Project.lastProjectID = projectID;
	}

	/**
	 * Constructs a {@link Project} object for a new project
	 * 
	 * @param title        the title of the project
	 * @param supervisorID the ID of the supervisor assigned to the project
	 * @param studentID    the ID of the student working on the project
	 * @param status       the status of the project
	 */
	public Project(String title, String supervisorID, String studentID, ProjectStatus status) {
		this.title = title;
		this.status = status;
		this.studentID = studentID;
		this.supervisorID = supervisorID;
		this.projectID = ++Project.lastProjectID;
	}

	// ---------- Getter and Setter Methods ---------- //
	/**
	 * Returns the ID of the project.
	 * 
	 * @return the project ID
	 */
	public int getProjectID() {
		return this.projectID;
	}

	/**
	 * Returns the title of the project.
	 * 
	 * @return the project title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the project.
	 * 
	 * @param title the project title to set
	 * @return true if the title is set successfully
	 */
	public boolean setTitle(String title) {
		this.title = title;
		return true;
	}

	/**
	 * Returns the status of the project.
	 * 
	 * @return the project status
	 */
	public ProjectStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status of the project.
	 * 
	 * @param status the {@link ProjectStatus} to set
	 * @return true if the status is set successfully
	 */
	public boolean setStatus(ProjectStatus status) {
		this.status = status;
		return true;
	}

	/**
	 * Returns the supervisor assigned to supervise the project.
	 * 
	 * @return the supervisor assigned to the project, or null if the supervisor is
	 *         not found
	 */
	public Supervisor getSupervisor() {
		if (DataStore.getSupervisorsData().containsKey(this.supervisorID)) {
			return DataStore.getSupervisorsData().get(this.supervisorID);
		} else if (DataStore.getFYPCoordinatorsData().containsKey(this.supervisorID)) {
			return DataStore.getFYPCoordinatorsData().get(this.supervisorID);
		}

		return null;
	}

	/**
	 * Sets the supervisor assigned to supervise the project.
	 * 
	 * @param supervisorID the supervisor ID to set
	 * @return true if the supervisor is set successfully
	 */
	public boolean setSupervisor(String supervisorID) {
		this.supervisorID = supervisorID;
		return true;
	}

	/**
	 * Returns the student assigned to the project.
	 * 
	 * @return the student assigned to the project
	 */
	public Student getStudent() {
		return DataStore.getStudentsData().get(this.studentID);
	}

	/**
	 * Sets the student assigned to the project.
	 * 
	 * @param studentID the ID of the student to set
	 * @return true if the student is set successfully
	 */
	public boolean setStudent(String studentID) {
		this.studentID = studentID;
		return true;
	}
}
