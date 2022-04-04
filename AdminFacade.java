package it.unibo.ingsoft.gwt.shared.usersfacade;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.unibo.ingsoft.gwt.client.settings.GreetingService;
import it.unibo.ingsoft.gwt.client.settings.GreetingServiceAsync;
import it.unibo.ingsoft.gwt.client.settings.Singleton;
import it.unibo.ingsoft.gwt.shared.users.Professor;
import it.unibo.ingsoft.gwt.shared.users.Secretary;
import it.unibo.ingsoft.gwt.shared.users.Student;
import it.unibo.ingsoft.gwt.shared.users.User;

/*
 * Classe facciata utilizzata dall'amministratore 
 * del portale per interagire con le operazioni
 * a lui consentite, senza dipendere direttamente 
 * dalle classi di basso livello che processano le 
 * operazioni stesse.
 * 
 * Un amministratore puo':
 * 1. Creare account per studenti / docenti / 
 * 	  segreteria utilizzando il loro indirizzo email.
 * 
 * 2. Aggiungere / Modificare/ Visualizzare le informazioni 
 * 	  personali degli studenti e dei docenti.
 *
 */

public class AdminFacade {
	private static AdminFacade adminFacade;
	
	public static synchronized AdminFacade getAdminFacade() {
		if (adminFacade == null) {
			adminFacade = new AdminFacade();
		}
		return adminFacade;
	}
	
	// Creazione nuovo account (Studente / Docente / Segreteria)
	public void addNewAccount(String email, String pass, String typeAccount){
		// Chiamo il server per aggiungere il nuovo account al database
		Singleton.getGreetingService().addUserToDB(email,pass,typeAccount, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR ADD_ACCOUNT_HANDLER: " + caught.getMessage());
			}
			@Override
			public void onSuccess(String result) {
				Window.alert("ADDED (IF ABSENT) NEW " + typeAccount.toUpperCase() + ": " + result);
			}
			
		});
	}

	public void cleaningDB() {
		Singleton.getGreetingService().clearDB(new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR CLEANING THE DATABASE: " + caught.getMessage() + ".");
			}
			@Override
			public void onSuccess(String result) {
				Window.alert(result);
			}
		});
	}
		
	
	
	/* 
	 * Aggiunta o modifica delle informazioni personali di specifico studente o di uno specifico docente.
	 * Bisogna specificare email e username dello studente o docente 
	 * per il quale si vogliono inserire le informazioni personali.
	 */
	public void addInfoToStudent(String email, String iD, String username, String name, String surname, Date birthday) {
		Singleton.getGreetingService().addInfoToStudentAccount(email,iD,username,name,surname,birthday,new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR ADDING PERSONAL INFO TO STUDENT: " + caught.getMessage());
			}
			@Override
			public void onSuccess(String result) {
				Window.alert(result);
			}
		});
	}
	
	public void addInfoToProfessor(String email, String username, String name, String surname, Date birthday) {
		Singleton.getGreetingService().addInfoToProfessorAccount(email,username,name,surname,birthday,new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR ADDING PERSONAL INFO TO PROFESSOR: " + caught.getMessage());
			}
			@Override
			public void onSuccess(String result) {
				Window.alert(result);
			}
		});
	}
	
	/*
	 * Visualizzazione delle informazioni personali di uno specifico studente o di uno specifico docente.
	 * Bisogna specificare email e username dello studente o docente
	 * per il quale si vogliono visualizzare le informazioni personali.
	 * Bisogna specificare in quale lista cercare tramite il parametro in input typeAccount [professor / student].
	 * */
//	protected String printPersonalInfo(String email, String username, String typeAccount) {
//		String s = "";
//		switch (typeAccount.toLowerCase()) {
//		case "professor":
//			for (Professor prof : Portale.uni.getProfessorList()) {
//				if (prof.getEmail().equalsIgnoreCase(email) && prof.getUsername().equals(username)) {
//					s = prof.toString();
//				}
//			}
//			break;
//		case "studente":
//			for (Student stud : Portale.uni.getStudentList()) {
//				if (stud.getEmail().equalsIgnoreCase(email) && stud.getUsername().equals(username)) {
//					s = stud.toString();
//				}
//			}
//			break;
//		default:
//			s = "Informazioni non trovate, riprova.";
//			break;
//		}
//		
//		return s;
//	}
	
	/*
	 * TODO: Visualizzazione delle informazioni personali di tutti gli studenti o docenti.
	 * Bisogna specificare in input il typeAccount [professor / student]. 
	 * */
	
}
