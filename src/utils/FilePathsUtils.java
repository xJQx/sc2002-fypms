package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link FilePathsUtils} class provides utility methods for managing file
 * paths within the application. It contains a method to return a mapping of CSV
 * file paths for various data types.
 */
public class FilePathsUtils {
	private static Map<String, String> filePathsMap = new HashMap<String, String>();

	/**
	 * Returns a mapping of CSV file paths for various data types used in the
	 * application. The returned map contains keys such as "user", "student",
	 * "supervisor", "fypcoordinator", "project", "request",
	 * "transferStudentRequest", and "changeProjectTitleRequest",
	 * each associated with their respective file paths.
	 *
	 * @return a {@link Map} containing the CSV file paths for various data types
	 */
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
