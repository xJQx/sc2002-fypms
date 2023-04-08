package store;

import java.util.HashMap;
import java.util.Map;

import models.FYPCoordinator;
import models.Project;
import models.Request;
import models.Student;
import models.Supervisor;

public class AppStore {
	private static Map<String, Student> studentsData = new HashMap<String, Student>();
	private static Map<String, Supervisor> supervisorsData = new HashMap<String, Supervisor>();
	private static Map<String, FYPCoordinator> fypcoordinatorsData = new HashMap<String, FYPCoordinator>();
	private static Map<Integer, Project> projectsData = new HashMap<Integer, Project>();
	private static Map<Integer, Request> requestData = new HashMap<Integer, Request>();
	
	private AppStore() {}
	
	public static boolean initAppStore() {
		return true;
	}
	
	// ---------- Student ---------- //
	public static Map<String, Student> getStudentsData() {
		return AppStore.studentsData;
	}
	
	public static void setStudentsData(HashMap<String, Student> studentsData) {
		AppStore.studentsData = studentsData;
	}
	
	// ---------- Supervisor ---------- //
	public static Map<String, Supervisor> getSupervisorsData() {
		return AppStore.supervisorsData;
	}
	
	public static void setSupervisorsData(HashMap<String, Supervisor> supervisorsData) {
		AppStore.supervisorsData = supervisorsData;
	}
	
	// ---------- FYP Coordinator ---------- //
	public static Map<String, FYPCoordinator> getFYPCoordinatorsData() {
		return AppStore.fypcoordinatorsData;
	}
	
	public static void setFYPCoordinatorsData(HashMap<String, FYPCoordinator> fypcoordinatorsData) {
		AppStore.fypcoordinatorsData = fypcoordinatorsData;
	}
	
	// ---------- Project ---------- //
	public static Map<Integer, Project> getProjectsData() {
		return AppStore.projectsData;
	}
	
	public static void setProjectsData(HashMap<Integer, Project> projectsData) {
		AppStore.projectsData = projectsData;
	}
	
	// ---------- Request ---------- //
	public static Map<Integer, Request> getRequestsData() {
		return AppStore.requestData;
	}
	
	public static void setRequestsData(HashMap<Integer, Request> requestData) {
		AppStore.requestData = requestData;
	}
}
