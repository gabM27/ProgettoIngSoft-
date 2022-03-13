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
	public AdminFacade() {
	}
	
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
	
	/* 
	 * Aggiunta o modifica delle informazioni personali di specifico studente o di uno specifico docente.
	 * Bisogna specificare email e username dello studente o docente 
	 * per il quale si vogliono inserire le informazioni personali.
	 * Bisogna specificare in quale lista cercare tramite il parametro in input typeAccount.
	 */
	protected void addPersonalInfo(String email, String username, 
			String name, String surname, Date birthday, String typeAccount) {
		switch (typeAccount.toLowerCase()) {
		case "professor":
			for (Professor p : Portale.uni.getProfessorList()) {
				if (p.getEmail().equalsIgnoreCase(email) && p.getUsername().equals(username)){
					p.setName(name);
					p.setSurname(surname);
					p.setBirthday(birthday);
					break;
				}
			}
			break;
		case "student":
			for (Student s : Portale.uni.getStudentList()) {
				if (s.getEmail().equalsIgnoreCase(email) && s.getUsername().equals(username)) {
					s.setName(name);
					s.setSurname(surname);
					s.setBirthday(birthday);
					break;
				}
			}
			break;
		}
	}
	
	/*
	 * Visualizzazione delle informazioni personali di uno specifico studente o di uno specifico docente.
	 * Bisogna specificare email e username dello studente o docente
	 * per il quale si vogliono visualizzare le informazioni personali.
	 * Bisogna specificare in quale lista cercare tramite il parametro in input typeAccount [professor / student].
	 * */
	protected String printPersonalInfo(String email, String username, String typeAccount) {
		String s = "";
		switch (typeAccount.toLowerCase()) {
		case "professor":
			for (Professor prof : Portale.uni.getProfessorList()) {
				if (prof.getEmail().equalsIgnoreCase(email) && prof.getUsername().equals(username)) {
					s = prof.toString();
				}
			}
			break;
		case "studente":
			for (Student stud : Portale.uni.getStudentList()) {
				if (stud.getEmail().equalsIgnoreCase(email) && stud.getUsername().equals(username)) {
					s = stud.toString();
				}
			}
			break;
		default:
			s = "Informazioni non trovate, riprova.";
			break;
		}
		
		return s;
	}
	
	/*
	 * TODO: Visualizzazione delle informazioni personali di tutti gli studenti o docenti.
	 * Bisogna specificare in input il typeAccount [professor / student]. 
	 * */
	
}
