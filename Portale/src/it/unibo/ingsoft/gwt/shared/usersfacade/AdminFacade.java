package it.unibo.ingsoft.gwt.shared.usersfacade;

import java.util.Date;

import it.unibo.ingsoft.gwt.client.Portale;
import it.unibo.ingsoft.gwt.shared.users.Professor;
import it.unibo.ingsoft.gwt.shared.users.Secretary;
import it.unibo.ingsoft.gwt.shared.users.Student;

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
	
	// Costruttore
	public AdminFacade() {}
	
	// Creazione account Studente / Docente / Segreteria
	protected void addNewAccount(String email, String username, String password, String typeAccount){
		
		switch (typeAccount.toLowerCase()) {
		case "secretary":
			Portale.uni.addSecretary(new Secretary(email, username, password));
			break;
		case "student":
			Portale.uni.addStudent(new Student(email,username,password));
			break;
		case "professor":
			Portale.uni.addProfessor(new Professor(email,username,password));
			break;
		}
		
		// TODO: Operazioni di scrittura su db
		
	}
	
	// Aggiunta informazioni personali di uno Studente o di un Docente
	protected void addPersonalInfo(String email, String username, String password, String name, String surname, Date birthday, String typeAccount) {
		int index;
		switch (typeAccount.toLowerCase()) {
		case "professor":
			index = Portale.uni.getProfessorList().indexOf(new Professor(email,username,password));
			Portale.uni.getProfessorList().get(index).setName(name);
			Portale.uni.getProfessorList().get(index).setName(surname);
			Portale.uni.getProfessorList().get(index).setBirthday(birthday);
			break;
		case "student":
			index = Portale.uni.getStudentList().indexOf(new Student(email,username,password));
			Portale.uni.getStudentList().get(index).setName(name);
			Portale.uni.getStudentList().get(index).setName(surname);
			Portale.uni.getStudentList().get(index).setBirthday(birthday);
			break;
		}
	}
}
