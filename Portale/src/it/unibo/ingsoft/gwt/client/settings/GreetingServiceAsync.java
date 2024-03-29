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
	
	void addCourseToDepartment(String departmentName, String courseName, String profEmail, Date startCourse, Date endCourse, 
			String description, String secondProf, AsyncCallback<String> callback);

	void viewCourses(String depName, AsyncCallback<String> callback);

	void deleteCourseFromDB(String departmentName, String courseName, AsyncCallback<String> callabck);

	void changeCourseFromDB(String nomeCorso, String profEmail, Date dataInizio, Date dataFine,
			String descrizioneCorso, String codocente, AsyncCallback<String> callback);
	
	void addExam(String nomeCorso, Date dataEsame, String orarioEsame, String durezza, String nomeAula,
			AsyncCallback<String> callback);

	void printCourseInfo(String courseName, AsyncCallback<String> callback);

	void updateExamInfo(String courseName, Date dataEsame, String orarioEsame, String durezzaEsame,
			String aulaSvolgimento, AsyncCallback<String> callback);

	void deleteExamFromDB(String courseName, AsyncCallback<String> callback);

	void signUpStudentToACourse(String courseName, String studentEmail, AsyncCallback<String> callback);

	void deleteStudentCourseRegistration(String courseName, String studentEmail, AsyncCallback<String> callback);
	
	void viewStudentRegisteredCourses(String studentEmail, AsyncCallback<String> callback);
	
	void viewStudentRegisteredCoursesExamSettedUp(String studentEmail, AsyncCallback<String> callback);

	void signUpStudentToAnExam(String courseName, String studentEmail, AsyncCallback<String> callback);
	
	void viewStudentRegisteredExams(String studentEmail, AsyncCallback<String> callback);

	void deleteStudentExamRegistration(String examName, String email, AsyncCallback<String> callback);

	void viewProfCourses(String profEmail, AsyncCallback<String> callback);

	void viewRegisteredStudentFromCourse(String courseName, AsyncCallback<String> callback);

	void addMarkToStudentExam(String studentEmail, String courseName, Integer mark, AsyncCallback<String> callback);

	void viewAllStudentsPersonalInfo(AsyncCallback<String> callback);

	void setExamsMarksVisibilityVisible(String examName, AsyncCallback<String> callback);

	void viewExamsSecList(String secEmail, AsyncCallback<String> callback);

	void viewExamsMarks(String studentEmail, AsyncCallback<String> callback);
}
