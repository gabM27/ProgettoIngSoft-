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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.client.settings.Singleton;
import it.unibo.ingsoft.gwt.shared.Status;
import it.unibo.ingsoft.gwt.shared.usersfacade.ProfessorFacade;

public class CreateChangeExamFormProfessorDashboard extends Composite {
	private Mainpage mainpage; // Riferimento alla mainpage
	private VerticalPanel mainPanel = new VerticalPanel(); // mainPanel che comprende tutti gli elementi della dashboard
	private String departmentName;
	private ListBox depNameBox;
	private String courseName;
	private ListBox courseNameBox;
	
	private Date dataEsame;
	private String orarioEsame;
	private String durezzaEsame;
	private String aulaSvolgimento;
	
	public CreateChangeExamFormProfessorDashboard(Mainpage main) {
		this.mainpage = main;
		initWidget(this.mainPanel);
		/*
		 * panel : contiene tutti gli elementi necessari per aggiungere un nuovo account.
		 * buttonPanel: contiene tutti i bottoni.
		 */
		VerticalPanel panel = new VerticalPanel();
		HorizontalPanel buttonPanel = new HorizontalPanel();
		
		// lista dipartimenti e lista corsi
		Label descriptionDepLabel = new Label("Seleziona il dipartimento per visualizzare la lista dei relativi corsi:");
		depNameBox = new ListBox();
		Label descriptionCourseLabel = new Label("Seleziona il corso per il quale si vuole aggiungere l'esame:");
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
				departmentName = depNameBox.getItemText(0);
				
				/*
				 * Gestione caso base in cui non viene modificato il valore del nomeDipartimento nel menu a tendina
				 * Si stampano i corsi del valore del dipartimento di default (valore n.0 del menu a tendina) 
				 */
				Singleton.getGreetingService().viewCourses(departmentName, new AsyncCallback<String>() {

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
						courseName = courseNameBox.getItemText(0);  
					}
				});
			}
		});

		depNameBox.addChangeHandler(new ViewCourses4AddExam());
		
		courseNameBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				courseName = courseNameBox.getSelectedItemText();
			}
		});
		
		/*
		 * DATA 
		 */
		Label dataLbl = new Label("Data esame:");
		DatePicker dataPick = new DatePicker();
		dataPick.setYearAndMonthDropdownVisible(true);
		dataPick.addValueChangeHandler(new ValueChangeHandler<Date>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Window.alert("Hai selezionato " + dataPick.getHighlightedDate());
				dataEsame = dataPick.getHighlightedDate();
			}
		});
		
		/*
		 * ORA 
		 */
		Label oraLbl = new Label("Orario dell'esame:");
		TextBox oraTB = new TextBox();
		oraTB.setText("[hh]:[mm]");
		
		oraTB.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				orarioEsame = oraTB.getText();
			}
		});
		
		/*
		 * DUREZZA
		 */
		 Label durezzaLbl = new Label("Inserimento difficolta' esame:");
		 ListBox level = new ListBox();
		 level.addItem("Bassa");
		 level.addItem("Media");
		 level.addItem("Alta");
		 durezzaEsame = level.getItemText(0); // Gestione caso base in cui non viene modificato il valore
		 
		 level.addChangeHandler(new ChangeHandler() {
			 @Override
			 public void onChange(ChangeEvent event) {
				 durezzaEsame = level.getSelectedItemText();
			 }
		 });
		 
		 /*
		  * AULA SVOLGIMENTO
		  */
		 Label aulaSvolgimentoLbl = new Label("Inserimento aula esame:");
		 TextBox aulaSvolgimentoTB = new TextBox();
		 aulaSvolgimentoTB.setText("Nome aula esame");
		 aulaSvolgimentoTB.addChangeHandler(new ChangeHandler() {
				 @Override
				 public void onChange(ChangeEvent event) {
					 aulaSvolgimento = aulaSvolgimentoTB.getText();
				 }
		 });
		
		/*
		 * BUTTONS
		 */
		Button btnSend = new Button("SEND"); // Send exam's info to DB
		btnSend.addClickHandler(new SendInfoHandler());
		Button btnBack = new Button("BACK");
		btnBack.addClickHandler(new ClickHandler() { // BACK TO Professor DASHBOARD
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
		panel.add(descriptionDepLabel);
		panel.add(depNameBox);
		panel.add(descriptionCourseLabel);
		panel.add(courseNameBox);
		panel.add(dataLbl);
		panel.add(dataPick);
		panel.add(oraLbl);
		panel.add(oraTB);
		panel.add(durezzaLbl);
		panel.add(level);
		panel.add(aulaSvolgimentoLbl);
		panel.add(aulaSvolgimentoTB);
		panel.setSpacing(5);
			
		/*
		 * ADDING ELEMENTS TO BUTTON PANEL
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
	
	private class ViewCourses4AddExam implements ChangeHandler {

		@Override
		public void onChange(ChangeEvent event) {
			departmentName = depNameBox.getSelectedItemText();
			
			Singleton.getGreetingService().viewCourses(departmentName, new AsyncCallback<String>() {

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
					courseName = courseNameBox.getItemText(0);  
				}
			});
		}
	}
	
	private class SendInfoHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			ProfessorFacade.getProfessorFacade().addNewExam(courseName, dataEsame, orarioEsame, durezzaEsame, aulaSvolgimento);
		}

	}

}
