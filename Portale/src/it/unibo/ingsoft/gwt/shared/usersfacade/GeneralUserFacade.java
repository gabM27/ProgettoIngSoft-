package it.unibo.ingsoft.gwt.shared.usersfacade;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TextArea;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.Singleton;

public class GeneralUserFacade {
	// Variabili istanza
	private static GeneralUserFacade generalUserFacade;

	public static synchronized GeneralUserFacade getGeneralUserFacade() {
		if (generalUserFacade == null) {
			generalUserFacade = new GeneralUserFacade();
		}
		return generalUserFacade;
	}

	/*
	 * Visualizzazione della lista dei dipartimenti
	 */
	public void printDepartmentsList(Mainpage main) {
		Singleton.getGreetingService().viewDepartments(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR printing departments list: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				String[] dipList = result.split("_");
				main.openViewDepartmentsList(dipList);
			}
		});
	}

	/*
	 * Visualizzazione della lista dei corsi
	 */
	public void printCoursesList(Mainpage main, String department) {
		Singleton.getGreetingService().viewCourses(department, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR printing courses list: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				String[] coursesList = result.split("_");
				main.openViewCoursesList(coursesList);
			}
		});
	}
	
}
