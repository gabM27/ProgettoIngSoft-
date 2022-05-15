package it.unibo.ingsoft.gwt.client.settings.secretarySettings;

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
import it.unibo.ingsoft.gwt.shared.usersfacade.SecretaryFacade;

public class MakeStudentsGradesVisible extends Composite {
	private Mainpage mainpage;
	private VerticalPanel mainPanel = new VerticalPanel();
	private ListBox examListBox;
	private String examName = "";
	
	
	public MakeStudentsGradesVisible(Mainpage main) {
		this.mainpage = main;
		initWidget(mainPanel);
		
		VerticalPanel panel = new VerticalPanel();
		
		Label descriptionLabel = new Label("Seleziona l'esame del quale si vogliono pubblicare i voti:");
		examListBox = new ListBox();
		examListBox.addItem("Nessun esame.");
		
		Singleton.getGreetingService().viewExamsSecList(ActualSession.getActualSession().getEmail(), 
				new AsyncCallback<String>() {
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERRORE: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				examListBox.clear();
				String[] coursesList = result.split("_");
				for (int i = 0; i < coursesList.length; i++) {
					examListBox.addItem(coursesList[i]);
				}
				// Gestione del caso in cui non si apre il menu a tendina e si lascia di default il primo valore
				examName = examListBox.getItemText(0);  
			}
		});

		examListBox.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					examName = examListBox.getSelectedItemText();
				}
			});
	
		/*
		 * HorizontalPanel che contiene i buttons
		 */
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		
		/*
		 * Buttons
		 */
		Button btnSignUp = new Button("PUBLISH"); // Pubblica i voti degli esami di un corso --> li rende visibili a un utente
		btnSignUp.addClickHandler(new SetVisibiltyHandler());
		
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
		panel.add(descriptionLabel);
		panel.add(examListBox);
		panel.setSpacing(5);
		
		this.mainPanel.add(panel);
		this.mainPanel.add(buttonsPanel);
	}

	
	
	private class SetVisibiltyHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			SecretaryFacade.getSecretaryFacade().setMarksVisibility(examName);
		}
		
	}
}
