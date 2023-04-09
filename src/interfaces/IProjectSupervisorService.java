package interfaces;

import java.util.ArrayList;

import models.Project;

public interface IProjectSupervisorService {
    public ArrayList<Project> getSubmittedProjects(String supervisorID);

    public boolean createProjects(ArrayList<Project> projects);

    public boolean updateProjectTitle(Project project, String title, String supervisorID);

    public boolean updateRemainingProjectsToUnavailable(String supervisorID);
}
