package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import enums.ProjectStatus;
import interfaces.IProjectSupervisorService;
import models.Project;
import stores.DataStore;

/**
 * The {@link ProjectSupervisorService} class implements
 * {@link IProjectSupervisorService},
 * providing project-related functionalities for supervisors.
 */
public class ProjectSupervisorService implements IProjectSupervisorService {

    /**
     * Constructs an instance of the {@link ProjectSupervisorService} class.
     */
    public ProjectSupervisorService() {
    };

    @Override
    public ArrayList<Project> getSubmittedProjects(String supervisorID) {
        Map<Integer, Project> projectsData = DataStore.getProjectsData();

        ArrayList<Project> submittedProjects = projectsData.values().stream()
                .filter(project -> project.getSupervisor() != null
                        && project.getSupervisor().getSupervisorID() == supervisorID)
                .collect(Collectors.toCollection(ArrayList::new));

        return submittedProjects;
    }

    @Override
    public boolean createProjects(ArrayList<Project> projects) {
        Map<Integer, Project> projectsData = DataStore.getProjectsData();
        Map<Integer, Project> newProjectsMap = new HashMap<Integer, Project>();

        for (int i = 0; i < projects.size(); i++) {
            newProjectsMap.put(projects.get(i).getProjectID(), projects.get(i));
        }

        projectsData.putAll(newProjectsMap);
        DataStore.setProjectsData(projectsData);

        return true;
    }

    @Override
    public boolean updateProjectTitle(Project project, String title, String supervisorID) {
        if (project.getSupervisor() != null && project.getSupervisor().getSupervisorID().equals(supervisorID)) {
            project.setTitle(title);

            return DataStore.saveData();
        } else {
            return false;
        }
    }

    @Override
    public boolean updateRemainingProjectsToUnavailable(String supervisorID) {
        ArrayList<Project> submittedProjects = this.getSubmittedProjects(supervisorID);

        for (Project project : submittedProjects) {
            if (project.getStatus() == ProjectStatus.AVAILABLE) {
                project.setStatus(ProjectStatus.UNAVAILABLE);
            }
        }

        return DataStore.saveData();
    }

    @Override
    public boolean updateUnavailableProjectsToAvailable(String supervisorID) {
        ArrayList<Project> submittedProjects = this.getSubmittedProjects(supervisorID);

        for (Project project : submittedProjects) {
            if (project.getStatus() == ProjectStatus.UNAVAILABLE) {
                project.setStatus(ProjectStatus.AVAILABLE);
            }
        }

        return DataStore.saveData();
    }

}
