package interfaces;

import java.util.ArrayList;

import enums.ProjectStatus;
import models.Project;

public interface IProjectFYPCoordinatorService {
    public ArrayList<Project> getAllProjects();

    public ArrayList<Project> getAllProjectsByStatus(ProjectStatus projectStatus);

    public ArrayList<Project> getAllProjectsBySupervisor(String supervisorID);

}