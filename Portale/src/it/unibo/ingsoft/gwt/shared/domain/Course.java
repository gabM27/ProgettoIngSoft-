package it.unibo.ingsoft.gwt.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Course implements Serializable{
	// Variabili istanza
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Date start;
	private Date end;
	private String description;
	private Exam exam; // VINCOLO: e' possibile avere un UNICO esame per corso
	private String prof;
	private String secondProf = "";
	
	private ArrayList<String> students; // iscritti al corso --> sostengono esame; se non lo sostengono voto esame = NC (-1)
	private ArrayList<Integer> marks; // iscritti e voti hanno lo stesso indice per collegare uno studente al relativo voto
	
	// Costruttore
	public Course(String name) {
		this.name = name;
		this.students = new ArrayList<String>(100); 
		this.marks = new ArrayList<Integer>(100); 
	}
	
	// Getters 
	public String getName() { return this.name; } // ritorna il nome del corso
	
	public ArrayList<String> getStudentsList() { return this.students; } // ritorna la lista degli iscritti
	
	public ArrayList<Integer> getMarks() { return this.marks; } // ritorna la lista intera di voti
	
	public Date getStartDate() { return this.start; } // ritorna la data di inizio del corso
	
	public Date getEndDate() { return this.end; } // ritorna la data di fine del corso
	
	public String getDescription() { return this.description; } // ritorna la descrizione del corso
	
	public Exam getExam() { return this.exam; } // ritorna l'oggetto esame presente nel corso
	
	public String getProf() { return this.prof; } // ritorna il docente del corso
	
	public String getSecondProf() { return this.secondProf; } // ritorna il co-docente del corso (se esiste), senn� ritorna stringa vuota
	
	// Setters
	public void setNome(String newName) { // modifica il nome del corso
		this.name = newName;
	}
	
	// Creazione esame (operazione consentita solo a un utente di tipo Docente)
	public void examSetting(Date examDate) {
		this.exam = new Exam(this.name, examDate);
	}
	
	public void setStartDate(Date start) {
		this.start = start;
	}

	public void setEndDate(Date end) {
		this.end = end;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setProf(String newProf) { // modifica il docente principale del corso
		this.prof = newProf;
	}
	
	public void setSecondProf(String newSecondProf) { // modifica il co-docente del corso
		this.secondProf = newSecondProf;
	}
	
	// Aggiunge un iscritto (ed un voto) al corso
	public void addStudent(String s) {
		this.students.add(s);
	}
	
	@Override
	public String toString() {
		return "Nome del corso: " + this.name + ".\n"
				+ "Descrizione del corso: " + this.description + ".\n"
				+ "Data inizio corso: " + this.start + ".\n"
				+ "Data fine corso: " + this.end + ".\n"
				+ "Numero studenti iscritti: " + this.students.size() + ".\n"
				+ "Docente principale: " + this.prof +".\n"
				+ "Co-docente: " + this.secondProf + "\n";
	}

	public String printExamInfo() { // stampa le info dell'esame
		return this.exam.toString() + "\n"
				+ "Numero studenti iscritti: " + this.students.size();
	}
	
	// Metodo che controlla se c'e' gia' uno studente all'interno della lista studenti
	public boolean is(String s) {
		boolean isThere = false;
		for (int i = 0; i < this.students.size(); i++) {
			if (s.equalsIgnoreCase(this.students.get(i))) {
				isThere = true;
				break;
			} // else --> isThere = false
		}
		return isThere;
	}

}
