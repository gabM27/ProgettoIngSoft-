package it.unibo.ingsoft.gwt.client.settings.professorSettings;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.client.settings.Singleton;
import it.unibo.ingsoft.gwt.shared.Status;
import it.unibo.ingsoft.gwt.shared.usersfacade.ProfessorFacade;

public class SendMarksDashboard extends Composite {
	private Mainpage mainpage;
	private VerticalPanel mainPanel = new VerticalPanel();
	private ListBox courseNameBox;
	private String courseName;
	private ListBox registeredStudentsEmailBox;
	private String studentEmail;
	private Integer mark;

	public SendMarksDashboard(Mainpage main) {
		this.mainpage = main;
		initWidget(mainPanel);

		/*
		 * panel --> VerticalPanel che comprende tutti gli elementi.
		 * buttonsPanel --> HorizontalPanel che comprende tutti i bottoni.
		 */
		VerticalPanel panel = new VerticalPanel();
		HorizontalPanel buttonsPanel = new HorizontalPanel();

		courseNameBox = new ListBox();
		registeredStudentsEmailBox = new ListBox();
		// Caso base
		registeredStudentsEmailBox.addItem("Nessun corso selezionato.");

		Label courseLbl = new Label("Seleziona il corso, tra quelli di tua competenza, per il quale vuoi inserire i voti: ");
		Label studentLbl = new Label("Seleziona lo studente, tra quelli iscritti all'esame del corso, per il quale vuoi inserire il voto:");
		Label markLbl = new Label("Scrivere il voto da aggiungere: inserire un valore tra 0 [non superato]; 18 -- 30 [superato]; 31 [30 e lode].");

		Singleton.getGreetingService().viewProfCourses(ActualSession.getActualSession().getEmail(), 
				new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERRORE: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				String[] coursesList = result.split("_");
				for (int i = 0; i < coursesList.length; i++) {
					courseNameBox.addItem(coursesList[i]);
				}
				// Gestione del caso in cui non si apre il menu a tendina e si lascia di default il primo valore
				courseName = courseNameBox.getItemText(0);
				Singleton.getGreetingService().viewRegisteredStudentFromCourse(courseName, 
						new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("ERROR viewing registered students form course: " + caught.getMessage());
					}

					@Override
					public void onSuccess(String result) {
						registeredStudentsEmailBox.clear();
						String[] registeredStudents = result.split("_");
						for (int i = 0; i < registeredStudents.length; i++) {
							registeredStudentsEmailBox.addItem(registeredStudents[i]);
						}
						// Gestione del caso in cui non si apre il menu a tendina e si lascia di default il primo valore
						studentEmail = registeredStudentsEmailBox.getItemText(0); 
					}

				});

			}
		});


		
		courseNameBox.addChangeHandler(new ViewRegisteredStudents4Course());

		TextArea markTA = new TextArea();
		markTA.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				mark = Integer.parseInt(markTA.getText());
			}

		});

		registeredStudentsEmailBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				studentEmail = registeredStudentsEmailBox.getSelectedItemText();
			}

		});

		/*
		 * ADDING ELEMENTS TO PANEL
		 */
		panel.add(courseLbl);
		panel.add(courseNameBox);
		panel.add(studentLbl);
		panel.add(registeredStudentsEmailBox);
		panel.add(markLbl);
		panel.add(markTA);

		/*
		 * Buttons
		 */
		Button btnSend = new Button("SEND"); // Invia il voto al DB
		btnSend.addClickHandler(new SendMarksHandler());

		Button btnBack = new Button("BACK");
		btnBack.addClickHandler(new ClickHandler() { // BACK TO DASHBOARD
			@Override
			public void onClick(ClickEvent event) {
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
		 * ADDING BUTTONS TO BUTTONS PANEL
		 */
		buttonsPanel.add(btnSend);
		buttonsPanel.add(btnBack);
		buttonsPanel.add(btnLogout);
		buttonsPanel.setSpacing(5);

		panel.setSpacing(5);
		buttonsPanel.setSpacing(5);	
		this.mainPanel.add(panel);
		this.mainPanel.add(buttonsPanel);
	}

	private class ViewRegisteredStudents4Course implements ChangeHandler {		

		@Override
		public void onChange(ChangeEvent event) {
			courseName = courseNameBox.getSelectedItemText();

			Singleton.getGreetingService().viewRegisteredStudentFromCourse(courseName, 
					new AsyncCallback<String>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("ERROR viewing registered students form course: " + caught.getMessage());
				}

				@Override
				public void onSuccess(String result) {
					registeredStudentsEmailBox.clear();
					String[] registeredStudents = result.split("_");
					for (int i = 0; i < registeredStudents.length; i++) {
						registeredStudentsEmailBox.addItem(registeredStudents[i]);
					}
					// Gestione del caso in cui non si apre il menu a tendina e si lascia di default il primo valore
					studentEmail = registeredStudentsEmailBox.getItemText(0); 
				}

			});
		}

	}

	private class SendMarksHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if ((mark == 0 )|| ((mark >= 18) && (mark <= 31))) {
				ProfessorFacade.getProfessorFacade().sendMark(studentEmail, courseName, mark);
			} else {
				Window.alert("Valore del voto non consentito.\n"
						+ "Inserire un valore tra quelli consentiti!\n"
						+ "[0] --> Bocciatura.\n"
						+ "[18-30] --> Esame superato.\n"
						+ "[31] --> Esame superato con 30 e lode!");
			}

		}

	}


}
