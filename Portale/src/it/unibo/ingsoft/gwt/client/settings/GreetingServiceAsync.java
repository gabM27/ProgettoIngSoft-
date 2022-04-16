package it.unibo.ingsoft.gwt.client.settings;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

import it.unibo.ingsoft.gwt.shared.Status;

public interface GreetingServiceAsync {
	
	void clearDB(AsyncCallback<String> callback);

	void adminInitialize(AsyncCallback<String> callback);
	
	void doLogin(String email, String password, AsyncCallback<Status> callback);

	void addUserToDB(String emailInput, String passwordInput, String accountType, AsyncCallback<String> callback);

	void addInfoToStudentAccount(String email, String iD, String username, String name,
				String surname, Date birthday, AsyncCallback<String> asyncCallback);

	void addInfoToProfessorAccount(String email, String username, String name, 
				String surname, Date birthday, AsyncCallback<String> asyncCallback);

	void viewPersonalInfo(String email, AsyncCallback<String> asyncCallback);
	
	void addDepartmentToDB(String nomeDip, AsyncCallback<String> callback);
	
	void viewDepartments(AsyncCallback<String> callback);
	
	void addCourseToDepartment(String departmentName, String courseName, Date startCourse, Date endCourse, 
			String description, String secondProf, AsyncCallback<String> callback);

	void viewCourses(String depName, AsyncCallback<String> callback);

	void deleteCourseFromDB(String departmentName, String courseName, AsyncCallback<String> callabck);
}
