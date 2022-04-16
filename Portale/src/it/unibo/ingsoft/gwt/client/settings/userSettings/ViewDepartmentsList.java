package it.unibo.ingsoft.gwt.client.settings.userSettings;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.shared.Status;

public class ViewDepartmentsList extends Composite {
	Mainpage mainpage;
	VerticalPanel mainPanel = new VerticalPanel();
	
	
	public ViewDepartmentsList(Mainpage main, String[] list) {
		this.mainpage = main;
		initWidget(mainPanel);
		
		/*
		 * panel che contiene tutti i componenti 
		 */
		VerticalPanel panel = new VerticalPanel();
		
		
		/*
		 * TextArea che contiene la lista dei dipartimenti
		 */
		TextArea departmentsList = new TextArea();
		String stringDepList = "";
		for (int i = 0; i < list.length; i++) {
			stringDepList += "- " + list[i].toUpperCase() + ".\n";
		}
		departmentsList.setText(stringDepList);
		departmentsList.setPixelSize(200,350);
		departmentsList.setReadOnly(true);
		
		/*
		 * HorizontalPanel che comprende tutti i bottoni
		 */
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		
		/*
		 * Buttons
		 */
		Button btnBack = new Button("BACK");
		btnBack.addClickHandler(new ClickHandler() { // BACK to DASHBOARD
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
		 * ADDING BUTTONS TO BUTTON PANEL 
		 */
		buttonsPanel.add(btnBack);
		buttonsPanel.add(btnLogout);
		buttonsPanel.setSpacing(5);
		
		/*
		 * ADDING ELEMENTS TO PANEL
		 */
		panel.add(departmentsList);
		panel.add(buttonsPanel);
		panel.setSpacing(5);
		
		// ADDING PANEL TO MAIN PANEL
		mainPanel.add(panel);
	}
}
