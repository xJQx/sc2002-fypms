package test;

import java.util.Map;

import models.FYPCoordinator;
import models.Project;
import models.Request;
import models.Student;
import models.Supervisor;
import services.CsvDataService;
import store.AppStore;

public class AppTest {
	public static void main(String[] args) {
		AppStore.initAppStore();
		Map<String, Student> studentMap = AppStore.getStudentsData();
		studentMap.get("YCHERN").setIsRegistered(false);
		AppStore.setStudentsData(studentMap);
		
		// Request
//		Map<Integer, Request> requestMap = csvDataService.importRequestData("data/request.csv");
//		csvDataService.exportRequestData("data/request.csv", requestMap);
	}
}
