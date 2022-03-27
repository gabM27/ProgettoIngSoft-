package it.unibo.ingsoft.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Homepage extends Composite {
	// Variabili istanza
	private VerticalPanel mainPanel = new VerticalPanel();
	private Mainpage mainpage;
	
	// Costruttore
	public Homepage(Mainpage main) {
		this.mainpage = main;
		
		initWidget(this.mainPanel);
		VerticalPanel panel = new VerticalPanel();

		Label descriptionLabel= new Label();
		descriptionLabel.setText("Benvenut*! Stai entrando nel portale universitario dell'ateneo " + Portale.uni.getAthenaeumName() 
									+ ", presso la citta' di " + Portale.uni.getCity() + ".");
		// Aggiungo label al pannello verticale
		panel.add(descriptionLabel);
		
		// Creo il bottone per il login
		Button btnLogin = new Button("LOGIN");
		btnLogin.setWidth("150px");
		// Gestion evento click su bottone Log In
		btnLogin.addClickHandler(new btnLoginHandler());

		// Aggiungo il bottone al pannello verticale
		panel.add(btnLogin);
		
		// Aggiungo il pannello verticale al mainPanel
		this.mainPanel.add(panel);
	}
	
	private class btnLoginHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			mainpage.openLoginpage();
		}
		
	}
	
}
