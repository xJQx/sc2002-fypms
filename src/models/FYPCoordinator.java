package models;

import enums.UserRole;

public class FYPCoordinator extends Supervisor {
	private String fypCoordinatorID;
	
	public FYPCoordinator(String fypCoordinatorID, String name, String email, String password, int numOfProjects) {
		super(fypCoordinatorID, name, email, password, numOfProjects);
		super.setRole(UserRole.FYPCOORDINATOR);
		this.fypCoordinatorID = fypCoordinatorID;
	}
	
	public String getFYPCoordinatorID() {
		return this.fypCoordinatorID;
	}
}
