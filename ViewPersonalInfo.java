package it.unibo.ingsoft.gwt.client.settings;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.shared.Status;

public class ViewPersonalInfo extends Composite {
	private Mainpage mainpage;
	private VerticalPanel mainPanel = new VerticalPanel();
	
	public ViewPersonalInfo(Mainpage main) {
		this.mainpage = main;
		initWidget(mainPanel);

		/*
		 * HorizontalPanel che contiene i buttons
		 */
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		
		/*
		 * Buttons
		 */
		Button btnBack = new Button("BACK");
		btnBack.addClickHandler(new ClickHandler() { // BACK TO ADMIN DASHBOARD
			@Override
			public void onClick(ClickEvent event) {
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
		 * ADDING BUTTONS TO buttonsPanel
		 */
		buttonsPanel.add(btnBack);
		buttonsPanel.add(btnLogout);
		buttonsPanel.setSpacing(5);
		
		/*
		 * ADDING ELEMENTS TO mainPanel
		 */
		
		this.mainPanel.add(buttonsPanel);
	}
}
