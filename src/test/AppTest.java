package test;

import java.util.List;
import java.util.Map;

import models.FYPCoordinator;
import models.Student;
import models.Supervisor;
import services.CsvDataService;

public class AppTest {
	public static void main(String[] args) {
		CsvDataService csvDataService = new CsvDataService();

		// Import
//		Map<String, Student> studentsMap = csvDataService.importStudentData("data/user.csv", "data/student.csv");
//		
//		for (Student student : studentsMap.values()) {
//			System.out.println("Student");
//			System.out.println(student.getStudentID());
//			System.out.println(student.getName());
//			System.out.println(student.getUserID());
//			System.out.println(student.getEmail());
//			System.out.println(student.getIsRegistered());
//			System.out.println("");
//		}
//		System.out.println(studentsMap.size());
//		
//		// Export
//		System.out.println(studentsMap.get("YCHERN").setPassword("newpassword", "password"));
//		csvDataService.exportStudentData("data/user.csv", "data/student.csv", studentsMap);
		
//		Map<String, Supervisor> supervisorMap = csvDataService.importSupervisorData("data/user.csv", "data/faculty.csv");
//		csvDataService.exportSupervisorData("data/user.csv", "data/faculty.csv", supervisorMap);
		
//		Map<String, FYPCoordinator> fypCoordinatorMap = csvDataService.importFYPCoordinatorData("data/user.csv", "data/faculty.csv", "data/fypcoordinator.csv");
//		csvDataService.exportFYPCoordinatorData("data/user.csv", "data/faculty.csv", "data/fypcoordinator.csv", fypCoordinatorMap);
	}
}
