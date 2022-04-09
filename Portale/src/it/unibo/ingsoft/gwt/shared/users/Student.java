package it.unibo.ingsoft.gwt.shared.users;

import java.io.Serializable;
import java.util.Date;

public class Student extends User implements Serializable{
	// Variabili istanza
	private String iD; // Numero di matricola
	private String name; // Nome dello studente
	private String surname; // Cognome dello studente
	private Date birthday; // Data di nascita

	// Costruttore
	public Student(String email, String password) {
		super(email,password);
	}

	// Getters
	public String getID() { return this.iD; } // Ritorna il numero di matricola dello studente
	
	public String getName() { return this.name; } // Ritorna il nome dello studente
	
	public String getSurname() { return this.surname; } // Ritorna il cognome dello studente
	
	public Date getBirthday() { return this.birthday; } // Ritorna la data di nascita dello studente
	
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
	
	@Override
	public String toString() { // Stampa le informazioni personali dello studente
		return "STUDENTE\n"
				+ "Nome: " + this.name + ".\n"
				+ "Cognome: " + this.surname + ".\n"
				+ "Username: " + this.getUsername() + ".\n"
				+ "Data di nascita: " + this.birthday + ".\n"
				+ "Matricola: " + this.iD + ".\n"
				+ "Email: " + this.getEmail() + ".";
	}
}
