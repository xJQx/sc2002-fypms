package utils;

import java.util.HashMap;
import java.util.Map;

public class FilePathsUtils {
	private static Map<String, String> filePathsMap = new HashMap<String, String>();
	
	public static Map<String, String> csvFilePaths() {
		filePathsMap.clear();

		// Initialize filePathsMap
		filePathsMap.put("user", "data/user.csv");
		filePathsMap.put("student", "data/student.csv");
		filePathsMap.put("supervisor", "data/faculty.csv");
		filePathsMap.put("fypcoordinator", "data/fypcoordinator.csv");
		filePathsMap.put("project", "data/project.csv");
		filePathsMap.put("request", "data/request.csv");
		filePathsMap.put("transferStudentRequest", "data/request_transfer_student.csv");
		filePathsMap.put("changeProjectTitleRequest", "data/request_change_project_title.csv");

		return filePathsMap;
	}
}
