package views;

import interfaces.IProjectView;
import models.Project;
import models.Supervisor;

/**
 * The {@link ProjectAvailableView} class implements {@link IProjectView} and
 * provides methods for displaying project information when the project is
 * available.
 */
public class ProjectAvailableView implements IProjectView {
	/**
	 * Constructs an instance of the {@link ProjectAvailableView} class.
	 */
	public ProjectAvailableView() {
	};

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
