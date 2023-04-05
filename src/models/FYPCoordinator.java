package models;

public class FYPCoordinator extends Supervisor {
	private String fypCoordinatorID;
	
	public FYPCoordinator(String fypCoordinatorID, String name, String email, String password) {
		super(fypCoordinatorID, name, email, password);
		this.fypCoordinatorID = fypCoordinatorID;
	}
	
	public String getFYPCoordinatorID() {
		return this.fypCoordinatorID;
	}
}
