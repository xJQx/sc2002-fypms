package interfaces;

import java.util.ArrayList;

import models.Project;

/**
 * The {@link IProjectStudentService} interface defines a contract for managing
 * student-related project services.
 */
public interface IProjectStudentService {
    /**
     * Retrieves a list of available projects.
     *
     * @return an {@link ArrayList} of {@link Project} objects representing the
     *         available projects
     */
    public ArrayList<Project> getAvailableProjects();

    /**
     * Retrieves the allocated project for the specified student.
     *
     * @param studentID the ID of the student
     * @return the {@link Project} object allocated to the student, or null if no
     *         project is allocated
     */
    public Project getAllocatedProject(String studentID);

    /**
     * Retrieves the reserved project for the specified student.
     *
     * @param studentID the ID of the student
     * @return the {@link Project} object reserved by the student, or null if no
     *         project is reserved
     */
    public Project getReservedProject(String studentID);

}
