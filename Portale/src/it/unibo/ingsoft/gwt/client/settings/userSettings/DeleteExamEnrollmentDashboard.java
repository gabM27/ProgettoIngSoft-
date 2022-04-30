package it.unibo.ingsoft.gwt.client.settings.userSettings;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.Mainpage;

public class DeleteExamEnrollmentDashboard extends Composite {
	// Variabili istanza
	private Mainpage mainpage;
	private VerticalPanel mainPanel = new VerticalPanel();
	
	public DeleteExamEnrollmentDashboard(Mainpage main) {
		this.mainpage = main;
		initWidget(mainPanel);
		
		
	}
	
	
}
