package interfaces;

import java.util.ArrayList;

import models.Project;

public interface IProjectStudentService {
    public ArrayList<Project> getAvailableProjects();

    public Project getAllocatedProject(String studentID);

    public Project getReservedProject(String studentID);

}
