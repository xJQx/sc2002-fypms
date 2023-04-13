package views;

import interfaces.IProjectView;

import models.Project;
import models.Student;
import models.Supervisor;

/**
 * The {@link ProjectSubmittedView} class implements {@link IProjectView} and
 * provides methods for displaying project information when the project
 * is submitted.
 */
public class ProjectSubmittedView implements IProjectView {
    /**
     * Constructs an instance of the {@link ProjectSubmittedView} class.
     */
    public ProjectSubmittedView() {
    };

    @Override
    public void displayProjectInfo(Project project) {
        Supervisor supervisor = project.getSupervisor();
        Student student = project.getStudent();

        System.out.println("Project ID: " + project.getProjectID());
        System.out.println("Project Title: " + project.getTitle());
        System.out.println("Project Status: " + project.getStatus());

        System.out.println("Supervisor's name: " + supervisor.getName());
        System.out.println("Supervisor's email address: " + supervisor.getEmail());

        System.out.println("Student's name: " + (student != null ? student.getName() : "-"));
        System.out.println("Student's email address: " + (student != null ? student.getEmail() : "-"));
    }

}
