package it.unibo.ingsoft.gwt.client.dashboards;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.shared.usersfacade.GeneralUserFacade;

public class SecretaryDashboard extends Composite {
	// Variabili istanza
	private Mainpage mainpage; // Riferimento alla mainpage
	private VerticalPanel mainPanel = new VerticalPanel(); // mainPanel che contiene la dashboard
	private int index;
	
	public SecretaryDashboard(Mainpage main) {
		this.mainpage = main;
		initWidget(this.mainPanel);
		/*
		 * OPERAZIONI SEGRETERIA
		 */
		Label statusLabel = new Label("" + ActualSession.getActualSession().getActualStatus());
		Label descriptionLabel = new Label("Scegli un'operazione dal menu a tendina.");
		
		ListBox actionList = new ListBox();
		
		actionList.addItem("Visualizza informazioni personali degli studenti");
		actionList.addItem("Pubblica i voti degli esami"); // Rende visibili i voti agli studenti
		actionList.setVisibleItemCount(2);
		actionList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				index = actionList.getSelectedIndex();
			}
		});
		actionList.addDoubleClickHandler(new SecretaryActionListHandler());
		
		/*
		 * BUTTONS
		 */
		Button btnLogout = new Button("LOGOUT"); // back to homepage, termina la sessione
		// Gestione dell'evento onClick del bottone
		btnLogout.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mainPanel.clear();
				mainpage.openHomepage();
			}
			
		});
		
		/*
		 * ADDING ELEMENTS TO MAIN PANEL
		 */
		this.mainPanel.add(statusLabel);
		this.mainPanel.add(descriptionLabel);
		this.mainPanel.add(actionList);
		this.mainPanel.add(btnLogout);
		
		this.mainPanel.setSpacing(5);
	}
	
	private class SecretaryActionListHandler implements DoubleClickHandler{
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			switch(index) {
			case 0:	// Visualizza info personali degli studenti
				GeneralUserFacade.getGeneralUserFacade().printAllStudentsPersonalInfo(mainpage);
				break;
			case 1:	// Inserisci i voti dell'esame 
				mainpage.openMakeStudentsGradesVisibleDashboard();
				break;
			default: // Default operation: error
				Window.alert("ERROR: DEFAULT OPERATION");
				break;
			}
		}



		
	}
}
