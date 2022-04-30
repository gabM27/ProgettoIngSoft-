package it.unibo.ingsoft.gwt.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.settings.Singleton;

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
		descriptionLabel.setText("Benvenut*! Stai entrando nel portale universitario dell'ateneo " + Portale.uni.getAthenaeumName() + ".");
		// Aggiungo label al pannello verticale
		panel.add(descriptionLabel);
		
		// Creo il bottone per il login
		Button btnLogin = new Button("LOGIN");
		btnLogin.setWidth("150px");
		
		// Gestion evento click su bottone Log In
		btnLogin.addClickHandler(new btnLoginHandler());

		/*
		 * HorizontalPanel che contiene le informazioni dell'università nella homepage
		 */
		HorizontalPanel informationPanel = new HorizontalPanel();
		Label informationLabel = new Label();
		
		informationLabel.setText("Numero di telefono: " + Portale.uni.getTelephoneNumber() + ""
				+ " - Indirizzo: " +  Portale.uni.getAddress() + ""
				+ " - Citta': " + Portale.uni.getCity() + ".");
		
		// Aggiungo label al pannello verticale
		informationPanel.add(informationLabel);
		
		/*
		 * ADDING DEPARTMENTS LIST TO HOMEPAGE
		 */
		VerticalPanel depPanel = new VerticalPanel();
		Label description = new Label("LISTA DIPARTIMENTI:");
		TextArea departmentsListTA = new TextArea();
		departmentsListTA.setText("Premi refresh per visualizzare la lista.");
		departmentsListTA.setPixelSize(200,275);
		departmentsListTA.setReadOnly(true);
		Button btnRefresh = new Button("Refresh");
		btnRefresh.addClickHandler(new RefreshHandler(departmentsListTA));

		/*
		 * ADDING ELEMENTS TO DEP PANEL
		 */
		depPanel.add(description);
		depPanel.add(departmentsListTA);
		depPanel.add(btnRefresh);
		depPanel.setSpacing(5);
		// ADDING DEP PANEL TO Panel
		panel.add(depPanel);
		
		/*
		 * ADDING LOGIN BUTTON TO PANEL
		 */
		panel.add(btnLogin);
		
		/*
		 * ADDING INFORMATION PANEL TO PANEL
		 */
		panel.add(informationPanel);
		
		
				
		panel.setSpacing(5);
		
		/*
		 * ADDING PANEL TO MAINPANEL
		 */
		this.mainPanel.add(panel);

	}
	
	private class RefreshHandler implements ClickHandler {
		private TextArea departmentsListTA;
		
		public RefreshHandler(TextArea area) {
			this.departmentsListTA = area;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			Singleton.getGreetingService().viewDepartments(new AsyncCallback<String>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("ERROR ADDING DEPARTMENTS LIST TO HOMEPAGE: " + caught.getMessage());
				}

				@Override
				public void onSuccess(String result) {
					// Divido la stringa contenente i dipartimenti (presi dal database)
					String[] returnList = result.split("_");
						
					String stringDepList = "";
					// Aggiunta dei dipartimenti alla textarea
					for (int i = 0; i < returnList.length; i++) {
						stringDepList += "- " + returnList[i].toUpperCase() + ".\n";
					}
						
					departmentsListTA.setText(stringDepList);
				}
				
			});
		}
		
	}
	
	private class btnLoginHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			mainpage.openLoginpage();
		}
		
	}
	
}
