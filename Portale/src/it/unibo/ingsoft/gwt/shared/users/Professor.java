package it.unibo.ingsoft.gwt.shared.users;

import java.io.Serializable;
import java.util.Date;

public class Professor extends User implements Serializable{
	// Variabili istanza
	private String name;
	private String surname;
	private Date birthday;
	private static final long serialVersionUID = 1L;
	
	// Costruttore
	public Professor(String email, String password) {
		super(email,password);
	}

	// Getters
	public String getName() { return this.name; } // Ritorna il nome del docente
	
	public String getSurname() { return this.surname; } // Ritorna il cognome del docente
	
	public Date getBirthday() { return this.birthday; } // Ritorna la data di nascita del docente
	
	// Setters
	public void setName(String newName) { // Modifica il nome del docente
		this.name = newName;
	}

	public void setSurname(String newSurname) { // Modifica il cognome del docente
		this.surname = newSurname;
	}
	
	public void setBirthday(Date newBirthday) { // Modifica la data di nascita del docente
		this.birthday = newBirthday;
	}
	
	@Override
	public String toString() { // Stampa le informazioni personali del docente
		return "DOCENTE\n"
				+ "Nome: " + this.name + ".\n"
				+ "Cognome: " + this.surname + ".\n"
				+ "Username: " + this.getUsername() + ".\n"
				+ "Data di nascita: " + this.birthday + ".\n"
				+ "Email: " + this.getEmail() + ".";
	}
	
	
}
