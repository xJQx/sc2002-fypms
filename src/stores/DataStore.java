package stores;

import java.util.HashMap;
import java.util.Map;

import interfaces.IFileDataService;
import models.FYPCoordinator;
import models.Project;
import models.Request;
import models.Student;
import models.Supervisor;

/**
 * The {@link DataStore} class provides utility methods for managing data
 * storage within the application. It offers methods to initialize the data
 * store, import and export data to and from the file system, and interact with
 * data maps for various data types.
 */
public class DataStore {
	private static IFileDataService fileDataService;
	private static Map<String, String> filePathsMap;

	private static Map<String, Student> studentsData = new HashMap<String, Student>();
	private static Map<String, Supervisor> supervisorsData = new HashMap<String, Supervisor>();
	private static Map<String, FYPCoordinator> fypcoordinatorsData = new HashMap<String, FYPCoordinator>();
	private static Map<Integer, Project> projectsData = new HashMap<Integer, Project>();
	private static Map<Integer, Request> requestData = new HashMap<Integer, Request>();

	private DataStore() {
	}

	/**
	 * Initializes the DataStore by setting up the file data service, file paths
	 * map, and importing data from the file system.
	 *
	 * @param fileDataService the {@link IFileDataService} instance to use for data
	 *                        operations
	 * @param filePathsMap    the {@link Map} containing file paths for various data
	 *                        types
	 * @return {@code true} if the initialization is successful, {@code false}
	 *         otherwise
	 */
	public static boolean initDataStore(IFileDataService fileDataService, Map<String, String> filePathsMap) {
		// Initialize fileDataService and filePathsMap
		DataStore.filePathsMap = filePathsMap;
		DataStore.fileDataService = fileDataService;

		// Import data
		DataStore.studentsData = fileDataService.importStudentData(filePathsMap.get("user"),
				filePathsMap.get("student"));
		DataStore.supervisorsData = fileDataService.importSupervisorData(filePathsMap.get("user"),
				filePathsMap.get("supervisor"));
		DataStore.fypcoordinatorsData = fileDataService.importFYPCoordinatorData(filePathsMap.get("user"),
				filePathsMap.get("supervisor"), filePathsMap.get("fypcoordinator"));
		DataStore.projectsData = fileDataService.importProjectData(filePathsMap.get("project"),
				filePathsMap.get("user"), filePathsMap.get("student"), filePathsMap.get("supervisor"),
				filePathsMap.get("fypcoordinator"));
		DataStore.requestData = fileDataService.importRequestData(filePathsMap.get("request"),
				filePathsMap.get("transferStudentRequest"), filePathsMap.get("changeProjectTitleRequest"));

		return true;
	}

	/**
	 * Saves the data from the DataStore to the file system.
	 *
	 * @return {@code true} if the data is saved successfully, {@code false}
	 *         otherwise
	 */
	public static boolean saveData() {
		DataStore.setStudentsData(studentsData);
		DataStore.setSupervisorsData(supervisorsData);
		DataStore.setFYPCoordinatorsData(fypcoordinatorsData);
		DataStore.setProjectsData(projectsData);
		DataStore.setRequestsData(requestData);

		return true;
	}

	// ---------- Student ---------- //
	/**
	 * Gets the students data map.
	 *
	 * @return a {@link Map} containing student ID as the key and {@link Student}
	 *         objects as the value
	 */
	public static Map<String, Student> getStudentsData() {
		return DataStore.studentsData;
	}

	/**
	 * Sets the students data map and saves the data to the file system.
	 *
	 * @param studentsData a {@link Map} containing student ID as the key and
	 *                     {@link Student} objects as the value
	 */
	public static void setStudentsData(Map<String, Student> studentsData) {
		DataStore.studentsData = studentsData;
		fileDataService.exportStudentData(filePathsMap.get("user"), filePathsMap.get("student"), studentsData);
	}

	// ---------- Supervisor ---------- //
	/**
	 * Gets the supervisors data map.
	 *
	 * @return a {@link Map} containing supervisor ID as the key and
	 *         {@link Supervisor} objects as the value
	 */
	public static Map<String, Supervisor> getSupervisorsData() {
		return DataStore.supervisorsData;
	}

	/**
	 * Sets the supervisors data map and saves the data to the file system.
	 *
	 * @param supervisorsData a {@link Map} containing supervisor ID as the key and
	 *                        {@link Supervisor} objects as the value
	 */
	public static void setSupervisorsData(Map<String, Supervisor> supervisorsData) {
		DataStore.supervisorsData = supervisorsData;
		fileDataService.exportSupervisorData(filePathsMap.get("user"), filePathsMap.get("supervisor"), supervisorsData);
	}

	// ---------- FYP Coordinator ---------- //
	/**
	 * Gets the FYP coordinators data map.
	 *
	 * @return a {@link Map} containing FYP coordinator ID as the key and
	 *         {@link FYPCoordinator} objects as the value
	 */
	public static Map<String, FYPCoordinator> getFYPCoordinatorsData() {
		return DataStore.fypcoordinatorsData;
	}

	/**
	 * Sets the FYP coordinators data map and saves the data to the file system.
	 *
	 * @param fypcoordinatorsData a {@link Map} containing FYP coordinator ID as the
	 *                            key and {@link FYPCoordinator} objects as the
	 *                            value
	 */
	public static void setFYPCoordinatorsData(Map<String, FYPCoordinator> fypcoordinatorsData) {
		DataStore.fypcoordinatorsData = fypcoordinatorsData;
		fileDataService.exportFYPCoordinatorData(filePathsMap.get("user"), filePathsMap.get("supervisor"),
				filePathsMap.get("fypcoordinator"), fypcoordinatorsData);
	}

	// ---------- Project ---------- //
	/**
	 * Gets the projects data map.
	 *
	 * @return a {@link Map} containing project ID as the key and {@link Project}
	 *         objects as the value
	 */
	public static Map<Integer, Project> getProjectsData() {
		return DataStore.projectsData;
	}

	/**
	 * Sets the projects data map and saves the data to the file system.
	 *
	 * @param projectsData a {@link Map} containing project ID as the key and
	 *                     {@link Project} objects as the value
	 */
	public static void setProjectsData(Map<Integer, Project> projectsData) {
		DataStore.projectsData = projectsData;
		fileDataService.exportProjectData(filePathsMap.get("project"), projectsData);
	}

	// ---------- Request ---------- //
	/**
	 * Gets the requests data map.
	 *
	 * @return a {@link Map} containing requestID as the key and {@link Request}
	 *         objects as the value
	 */
	public static Map<Integer, Request> getRequestsData() {
		return DataStore.requestData;
	}

	/**
	 * Sets the requests data map and saves the data to the file system.
	 *
	 * @param requestData a {@link Map} containing request ID as the key and
	 *                    {@link Request} objects as the value
	 */
	public static void setRequestsData(Map<Integer, Request> requestData) {
		DataStore.requestData = requestData;
		fileDataService.exportRequestData(filePathsMap.get("request"), filePathsMap.get("transferStudentRequest"),
				filePathsMap.get("changeProjectTitleRequest"), requestData);
	}
}
