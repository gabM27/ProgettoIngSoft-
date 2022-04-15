package it.unibo.ingsoft.gwt.client.settings;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import it.unibo.ingsoft.gwt.shared.Status;

@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	
	String clearDB();
	
	String adminInitialize();
	
	Status doLogin(String email, String password);
	
	String addUserToDB(String emailInput, String passwordInput, String accountType);
	
	
	String addInfoToStudentAccount(String email, String iD, String username, 
				String name, String surname, Date birthday);
	
	String addInfoToProfessorAccount(String email, String username, 
				String name, String surname, Date birthday);
	
	String viewPersonalInfo(String email);
	
	String addDepartmentToDB(String nomeDip);

	String viewDepartments();

	String addCourseToDepartment(String departmentName, String courseName, 
			Date startCourse, Date endCourse, String description, String secondProf);
	
	String viewCourses(String depName);
	
}
