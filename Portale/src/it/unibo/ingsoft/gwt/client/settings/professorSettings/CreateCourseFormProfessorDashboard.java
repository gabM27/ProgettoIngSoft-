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
import it.unibo.ingsoft.gwt.shared.usersfacade.ProfessorFacade;

public class CreateCourseFormProfessorDashboard extends Composite{
	private Mainpage mainpage; // Riferimento alla mainpage
	private VerticalPanel mainPanel = new VerticalPanel(); // mainPanel che comprende tutti gli elementi della dashboard
	private String nomeDipartimento = "";
	private String nomeCorso = "";
	private Date dataInizio; 
	private Date dataFine;
	private String descrizioneCorso = "";
	private String codocente = "";

	public CreateCourseFormProfessorDashboard(Mainpage main) {
		this.mainpage = main;
		initWidget(this.mainPanel);
		/*
		 * formPanel: contiene tutti gli elementi necessari per aggiungere i dati di un corso.
		 * buttonPanel: contiene tutti i bottoni.
		 */
		VerticalPanel formPanel = new VerticalPanel();
		HorizontalPanel buttonPanel = new HorizontalPanel();
		
		Label descriptionLbl = new Label("CREAZIONE DI UN CORSO.");
		
		/*
		 * NOME DEL DIPARTIMENTO DI APPARTENENZA DEL CORSO
		 */
		Label departmentLbl = new Label("Scegli il nome del dipartimento di appartenenza del corso da aggiungere:");
		ListBox nomeDipBox = new ListBox();
		
		Singleton.getGreetingService().viewDepartments(new AsyncCallback<String>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERRORE: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				String[] dipList = result.split("_");
				for (int i = 0; i < dipList.length; i++) {
					nomeDipBox.addItem(dipList[i]);
				}
				// Gestione del caso in cui non si apre il menu a tendina e si lascia di default il primo valore
				nomeDipartimento = nomeDipBox.getItemText(0);  
				
			}
		});
		
		nomeDipBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				nomeDipartimento = nomeDipBox.getSelectedItemText();
			}
		});
		
		/*
		 * NOME CORSO
		 */
		Label nomeCorsolLbl = new Label("Nome del corso che si vuole creare: ");
		TextBox nomeTB = new TextBox();
		nomeTB.setText("NOME CORSO");
		nomeTB.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				nomeCorso = nomeTB.getText();
			}
		});
		
		/*
		 * DATA INZIO
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
		Button btnSend = new Button("SEND"); // Send course's info to DB
		btnSend.addClickHandler(new SendInfoHandler());
		
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
			formPanel.add(descriptionLbl);
			formPanel.add(departmentLbl);
			formPanel.add(nomeDipBox);
			formPanel.add(nomeCorsolLbl);
			formPanel.add(nomeTB);
			formPanel.add(dataInizioLbl);
			formPanel.add(dataInizioPick);
			formPanel.add(dataFineLbl);
			formPanel.add(dataFinePick);
			formPanel.add(DescrizioneLbl);
			formPanel.add(descrizioneTA);
			formPanel.add(coDocentLbl);
			formPanel.add(coDocentTB);
			
			formPanel.setSpacing(5);
			
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
			this.mainPanel.add(formPanel);
			this.mainPanel.add(buttonPanel);
	
	}

	private class SendInfoHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			ProfessorFacade.getProfessorFacade().addNewCourse(nomeDipartimento, nomeCorso,
					ActualSession.getActualSession().getEmail(),
					dataInizio, dataFine, descrizioneCorso, codocente);
		}

	}

}
