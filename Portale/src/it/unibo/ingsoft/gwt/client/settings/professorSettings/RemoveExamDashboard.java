package it.unibo.ingsoft.gwt.client.settings.professorSettings;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.Mainpage;

public class RemoveExamDashboard extends Composite{
	// Variabili istanza
	private Mainpage mainpage;
	private VerticalPanel mainPanel = new VerticalPanel();
	
	public RemoveExamDashboard(Mainpage main) {
		initWidget(this.mainPanel);
		this.mainpage = main;
		
		
	}
	
	
}
