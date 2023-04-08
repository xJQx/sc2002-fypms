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
import store.AppStore;

public class AppTest {
	public static void main(String[] args) {
		AppStore.initAppStore();
		
		// ChangeProjectTitleRequest - Student to Supervisor
//		Map<Integer, Request> requestsMap = AppStore.getRequestsData();
//		Request newRequest = new ChangeProjectTitleRequest("YCHERN", "ASMADHUKUMAR", 1, "Machine Learning-based Interference Mitigation in a Multi-tier Networks");
//		requestsMap.put(newRequest.getRequestID(), newRequest);
//		AppStore.setRequestsData(requestsMap);
//		newRequest.approve();
		
		// AllocateProjectRequest - Student to FYPCoordinator
//		Map<Integer, Request> requestsMap = AppStore.getRequestsData();
//		Request newRequest = new AllocateProjectRequest("YCHERN", "ASFLI", 1);
//		requestsMap.put(newRequest.getRequestID(), newRequest);
//		AppStore.setRequestsData(requestsMap);
//		newRequest.approve();
		
		// DeregisterProjectRequest - Student to FYPCoordinator
//		Map<Integer, Request> requestsMap2 = AppStore.getRequestsData();
//		Request newRequest2 = new DeregisterProjectRequest("YCHERN", "ASFLI", 1);
//		requestsMap2.put(newRequest2.getRequestID(), newRequest2);
//		AppStore.setRequestsData(requestsMap2);
//		newRequest2.approve();
		
		// TransferStudentRequest - Supervisor to FYP Coordinator
//		Map<Integer, Request> requestsMap = AppStore.getRequestsData();
//		Request newRequest = new TransferStudentRequest("ASMADHUKUMAR", "ASFLI", 1, "ASCKYEO");
//		requestsMap.put(newRequest.getRequestID(), newRequest);
//		AppStore.setRequestsData(requestsMap);
//		newRequest.approve();
		
//		Map<String, Student> studentMap = AppStore.getStudentsData();
//		studentMap.get("YCHERN").setIsDeregistered(true);
//		AppStore.setStudentsData(studentMap);
		
		// Request
//		Map<Integer, Request> requestMap = csvDataService.importRequestData("data/request.csv");
//		csvDataService.exportRequestData("data/request.csv", requestMap);
	}
}
