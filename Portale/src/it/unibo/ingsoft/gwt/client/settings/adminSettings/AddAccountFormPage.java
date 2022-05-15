package it.unibo.ingsoft.gwt.client.settings.adminSettings;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.shared.Status;
import it.unibo.ingsoft.gwt.shared.usersfacade.AdminFacade;

public class AddAccountFormPage extends Composite{
	// Variabili istanza
	private Mainpage mainpage; // Riferimento alla mainpage
	private VerticalPanel mainPanel = new VerticalPanel(); // MainPanel della pagina, contiene tutti gli elementi
	private String emailInput; // Email che deve essere aggiunta per il nuovo account
	private String passwordInput; // Password che deve essere aggiunta al nuovo account
	private String accountType = "student"; // Tipo di account da aggiungere, default : student
	
	
	public AddAccountFormPage(Mainpage main) {
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
		Label emailLbl = new Label("Email del nuovo account:");
		TextBox emailTB = new TextBox();
		emailTB.setText("EMAIL");
		emailTB.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				emailInput = emailTB.getText();
			}
			
		});
		
		/*
		 * PASSWORD
		 */
		Label passLbl = new Label("Password del nuovo account:");
		PasswordTextBox passTB = new PasswordTextBox();
		passTB.setText("PASS");
		passTB.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				passwordInput = passTB.getText();
			}
			
		});
		/*
		 * TYPE_ACCOUNT
		 */
		ListBox typeAccountList = new ListBox();
		typeAccountList.addItem("Student");
		typeAccountList.addItem("Professor");
		typeAccountList.addItem("Secretary");
		typeAccountList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				accountType = typeAccountList.getSelectedItemText().toLowerCase();
			}
		});
		/*
		 * BUTTONS
		 */
		Button btnAdd = new Button("ADD"); // ADD NEW ACCOUNT
		btnAdd.addClickHandler(new AddAccountHandler());
		
		Button btnBack = new Button("BACK");
		btnBack.addClickHandler(new ClickHandler() { // BACK TO ADMIN DASHBOARD
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
		 * ADDING ELEMENTS TO FORM PANEL
		 */
		formPanel.add(emailLbl);
		formPanel.add(emailTB);
		formPanel.add(passLbl);
		formPanel.add(passTB);
		formPanel.add(typeAccountList);
		formPanel.setSpacing(5);
		/*
		 * ADDING ELEMENTS TO BUTTON PANEL
		 */
		buttonPanel.add(btnAdd);
		buttonPanel.add(btnBack);
		buttonPanel.add(btnLogout);
		buttonPanel.setSpacing(5);
		/*
		 * ADDING PANELS TO MAIN PANEL
		 */
		this.mainPanel.add(formPanel);
		this.mainPanel.add(buttonPanel);
		
	}
	
	private class AddAccountHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			AdminFacade.getAdminFacade().addNewAccount(emailInput, passwordInput, accountType.toLowerCase());
			
		}
	}
		
		
	
}
