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

public class StudentDashboard extends Composite {
	// Variabili istanza
	private Mainpage mainpage; // Riferimento alla mainpage
	private VerticalPanel mainPanel = new VerticalPanel(); // mainPanel che contiene la dashboard
	private int index;
	
	public StudentDashboard(Mainpage main) {
		this.mainpage = main;
		initWidget(this.mainPanel);
		
		/*
		 * OPERAZIONI STUDENTE
		 */
		Label statusLabel = new Label("" + ActualSession.getActualSession().getActualStatus());
		Label descriptionLabel = new Label("Scegli un'operazione dal menu a tendina.");
		
		ListBox actionList = new ListBox();
		
		actionList.addItem("Visualizzazione lista dei corsi disponibili");
		actionList.addItem("Visualizzazione informazioni di un corso");
		actionList.addItem("Iscrizione a un corso");
		actionList.addItem("Cancellazione iscrizione a un corso");
		actionList.addItem("Iscrizione ad un esame");
		actionList.addItem("Cancellazione iscrizione a un esame");
		actionList.addItem("Visualizzazione informazioni personali");
		actionList.addItem("Visualizzazione voti degli esami svolti");
		actionList.setVisibleItemCount(8);
		actionList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				index = actionList.getSelectedIndex();
			}
		});
		actionList.addDoubleClickHandler(new StudentActionListHandler());
		
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
	
	private class StudentActionListHandler implements DoubleClickHandler{
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			switch(index) {
			case 0:	// Visualizzazione dei corsi disponibili
				mainpage.openSearchCourse();
				break;
			case 1: // Visualizzazione informazione di un corso
				mainpage.openSearchCourse4Info();
				break;
			case 2:	// Iscrizione a un corso 
				mainpage.openCourseEnrollment(); // TODO: completare
				break;
			case 3: // Cancellazione iscrizione da un corso
				mainpage.openDeleteCourseEnrollment(); // TODO: completare
				break;
			case 4: // Registrazione all'esame relativo a un corso 
				mainpage.openExamEnrollment(); // TODO: completare
				break;
			case 5: // Cancellazione registrazione all'esame relativo a un corso
				mainpage.openDeleteExamEnrollment(); // TODO: completare
				break;
			case 6: // Visualizza info personali degli studenti iscritti 
				GeneralUserFacade.getGeneralUserFacade().printPersonalInfo(mainpage);
				break;
			case 7:	// Visualizza voti degli esami svolti 
				//TODO:
				break;
			default: // Default operation: error
				Window.alert("ERROR: DEFAULT OPERATION");
				break;
			}
		}



		
	}
}
