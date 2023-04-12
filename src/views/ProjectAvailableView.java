package views;

import interfaces.IProjectView;
import models.Project;
import models.Supervisor;

public class ProjectAvailableView implements IProjectView{

	@Override
	public void displayProjectInfo(Project project) {
		Supervisor supervisor = project.getSupervisor();
		
		System.out.println("Project ID: " + project.getProjectID());
		System.out.println("Project Title: " + project.getTitle());
		System.out.println("Project Status: " + project.getStatus());	
		
		System.out.println("Supervisor's name: " + supervisor.getName());
		System.out.println("Supervisor's email address: " + supervisor.getEmail());
	}
}
