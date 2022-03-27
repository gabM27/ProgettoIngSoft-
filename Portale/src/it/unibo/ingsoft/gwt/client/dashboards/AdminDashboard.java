package it.unibo.ingsoft.gwt.client.dashboards;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.Mainpage;

public class AdminDashboard extends Composite {
	private Mainpage mainpage;
	private VerticalPanel mainPanel = new VerticalPanel();
	
	public AdminDashboard(Mainpage main) {
		this.mainpage = main;
		initWidget(this.mainPanel);
		Label descriptionLabel = new Label("Scegli un'operazione dal menu a tendina.");

		this.mainPanel.add(descriptionLabel);
		
		ListBox actionList = new ListBox();
		
		actionList.addItem("Creazione nuovo account utente - STUDENTE");
		actionList.addItem("Creazione nuovo account utente - DOCENTE");
		actionList.addItem("Creazione nuovo account utente - SEGRETERIA");
		actionList.addItem("Inserisci informazioni personali utente - STUDENTE");
		actionList.addItem("Inserisci informazioni personali utente - DOCENTE");
		actionList.addItem("Pulizia DB");
		
		this.mainPanel.add(actionList);
	}
}
