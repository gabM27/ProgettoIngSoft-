package it.unibo.ingsoft.gwt.shared.usersfacade;

import java.util.Date;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.unibo.ingsoft.gwt.client.settings.ActualSession;
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
	
	public void addNewCourse(String departmentName, String courseName, String profEmail, 
			Date startCourse, Date endCourse, String description, String secondProf) {
		Singleton.getGreetingService().addCourseToDepartment(departmentName, courseName, profEmail, startCourse, endCourse, description, secondProf , new AsyncCallback<String>() {

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
	
	public void changeCourseInfo(String nomeCorso, Date dataInizio, Date dataFine,
			String descrizioneCorso, String codocente) {
		Singleton.getGreetingService().changeCourseFromDB(nomeCorso,ActualSession.getActualSession().getEmail(), dataInizio, dataFine, 
				descrizioneCorso, codocente, new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("ERROR UPDATING the course: " + caught.getMessage());
					}

					@Override
					public void onSuccess(String result) {
						Window.alert("TRYED UPDATING the course.\nResult: " + result);
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

	public void addNewExam(String nomeCorso, Date dataEsame, String orarioEsame, String durezza, String nomeAula) {
		Singleton.getGreetingService().addExam(nomeCorso,dataEsame,orarioEsame,durezza,nomeAula, 
				new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR ADDING NEW EXAM: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				Window.alert("TRYED ADDING NEW EXAM.\nRESULT: " + result);
			}
		});
	}

	public void updateExamInfo(String courseName, Date dataEsame, String orarioEsame, String durezzaEsame,
			String aulaSvolgimento) {
		Singleton.getGreetingService().updateExamInfo(courseName, dataEsame, orarioEsame, durezzaEsame,
				aulaSvolgimento, new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("ERROR UPDATING EXAM'S INFO: " + caught.getMessage());
					}

					@Override
					public void onSuccess(String result) {
						Window.alert("TRYED UPDATING EXAM'S INFO: " + result);
					}
			
		});
		
	}

	public void deleteExam(String courseName) {
		Singleton.getGreetingService().deleteExamFromDB(courseName, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR DELETING EXAM: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				Window.alert("TRYED DELETING EXAM.\nResult: " + result);
			}
			
		});
	}

	public void sendMark(String studentEmail, String courseName, Integer mark) {
		Singleton.getGreetingService().addMarkToStudentExam(studentEmail, courseName, mark, 
				new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("ERROR ADDING MARK TO STUDENT EXAM: " + caught.getMessage());
					}

					@Override
					public void onSuccess(String result) {
						Window.alert("TRYED ADDING MARK TO STUDENT EXAM.\nResult: " + result);
					}
			
		});
		
	}
	
}
