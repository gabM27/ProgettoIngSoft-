package it.unibo.ingsoft.gwt.shared.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Professor extends User implements Serializable{
	// Variabili istanza
	private String name ="";
	private String surname = "";
	private Date birthday;
	private ArrayList<String> courses; // lista dei corsi tenuti dal docente

	private static final long serialVersionUID = 1L;

	// Costruttore
	public Professor(String email, String password) {
		super(email,password);
		this.courses = new ArrayList<String>();
	} 

	// Getters
	public String getName() { return this.name; } // Ritorna il nome del docente

	public String getSurname() { return this.surname; } // Ritorna il cognome del docente

	public Date getBirthday() { return this.birthday; } // Ritorna la data di nascita del docente

	public ArrayList<String> getCoursesList() { return this.courses; } // Ritorna la lista dei corsi tenuti dal docente

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

	// Aggiunge un corso alla lista dei corsi, se già non presente
	public void addCourse(String newCourse) {
		if (!isCourseThere(newCourse)) {
			this.courses.add(newCourse);
		}
	}

	// Elimina un corso dalla lista
	public void deleteCourse(String c) {
		if (isCourseThere(c)) {
			this.courses.remove(c);
		}
	}

	// Metodo che controlla se c'e' gia' un corso all'interno della lista
	public boolean isCourseThere(String c) {
		boolean isThere = false;
		for (int i = 0; i < this.courses.size(); i++) {
			if (c.equalsIgnoreCase(this.courses.get(i))) {
				isThere = true;
				break;
			} // else --> isThere = false
		}
		return isThere;
	}

	@Override
	public String toString() { // Stampa le informazioni personali del docente
		return "DOCENTE\n"
		+ "Nome: " + this.name + ".\n"
		+ "Cognome: " + this.surname + ".\n"
		+ "Username: " + this.getUsername() + ".\n"
		+ "Data di nascita: " + this.birthday + ".\n"
		+ "Email: " + this.getEmail() + ".\n"
		+ "Lista corsi tenuti dal docente: " + this.courses.toString() + ".";
	}


}
