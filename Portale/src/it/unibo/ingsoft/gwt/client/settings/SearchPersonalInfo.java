<<<<<<< HEAD
package it.unibo.ingsoft.gwt.client.settings.adminSettings;

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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.shared.Status;
import it.unibo.ingsoft.gwt.shared.usersfacade.AdminFacade;

public class SearchPersonalInfo extends Composite {
	private Mainpage mainpage;
	private VerticalPanel mainPanel = new VerticalPanel();
	private String email;
	
	public SearchPersonalInfo(Mainpage main) {
		this.mainpage = main;
		initWidget(mainPanel);

		VerticalPanel emailPanel = new VerticalPanel();
		
		Label emailLbl = new Label("Email dell'account del quale si vogliono visualizzare le informazioni personali:");
		TextBox emailTB = new TextBox();
		emailTB.setText("EMAIL");
		emailTB.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				email = emailTB.getText();
			}
			
		});
		
		emailPanel.add(emailLbl);
		emailPanel.add(emailTB);
		emailPanel.setSpacing(5);
		
		/*
		 * HorizontalPanel che contiene i buttons
		 */
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		
		/*
		 * Buttons
		 */
		Button btnView = new Button("VIEW"); // Search info in DB
		btnView.addClickHandler(new ViewInfoHandler());
		
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
		 * ADDING BUTTONS TO buttonsPanel
		 */
		buttonsPanel.add(btnView);
		buttonsPanel.add(btnBack);
		buttonsPanel.add(btnLogout);
		buttonsPanel.setSpacing(5);
		
		/*
		 * ADDING ELEMENTS TO mainPanel
		 */
		this.mainPanel.add(emailPanel);
		this.mainPanel.add(buttonsPanel);
	}
	
	private class ViewInfoHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			AdminFacade.getAdminFacade().printPersonalInfo(email, mainpage);
		}
	}	
}
=======
package it.unibo.ingsoft.gwt.client.settings.adminSettings;

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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.shared.Status;
import it.unibo.ingsoft.gwt.shared.usersfacade.AdminFacade;

public class SearchPersonalInfo extends Composite {
	private Mainpage mainpage;
	private VerticalPanel mainPanel = new VerticalPanel();
	private String email;
	
	public SearchPersonalInfo(Mainpage main) {
		this.mainpage = main;
		initWidget(mainPanel);

		VerticalPanel emailPanel = new VerticalPanel();
		
		Label emailLbl = new Label("Email dell'account del quale si vogliono visualizzare le informazioni personali:");
		TextBox emailTB = new TextBox();
		emailTB.setText("EMAIL");
		emailTB.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				email = emailTB.getText();
			}
			
		});
		
		emailPanel.add(emailLbl);
		emailPanel.add(emailTB);
		emailPanel.setSpacing(5);
		
		/*
		 * HorizontalPanel che contiene i buttons
		 */
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		
		/*
		 * Buttons
		 */
		Button btnView = new Button("VIEW"); // Search info in DB
		btnView.addClickHandler(new ViewInfoHandler());
		
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
		 * ADDING BUTTONS TO buttonsPanel
		 */
		buttonsPanel.add(btnView);
		buttonsPanel.add(btnBack);
		buttonsPanel.add(btnLogout);
		buttonsPanel.setSpacing(5);
		
		/*
		 * ADDING ELEMENTS TO mainPanel
		 */
		this.mainPanel.add(emailPanel);
		this.mainPanel.add(buttonsPanel);
	}
	
	private class ViewInfoHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			AdminFacade.getAdminFacade().printPersonalInfo(email, mainpage);
		}
	}	
}
>>>>>>> branch 'sviluppo' of https://github.com/gabM27/ProgettoIngSoft-.git
