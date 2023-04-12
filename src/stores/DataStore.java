package stores;

import java.util.HashMap;
import java.util.Map;

import interfaces.IFileDataService;
import models.FYPCoordinator;
import models.Project;
import models.Request;
import models.Student;
import models.Supervisor;

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

	public static boolean saveData() {
		DataStore.setStudentsData(studentsData);
		DataStore.setSupervisorsData(supervisorsData);
		DataStore.setFYPCoordinatorsData(fypcoordinatorsData);
		DataStore.setProjectsData(projectsData);
		DataStore.setRequestsData(requestData);

		return true;
	}

	// ---------- Student ---------- //
	public static Map<String, Student> getStudentsData() {
		return DataStore.studentsData;
	}

	public static void setStudentsData(Map<String, Student> studentsData) {
		DataStore.studentsData = studentsData;
		fileDataService.exportStudentData(filePathsMap.get("user"), filePathsMap.get("student"), studentsData);
	}

	// ---------- Supervisor ---------- //
	public static Map<String, Supervisor> getSupervisorsData() {
		return DataStore.supervisorsData;
	}

	public static void setSupervisorsData(Map<String, Supervisor> supervisorsData) {
		DataStore.supervisorsData = supervisorsData;
		fileDataService.exportSupervisorData(filePathsMap.get("user"), filePathsMap.get("supervisor"), supervisorsData);
	}

	// ---------- FYP Coordinator ---------- //
	public static Map<String, FYPCoordinator> getFYPCoordinatorsData() {
		return DataStore.fypcoordinatorsData;
	}

	public static void setFYPCoordinatorsData(Map<String, FYPCoordinator> fypcoordinatorsData) {
		DataStore.fypcoordinatorsData = fypcoordinatorsData;
		fileDataService.exportFYPCoordinatorData(filePathsMap.get("user"), filePathsMap.get("supervisor"),
				filePathsMap.get("fypcoordinator"), fypcoordinatorsData);
	}

	// ---------- Project ---------- //
	public static Map<Integer, Project> getProjectsData() {
		return DataStore.projectsData;
	}

	public static void setProjectsData(Map<Integer, Project> projectsData) {
		DataStore.projectsData = projectsData;
		fileDataService.exportProjectData(filePathsMap.get("project"), projectsData);
	}

	// ---------- Request ---------- //
	public static Map<Integer, Request> getRequestsData() {
		return DataStore.requestData;
	}

	public static void setRequestsData(Map<Integer, Request> requestData) {
		DataStore.requestData = requestData;
		fileDataService.exportRequestData(filePathsMap.get("request"), filePathsMap.get("transferStudentRequest"),
				filePathsMap.get("changeProjectTitleRequest"), requestData);
	}
}
