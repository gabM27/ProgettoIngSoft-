package it.unibo.ingsoft.gwt.client.dashboards;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.client.settings.Singleton;

public class ProfessorDashboard extends Composite {
	// Variabili istanza
	private Mainpage mainpage; // Riferimento alla mainpage
	private VerticalPanel mainPanel = new VerticalPanel(); // mainPanel che contiene la dashboard
	private int index;
	
	public ProfessorDashboard(Mainpage main) {
		this.mainpage = main;
		initWidget(this.mainPanel);
		
		/*
		 * OPERAZIONI PROFESSOR
		 */
		Label statusLabel = new Label("" + ActualSession.getActualSession().getActualStatus());
		Label descriptionLabel = new Label("Scegli un'operazione dal menu a tendina.");
		
		ListBox actionList = new ListBox();
		
		actionList.addItem("Creazione / Modifica di un corso");
		actionList.addItem("Cancellazione di un corso");
		actionList.addItem("Creazione / Modifica di un esame");
		actionList.addItem("Cancellazione di un esame");
		actionList.addItem("Visualizzare le proprie informazioni personali");
		actionList.addItem("Visualizzazione corsi disponibili");
		actionList.addItem("Invia i voti degli esami alla segreteria");
		actionList.setVisibleItemCount(7);
		
		actionList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				index = actionList.getSelectedIndex();
			}
		});
		
		actionList.addDoubleClickHandler(new ProfessorActionListHandler());
		
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
	
	private class ProfessorActionListHandler implements DoubleClickHandler{
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			switch(index) {
			case 0:	// Creazione / Modifica un corso
				mainpage.openCreateChangeCourseFormProfessorDashboard();
				break;
			case 1: // Cancellazione di un corso
				mainpage.openRemoveCourseDashboard();
				break;
			case 2:	// Creazione / Modifica  un esame
				mainpage.openCreateChangeTestFormProfessorDashboard();
				break;
			case 3: // Cancellazione di un esame
				// TODO:
				break;
			case 4: // Visualizzazione informazioni personali
				Singleton.getGreetingService().viewPersonalInfo(ActualSession.getActualSession().getEmail(),
						new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("ERRORE NELLA VISUALIZZAZIONE DELLE INFORMAZIONI PERSONALI: " + caught.getMessage());
					}

					@Override
					public void onSuccess(String result) {
						mainpage.openViewPersonalInfo(result);
					}
					
				});
				
				break;
			case 5:
				mainpage.openSearchCourse();
				break;
			case 6: // Invia i voti degli esami alla segreteria ccx  
				
				break;
			default: // Default operation: error
				Window.alert("DEFAULT OPERATION");
				break;
			}
		}



		
	}
}

