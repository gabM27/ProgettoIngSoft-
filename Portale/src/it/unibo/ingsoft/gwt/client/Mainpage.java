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
import it.unibo.ingsoft.gwt.server.UniDB;

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

	public void openAdminDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new AdminDashboard(this));
	}

	public void openStudentDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new StudentDashboard(this));
	}

	public void openProfessorDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new ProfessorDashboard(this));
	}

	public void openSecretaryDashboard() {
		this.mainPanel.clear();
		this.mainPanel.add(new SecretaryDashboard(this));
	}
	
	private static void doAdminInitialize() {
		Singleton.getGreetingService().adminInitialize(new AsyncCallback<String> () {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR in initializing admin account: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				Window.alert("Admin initializing success: " + result);
			}
			
		});
	}
}