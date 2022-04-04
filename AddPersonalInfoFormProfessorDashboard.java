package it.unibo.ingsoft.gwt.client.settings;

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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.shared.Status;

public class AddPersonalInfoFormProfessorDashboard extends Composite {
	private Mainpage mainpage;
	private VerticalPanel mainPanel = new VerticalPanel();
	private String emailInput;
	private String usernameInput;
	private String nameInput;
	private String surnameInput;
	private Date birthdayInput;
	
	public AddPersonalInfoFormProfessorDashboard(Mainpage main) {
		this.mainpage = main;
		initWidget(this.mainPanel);
		
		/*
		 * formPanel : contiene tutti gli elementi necessari per aggiungere un nuovo account.
		 * buttonPanel: contiene tutti i bottoni.
		 */
		VerticalPanel formPanel = new VerticalPanel();
		HorizontalPanel buttonPanel = new HorizontalPanel();
		
		/*
		 * EMAIL
		 */
		Label emailLbl = new Label("Email dell'account al quale si vogliono aggiungere/modificare le informazioni");
		TextBox emailTB = new TextBox();
		emailTB.setText("EMAIL");
		emailTB.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				emailInput = emailTB.getText();
			}
		});
	
		/*
		 * Username
		 */
		Label usernameLbl = new Label("Username dello studente:");
		TextBox usernameTB = new TextBox();
		usernameTB.setText("USERNAME");
		usernameTB.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				usernameInput = usernameTB.getText();
			}
		});
		
		/*
		 * Name
		 */
		Label nameLbl = new Label("Nome dello studente:");
		TextBox nameTB = new TextBox();
		nameTB.setText("NAME");
		nameTB.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				nameInput = nameTB.getText();
			}
		});
		
		/*
		 * Surname
		 */
		Label surnameLbl = new Label("Cognome dello studente:");
		TextBox surnameTB = new TextBox();
		surnameTB.setText("SURNAME");
		surnameTB.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				surnameInput = surnameTB.getText();
			}
		});
		
		/*
		 * Birthday
		 */
		Label birthdayLbl = new Label("Data di nascita dello studente:");
		DatePicker birthdayPick = new DatePicker();
		birthdayPick.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				// DEBUG
				Window.alert("Hai selezionato " + birthdayPick.getHighlightedDate());
				birthdayInput = birthdayPick.getHighlightedDate();
			}
		});
		
		/*
		 * BUTTONS
		 */
		Button btnSend = new Button("SEND"); // Send info to DB
		btnSend.addClickHandler(new SendInfoHandler());
		Button btnBack = new Button("BACK");
		btnBack.addClickHandler(new ClickHandler() { // BACK TO ADMIN DASHBOARD
			@Override
			public void onClick(ClickEvent event) {
				mainPanel.clear();
				mainpage.openAdminDashboard();

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
		formPanel.add(emailLbl);
		formPanel.add(emailTB);
		formPanel.add(usernameLbl);
		formPanel.add(usernameTB);
		formPanel.add(nameLbl);
		formPanel.add(nameTB);
		formPanel.add(surnameLbl);
		formPanel.add(surnameTB);
		formPanel.add(birthdayLbl);
		formPanel.add(birthdayPick);
		
		
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
			Singleton.getGreetingService().addInfoToProfessorAccount(emailInput, usernameInput, 
						nameInput, surnameInput, birthdayInput, new AsyncCallback<String>() {
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("ERROR ADDING INFO TO STUDENT ACCOUNT: " + caught.getMessage());
							}
							@Override
							public void onSuccess(String result) {
								Window.alert(result);
							}
			});	
		}
	}
	
	
	
}
