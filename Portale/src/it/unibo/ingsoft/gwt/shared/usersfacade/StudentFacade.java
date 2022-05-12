package it.unibo.ingsoft.gwt.shared.usersfacade;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.client.settings.Singleton;

/*
 * Classe facciata utilizzata da uno studente 
 * per interagire con le operazioni
 * a lui consentite, senza dipendere direttamente 
 * dalle classi di basso livello che processano le 
 * operazioni stesse.
 * 
 * Uno studente puo':
 * 1. Visualizzare i corsi disponibili.
 * 
 * 2. Iscriversi ad un corso.
 * 
 * 3. Registrarsi per un esame di un corso.
 * 
 * 4. Visualizzare le proprie informazioni personali.
 * 
 * 5. Visualizzare i voti degli esami svolti.
 * 
 */

public class StudentFacade {
	// Variabili istanza
	private static StudentFacade generalUserFacade;

	public static synchronized StudentFacade getStudentFacade() {
		if (generalUserFacade == null) {
			generalUserFacade = new StudentFacade();
		}
		return generalUserFacade;
	}

	public void courseRegistration(String courseName) {
		Singleton.getGreetingService().signUpStudentToACourse(courseName,
				ActualSession.getActualSession().getEmail(),
				new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR signing-up to the course: "+ caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				Window.alert("TRYED signing-up to the course.\nResult: " + result);
			}

		});
	}

	public void deleteCourseRegistration(String courseName) {
		Singleton.getGreetingService().deleteStudentCourseRegistration(courseName,
				ActualSession.getActualSession().getEmail(),
				new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR deleting registration from the course: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				Window.alert("TRYED deleting course registration from the course.\nResult: " + result);
			}

		});
	}

	public void examRegistration(String courseName) {
		Singleton.getGreetingService().signUpStudentToAnExam(courseName, 
				ActualSession.getActualSession().getEmail(),
				new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR SIGNING UP TO THE COURSE EXAM: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				Window.alert("TRYED signing up to the course exam.\nResult: " + result);
			}

		});
	}

	public void deleteExamRegitration(String examName) {
		Singleton.getGreetingService().deleteStudentExamRegistration(examName,
				ActualSession.getActualSession().getEmail(),
				new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR deleting registration from the exam: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				Window.alert("TRYED deleting exam registration from the exam.\nResult: " + result);
			}

		});
	}

	public void viewExamsMarksInfo(Mainpage main) {
		Singleton.getGreetingService().viewExamsMarks(ActualSession.getActualSession().getEmail(),
				new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR PRINTING EXAMS' MARKS INFO: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				Window.alert("Printing exams' marks info...");
				if (result.equals("")) {
					main.openViewExamMarksDashboard("NESSUN VOTO DISPONIBILE.");
				} else {
					main.openViewExamMarksDashboard(result);	
				}
			}
		});
	}

}
