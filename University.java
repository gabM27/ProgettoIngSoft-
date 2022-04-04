package it.unibo.ingsoft.gwt.shared.domain;

import java.util.List;
import java.util.Vector;

import org.mapdb.DB;
import org.mapdb.HTreeMap;

import it.unibo.ingsoft.gwt.server.UniDB;
import it.unibo.ingsoft.gwt.shared.users.Professor;
import it.unibo.ingsoft.gwt.shared.users.Secretary;
import it.unibo.ingsoft.gwt.shared.users.Student;
import it.unibo.ingsoft.gwt.shared.users.User;

public class University {
	// Variabili istanza
	private String athenaeumName; // Nome dell'ateneo
	private String city; // Nome della citta' in cui si trova l'ateneo
	private Secretary secretary; // Segreteria dell'universita'
	
//	private List<Department> departments; // Lista dei dipartimenti dell'ateneo
//	private List<Professor> professors; // Lista dei professori dell'ateneo
//	private List<Student> students;	// Lista degli studenti dell'ateneo
	
	// Costruttore
	public University(String athenaeumName, String city) {
		this.athenaeumName = athenaeumName;
		this.city = city;
		// TODO: createDB();
//		this.departments = new Vector<Department>(8,4); // Primo parametro = capacita' iniziale; secondo parametro = capacita' incremento
//		this.professors = new Vector<Professor>(120,15); // Primo parametro = capacita' iniziale; secondo parametro = capacita' incremento
//		this.students = new Vector<Student>(200,50); // Primo parametro = capacita' iniziale; secondo parametro = capacita' incremento
	}
	
	// Getters
	public String getAthenaeumName() { return this.athenaeumName; } // ritorna il nome dell'ateneo
	
	public String getCity() { return this.city; } // ritorna il nome della citta' relativa all'università
	
	
//	public List<Department> getDepartmentsList() { return this.departments; } // ritorna la lista dei dipartimenti

//	public List<Professor> getProfessorList() { return this.professors; } // ritorna la lista dei professori

//	public List<Student> getStudentList() { return this.students; } // ritorna la lista degli studenti
	
	// Setters
	public void setAthenaeumName(String newName) { // modifica il nome dell'ateneo
		this.athenaeumName = newName;
	}

	// Istanzia la segreteria dell'universita'
//	public boolean addSecretary(Secretary sec) {
//		if (this.secretary == null) {
//			this.secretary = sec;
//			return true; // Inserimento avvenuto
//		} else {
//			return false; // Istanza della variabile gia' inizializzata
//		}
//	}
	
//	// Aggiunge un dipartimento alla lista dei dipartimenti
//	public void addDepartment(Department d) {
//		this.departments.add(d);
//		// aggiunta a mapdb 
//	}
	
	/*
	 * Aggiunge un professore alla lista dei professori
	 * Prima verifica se email o username sono gia'presenti
	 */
//TODO:	public void addUser(User s) {
//		if (!this.isAlreadyThere(s.getEmail())) {
//			// TODO: this.uniDB.addUsers(s);
//		}
//	}
	
//	/*
//	 * Aggiunge uno studente alla lista dei studenti
//	 * Prima verifica se email o username sono gia'presenti
//	 */
//	public void addStudent(Student s) {
//		if (!this.isAlreadyThere(s.getEmail(), s.getUsername(),"student")) {
//			this.students.add(s);
//		}
//	}

	/*
	 * Controllo che un utente esiste nella lista degli studenti o dei professori del DB
	 * 
	 */
//	public boolean isAlreadyThere(String email) {
//		boolean isThere = false;
//		//TODO: HTreeMap<String, User> tmp = this.uniDB.getUsersMap();
//		for (String key : tmp.keySet()) {
//			if (email.equalsIgnoreCase(key)) {
//				isThere = true;
//				break;
//			}
//		}
//		
//		return isThere;
//	}
//	
}
