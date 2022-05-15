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

	String addCourseToDepartment(String departmentName, String courseName, String profEmail,
			Date startCourse, Date endCourse, String description, String secondProf);
	
	String viewCourses(String depName);
	
	String deleteCourseFromDB(String departmentName, String courseName);

	String addExam(String nomeCorso, Date dataEsame, String orarioEsame, String durezza, String nomeAula);

	String changeCourseFromDB(String nomeCorso, String profEmail, Date dataInizio, Date dataFine,
			String descrizioneCorso, String codocente);

	String printCourseInfo(String nomeCorso);
	
	String updateExamInfo(String courseName, Date dataEsame, String orarioEsame, String durezzaEsame,
			String aulaSvolgimento);
	
	String  deleteExamFromDB(String courseName);

	String signUpStudentToACourse(String courseName, String studentEmail);

	String deleteStudentCourseRegistration(String courseName, String studentEmail);
	
	String viewStudentRegisteredCourses(String studentEmail);
	
	String viewStudentRegisteredCoursesExamSettedUp(String studentEmail);
	
	String signUpStudentToAnExam(String courseName, String studentEmail);

	String viewStudentRegisteredExams(String studentEmail);

	String deleteStudentExamRegistration(String examName, String studentEmail);

	String viewProfCourses(String profEmail);
	
	String viewRegisteredStudentFromCourse(String courseName);
	
	String addMarkToStudentExam(String studentEmail, String  courseName, Integer mark);
	
	String viewAllStudentsPersonalInfo();
	
	String setExamsMarksVisibilityVisible(String examName);
	
	String viewExamsSecList(String secEmail);

	String viewExamsMarks(String studentEmail);
}
