package it.unibo.ingsoft.gwt.client.settings.adminSettings;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.shared.Status;
import it.unibo.ingsoft.gwt.shared.usersfacade.AdminFacade;

public class AddDepartmentFormDashboard extends Composite {
	// Variabili istanza
	Mainpage mainpage;
	VerticalPanel mainPanel = new VerticalPanel();
	private String departmentName;
	
	public AddDepartmentFormDashboard(Mainpage main) {
		this.mainpage = main;
		initWidget(mainPanel);
		
		/*
		 * Panel che comprende tutti gli elementi del form
		 */
		VerticalPanel panel = new VerticalPanel();
		
		/*
		 * DEPARTMENT NAME
		 */
		Label depLbl = new Label("Nome del dipartimento da aggiungere.");
		TextBox depTB = new TextBox();
		depTB.setText("NOME DIPARTIMENTO");
		depTB.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				departmentName = depTB.getText();
			}
		});
		
		/*
		 * HorizontalPanel che comprende tutti i bottoni
		 */
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		
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
		 * ADDING ELEMENTS TO PANEL
		 */
		panel.add(depLbl);
		panel.add(depTB);
		panel.setSpacing(5);
		/*
		 * ADDING BUTTONS TO BUTTON PANEL 
		 */
		buttonsPanel.add(btnSend);
		buttonsPanel.add(btnBack);
		buttonsPanel.add(btnLogout);
		buttonsPanel.setSpacing(5);
		
		/*
		 * ADDING ELEMENTS TO MAIN PANEL 
		 */
		this.mainPanel.add(panel);
		this.mainPanel.add(buttonsPanel);
		
	}
	
	private class SendInfoHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			AdminFacade.getAdminFacade().addNewDepartment(departmentName);
		}
	}
}
