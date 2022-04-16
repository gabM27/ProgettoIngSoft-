package it.unibo.ingsoft.gwt.shared.usersfacade;

import java.util.Date;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.unibo.ingsoft.gwt.client.settings.Singleton;


public class ProfessorFacade {
	// Variabili istanza
	private static ProfessorFacade professorFacade;
	
	// Ritorna l'istanza di professorFacade, se e' null la istanzia.
	public static synchronized ProfessorFacade getProfessorFacade() {
		if (professorFacade == null) {
			professorFacade = new ProfessorFacade();
		}
		return professorFacade;
	}
	
	public void addNewCourse(String departmentName, String courseName, 
			Date startCourse, Date endCourse, String description, String secondProf) {
		Singleton.getGreetingService().addCourseToDepartment(departmentName, courseName, startCourse, endCourse, description, secondProf , new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR adding new course: " + caught.getMessage() + ".");
			}

			@Override
			public void onSuccess(String result) {
				Window.alert("Tryed adding new course.\nResult: " + result);
			}
		});
	}
	
	public void deleteCourse(String departmentName, String courseName) {
		Singleton.getGreetingService().deleteCourseFromDB(departmentName,courseName, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR DELETING COURSE: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				Window.alert("TRYED DELETING COURSE:\n" + result);
			}
			
		});
	}
	
	
	
}
