package it.unibo.ingsoft.gwt.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.client.settings.Singleton;
import it.unibo.ingsoft.gwt.shared.Status;

public class Loginpage extends Composite {
	private Mainpage mainpage;
	private FormPanel loginPanel = new FormPanel();
	private String inputEmail;
	private String inputPass;
	
	public Loginpage(Mainpage main) {
		this.mainpage = main;
		initWidget(this.loginPanel);
		
		// Creo un verticalPanel che comprende tutti gli elementi che servono
		VerticalPanel panel = new VerticalPanel();

		/*
		 * EMAIL
		 */
		// Creo un label per la scritta "Email: "
		Label emailLabel = new Label("Email: ");
		// Creo una textBox per inserire l'email
		TextBox emailBox = new TextBox();
		emailBox.setText("EMAIL");
		emailBox.setFocus(true);
		emailBox.selectAll();

		// Gestione dell'inserimento di email nella textBox
		emailBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				inputEmail = emailBox.getText();
			}
		});
		
		/*
		 * PASSWORD
		 */
		// Creo un label per la scritta "Password: "
		Label passLabel = new Label("Password: ");
		// Creo una textBox per inserire la Password
		PasswordTextBox passBox = new PasswordTextBox();
		passBox.setText("pass");
		passBox.setFocus(true);
		passBox.selectAll();
		
		// Gestione dell'inserimento della password nella textBox
		passBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				inputPass = passBox.getValue();
			}
		});
		
		/*
		 * BUTTONS
		 */
		Button btnSend = new Button("LOGIN");
		btnSend.setWidth("150px");
		btnSend.addClickHandler(new LoginHandler());
		
		// Creo il bottone per tornare alla homepage
		Button btnBackHome = new Button("BACK");
		btnBackHome.setWidth("150px");
		// Gestisco l'evento del click 
		btnBackHome.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent e) {
				mainpage.openHomepage();
			}
		});
		
		/*
		 * ADDING ELEMENTS TO VERTICAL PANEL
		 */
		panel.add(emailLabel);
		panel.add(emailBox);
		panel.add(passLabel);
		panel.add(passBox);
		
		panel.add(btnSend);// bottone per inviare la richiesta di login
		panel.add(btnBackHome);// bottone per tornare alla homepage
		
		panel.setSpacing(5);
		
		/*
		 * ADDING TO MAIN PANEL 
		 */
		loginPanel.add(panel);
	}
	
	/*
	 * LOGIN HANDLER  
	 */
	private class LoginHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			Window.alert("Hai inserito: " + inputEmail + " --- " + inputPass);
			
			Singleton.getGreetingService().doLogin(inputEmail,inputPass,new AsyncCallback<Status>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("ERROR IN LOGIN: " + caught.getMessage());
				}

				@Override
				public void onSuccess(Status status) {
					switch(status) {
					case NOT_REGISTERED:
						Window.alert("Account non presente nel DB, chiedere all'admin di aggiungerlo per poter procedere con il LOGIN.");
						break;
					case WRONG_PASS:
						Window.alert("Password errata, riprovare.");
						break;
					case ADMIN:
						ActualSession.getActualSession().setActualSession(inputEmail, status);
						mainpage.openAdminDashboard();
						break;
					case STUDENT:
						ActualSession.getActualSession().setActualSession(inputEmail, status);
						mainpage.openStudentDashboard();
						break;
					case PROFESSOR:
						ActualSession.getActualSession().setActualSession(inputEmail, status);
						mainpage.openProfessorDashboard();
						break;
					case SECRETARY:
						ActualSession.getActualSession().setActualSession(inputEmail, status);
						mainpage.openSecretaryDashboard();
						break;
					default: 
						Window.alert("ERROR: default situation --> something gone wrong.");
						break;
					}
				}
				
			});
		}
	}
	
}
