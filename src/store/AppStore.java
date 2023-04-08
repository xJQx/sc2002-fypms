package store;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import interfaces.IFileDataService;
import models.FYPCoordinator;
import models.Project;
import models.Request;
import models.Student;
import models.Supervisor;
import services.CsvDataService;

public class AppStore {
	private static IFileDataService fileDataService = new CsvDataService();
	private static Map<String, String> filePathsMap = new HashMap<String, String>();
	
	private static Map<String, Student> studentsData = new HashMap<String, Student>();
	private static Map<String, Supervisor> supervisorsData = new HashMap<String, Supervisor>();
	private static Map<String, FYPCoordinator> fypcoordinatorsData = new HashMap<String, FYPCoordinator>();
	private static Map<Integer, Project> projectsData = new HashMap<Integer, Project>();
	private static Map<Integer, Request> requestData = new HashMap<Integer, Request>();	
	
	
	private AppStore() {}
	
	public static boolean initAppStore() {
		// Initialize filePathsMap
		filePathsMap.put("user", "data/user.csv");
		filePathsMap.put("student", "data/student.csv");
		filePathsMap.put("supervisor", "data/faculty.csv");
		filePathsMap.put("fypcoordinator", "data/fypcoordinator.csv");
		filePathsMap.put("project", "data/project.csv");
		filePathsMap.put("request", "data/request.csv");
		
		// Import data
		AppStore.studentsData = fileDataService.importStudentData(filePathsMap.get("user"), filePathsMap.get("student"));
		AppStore.supervisorsData = fileDataService.importSupervisorData(filePathsMap.get("user"), filePathsMap.get("supervisor"));
		AppStore.fypcoordinatorsData = fileDataService.importFYPCoordinatorData(filePathsMap.get("user"), filePathsMap.get("supervisor"), filePathsMap.get("fypcoordinator"));
		AppStore.projectsData = fileDataService.importProjectData(filePathsMap.get("project"), filePathsMap.get("user"), filePathsMap.get("student"), filePathsMap.get("supervisor"), filePathsMap.get("fypcoordinator"));
		AppStore.requestData = fileDataService.importRequestData(filePathsMap.get("request"));
		
		return true;
	}
	
	// ---------- Student ---------- //
	public static Map<String, Student> getStudentsData() {
		return AppStore.studentsData;
	}
	
	public static void setStudentsData(Map<String, Student> studentsData) {
		AppStore.studentsData = studentsData;
		fileDataService.exportStudentData(filePathsMap.get("user"), filePathsMap.get("student"), studentsData);
	}
	
	// ---------- Supervisor ---------- //
	public static Map<String, Supervisor> getSupervisorsData() {
		return AppStore.supervisorsData;
	}
	
	public static void setSupervisorsData(Map<String, Supervisor> supervisorsData) {
		AppStore.supervisorsData = supervisorsData;
		fileDataService.exportSupervisorData(filePathsMap.get("user"), filePathsMap.get("supervisor"), supervisorsData);
	}
	
	// ---------- FYP Coordinator ---------- //
	public static Map<String, FYPCoordinator> getFYPCoordinatorsData() {
		return AppStore.fypcoordinatorsData;
	}
	
	public static void setFYPCoordinatorsData(Map<String, FYPCoordinator> fypcoordinatorsData) {
		AppStore.fypcoordinatorsData = fypcoordinatorsData;
		fileDataService.exportFYPCoordinatorData(filePathsMap.get("user"), filePathsMap.get("supervisor"), filePathsMap.get("fypcoordinator"), fypcoordinatorsData);
	}
	
	// ---------- Project ---------- //
	public static Map<Integer, Project> getProjectsData() {
		return AppStore.projectsData;
	}
	
	public static void setProjectsData(Map<Integer, Project> projectsData) {
		AppStore.projectsData = projectsData;
		fileDataService.exportProjectData(filePathsMap.get("project"), projectsData);
	}
	
	// ---------- Request ---------- //
	public static Map<Integer, Request> getRequestsData() {
		return AppStore.requestData;
	}
	
	public static void setRequestsData(Map<Integer, Request> requestData) {
		AppStore.requestData = requestData;
		fileDataService.exportRequestData(filePathsMap.get("request"), requestData);
	}
}
