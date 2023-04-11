package views;

import interfaces.IProjectView;
import models.Project;
import models.Student;
import models.Supervisor;

public class AllocatedProjectView implements IProjectView {

	public void displayProjectInfo(Project project) {
		Supervisor supervisor = project.getSupervisor();
		Student student = project.getStudent();
		
		System.out.println("Project ID: " + project.getProjectID());
		System.out.println("Project Title: " + project.getTitle());
		System.out.println("Project Status: " + project.getStatus());
		
		System.out.println("Supervisor's name: " + supervisor.getName());
		System.out.println("Supervisor's email address: " + supervisor.getEmail());
		
		System.out.println("Student's name: " + student.getName());
		System.out.println("Student's email address: " + student.getEmail());
	}
}
