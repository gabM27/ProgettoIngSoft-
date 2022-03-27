package it.unibo.ingsoft.gwt.shared.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class Department implements Serializable {
	
	// Variabili istanza
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String telephoneNumber;
	private List<Course> courses;
	
	// Costruttore
	public Department(String name, String address, String telephoneNumber){
		this.name = name;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
		this.courses = new Vector<Course>(15,5); // Primo parametro = capacita' iniziale; secondo paramtetro = capacita' incremento
	}
	
	// Getters
	public String getName() { return this.name; } // ritorna il nome del dipartimento
	
	public String getAddress() { return this.address; } // ritorna l'indirizzo della sede del dipartimento

	public String getTelephoneNumber() { return this.telephoneNumber; } // ritorna il numero di telefono del dipartimento
	
	public List<Course> getCoursesList() { return this.courses; } // ritorna la lista dei corsi disponibili 

	// Setters
	public void setName(String newName) { // modifica il nome del dipartimento
		this.name = newName;
	}
	
	public void setAddress(String newAddress) { // modifica l'indirizzo del dipartimento
		this.address = newAddress;
	}
	
	public void setTelephoneNumber(String newTelephoneNumber) { // modifica il numero telefonico del dipartimento
		this.telephoneNumber = newTelephoneNumber;
	}
	
	// Aggiunge un corso al dipartimento
	public void addCourse(Course c) {
		this.courses.add(c);
	}
}
