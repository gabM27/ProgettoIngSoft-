package it.unibo.ingsoft.gwt.shared.domain;

import java.util.List;
import java.util.Vector;

public class University {
	// Variabili istanza
	private String athenaeumName;
	private String city;
	private List<Department> departments;
	
	// Costruttore
	public University(String athenaeumName, String city) {
		this.athenaeumName = athenaeumName;
		this.city = city;
		this.departments = new Vector<Department>(8,4); // Primo parametro = capacita' iniziale; secondo parametro = capacita' incremento
	}
	
	// Getters
	public String getAthenaeumName() { return this.athenaeumName; } // ritorna il nome dell'ateneo
	
	public String getCity() { return this.city; } // ritorna il nome della citta' relativa all'università
	
	public List<Department> getDepartmentsList() { return this.departments; } // ritorna la lista dei dipartimenti
	
	// Setters
	public void setAthenaeumName(String newName) { // modifica il nome dell'ateneo
		this.athenaeumName = newName;
	}

	// Aggiunge un dipartimento alla lista
	public void addDepartment(Department d) {
		this.departments.add(d);
	}
	
}
