package it.unibo.ingsoft.gwt.shared.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class Department implements Serializable {
	
	// Variabili istanza
	private static final long serialVersionUID = 1L;
	private String name;
	private List<Course> courses;
	
	// Costruttore
	public Department(String name){
		this.name = name;
		this.courses = new Vector<Course>(15,5); // Primo parametro = capacita' iniziale; secondo paramtetro = capacita' incremento
	}
	
	// Getters
	public String getName() { return this.name; } // ritorna il nome del dipartimento
	
	public List<Course> getCoursesList() { return this.courses; } // ritorna la lista dei corsi disponibili 

	// Setters
	public void setName(String newName) { // modifica il nome del dipartimento
		this.name = newName;
	}
	
	// Aggiunge un corso al dipartimento
	public void addCourse(Course c) {
		this.courses.add(c);
	}
	
	// stampa tutti i corsi
	public String printCourses() {
		String x = "";
		for (Course c : this.courses) {
			x += c.toString()+"\n";
		}
		return x;
	}
}
