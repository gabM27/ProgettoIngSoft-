package it.unibo.ingsoft.gwt.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.dashboards.AdminDashboard;
import it.unibo.ingsoft.gwt.client.dashboards.ProfessorDashboard;
import it.unibo.ingsoft.gwt.client.dashboards.SecretaryDashboard;
import it.unibo.ingsoft.gwt.client.dashboards.StudentDashboard;
import it.unibo.ingsoft.gwt.client.settings.Singleton;
import it.unibo.ingsoft.gwt.client.settings.adminSettings.AddAccountFormPage;
import it.unibo.ingsoft.gwt.client.settings.adminSettings.AddDepartmentFormDashboard;
import it.unibo.ingsoft.gwt.client.settings.adminSettings.AddPersonalInfoFormProfessorDashboard;
import it.unibo.ingsoft.gwt.client.settings.adminSettings.AddPersonalInfoFormStudentDashboard;
import it.unibo.ingsoft.gwt.client.settings.professorSettings.CreateChangeCourseFormProfessorDashboard;
import it.unibo.ingsoft.gwt.client.settings.professorSettings.CreateChangeTestFormProfessorDashboard;
import it.unibo.ingsoft.gwt.client.settings.userSettings.ViewDepartmentsList;
import it.unibo.ingsoft.gwt.client.settings.userSettings.ViewPersonalInfo;
import it.unibo.ingsoft.gwt.client.settings.userSettings.SearchCourse;
import it.unibo.ingsoft.gwt.client.settings.userSettings.SearchPersonalInfo;
import it.unibo.ingsoft.gwt.client.settings.userSettings.ViewCoursesList;
import it.unibo.ingsoft.gwt.shared.Status;

public class Mainpage extends Composite{
	// Variabili istanza
	private VerticalPanel mainPanel = new VerticalPanel();
	
	// Costruttore
	public Mainpage() {
		doAdminInitialize();
		
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
	
	private static void doAdminInitialize() {
		Singleton.getGreetingService().adminInitialize(new AsyncCallback<String> () {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR in initializing admin account: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				System.out.println("Admin initializing success: " + result);
			}
			
		});
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
	
	public void openCreateChangeCourseFormProfessorDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new CreateChangeCourseFormProfessorDashboard(this));
	}

	public void openCreateChangeTestFormProfessorDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new CreateChangeTestFormProfessorDashboard(this));
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
}