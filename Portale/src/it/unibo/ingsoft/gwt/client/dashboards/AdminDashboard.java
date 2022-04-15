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
import it.unibo.ingsoft.gwt.shared.usersfacade.AdminFacade;
import it.unibo.ingsoft.gwt.shared.usersfacade.GeneralUserFacade;

public class AdminDashboard extends Composite {
	// Variabili istanza
	private Mainpage mainpage; // Riferimento alla mainpage
	private VerticalPanel mainPanel = new VerticalPanel(); // mainPanel che contiene la dashboard
	private int index;
	
	public AdminDashboard(Mainpage main) {
		this.mainpage = main;
		initWidget(this.mainPanel);
		/*
		 * OPERAZIONI ADMIN
		 */
		Label statusLabel = new Label("" + ActualSession.getActualSession().getActualStatus());
		Label descriptionLabel = new Label("Scegli un'operazione dal menu a tendina.");
		
		ListBox actionList = new ListBox();
		
		actionList.addItem("Creazione nuovo account utente");
		actionList.addItem("Inserimento/Modifica informazioni personali STUDENTE");
		actionList.addItem("Inserimento/Modifica informazioni personali DOCENTE");
		actionList.addItem("Visualizzazione informazioni personali");
		actionList.addItem("Inserimento nuovo dipartimento");
		actionList.addItem("Visualizzazione lista dipartimenti");
		actionList.addItem("Visualizzazione corsi disponibili");
		actionList.addItem("Pulizia database");
		actionList.setVisibleItemCount(8);
		actionList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				index = actionList.getSelectedIndex();
			}
		});
		actionList.addDoubleClickHandler(new AdminActionListHandler());
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
	
	private class AdminActionListHandler implements DoubleClickHandler{
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			switch(index) {
			case 0:	// Creazione nuovo account utente
				mainpage.openAddAccountFormDashboard();
				break;
			case 1:	// Inserisci informazioni personali utente - STUDENTE
				mainpage.openAddPersonalInfoFormStudentDashboard();
				break;
			case 2: // Inserisci informazioni personali utente - DOCENTE
				mainpage.openAddPersonalInfoFormProfessorDashboard();
				break;
			case 3:
				mainpage.openSearchPersonalInfo();
				break;
			case 4: 
				mainpage.openAddDepartmentFormDashboard();
				break;
			case 5:
				GeneralUserFacade.getGeneralUserFacade().printDepartmentsList(mainpage);
				break;
			case 6:
				mainpage.openSearchCourse();
				break;
			case 7: // Pulizia DB
				AdminFacade.getAdminFacade().cleaningDB();
				break;
			default: // Default operation: error
				Window.alert("ERROR: DEFAULT OPERATION NOT ALLOWED");
				break;
			}
		}



		
	}
}
