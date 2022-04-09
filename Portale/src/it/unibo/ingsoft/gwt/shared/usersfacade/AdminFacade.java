package it.unibo.ingsoft.gwt.shared.usersfacade;

import java.util.Date;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.Singleton;

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
	
	// Aggiunta nuovo account (Studente / Docente / Segreteria)
	public void addNewAccount(String email, String pass, String typeAccount){
		// Chiamo il server per aggiungere il nuovo account al database
		Singleton.getGreetingService().addUserToDB(email,pass,typeAccount, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR: " + caught.getMessage());
			}
			@Override
			public void onSuccess(String result) {
				Window.alert("TRIED ADDING NEW " + typeAccount.toUpperCase() + ".\nResult: " + result);
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
	 * Bisogna specificare email dello studente o docente
	 * per il quale si vogliono visualizzare le informazioni personali.
	 * */
	public void printPersonalInfo(String email, Mainpage mainpage) {
		Singleton.getGreetingService().viewPersonalInfo(email, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				mainpage.openViewPersonalInfo(result);
			}
		});
		
	}
	
	/*
	 * TODO: Visualizzazione delle informazioni personali di tutti gli studenti o docenti.
	 * Bisogna specificare in input il typeAccount [professor / student]. 
	 * */

	/*
	 * Aggiunta di un dipartimento al portale 
	 */
	public void addNewDepartment(String nomeDipartimento) {
		// Chiamo il server per aggiungere il nuovo dipartimento
		Singleton.getGreetingService().addDepartmentToDB(nomeDipartimento, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR adding new department: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				Window.alert("TRIED ADDING NEW DEPARTMENT.\nRESULT: " + result);
			}
			
		});
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
	
	
}
