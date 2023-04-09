package test;

import java.util.Map;

import models.AllocateProjectRequest;
import models.ChangeProjectTitleRequest;
import models.DeregisterProjectRequest;
import models.FYPCoordinator;
import models.Project;
import models.Request;
import models.Student;
import models.Supervisor;
import models.TransferStudentRequest;
import services.CsvDataService;
import store.DataStore;

public class AppTest {
	public static void main(String[] args) {
		DataStore.initDataStore();

		// ChangeProjectTitleRequest - Student to Supervisor
		// Map<Integer, Request> requestsMap = DataStore.getRequestsData();
		// Request newRequest = new ChangeProjectTitleRequest("YCHERN", "ASMADHUKUMAR",
		// 1, "Machine Learning-based Interference Mitigation in a Multi-tier
		// Networks");
		// requestsMap.put(newRequest.getRequestID(), newRequest);
		// DataStore.setRequestsData(requestsMap);
		// newRequest.approve();

		// AllocateProjectRequest - Student to FYPCoordinator
		// Map<Integer, Request> requestsMap = DataStore.getRequestsData();
		// Request newRequest = new AllocateProjectRequest("YCHERN", "ASFLI", 1);
		// requestsMap.put(newRequest.getRequestID(), newRequest);
		// DataStore.setRequestsData(requestsMap);
		// newRequest.approve();

		// DeregisterProjectRequest - Student to FYPCoordinator
		// Map<Integer, Request> requestsMap2 = DataStore.getRequestsData();
		// Request newRequest2 = new DeregisterProjectRequest("YCHERN", "ASFLI", 1);
		// requestsMap2.put(newRequest2.getRequestID(), newRequest2);
		// DataStore.setRequestsData(requestsMap2);
		// newRequest2.approve();

		// TransferStudentRequest - Supervisor to FYP Coordinator
		// Map<Integer, Request> requestsMap = DataStore.getRequestsData();
		// Request newRequest = new TransferStudentRequest("ASMADHUKUMAR", "ASFLI", 1,
		// "ASCKYEO");
		// requestsMap.put(newRequest.getRequestID(), newRequest);
		// DataStore.setRequestsData(requestsMap);
		// newRequest.approve();

		// Map<String, Student> studentMap = DataStore.getStudentsData();
		// studentMap.get("YCHERN").setIsDeregistered(true);
		// DataStore.setStudentsData(studentMap);

		// Request
		// Map<Integer, Request> requestMap =
		// csvDataService.importRequestData("data/request.csv");
		// csvDataService.exportRequestData("data/request.csv", requestMap);
	}
}
