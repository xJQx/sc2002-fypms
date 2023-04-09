package services;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import enums.ProjectStatus;
import interfaces.IProjectStudentService;
import models.Project;
import store.DataStore;

public class ProjectStudentService implements IProjectStudentService {

    @Override
    public ArrayList<Project> getAvailableProjects() {
        Map<Integer, Project> projectsData = DataStore.getProjectsData();
        ArrayList<Project> availableProjects = projectsData.values().stream()
                .filter(project -> project.getStatus() == ProjectStatus.AVAILABLE)
                .collect(Collectors.toCollection(ArrayList::new));

        return availableProjects;
    }

    @Override
    public Project getAllocatedProject(String studentID) {
        Map<Integer, Project> projectsData = DataStore.getProjectsData();

        Project allocatedProject = projectsData.values().stream()
                .filter(project -> project.getStatus() == ProjectStatus.AVAILABLE
                        && project.getStudent() != null
                        && Objects.equals(project.getStudent().getStudentID(), studentID))
                .findFirst()
                .orElse(null);

        return allocatedProject;
    }

    @Override
    public Project getReservedProject(String studentID) {
        Map<Integer, Project> projectsData = DataStore.getProjectsData();

        Project reservedProject = projectsData.values().stream()
                .filter(project -> project.getStatus() == ProjectStatus.RESERVED
                        && project.getStudent() != null
                        && Objects.equals(project.getStudent().getStudentID(), studentID))
                .findFirst()
                .orElse(null);

        return reservedProject;
    }

}
