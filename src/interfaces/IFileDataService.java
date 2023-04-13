package interfaces;

import java.util.Map;

import models.FYPCoordinator;
import models.Project;
import models.Request;
import models.Student;
import models.Supervisor;

/**
 * The {@link IFileDataService} interface defines a contract for importing and
 * exporting data to and from files.
 */
public interface IFileDataService {
	// ---------- Student ---------- //
	/**
	 * Imports student data from the specified file paths.
	 *
	 * @param usersFilePath    the file path of the users file
	 * @param studentsFilePath the file path of the students file
	 * @return a {@link Map} of {@link Student} objects with their IDs as keys
	 */
	Map<String, Student> importStudentData(String usersFilePath, String studentsFilePath);
	
	/**
	 * Exports student data to the specified file paths.
	 *
	 * @param usersFilePath    the file path of the users file
	 * @param studentsFilePath the file path of the students file
	 * @param studentMap       a {@link Map} of {@link Student} objects with their
	 *                         IDs as keys
	 * @return true if the data was exported successfully, false otherwise
	 */
	boolean exportStudentData(String usersFilePath, String studentsFilePath, Map<String, Student> studentMap);

	// ---------- Supervisor ---------- //
	/**
	 * Imports supervisor data from the specified file paths.
	 *
	 * @param usersFilePath      the file path of the users file
	 * @param supervisorFilePath the file path of the supervisors file
	 * @return a {@link Map} of {@link Supervisor} objects with their IDs as keys
	 */
	Map<String, Supervisor> importSupervisorData(String usersFilePath, String supervisorFilePath);
	
	/**
	 * Exports supervisor data to the specified file paths.
	 *
	 * @param usersFilePath       the file path of the users file
	 * @param supervisorsFilePath the file path of the supervisors file
	 * @param supervisorMap       a {@link Map} of {@link Supervisor} objects with
	 *                            their IDs as keys
	 * @return true if the data was exported successfully, false otherwise
	 */
	boolean exportSupervisorData(String usersFilePath, String supervisorsFilePath, Map<String, Supervisor> supervisorMap);

	// ---------- FYPCoordinator ---------- //
	/**
	 * Imports FYP Coordinator data from the specified file paths.
	 *
	 * @param usersFilePath           the file path of the users file
	 * @param supervisorsFilePath     the file path of the supervisors file
	 * @param fypCoordinatorsFilePath the file path of the FYP Coordinators file
	 * @return a {@link Map} of {@link FYPCoordinator} objects with their IDs as
	 *         keys
	 */
	Map<String, FYPCoordinator> importFYPCoordinatorData(String usersFilePath, String supervisorsFilePath, String fypCoordinatorsFilePath);
	
	/**
	 * Exports FYP Coordinator data to the specified file paths.
	 *
	 * @param usersFilePath           the file path of the users file
	 * @param supervisorsFilePath     the file path of the supervisors file
	 * @param fypCoordinatorsFilePath the file path of the FYP Coordinators file
	 * @param fypCoordinatorMap       a {@link Map} of {@link FYPCoordinator}
	 *                                objects with their IDs as keys
	 * @return true if the data was exported successfully, false otherwise
	 */
	boolean exportFYPCoordinatorData(String usersFilePath, String supervisorsFilePath, String fypCoordinatorsFilePath, Map<String, FYPCoordinator> fypCoordinatorMap);

	// ---------- Project ---------- //
	/**
	 * Imports project data from the specified file paths.
	 *
	 * @param projectsFilePath        the file path of the projects file
	 * @param usersFilePath           the file path of the users file
	 * @param studentsFilePath        the file path of the students file
	 * @param supervisorsFilePath     the file path of the supervisors file
	 * @param fypCoordinatorsFilePath the file path of the FYP Coordinators file
	 * @return a {@link Map} of {@link Project} objects with their IDs as keys
	 */
	Map<Integer, Project> importProjectData(String projectsFilePath, String usersFilePath, String studentsFilePath, String supervisorsFilePath, String fypCoordinatorsFilePath);
	
	/**
	 * Exports project data to the specified file path.
	 *
	 * @param projectsFilePath the file path of the projects file
	 * @param projectMap       a {@link Map} of {@link Project} objects with their
	 *                         IDs as keys
	 * @return true if the data was exported successfully, false otherwise
	 */
	boolean exportProjectData(String projectsFilePath, Map<Integer, Project> projectMap);

	// ---------- Request ---------- //
	/**
	 * Imports request data from the specified file paths.
	 *
	 * @param requestsFilePath                   the file path of the requests file
	 * @param transferStudentRequestsFilePath    the file path of the transfer
	 *                                           student requests file
	 * @param changeProjectTitleRequestsFilePath the file path of the change project
	 *                                           title requests file
	 * @return a {@link Map} of {@link Request} objects with their IDs as keys
	 */
	Map<Integer, Request> importRequestData(String requestsFilePath, String transferStudentRequestsFilePath, String changeProjectTitleRequestsFilePath);

	/**
	 * Exports request data to the specified file paths.
	 *
	 * @param requestsFilePath                   the file path of the requests file
	 * @param transferStudentRequestsFilePath    the file path of the transfer
	 *                                           student requests file
	 * @param changeProjectTitleRequestsFilePath the file path of the change project
	 *                                           title requests file
	 * @param requestMap                         a {@link Map} of {@link Request}
	 *                                           objects with their IDs as keys
	 * @return true if the data was exported successfully, false otherwise
	 */
	boolean exportRequestData(String requestsFilePath, String transferStudentRequestsFilePath, String changeProjectTitleRequestsFilePath, Map<Integer, Request> requestMap);
}
