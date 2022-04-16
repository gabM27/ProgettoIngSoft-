package it.unibo.ingsoft.gwt.client.settings.userSettings;

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
import it.unibo.ingsoft.gwt.shared.usersfacade.GeneralUserFacade;

public class SearchCourse extends Composite{
	private Mainpage mainpage;
	private VerticalPanel mainPanel = new VerticalPanel();
	private String department;
	
	public SearchCourse(Mainpage main) {
		this.mainpage = main;
		initWidget(mainPanel);

		VerticalPanel panel = new VerticalPanel();
		
		Label depLabel = new Label("Seleziona il dipartimento per visualizzare la lista dei relativi corsi disponibili:");
		ListBox nomeDipBox = new ListBox();
		
		Singleton.getGreetingService().viewDepartments(new AsyncCallback<String>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERRORE: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				String[] dipList = result.split("_");
				for (int i = 0; i < dipList.length; i++) {
					nomeDipBox.addItem(dipList[i]);
				}
				// Gestione del caso in cui non si apre il menu a tendina e si lascia di default il primo valore
				department = nomeDipBox.getItemText(0);  
			}
		});
		
		nomeDipBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				department = nomeDipBox.getSelectedItemText();
			}
		});
		
		/*
		 * HorizontalPanel che contiene i buttons
		 */
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		
		/*
		 * Buttons
		 */
		Button btnView = new Button("VIEW"); // Search coursesList in DB
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
		 * ADDING ELEMENTS TO PANEL
		 */
		panel.add(depLabel);
		panel.add(nomeDipBox);
		panel.setSpacing(5);
		
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
		this.mainPanel.add(panel);
		this.mainPanel.add(buttonsPanel);
	}
	
	private class ViewInfoHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			GeneralUserFacade.getGeneralUserFacade().printCoursesList(mainpage, department);
			
		}
	}	
}