package it.unibo.ingsoft.gwt.shared.users;

import java.util.Date;

public class Professor extends User{
	private String name;
	private String surname;
	private Date birthday;
	
	public Professor(String email, String username, String password) {
		super(email, username, password);
	}

	// Getters
	public String getName() { return this.name; } // ritorna il nome del docente
	
	public String getSurname() { return this.surname; } // ritorna il cognome del docente
	
	public Daate getBirthday() { return this.birthday; } // ritorna la data di nascita del docente
	
	// Setters
	public void setName(String newName) { // modifica il nome del docente
		this.name = newName;
	}

	public void setSurname(String newSurname) { // modifica il cognome del docente
		this.surname = newSurname;
	}
	
	public void setBirthday(Date newBirthday) { // modifica la data di nascita del docente
		this.birthday = newBirthday;
	}
	
	
	// Altre operazioni
	
	
}
