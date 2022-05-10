package it.unibo.ingsoft.gwt.client.settings.studentSettings;

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
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.client.settings.Singleton;
import it.unibo.ingsoft.gwt.shared.Status;
import it.unibo.ingsoft.gwt.shared.usersfacade.StudentFacade;

public class CourseEnrollmentDashboard extends Composite {
	// Variabili istanza
	private Mainpage mainpage;
	private VerticalPanel mainPanel = new VerticalPanel();
	private String departmentName;
	private ListBox depNameBox;
	private String courseName;
	private ListBox courseNameBox;
	
	public CourseEnrollmentDashboard(Mainpage main) {
		this.mainpage = main;
		initWidget(mainPanel);
		
		VerticalPanel panel = new VerticalPanel();
		
		Label descriptionDepLabel = new Label("Seleziona il dipartimento per visualizzare la lista dei relativi corsi:");
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

		depNameBox.addChangeHandler(new ViewCourses4SignUp());
		
		courseNameBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				courseName = courseNameBox.getSelectedItemText();
			}
		});
		
		Label descriptionCourseLabel = new Label("Seleziona il corso al quale ci si vuole iscrivere:");
		
		/*
		 * HorizontalPanel che contiene i buttons
		 */
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		
		/*
		 * Buttons
		 */
		Button btnSignUp = new Button("SIGN UP"); // Iscrive lo studente al corso selezionato 
		btnSignUp.addClickHandler(new SignUpCourseHandler());
		
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
		buttonsPanel.add(btnSignUp);
		buttonsPanel.add(btnBack);
		buttonsPanel.add(btnLogout);
		buttonsPanel.setSpacing(5);
		
		/*
		 * ADDING ELEMENTS TO PANEL
		 */
		panel.add(descriptionDepLabel);
		panel.add(depNameBox);
		panel.add(descriptionCourseLabel);
		panel.add(courseNameBox);
		panel.setSpacing(5);
		
		this.mainPanel.add(panel);
		this.mainPanel.add(buttonsPanel);
	}
	
	private class ViewCourses4SignUp implements ChangeHandler{

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
	
	private class SignUpCourseHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			StudentFacade.getStudentFacade().courseRegistration(courseName);
		}
		
	}
}