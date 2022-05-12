package it.unibo.ingsoft.gwt.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.dashboards.AdminDashboard;
import it.unibo.ingsoft.gwt.client.dashboards.ProfessorDashboard;
import it.unibo.ingsoft.gwt.client.dashboards.SecretaryDashboard;
import it.unibo.ingsoft.gwt.client.dashboards.StudentDashboard;
import it.unibo.ingsoft.gwt.client.settings.adminSettings.AddAccountFormPage;
import it.unibo.ingsoft.gwt.client.settings.adminSettings.AddDepartmentFormDashboard;
import it.unibo.ingsoft.gwt.client.settings.adminSettings.AddPersonalInfoFormProfessorDashboard;
import it.unibo.ingsoft.gwt.client.settings.adminSettings.AddPersonalInfoFormStudentDashboard;
import it.unibo.ingsoft.gwt.client.settings.professorSettings.CreateCourseFormProfessorDashboard;
import it.unibo.ingsoft.gwt.client.settings.professorSettings.CreateExamFormProfessorDashboard;
import it.unibo.ingsoft.gwt.client.settings.professorSettings.ChangeCourseInfoProfessorDashboard;
import it.unibo.ingsoft.gwt.client.settings.professorSettings.ChangeExamFormProfessorDashboard;
import it.unibo.ingsoft.gwt.client.settings.professorSettings.RemoveCourseDashboard;
import it.unibo.ingsoft.gwt.client.settings.professorSettings.RemoveExamDashboard;
import it.unibo.ingsoft.gwt.client.settings.professorSettings.SendMarksDashboard;
import it.unibo.ingsoft.gwt.client.settings.secretarySettings.MakeStudentsGradesVisible;
import it.unibo.ingsoft.gwt.client.settings.studentSettings.CourseEnrollmentDashboard;
import it.unibo.ingsoft.gwt.client.settings.studentSettings.DeleteCourseEnrollmentDashboard;
import it.unibo.ingsoft.gwt.client.settings.studentSettings.DeleteExamEnrollmentDashboard;
import it.unibo.ingsoft.gwt.client.settings.studentSettings.ExamEnrollmentDashboard;
import it.unibo.ingsoft.gwt.client.settings.userSettings.ViewDepartmentsList;
import it.unibo.ingsoft.gwt.client.settings.userSettings.ViewPersonalInfo;
import it.unibo.ingsoft.gwt.client.settings.userSettings.ViewAllStudentsPersonalInfo;
import it.unibo.ingsoft.gwt.client.settings.userSettings.SearchCourse;
import it.unibo.ingsoft.gwt.client.settings.userSettings.SearchCourse4Info;
import it.unibo.ingsoft.gwt.client.settings.userSettings.SearchPersonalInfo;
import it.unibo.ingsoft.gwt.client.settings.userSettings.ViewCoursesList;
import it.unibo.ingsoft.gwt.shared.Status;

public class Mainpage extends Composite{
	// Variabili istanza
	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel optionalPanel = new VerticalPanel(); // Attualmente serve solo per stampare le informazioni di un corso
	
	// Costruttore
	public Mainpage() {
		initWidget(mainPanel);
		
		openHomepage();
	}
	
	public void openHomepage() {
		this.mainPanel.clear();
		this.mainPanel.add(new Homepage(this));
		
	}
	
	public void openLoginpage() {
		this.mainPanel.clear();
		this.mainPanel.add(new Loginpage(this));
	}

	public void openAccountDashboard(Status status) {
		this.mainPanel.clear();
		
		switch(status) {
		case NOT_REGISTERED:
			this.mainPanel.clear();
			this.openHomepage();
			break;
		case WRONG_PASS:
			this.mainPanel.clear();
			this.openLoginpage();
			break;
		case ADMIN:
			this.mainPanel.clear();
			this.mainPanel.add(new AdminDashboard(this));
			break;
		case STUDENT:
			this.mainPanel.clear();
			this.mainPanel.add(new StudentDashboard(this));
			break;
		case PROFESSOR:
			this.mainPanel.clear();
			this.mainPanel.add(new ProfessorDashboard(this));
			break;
		case SECRETARY:
			this.mainPanel.clear();
			this.mainPanel.add(new SecretaryDashboard(this));
			break;
		default: 
			Window.alert("ERROR: default situation --> something gone wrong.");
			break;
		}
	}

	public void openAddAccountFormDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new AddAccountFormPage(this));
	}
	
	public void openAddPersonalInfoFormStudentDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new AddPersonalInfoFormStudentDashboard(this));
	}

	public void openAddPersonalInfoFormProfessorDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new AddPersonalInfoFormProfessorDashboard(this));
	}

	public void openSearchPersonalInfo() {
		this.mainPanel.clear();
		this.mainPanel.add(new SearchPersonalInfo(this));
	}

	public void openViewPersonalInfo(String info) {
		this.mainPanel.clear();
		this.mainPanel.add(new ViewPersonalInfo(this,info));
	}
	
	public void openCreateCourseFormProfessorDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new CreateCourseFormProfessorDashboard(this));
	}

	public void openChangeCourseInfoProfessorDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new ChangeCourseInfoProfessorDashboard(this));
	}
	
	public void openChangeExamFormProfessorDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new ChangeExamFormProfessorDashboard(this));
	}
	
	public void openCreateExamFormProfessorDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new CreateExamFormProfessorDashboard(this));
	}
	
	public void openAddDepartmentFormDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new AddDepartmentFormDashboard(this));
	}
	
	public void openViewDepartmentsList(String[] list) {
		this.mainPanel.clear();
		this.mainPanel.add(new ViewDepartmentsList(this, list));
	}
	
	public void openViewCoursesList(String[] list) {
		this.mainPanel.clear();
		this.mainPanel.add(new ViewCoursesList(this, list));
	}
	
	public void openSearchCourse() {
		this.mainPanel.clear();
		this.mainPanel.add(new SearchCourse(this));
	}

	public void openRemoveCourseDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new RemoveCourseDashboard(this));
	}

	public void openRemoveExamDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new RemoveExamDashboard(this));
	}

	public void openSearchCourse4Info() {
		this.mainPanel.clear();
		this.mainPanel.add(new SearchCourse4Info(this));
	}
	
	public void addCourseInfoTA(String courseInfo) {
		this.optionalPanel.clear(); // Caso in cui ci siano già inserite altre informazioni
		
		TextArea courseInfoTA = new TextArea();
		courseInfoTA.setText(courseInfo);
		courseInfoTA.setPixelSize(500, 200);
		courseInfoTA.setReadOnly(true);
		
		this.optionalPanel.add(new Label("INFORMAZIONI DELL'ESAME: "));
		this.optionalPanel.add(courseInfoTA);
		
		this.optionalPanel.setSpacing(5);
		
		this.mainPanel.add(optionalPanel);
	}

	public void openCourseEnrollment() {
		this.mainPanel.clear();
		this.mainPanel.add(new CourseEnrollmentDashboard(this));
	}

	public void openExamEnrollment() {
		this.mainPanel.clear();
		this.mainPanel.add(new ExamEnrollmentDashboard(this));
	}

	public void openDeleteCourseEnrollment() {
		this.mainPanel.clear();
		this.mainPanel.add(new DeleteCourseEnrollmentDashboard(this));
	}

	public void openDeleteExamEnrollment() {
		this.mainPanel.clear();
		this.mainPanel.add(new DeleteExamEnrollmentDashboard(this));
	}
	
	public void openSendMarksDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new SendMarksDashboard(this));
	}

	public void openViewStudentsPersonalInfo(String info) {
		this.mainPanel.clear();
		this.mainPanel.add(new ViewAllStudentsPersonalInfo(this, info));
	}

	public void openMakeStudentsGradesVisibleDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new MakeStudentsGradesVisible(this));
	}
	
}