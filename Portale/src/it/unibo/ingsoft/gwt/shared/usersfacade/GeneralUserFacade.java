package it.unibo.ingsoft.gwt.shared.usersfacade;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
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

	/*
	 * Visualizzazione informazioni personali
	 */
	public void printPersonalInfo(Mainpage mainpage) {
		Singleton.getGreetingService().viewPersonalInfo(ActualSession.getActualSession().getEmail(),
				new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERRORE NELLA VISUALIZZAZIONE DELLE INFORMAZIONI PERSONALI: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				mainpage.openViewPersonalInfo(result);
			}

		});
	}

	/*
	 * Visualizzazione informazioni di un corso
	 */
	public void printCourseInfo(Mainpage main, String courseName) {
		Singleton.getGreetingService().printCourseInfo(courseName, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR SEARCHING THE COURSE: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				Window.alert("Printing course's info...");
				main.addCourseInfoTA(result);
			}

		});
	}


	/*
	 * Visualizzazione informazioni personali di tutti gli studenti
	 */
	public void printAllStudentsPersonalInfo(Mainpage main) {
		Singleton.getGreetingService().viewAllStudentsPersonalInfo(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR PRINTING ALL STUDENTS INFO: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				Window.alert("Printing students' info...");
				main.openViewStudentsPersonalInfo(result);
			}
		});
	}

}
