package it.unibo.ingsoft.gwt.shared.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Student extends User implements Serializable{
	// Variabili istanza
	private String iD; // Numero di matricola
	private String name; // Nome dello studente
	private String surname; // Cognome dello studente
	private Date birthday; // Data di nascita
	private ArrayList<String> courses; // Lista dei corsi ai quali lo studente è iscritto
	private ArrayList<String> exams; // Lista degli esami ai quali lo studente è iscritto
	
	private static final long serialVersionUID = 1L;
	
	// Costruttore
	public Student(String email, String password) {
		super(email,password);
		this.courses = new ArrayList<String>(20);
		this.exams = new ArrayList<String>(20);
	}

	// Getters
	public String getID() { return this.iD; } // Ritorna il numero di matricola dello studente
	
	public String getName() { return this.name; } // Ritorna il nome dello studente
	
	public String getSurname() { return this.surname; } // Ritorna il cognome dello studente
	
	public Date getBirthday() { return this.birthday; } // Ritorna la data di nascita dello studente
	
	public ArrayList<String> getCourses() { return this.courses; } // Ritorna la lista dei corsi a cui è iscritto lo studente
	
	public ArrayList<String> getExams() { return this.exams; } // Ritorna la lista degli esami a cui è iscritto lo studente
	
	// Setters
	public void setID(String newID) { // Modifica il numero di matricola dello studente
		this.iD = newID;
	}
	
	public void setName(String newName) { // Modifica il nome dello studente
		this.name = newName;
	}
	
	public void setSurname(String newSurname) { // Modifica il cognome dello studente
		this.surname = newSurname;
	}
	
	public void setBirthday(Date newBirthday) { // Modifica la data di nascita dello studente
		this.birthday = newBirthday;
	}
	
	// Aggiunge un corso alla lista
	public void addCourse(String newCourse) {
		if (!isCourseThere(newCourse)) {
			this.courses.add(newCourse);
		}
	}
	
	// Aggiunge un esame alla lista
	public void addExam(String newExam) {
		if (!isExamThere(newExam)) {
			this.exams.add(newExam);
		}
	}
	
	// Elimina un corso dalla lista
	public void deleteCourse(String c) {
		if (isCourseThere(c)) {
			this.courses.remove(c);
		}
	}
	
	// Elimina un esame dalla lista
	public void deleteExam(String e) {
		if (isExamThere(e)) {
			this.exams.remove(e);
		}
	}
	
	// Metodo che controlla se c'e' gia' un corso all'interno della lista
	private boolean isCourseThere(String c) {
		boolean isThere = false;
		for (int i = 0; i < this.courses.size(); i++) {
			if (c.equalsIgnoreCase(this.courses.get(i))) {
				isThere = true;
				break;
			} // else --> isThere = false
		}
		return isThere;
	}
	
	// Metodo che controlla se c'e' gia' un esame all'interno della lista
	private boolean isExamThere(String e) {
		boolean isThere = false;
		for (int i = 0; i < this.exams.size(); i++) {
			if (e.equalsIgnoreCase(this.exams.get(i))) {
				isThere = true;
				break;
			} // else --> isThere = false
		}
		return isThere;
	}

	@Override
	public String toString() { // Stampa le informazioni personali dello studente
		return "STUDENTE\n"
				+ "Nome: " + this.name + ".\n"
				+ "Cognome: " + this.surname + ".\n"
				+ "Username: " + this.getUsername() + ".\n"
				+ "Data di nascita: " + this.birthday + ".\n"
				+ "Matricola: " + this.iD + ".\n"
				+ "Email: " + this.getEmail() + ".\n"
				+ "Lista dei corsi ai quali lo studente e' iscritto: " + this.courses.toString() + ".\n"
				+ "Lista degli esami ai quali lo studente e' iscritto: " + this.exams.toString() + ".";
	}
}
