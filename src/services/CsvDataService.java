package services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enums.ProjectStatus;
import enums.RequestStatus;
import enums.RequestType;
import interfaces.IFileDataService;
import models.AllocateProjectRequest;
import models.ChangeProjectTitleRequest;
import models.DeregisterProjectRequest;
import models.FYPCoordinator;
import models.Project;
import models.Request;
import models.Student;
import models.Supervisor;
import models.TransferStudentRequest;

/**
 * The {@link CsvDataService} class implements the {@link IFileDataService}
 * interface and provides
 * methods for reading and writing data from/to CSV files.
 */
public class CsvDataService implements IFileDataService {
	/**
	 * The list of headers for the CSV file that stores user data.
	 */
	private static List<String> userCsvHeaders = new ArrayList<String>();

	/**
	 * The list of headers for the CSV file that stores student data.
	 */
	private static List<String> studentCsvHeaders = new ArrayList<String>();

	/**
	 * The list of headers for the CSV file that stores supervisor data.
	 */
	private static List<String> supervisorCsvHeaders = new ArrayList<String>();

	/**
	 * The list of headers for the CSV file that stores FYP coordinator data.
	 */
	private static List<String> fypCoordinatorCsvHeaders = new ArrayList<String>();

	/**
	 * The list of headers for the CSV file that stores project data.
	 */
	private static List<String> projectCsvHeaders = new ArrayList<String>();

	/**
	 * The list of headers for the CSV file that stores request data.
	 */
	private static List<String> requestCsvHeaders = new ArrayList<String>();

	/**
	 * The list of headers for the CSV file that stores transfer student request
	 * data.
	 */
	private static List<String> transferStudentRequestCsvHeaders = new ArrayList<String>();

	/**
	 * The list of headers for the CSV file that stores change project title request
	 * data.
	 */
	private static List<String> changeProjectTitleRequestCsvHeaders = new ArrayList<String>();

	/**
	 * Constructs an instance of the {@link CsvDataService} class.
	 */
    public CsvDataService() {
    };

	// ---------- Helper Function ---------- //
	/**
	 * Reads data from the CSV file located at the given file path and returns it as
	 * a list of string arrays.
	 *
	 * @param filePath the file path of the CSV file to read
	 * @param headers  the list of headers for the CSV file
	 * @return a list of string arrays containing the CSV data
	 */
	public List<String[]> readCsvFile(String filePath, List<String> headers) {
		List<String[]> dataList = new ArrayList<String[]>();
		headers.clear();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			// Headers
			String[] headerRow = br.readLine().split(",");
			for (String header : headerRow) {
				headers.add(header);
			}

			// Content
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				dataList.add(values);
			}

		} catch (IOException e) {
			System.out.println("Cannot import data!");
		}

		return dataList;
	}

	/**
	 * Writes the given data to a CSV file located at the given file path.
	 *
	 * @param filePath the file path of the CSV file to write
	 * @param headers  the list of headers for the CSV file
	 * @param lines    the list of lines to write to the CSV file
	 * @return true if the data is written successfully, false otherwise
	 */
	public boolean writeCsvFile(String filePath, List<String> headers, List<String> lines) {
		try (FileWriter writer = new FileWriter(filePath)) {
			// Write Headers
			String headerLine = String.join(",", headers);
			writer.write(headerLine + "\n");

			// Write Content
			for (String line : lines) {
				writer.write(line + "\n");
			}
		} catch (IOException e) {
			System.out.println("Cannot export data!");
			return false;
		}
		return true;
	}

	/**
	 * Parses a string array containing user data and returns a map of user
	 * information.
	 *
	 * @param userRow the string array containing the user data
	 * @return a map of user information, where the keys are "userID", "password",
	 *         "email", "role", and "name" and the values are the corresponding
	 *         values in the userRow array
	 */
	private Map<String, String> parseUserRow(String[] userRow) {
		String userID = userRow[0];
		String password = userRow[1];
		String email = userRow[2];
		String role = userRow[3];
		String name = userRow[4];

		// to handle names with comma
		for (int i = 5; i < userRow.length; i++) {
			if (i != 4)
				name += ",";
			name += userRow[i];
		}

		// Return
		Map<String, String> userInfoMap = new HashMap<String, String>();
		userInfoMap.put("userID", userID);
		userInfoMap.put("password", password);
		userInfoMap.put("email", email);
		userInfoMap.put("role", role);
		userInfoMap.put("name", name);

		return userInfoMap;
	}

	// ---------- Interface method implementation ---------- //
	// Student
	@Override
	public Map<String, Student> importStudentData(String usersFilePath, String studentsFilePath) {
		Map<String, Student> studentsMap = new HashMap<String, Student>();

		List<String[]> usersRows = this.readCsvFile(usersFilePath, userCsvHeaders);
		List<String[]> studentsRows = this.readCsvFile(studentsFilePath, studentCsvHeaders);

		for (String[] userRow : usersRows) {
			Map<String, String> userInfoMap = parseUserRow(userRow);

			String role = userInfoMap.get("role");
			if (!role.equals("student"))
				continue;

			String userID = userInfoMap.get("userID");
			String password = userInfoMap.get("password");
			String name = userInfoMap.get("name");
			String email = userInfoMap.get("email");

			// get the associated student data
			boolean isDeregistered = false;
			for (String[] studentRow : studentsRows) {
				if (!studentRow[0].equals(userID))
					continue;

				isDeregistered = Boolean.parseBoolean(studentRow[1]);
			}

			Student student = new Student(userID, name, email, password, isDeregistered);

			studentsMap.put(userID, student);
		}

		return studentsMap;
	}

	@Override
	public boolean exportStudentData(String usersFilePath, String studentsFilePath, Map<String, Student> studentMap) {
		List<String> studentLines = new ArrayList<String>();
		List<String> userLines = new ArrayList<String>();

		// User
		List<String[]> usersRows = this.readCsvFile(usersFilePath, userCsvHeaders);
		for (String[] userRow : usersRows) {
			Map<String, String> userInfoMap = parseUserRow(userRow);
			String userLine = String.format("%s,%s,%s,%s,%s",
					userInfoMap.get("userID"),
					userInfoMap.get("password"),
					userInfoMap.get("email"),
					userInfoMap.get("role"),
					userInfoMap.get("name"));

			if (userInfoMap.get("role").equals("student")) {
				Student student = studentMap.get(userInfoMap.get("userID"));

				userLine = String.format("%s,%s,%s,%s,%s",
						student.getUserID(),
						student.getPassword(),
						student.getEmail(),
						"student", // role
						student.getName());
			}

			userLines.add(userLine);
		}

		// Student
		for (Student student : studentMap.values()) {
			String studentLine = String.format("%s,%b",
					student.getStudentID(),
					student.getIsDeregistered());

			studentLines.add(studentLine);
		}

		// Write to CSV
		boolean success1 = this.writeCsvFile(usersFilePath, userCsvHeaders, userLines);
		boolean success2 = this.writeCsvFile(studentsFilePath, studentCsvHeaders, studentLines);
		return success1 && success2;
	}

	// Supervisor
	@Override
	public Map<String, Supervisor> importSupervisorData(String usersFilePath, String supervisorsFilePath) {
		Map<String, Supervisor> supervisorsMap = new HashMap<String, Supervisor>();

		List<String[]> usersRows = this.readCsvFile(usersFilePath, userCsvHeaders);
		List<String[]> supervisorsRows = this.readCsvFile(supervisorsFilePath, supervisorCsvHeaders);

		for (String[] userRow : usersRows) {
			Map<String, String> userInfoMap = parseUserRow(userRow);

			String role = userInfoMap.get("role");
			if (!role.equals("supervisor"))
				continue;

			String userID = userInfoMap.get("userID");
			String password = userInfoMap.get("password");
			String name = userInfoMap.get("name");
			String email = userInfoMap.get("email");

			// get the associated supervisor data
			int numOfProjects = 0;
			for (String[] supervisorRow : supervisorsRows) {
				if (!supervisorRow[0].equals(userID))
					continue;

				numOfProjects = Integer.parseInt(supervisorRow[1]);
			}

			Supervisor supervisor = new Supervisor(userID, name, email, password, numOfProjects);

			supervisorsMap.put(userID, supervisor);
		}

		return supervisorsMap;
	}

	@Override
	public boolean exportSupervisorData(String usersFilePath, String supervisorsFilePath,
			Map<String, Supervisor> supervisorMap) {
		List<String> supervisorLines = new ArrayList<String>();
		List<String> userLines = new ArrayList<String>();

		// User
		List<String[]> usersRows = this.readCsvFile(usersFilePath, userCsvHeaders);
		for (String[] userRow : usersRows) {
			Map<String, String> userInfoMap = parseUserRow(userRow);
			String userLine = String.format("%s,%s,%s,%s,%s",
					userInfoMap.get("userID"),
					userInfoMap.get("password"),
					userInfoMap.get("email"),
					userInfoMap.get("role"),
					userInfoMap.get("name"));

			if (userInfoMap.get("role").equals("supervisor")) {
				Supervisor supervisor = supervisorMap.get(userInfoMap.get("userID"));

				userLine = String.format("%s,%s,%s,%s,%s",
						supervisor.getUserID(),
						supervisor.getPassword(),
						supervisor.getEmail(),
						"supervisor", // role
						supervisor.getName());
			}

			userLines.add(userLine);
		}

		// Supervisor
		for (Supervisor supervisor : supervisorMap.values()) {
			String supervisorLine = String.format("%s,%d",
					supervisor.getSupervisorID(),
					supervisor.getNumOfProjects());

			supervisorLines.add(supervisorLine);
		}

		// Write to CSV
		boolean success1 = this.writeCsvFile(usersFilePath, userCsvHeaders, userLines);
		boolean success2 = this.writeCsvFile(supervisorsFilePath, supervisorCsvHeaders, supervisorLines);
		return success1 && success2;
	}

	// FYP Coordinator
	@Override
	public Map<String, FYPCoordinator> importFYPCoordinatorData(String usersFilePath, String supervisorsFilePath,
			String fypCoordinatorsFilePath) {
		Map<String, FYPCoordinator> fypCoordinatorsMap = new HashMap<String, FYPCoordinator>();

		List<String[]> usersRows = this.readCsvFile(usersFilePath, userCsvHeaders);
		List<String[]> supervisorsRows = this.readCsvFile(supervisorsFilePath, supervisorCsvHeaders);
		this.readCsvFile(fypCoordinatorsFilePath, fypCoordinatorCsvHeaders);

		for (String[] userRow : usersRows) {
			Map<String, String> userInfoMap = parseUserRow(userRow);

			String role = userInfoMap.get("role");
			if (!role.equals("fypcoordinator"))
				continue;

			String userID = userInfoMap.get("userID");
			String password = userInfoMap.get("password");
			String name = userInfoMap.get("name");
			String email = userInfoMap.get("email");

			// get the associated supervisor data since a FYP Coordinator is also a
			// Supervisor
			int numOfProjects = 0;
			for (String[] supervisorRow : supervisorsRows) {
				if (!supervisorRow[0].equals(userID))
					continue;

				numOfProjects = Integer.parseInt(supervisorRow[1]);
			}

			FYPCoordinator fypCoordinator = new FYPCoordinator(userID, name, email, password, numOfProjects);

			fypCoordinatorsMap.put(userID, fypCoordinator);
		}

		return fypCoordinatorsMap;
	}

	@Override
	public boolean exportFYPCoordinatorData(String usersFilePath, String supervisorsFilePath,
			String fypCoordinatorsFilePath, Map<String, FYPCoordinator> fypCoordinatorMap) {
		List<String> fypCoordinatorLines = new ArrayList<String>();
		List<String> supervisorLines = new ArrayList<String>();
		List<String> userLines = new ArrayList<String>();

		// User
		List<String[]> usersRows = this.readCsvFile(usersFilePath, userCsvHeaders);
		for (String[] userRow : usersRows) {
			Map<String, String> userInfoMap = parseUserRow(userRow);
			String userLine = String.format("%s,%s,%s,%s,%s",
					userInfoMap.get("userID"),
					userInfoMap.get("password"),
					userInfoMap.get("email"),
					userInfoMap.get("role"),
					userInfoMap.get("name"));

			if (userInfoMap.get("role").equals("fypcoordinator")) {
				FYPCoordinator fypCoordinator = fypCoordinatorMap.get(userInfoMap.get("userID"));

				userLine = String.format("%s,%s,%s,%s,%s",
						fypCoordinator.getUserID(),
						fypCoordinator.getPassword(),
						fypCoordinator.getEmail(),
						"fypcoordinator", // role
						fypCoordinator.getName());
			}

			userLines.add(userLine);
		}

		// Supervisor
		List<String[]> supervisorsRows = this.readCsvFile(supervisorsFilePath, supervisorCsvHeaders);
		for (String[] supervisorRow : supervisorsRows) {
			String supervisorLine = String.format("%s,%d", supervisorRow[0], Integer.parseInt(supervisorRow[1]));

			if (fypCoordinatorMap.get(supervisorRow[0]) != null) {
				FYPCoordinator fypCoordinator = fypCoordinatorMap.get(supervisorRow[0]);

				supervisorLine = String.format("%s,%d",
						fypCoordinator.getSupervisorID(),
						fypCoordinator.getNumOfProjects());
			}

			supervisorLines.add(supervisorLine);
		}

		// FYP Coordinator
		for (FYPCoordinator fypcoordinator : fypCoordinatorMap.values()) {
			String fypcoordinatorLine = String.format("%s", fypcoordinator.getSupervisorID());

			fypCoordinatorLines.add(fypcoordinatorLine);
		}

		// Write to CSV
		boolean success1 = this.writeCsvFile(usersFilePath, userCsvHeaders, userLines);
		boolean success2 = this.writeCsvFile(supervisorsFilePath, supervisorCsvHeaders, supervisorLines);
		boolean success3 = this.writeCsvFile(fypCoordinatorsFilePath, fypCoordinatorCsvHeaders, fypCoordinatorLines);
		return success1 && success2 && success3;
	}

	// Projects
	@Override
	public Map<Integer, Project> importProjectData(String projectsFilePath, String usersFilePath,
			String studentsFilePath, String supervisorsFilePath, String fypCoordinatorsFilePath) {
		Map<Integer, Project> projectsMap = new HashMap<Integer, Project>();

		List<String[]> projectsRows = this.readCsvFile(projectsFilePath, projectCsvHeaders);

		for (String[] projectRow : projectsRows) {
			int projectID = Integer.parseInt(projectRow[0]);
			String title = projectRow[1];
			ProjectStatus status = ProjectStatus.valueOf(projectRow[2]);
			String supervisorID = projectRow[3];
			String studentID = projectRow.length > 4 ? projectRow[4] : null;

			Project project = new Project(projectID, title, supervisorID, studentID, status);

			projectsMap.put(projectID, project);
		}

		return projectsMap;
	}

	@Override
	public boolean exportProjectData(String projectsFilePath, Map<Integer, Project> projectMap) {
		List<String> projectLines = new ArrayList<String>();

		// Project
		for (Project project : projectMap.values()) {
			String projectLine = String.format("%d,%s,%s,%s,%s",
					project.getProjectID(),
					project.getTitle(),
					project.getStatus(),
					project.getSupervisor().getSupervisorID(),
					project.getStudent() != null ? project.getStudent().getStudentID() : "");

			projectLines.add(projectLine);
		}

		// Write to CSV
		return this.writeCsvFile(projectsFilePath, projectCsvHeaders, projectLines);
	}

	// Requests
	@Override
	public Map<Integer, Request> importRequestData(String requestsFilePath, String transferStudentRequestsFilePath,
			String changeProjectTitleRequestsFilePath) {
		Map<Integer, Request> requestsMap = new HashMap<Integer, Request>();
		List<String[]> requestsRows = this.readCsvFile(requestsFilePath, requestCsvHeaders);
		List<String[]> transferStudentRequestsRows = this.readCsvFile(transferStudentRequestsFilePath,
				transferStudentRequestCsvHeaders);
		List<String[]> changeProjectTitleRequestsRows = this.readCsvFile(changeProjectTitleRequestsFilePath,
				changeProjectTitleRequestCsvHeaders);

		for (String[] requestRow : requestsRows) {
			int requestID = Integer.parseInt(requestRow[0]);
			int projectID = Integer.parseInt(requestRow[1]);
			String senderID = requestRow[2];
			String receiverID = requestRow[3];
			RequestStatus status = RequestStatus.valueOf(requestRow[4]);
			ArrayList<String> history = new ArrayList<String>(Arrays.asList(requestRow[5].split(";")));
			RequestType type = RequestType.valueOf(requestRow[6]);

			// Handle different Requests subclasses
			if (type == RequestType.TRANSFER_STUDENT) { // TransferStudentRequest
				for (String[] transferStudentRequestRow : transferStudentRequestsRows) {
					if (requestID != Integer.parseInt(transferStudentRequestRow[0]))
						continue;

					String replacementSupervisorID = transferStudentRequestRow[1];
					Request transferStudentRequest = new TransferStudentRequest(senderID, receiverID, projectID,
							requestID, status, history, replacementSupervisorID);

					requestsMap.put(requestID, transferStudentRequest);
					break;
				}
			} else if (type == RequestType.CHANGE_PROJECT_TITLE) { // ChangeProjectTitleRequest
				for (String[] changeProjectTitleRequestRow : changeProjectTitleRequestsRows) {
					if (requestID != Integer.parseInt(changeProjectTitleRequestRow[0]))
						continue;

					String newTitle = changeProjectTitleRequestRow[1];
					Request changeProjectTitleRequest = new ChangeProjectTitleRequest(senderID, receiverID, projectID,
							requestID, status, history, newTitle);

					requestsMap.put(requestID, changeProjectTitleRequest);
					break;
				}
			} else if (type == RequestType.ALLOCATE_PROJECT) { // AllocateProjectRequest
				Request allocateProjectRequest = new AllocateProjectRequest(senderID, receiverID, projectID, requestID,
						status, history);
				requestsMap.put(requestID, allocateProjectRequest);
			} else if (type == RequestType.DEREGISTER_PROJECT) { // DeregisterProjectRequest
				Request deregisterProjectRequest = new DeregisterProjectRequest(senderID, receiverID, projectID,
						requestID, status, history);
				requestsMap.put(requestID, deregisterProjectRequest);
			}
		}

		return requestsMap;
	}

	@Override
	public boolean exportRequestData(String requestsFilePath, String transferStudentRequestsFilePath,
			String changeProjectTitleRequestsFilePath, Map<Integer, Request> requestMap) {
		List<String> requestLines = new ArrayList<String>();
		List<String> transferStudentRequestLines = new ArrayList<String>();
		List<String> changeProjectTitleLines = new ArrayList<String>();

		// Request
		for (Request request : requestMap.values()) {
			// To handle different Requests subclasses
			if (request.getType() == RequestType.TRANSFER_STUDENT) {
				TransferStudentRequest transferStudentRequest = (TransferStudentRequest) request;
				String transferStudentRequestLine = String.format("%d,%s",
						transferStudentRequest.getRequestID(),
						transferStudentRequest.getReplacementSupervisorID());

				transferStudentRequestLines.add(transferStudentRequestLine);
			} else if (request.getType() == RequestType.CHANGE_PROJECT_TITLE) {
				ChangeProjectTitleRequest changeProjectTitleRequest = (ChangeProjectTitleRequest) request;
				String changeProjectTitleRequestLine = String.format("%d,%s",
						changeProjectTitleRequest.getRequestID(),
						changeProjectTitleRequest.getNewTitle());

				changeProjectTitleLines.add(changeProjectTitleRequestLine);
			}

			// Request base class
			String requestLine = String.format("%d,%d,%s,%s,%s,%s,%s",
					request.getRequestID(),
					request.getProject().getProjectID(),
					request.getSender().getUserID(),
					request.getReceiver().getUserID(),
					request.getStatus(),
					String.join(";", request.getHistory()),
					request.getType());

			requestLines.add(requestLine);
		}

		// Write to CSV
		boolean success1 = this.writeCsvFile(requestsFilePath, requestCsvHeaders, requestLines);
		boolean success2 = this.writeCsvFile(transferStudentRequestsFilePath, transferStudentRequestCsvHeaders,
				transferStudentRequestLines);
		boolean success3 = this.writeCsvFile(changeProjectTitleRequestsFilePath, changeProjectTitleRequestCsvHeaders,
				changeProjectTitleLines);
		return success1 && success2 && success3;
	}
}
