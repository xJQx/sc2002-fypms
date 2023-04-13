package models;

import enums.UserRole;

public class FYPCoordinator extends Supervisor {
	private String fypCoordinatorID;

	/**
	 * Constructs a {@link FYPCoordinator} object with given coordinator ID, name,
	 * email,
	 * password, and number of projects.
	 * 
	 * @param fypCoordinatorID the ID of the FYPCoordinator
	 * @param name             the name of the FYPCoordinator
	 * @param email            the email address of the FYPCoordinator
	 * @param password         the password of the FYPCoordinator
	 * @param numOfProjects    the number of projects supervised by the
	 *                         FYPCoordinator
	 */
	public FYPCoordinator(String fypCoordinatorID, String name, String email, String password, int numOfProjects) {
		super(fypCoordinatorID, name, email, password, numOfProjects);
		super.setRole(UserRole.FYPCOORDINATOR);
		this.fypCoordinatorID = fypCoordinatorID;
	}

	/**
	 * Returns the coordinator ID of the FYPCoordinator.
	 * 
	 * @return the coordinator ID
	 */
	public String getFYPCoordinatorID() {
		return this.fypCoordinatorID;
	}
}
