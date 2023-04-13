package interfaces;

import java.util.ArrayList;

import models.Project;

/**
 * The {@link IProjectSupervisorService} interface defines a contract for
 * managing supervisor-related project services.
 */
public interface IProjectSupervisorService {
    /**
     * Retrieves a list of projects submitted by the supervisor with the specified
     * ID.
     *
     * @param supervisorID the ID of the supervisor
     * @return an {@link ArrayList} of {@link Project} objects representing the
     *         submitted projects
     */
    public ArrayList<Project> getSubmittedProjects(String supervisorID);

    /**
     * Creates new projects from the provided list of projects.
     *
     * @param projects an {@link ArrayList} of {@link Project} objects to be created
     * @return true if the projects were created successfully, false otherwise
     */
    public boolean createProjects(ArrayList<Project> projects);

    /**
     * Updates the title of the specified project if the supervisor ID matches
     * the project's supervisor.
     *
     * @param project      the {@link Project} object to update
     * @param title        the new title for the project
     * @param supervisorID the ID of the supervisor making the update
     * @return true if the project title was updated successfully, false otherwise
     */
    public boolean updateProjectTitle(Project project, String title, String supervisorID);

    /**
     * Updates the remaining projects of the specified supervisor to unavailable.
     *
     * @param supervisorID the ID of the supervisor
     * @return true if the remaining projects were updated successfully, false
     *         otherwise
     */
    public boolean updateRemainingProjectsToUnavailable(String supervisorID);

    /**
     * Updates the unavailable projects of the specified supervisor to available.
     *
     * @param supervisorID the ID of the supervisor
     * @return true if the unavailable projects were updated successfully, false
     *         otherwise
     */
    public boolean updateUnavailableProjectsToAvailable(String supervisorID);
}
