package services;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import enums.ProjectStatus;
import interfaces.IProjectFYPCoordinatorService;
import models.Project;
import stores.DataStore;

public class ProjectFYPCoordinatorService extends ProjectSupervisorService implements IProjectFYPCoordinatorService {

    @Override
    public ArrayList<Project> getAllProjects() {
        return new ArrayList<Project>(DataStore.getProjectsData().values());
    }

    @Override
    public ArrayList<Project> getAllProjectsByStatus(ProjectStatus projectStatus) {
        Map<Integer, Project> projectsData = DataStore.getProjectsData();

        ArrayList<Project> filteredProjects = projectsData.values().stream()
                .filter(project -> project.getStatus() == projectStatus)
                .collect(Collectors.toCollection(ArrayList::new));

        return filteredProjects;
    }

    @Override
    public ArrayList<Project> getAllProjectsBySupervisor(String supervisorID) {
        Map<Integer, Project> projectsData = DataStore.getProjectsData();

        ArrayList<Project> filteredProjects = projectsData.values().stream()
                .filter(project -> project.getSupervisor() != null
                        && project.getSupervisor().getSupervisorID().equals(supervisorID))
                .collect(Collectors.toCollection(ArrayList::new));

        return filteredProjects;
    }

}
