package it.unibo.ingsoft.gwt.client.settings.professorSettings;

import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.client.settings.Singleton;
import it.unibo.ingsoft.gwt.shared.Status;

public class ChangeCourseInfoProfessorDashboard extends Composite {
	// Variabili istanza
	private Mainpage mainpage;
	private VerticalPanel mainPanel = new VerticalPanel();
	
	private String nomeDipartimento = "";
	private String nomeCorso = "";
	private Date dataInizio; 
	private Date dataFine;
	private String descrizioneCorso = "";
	private String codocente = "";
	
	private ListBox depNameBox;
	private ListBox courseNameBox;
	
	
	public ChangeCourseInfoProfessorDashboard(Mainpage main) {
		this.mainpage = main;
		initWidget(this.mainPanel);
		/*
		 * panel: contiene tutti gli elementi necessari per modificare i dati di un corso.
		 * buttonPanel: contiene tutti i bottoni.
		 */
		VerticalPanel panel = new VerticalPanel();
		HorizontalPanel buttonPanel = new HorizontalPanel();
		
		Label descriptionLbl = new Label("MODIFICA DI UN CORSO.");
		
		/*
		 * NOME DEL DIPARTIMENTO DI APPARTENENZA DEL CORSO
		 */
		Label departmentLbl = new Label("Scegli il nome del dipartimento di appartenenza del corso da modificare:");
		depNameBox = new ListBox();
		depNameBox = new ListBox();
		courseNameBox = new ListBox();
		// Caso base
		courseNameBox.addItem("Nessun dipartimento selezionato.");
		
		/*
		 * DEPARTMENTS LISTBOX
		 */
		Singleton.getGreetingService().viewDepartments(new AsyncCallback<String>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERRORE: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				String[] dipList = result.split("_");
				for (int i = 0; i < dipList.length; i++) {
					depNameBox.addItem(dipList[i]);
				}
				// Gestione del caso in cui non si apre il menu a tendina e si lascia di default il primo valore
				nomeDipartimento = depNameBox.getItemText(0);
				
				/*
				 * Gestione caso base in cui non viene modificato il valore del nomeDipartimento nel menu a tendina
				 * Si stampano i corsi del valore del dipartimento di default (valore n.0 del menu a tendina) 
				 */
				Singleton.getGreetingService().viewCourses(nomeDipartimento, new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("ERRORE: " + caught.getMessage());
					}

					@Override
					public void onSuccess(String result) {
						courseNameBox.clear();
						String[] coursesList = result.split("_");
						for (int i = 0; i < coursesList.length; i++) {
							courseNameBox.addItem(coursesList[i]);
						}
						// Gestione del caso in cui non si apre il menu a tendina e si lascia di default il primo valore
						nomeCorso = courseNameBox.getItemText(0);  
					}
				});
			}
		});

		depNameBox.addChangeHandler(new ViewCourses4Change());
		
		courseNameBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				nomeCorso = courseNameBox.getSelectedItemText();
			}
		});
		
		/*
		 * NOME CORSO
		 */
		Label nomeCorsolLbl = new Label("Nome del corso che si vuole modificare: ");
		
		// TODO:
		
		/*
		 * DATA INIZIO
		 */
		Label dataInizioLbl = new Label("Data inizio:");
		DatePicker dataInizioPick = new DatePicker();
		dataInizioPick.setYearAndMonthDropdownVisible(true);
		dataInizio = dataInizioPick.getHighlightedDate(); // Gestione caso in cui non venga aggiornato il pickdate
		
		dataInizioPick.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				// DEBUG
				Window.alert("Hai selezionato come data inizio: " + dataInizioPick.getHighlightedDate());
				dataInizio = dataInizioPick.getHighlightedDate();
			}
		});
		
		/*
		 * DATA FINE 
		 */
		Label dataFineLbl = new Label("Data fine:");
		DatePicker dataFinePick = new DatePicker();
		dataFinePick.setYearAndMonthDropdownVisible(true);
		dataFine = dataFinePick.getHighlightedDate(); // Gestione caso in cui non venga aggiornato il pickdate
		
		dataFinePick.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				// DEBUG
				Window.alert("Hai selezionato come data fine: " + dataFinePick.getHighlightedDate());
				dataFine = dataFinePick.getHighlightedDate();
			}
		});
		
		/*
		 * DESCRIZIONE 
		 */
		Label DescrizioneLbl = new Label("Descrizione corso:");
		TextArea descrizioneTA = new TextArea();
		descrizioneTA.setPixelSize(300,100);
		descrizioneTA.setText("DESCRIZIONE");
		
		descrizioneTA.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				descrizioneCorso = descrizioneTA.getText();
			}
			
		});
		
		/*
		 * CO-DOCENTE 
		 */
		Label coDocentLbl = new Label("Email del co-docente:");
		TextBox coDocentTB = new TextBox();
		coDocentTB.setText("");
		coDocentTB.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				codocente = coDocentTB.getText();
			}
			
		});
		 
		 /*
		  * BUTTONS
		  */
		Button btnSend = new Button("UPDATE"); // Send updates to DB
//		btnSend.addClickHandler(new SendInfoHandler());
		
		Button btnBack = new Button("BACK");
			btnBack.addClickHandler(new ClickHandler() { // BACK TO PROFESSOR DASHBOARD
				@Override
				public void onClick(ClickEvent event) {
					mainPanel.clear();
					mainpage.openAccountDashboard(ActualSession.getActualSession().getActualStatus());
				}
			});
			
			Button btnLogout = new Button("LOGOUT"); // BACK TO HOMEPAGE
			btnLogout.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ActualSession.getActualSession().setActualSession("", Status.NOT_REGISTERED);
					mainpage.openHomepage();
				}
			});
			
			/*
			 * ADDING ELEMENTS TO FORM PANEL
			 */
			panel.add(descriptionLbl);
			panel.add(departmentLbl);
			panel.add(depNameBox);
			panel.add(nomeCorsolLbl);
			panel.add(courseNameBox);
			panel.add(dataInizioLbl);
			panel.add(dataInizioPick);
			panel.add(dataFineLbl);
			panel.add(dataFinePick);
			panel.add(DescrizioneLbl);
			panel.add(descrizioneTA);
			panel.add(coDocentLbl);
			panel.add(coDocentTB);
			
			panel.setSpacing(5);
			
			/*
			 * ADDING ELEMETS TO BUTTON PANEL
			 */
			buttonPanel.add(btnSend);
			buttonPanel.add(btnBack);
			buttonPanel.add(btnLogout);
			buttonPanel.setSpacing(5);
			
			/*
			 * ADDING PANELS TO MAIN PANEL
			 */
			this.mainPanel.add(panel);
			this.mainPanel.add(buttonPanel);
	
	}

	
	private class ViewCourses4Change implements ChangeHandler{

		@Override
		public void onChange(ChangeEvent event) {
			nomeDipartimento = depNameBox.getSelectedItemText();
			
			Singleton.getGreetingService().viewCourses(nomeDipartimento, new AsyncCallback<String>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("ERRORE: " + caught.getMessage());
				}

				@Override
				public void onSuccess(String result) {
					courseNameBox.clear();
					String[] coursesList = result.split("_");
					for (int i = 0; i < coursesList.length; i++) {
						courseNameBox.addItem(coursesList[i]);
					}
					// Gestione del caso in cui non si apre il menu a tendina e si lascia di default il primo valore
					nomeCorso = courseNameBox.getItemText(0);  
				}
			});
		}
		
	}
//	private class SendInfoHandler implements ClickHandler{
//
//		@Override
//		public void onClick(ClickEvent event) {
//			ProfessorFacade.getProfessorFacade().changeCourseInfo(nomeDipartimento, nomeCorso,
//					dataInizio, dataFine, descrizioneCorso, codocente);
//		}
//
//	}

}