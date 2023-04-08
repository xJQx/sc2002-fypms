package interfaces;

import java.util.Map;

import models.FYPCoordinator;
import models.Project;
import models.Request;
import models.Student;
import models.Supervisor;

public interface IFileDataService {
	// ---------- Student ---------- //
	Map<String, Student> importStudentData(String usersFilePath, String studentsFilePath);
	boolean exportStudentData(String usersFilePath, String studentsFilePath, Map<String, Student> studentMap);

	// ---------- Supervisor ---------- //
	Map<String, Supervisor> importSupervisorData(String usersFilePath, String supervisorFilePath);
	boolean exportSupervisorData(String usersFilePath, String supervisorsFilePath, Map<String, Supervisor> supervisorMap);

	// ---------- FYPCoordinator ---------- //
	Map<String, FYPCoordinator> importFYPCoordinatorData(String usersFilePath, String supervisorsFilePath, String fypCoordinatorsFilePath);
	boolean exportFYPCoordinatorData(String usersFilePath, String supervisorsFilePath, String fypCoordinatorsFilePath, Map<String, FYPCoordinator> fypCoordinatorMap);

	// ---------- Project ---------- //
	Map<Integer, Project> importProjectData(String projectsFilePath, String usersFilePath, String studentsFilePath, String supervisorsFilePath, String fypCoordinatorsFilePath);
	boolean exportProjectData(String projectsFilePath, Map<Integer, Project> projectMap);

	// ---------- Request ---------- //
	Map<Integer, Request> importRequestData(String requestsFilePath, String transferStudentRequestsFilePath, String changeProjectTitleRequestsFilePath);
	boolean exportRequestData(String requestsFilePath, String transferStudentRequestsFilePath, String changeProjectTitleRequestsFilePath, Map<Integer, Request> requestMap);
}
