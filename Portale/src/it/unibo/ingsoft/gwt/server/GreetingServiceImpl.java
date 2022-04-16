package it.unibo.ingsoft.gwt.server;


import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import it.unibo.ingsoft.gwt.client.settings.GreetingService;
import it.unibo.ingsoft.gwt.shared.Status;


public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
	private static final long serialVersionUID = 1L;

	@Override
	public String clearDB() {
		if (UniDB.clearUniDB()) {
			return "DB cleaned";
		} else {
			return "An error occurred cleaning DB";
		}
	}

	@Override
	public String adminInitialize() {
		UniDB.adminUserInitialize();
		return "ADMIN inizializzato";
	}

	@Override
	public Status doLogin(String email, String password) {
		return UniDB.loginRequest(email, password);
	}	

	@Override
	public String addUserToDB(String email,String pass, String typeAccount) {
		return UniDB.addUsers(email, pass, typeAccount);
	}
	
	@Override
	public String addInfoToStudentAccount(String email, String iD, String username, String name,
					String surname, Date birthday) {
		return UniDB.addPersonalInfoToStudent(email, iD, username, name, surname, birthday);
	}
	
	@Override
	public String addInfoToProfessorAccount(String email, String username, String name,
			String surname, Date birthday){
		return UniDB.addPersonalInfoToProfessor(email, username, name, surname, birthday);
	}
	
	@Override
	public String viewPersonalInfo(String email) {
		return UniDB.viewPersonalInfo(email);
	}

	@Override
	public String addDepartmentToDB(String nomeDip) {
		return UniDB.addDepartment(nomeDip);
	}
	
	@Override
	public String viewDepartments() {
		return UniDB.viewDepartmentsList();
	}
	
	@Override
	public String addCourseToDepartment(String departmentName, String courseName, 
			Date startCourse, Date endCourse, String description, String secondProf) {
		return UniDB.addCourse(departmentName, courseName, startCourse, endCourse, description, secondProf);	
	}
	
	@Override
	public String viewCourses(String depName) {
		return UniDB.viewCoursesList(depName);
	}
	
	@Override
	public String deleteCourseFromDB(String departmentName, String courseName) {
		return UniDB.deleteCourse(departmentName, courseName);
	}
	
}
