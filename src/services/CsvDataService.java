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
import interfaces.IFileDataService;
import models.FYPCoordinator;
import models.Project;
import models.Request;
import models.Student;
import models.Supervisor;

public class CsvDataService implements IFileDataService {
	private static List<String> userCsvHeaders = new ArrayList<String>();
	private static List<String> studentCsvHeaders = new ArrayList<String>();
	private static List<String> supervisorCsvHeaders = new ArrayList<String>();
	private static List<String> fypCoordinatorCsvHeaders = new ArrayList<String>();
	private static List<String> projectCsvHeaders = new ArrayList<String>();
	private static List<String> requestCsvHeaders = new ArrayList<String>();
	
	// ---------- Helper Function ---------- //
	private List<String[]> readCsvFile(String filePath, List<String> headers) {
		List<String[]> dataList = new ArrayList<String[]>();
		headers.clear();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			// Headers
			String[] headerRow = br.readLine().split(",");
			for (String header: headerRow) {
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
	
	private boolean writeCsvFile(String filePath, List<String> headers, List<String> lines) {
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
	
	private Map<String, String> parseUserRow(String[] userRow) {
		String userID = userRow[0];
		String password = userRow[1];
		String email = userRow[2];
		String role = userRow[3];
		String name = userRow[4];
		
		// to handle names with comma
		for (int i = 5; i < userRow.length; i++) {
			if (i != 4) name += ",";
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
	public Map<String, Student> importStudentData(String usersFilePath, String studentsFilePath) {
		Map<String, Student> studentsMap = new HashMap<String, Student>();
		
		List<String[]> usersRows = this.readCsvFile(usersFilePath, userCsvHeaders);
		List<String[]> studentsRows = this.readCsvFile(studentsFilePath, studentCsvHeaders);
		
		for (String[] userRow : usersRows) {
			Map<String, String> userInfoMap = parseUserRow(userRow);
			
			String role = userInfoMap.get("role");
			if (!role.equals("student")) continue;

			String userID = userInfoMap.get("userID");
			String password = userInfoMap.get("password");
			String name = userInfoMap.get("name");
			String email = userInfoMap.get("email");
			
			// get the associated student data
			boolean isDeregistered = false;
			for (String[] studentRow: studentsRows) {
				if (!studentRow[0].equals(userID)) continue;
				
				isDeregistered = Boolean.parseBoolean(studentRow[1]);
			}
			
			
			Student student = new Student(userID, name, email, password, isDeregistered);
			
			studentsMap.put(userID, student);
		}
		
		return studentsMap;
	}

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
	public Map<String, Supervisor> importSupervisorData(String usersFilePath, String supervisorsFilePath) {
		Map<String, Supervisor> supervisorsMap = new HashMap<String, Supervisor>();
		
		List<String[]> usersRows = this.readCsvFile(usersFilePath, userCsvHeaders);
		List<String[]> supervisorsRows = this.readCsvFile(supervisorsFilePath, supervisorCsvHeaders);
		
		for (String[] userRow : usersRows) {
			Map<String, String> userInfoMap = parseUserRow(userRow);
			
			String role = userInfoMap.get("role");
			if (!role.equals("supervisor")) continue;

			String userID = userInfoMap.get("userID");
			String password = userInfoMap.get("password");
			String name = userInfoMap.get("name");
			String email = userInfoMap.get("email");
			
			// get the associated supervisor data
			int numOfProjects = 0;
			for (String[] supervisorRow: supervisorsRows) {
				if (!supervisorRow[0].equals(userID)) continue;
				
				numOfProjects = Integer.parseInt(supervisorRow[1]);
			}
			
			Supervisor supervisor = new Supervisor(userID, name, email, password, numOfProjects);
			
			supervisorsMap.put(userID, supervisor);
		}
		
		return supervisorsMap;
	}

	public boolean exportSupervisorData(String usersFilePath, String supervisorsFilePath, Map<String, Supervisor> supervisorMap) {
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
	public Map<String, FYPCoordinator> importFYPCoordinatorData(String usersFilePath, String supervisorsFilePath, String fypCoordinatorsFilePath) {
		Map<String, FYPCoordinator> fypCoordinatorsMap = new HashMap<String, FYPCoordinator>();
		
		List<String[]> usersRows = this.readCsvFile(usersFilePath, userCsvHeaders);
		List<String[]> supervisorsRows = this.readCsvFile(supervisorsFilePath, supervisorCsvHeaders);
		List<String[]> fypCoordinatorsRows = this.readCsvFile(fypCoordinatorsFilePath, fypCoordinatorCsvHeaders);
		
		for (String[] userRow : usersRows) {
			Map<String, String> userInfoMap = parseUserRow(userRow);
			
			String role = userInfoMap.get("role");
			if (!role.equals("fypcoordinator")) continue;

			String userID = userInfoMap.get("userID");
			String password = userInfoMap.get("password");
			String name = userInfoMap.get("name");
			String email = userInfoMap.get("email");
			
			// get the associated fypcoordinator data
			// for (String[] fypCoordinatorRow: fypCoordinatorsRows) {
			//	if (!fypCoordinatorRow[0].equals(userID)) continue;
			// }
			
			// get the associated supervisor data since a FYP Coordinator is also a Supervisor
			int numOfProjects = 0;
			for (String[] supervisorRow: supervisorsRows) {
				if (!supervisorRow[0].equals(userID)) continue;
				
				numOfProjects = Integer.parseInt(supervisorRow[1]);
			}
			
			FYPCoordinator fypCoordinator = new FYPCoordinator(userID, name, email, password, numOfProjects);
			
			fypCoordinatorsMap.put(userID, fypCoordinator);
		}
		
		return fypCoordinatorsMap;
	}

	public boolean exportFYPCoordinatorData(String usersFilePath, String supervisorsFilePath, String fypCoordinatorsFilePath, Map<String, FYPCoordinator> fypCoordinatorMap) {
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
	public Map<Integer, Project> importProjectData(String projectsFilePath, String usersFilePath, String studentsFilePath, String supervisorsFilePath, String fypCoordinatorsFilePath) {
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
	
	public boolean exportProjectData(String projectsFilePath, Map<Integer, Project> projectMap) {
		List<String> projectLines = new ArrayList<String>();
		
		// Project
		for (Project project: projectMap.values()) {
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
	public Map<Integer, Request> importRequestData(String requestsFilePath) {
		Map<Integer, Request> requestsMap = new HashMap<Integer, Request>();
		List<String[]> requestsRows = this.readCsvFile(requestsFilePath, requestCsvHeaders);
		
		for (String[] requestRow : requestsRows) {
			int requestID = Integer.parseInt(requestRow[0]);
			int projectID = Integer.parseInt(requestRow[1]);
			String senderID = requestRow[2];
			String receiverID = requestRow[3];
			RequestStatus status = RequestStatus.valueOf(requestRow[4]);
			ArrayList<String> history = new ArrayList<String>(Arrays.asList(requestRow[5].split(";")));
			
			// Request request = new Request(senderID, receiverID, projectID, requestID, status, history);
			
			// requestsMap.put(requestID, request);
		}
		
		return requestsMap;
	}
	
	public boolean exportRequestData(String requestsFilePath, Map<Integer, Request> requestMap) {
		List<String> requestLines = new ArrayList<String>();
		
		// Request
		for (Request request: requestMap.values()) {
			String requestLine = String.format("%d,%d,%s,%s,%s,%s",
					request.getRequestID(),
					request.getProject().getProjectID(),
					request.getSender().getUserID(),
					request.getReceiver().getUserID(),
					request.getStatus(),
					String.join(";", request.getHistory()));
			
			requestLines.add(requestLine);
		}
		
		// Write to CSV
		return this.writeCsvFile(requestsFilePath, requestCsvHeaders, requestLines);
	}
}
