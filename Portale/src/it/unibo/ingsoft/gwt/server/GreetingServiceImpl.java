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
	public String addCourseToDepartment(String departmentName, String courseName, String profEmail,
			Date startCourse, Date endCourse, String description, String secondProf) {
		return UniDB.addCourse(departmentName, courseName, profEmail, startCourse, endCourse, description, secondProf);	
	}
	
	@Override
	public String viewCourses(String depName) {
		return UniDB.viewCoursesList(depName);
	}
	
	@Override
	public String printCourseInfo(String nomeCorso) {
		return UniDB.viewCourseInfo(nomeCorso);
	}
	
	@Override
	public String deleteCourseFromDB(String departmentName, String courseName) {
		return UniDB.deleteCourse(departmentName, courseName);
	}
	
	@Override
	public String changeCourseFromDB(String nomeCorso, Date dataInizio, Date dataFine,
			String descrizioneCorso, String codocente) {
		return UniDB.changeCourseInfo(nomeCorso, dataInizio, dataFine, descrizioneCorso, codocente);
	}
	
	@Override
	public String addExam(String nomeCorso, Date dataEsame, String orarioEsame, String durezza, String nomeAula) {
		return UniDB.addExamToCourse(nomeCorso, dataEsame, orarioEsame, durezza, nomeAula);
	}
	
	@Override 
	public String updateExamInfo(String courseName, Date dataEsame, String orarioEsame, String durezzaEsame,
			String aulaSvolgimento) {
		return UniDB.changeExamInfo(courseName, dataEsame, orarioEsame, durezzaEsame, aulaSvolgimento);
	}
	
	@Override
	public String deleteExamFromDB(String courseName) {
		return UniDB.deleteExam(courseName);
	}

	@Override
	public String signUpStudentToACourse(String courseName, String studentEmail) {
		return UniDB.signUpStudentToCourse(courseName, studentEmail);
	}
	
	@Override
	public String deleteStudentCourseRegistration(String courseName, String studentEmail) {
		return UniDB.deleteStudentCourseRegistrationFromDB(courseName, studentEmail);
	}
	
	@Override
	public String viewStudentRegisteredCourses(String studentEmail) {
		return UniDB.viewStudentRegisteredCourseList(studentEmail);
	}
	
	@Override
	public String viewStudentRegisteredCoursesExamSettedUp(String studentEmail) {
		return UniDB.viewStudentRegisteredCoursesExamSettedUpList(studentEmail);
	}
	
	@Override
	public String signUpStudentToAnExam(String courseName, String studentEmail) {
		return UniDB.signUpStudentToACourseExam(courseName, studentEmail);
	}
	
	@Override
	public String viewStudentRegisteredExams(String studentEmail) {
		return UniDB.viewStudentRegisteredExamList(studentEmail);
	}
	
	@Override
	public String deleteStudentExamRegistration(String examName, String studentEmail) {
		return UniDB.deleteStudentExamRegistrationFromDB(examName, studentEmail);
	}
	
	@Override
	public String viewProfCourses(String profEmail) {
		return UniDB.viewProfCoursesList(profEmail);
	}
	
	@Override
	public String viewRegisteredStudentFromCourse(String courseName) {
		return UniDB.viewRegisteredStudentsFromCourse(courseName);
	}
	
	@Override
	public String addMarkToStudentExam(String studentEmail, String courseName, Integer mark) {
		return UniDB.addMarkToStudentExamInDB(studentEmail, courseName, mark);
	}
	
	 @Override
	 public String viewAllStudentsPersonalInfo() {
		 return UniDB.viewAllStudentsPersonalInfo();
	 }
	
}
