package it.unibo.ingsoft.gwt.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;


public class Department implements Serializable {
	
	// Variabili istanza
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<String> courses = new ArrayList<String>(15);
	
	// Costruttore
	public Department(String name){
		this.name = name;
	}
	// Getters
	public String getName() { return this.name; } // ritorna il nome del dipartimento
	
	public ArrayList<String> getCoursesList() { return this.courses; } // ritorna la lista dei corsi disponibili 

	// Setters
	public void setName(String newName) { // modifica il nome del dipartimento
		this.name = newName;
	}
	
	// Aggiunge un corso al dipartimento
	public void addCourse(String c) {
		if (!isCourseThere(c)) {
			this.courses.add(c);
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
	public String toString() {
		return "NOME DIPARTIMENTO: " + this.name + ".\n"
				+ "lista corsi: " + this.courses ;
		
	}
}
