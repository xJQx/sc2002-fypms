package interfaces;

import java.util.ArrayList;

import enums.ProjectStatus;
import models.Project;

/**
 * The {@link IProjectFYPCoordinatorService} interface defines a contract for
 * managing FYP Coordinator-related project services.
 */
public interface IProjectFYPCoordinatorService extends IProjectSupervisorService {
    /**
     * Retrieves a list of all projects.
     *
     * @return an {@link ArrayList} of {@link Project} objects representing all
     *         the projects
     */
    public ArrayList<Project> getAllProjects();

    /**
     * Retrieves a list of projects filtered by the specified project status.
     *
     * @param projectStatus the {@link ProjectStatus} to filter projects by
     * @return an {@link ArrayList} of {@link Project} objects with the specified
     *         project status
     */
    public ArrayList<Project> getAllProjectsByStatus(ProjectStatus projectStatus);

    /**
     * Retrieves a list of projects submitted by the specified supervisor.
     *
     * @param supervisorID the ID of the supervisor
     * @return an {@link ArrayList} of {@link Project} objects submitted by the
     *         specified supervisor
     */
    public ArrayList<Project> getAllProjectsBySupervisor(String supervisorID);

}
