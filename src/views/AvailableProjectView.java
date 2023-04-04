package views;

public class AvailableProjectView implements IProjectView{
	
	public void displayProjectInfo (Project project) {
		Supervisor supervisor = project.getSupervisor();
		System.out.println("Project ID: " project.getProjectID());
		System.out.println("Supervisor's name: " + supervisor.getName());
		System.out.println("Supervisor's email address: " supervisor.getEmail());
		System.out.println("Project Title: " + project.getTitle());
		System.out.println("Project Status: " + project.getStatus());
		
	}
}
