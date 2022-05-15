package it.unibo.ingsoft.gwt.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Course implements Serializable{
	// Variabili istanza
	private static final long serialVersionUID = 1L;
	
	private String name = "";
	private Date start;
	private Date end;
	private String description = "";
	private Exam exam; // VINCOLO: e' possibile avere un UNICO esame per corso
	private String prof = "";
	private String secondProf = "";
	
	private boolean areGradesVisibleToStudents = false; // booleano che, se true, permette agli studenti di visualizzare i voti, no altrimenti. Viene modificato dalla segreteria
	private ArrayList<String> students; // iscritti al corso
	private ArrayList<Integer> examStudents; // iscritti all'esame del corso con relativo voto --> value default non sostenuto -1; esame superato 18-31 (31 == 30L) 
	/* 
	 * Le due liste presentano gli studenti inseriti nel medesimo metodo
	 * in modo da avere lo stesso indice per poter recuperare 
	 * i voti degli esami a partire dall'indice del relativo studente nell'altra lista.
	 */
	
	
	// Costruttore
	public Course(String name) {
		this.name = name;
		this.students = new ArrayList<String>(100); 
		this.examStudents = new ArrayList<Integer>();
	}
	
	// Getters 
	public String getName() { return this.name; } // ritorna il nome del corso
	
	public ArrayList<String> getStudentsList() { return this.students; } // ritorna la lista degli iscritti
	
	public ArrayList<Integer> getExamStudentsMarks() { return this.examStudents; } // ritorna la la lista dei voti degli studenti iscritti all'esame 
	
	public Date getStartDate() { return this.start; } // ritorna la data di inizio del corso
	
	public Date getEndDate() { return this.end; } // ritorna la data di fine del corso
	
	public String getDescription() { return this.description; } // ritorna la descrizione del corso
	
	public Exam getExam() { return this.exam; } // ritorna l'oggetto esame presente nel corso
	
	public String getProf() { return this.prof; } // ritorna il docente del corso
	
	public String getSecondProf() { return this.secondProf; } // ritorna il co-docente del corso (se esiste), senn� ritorna stringa vuota
	
	public boolean getAreGradesVisibleToStudents() { return this.areGradesVisibleToStudents; }
	
	// Setters
	public void setNome(String newName) { // modifica il nome del corso
		this.name = newName;
	}
	
	// Creazione esame (operazione consentita solo a un utente di tipo Docente)
	public void examSetting(Date examDate) {
		this.exam = new Exam(this.name, examDate);
	}
	
	// Cancellazione dell'esame --> null value
	public void examDeleting() {
		this.exam = null;
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
	
	// Aggiunge un iscritto al corso
	public void addStudent(String s) {
		if (!isStudentThere(s)) {
			this.students.add(s);
			this.examStudents.add(-1); // valore default --> corrisponde a non sostenuto
		}
	}
	
	// Elimina uno studente dalla lista degli iscritti al corso e dalla lista degli iscritti all'esame (se presente)
	public void deleteStudent(String stu) {
		if (isStudentThere(stu)) {
			this.examStudents.remove(this.students.indexOf(stu));
			this.students.remove(stu);
		}
	}

	// Modifica il voto relativo all'esame di uno studente
	public void setMark(String stu, Integer mark) {
		int index = this.students.indexOf(stu);
		this.examStudents.set(index, mark);
	}
	
	public void setAreGradesVisibleToStudents(boolean newVisibility) { // modifica la visibilità dei voti agli studenti. Può essere utilizzato solo da un account di tipo segreteria.
		this.areGradesVisibleToStudents = newVisibility;
	}
	
	@Override
	public String toString() {
		if (this.exam != null) {
		return "Nome del corso: " + this.name + ".\n"
				+ "Descrizione del corso: " + this.description + ".\n"
				+ "Data inizio corso: " + this.start + ".\n"
				+ "Data fine corso: " + this.end + ".\n"
				+ "Esame: " + this.exam.toString() + ".\n"
				+ "Numero studenti iscritti al corso: " + this.students.size() + ".\n"
				+ "Docente principale: " + this.prof +".\n"
				+ "Co-docente: " + this.secondProf + "\n";
		} else {
			return "Nome del corso: " + this.name + ".\n"
					+ "Descrizione del corso: " + this.description + ".\n"
					+ "Data inizio corso: " + this.start + ".\n"
					+ "Data fine corso: " + this.end + ".\n"
					+ "Esame NON ANCORA INSERITO.\n"
					+ "Numero studenti iscritti al corso: " + this.students.size() + ".\n"
					+ "Docente principale: " + this.prof +".\n"
					+ "Co-docente: " + this.secondProf + "\n";
		}
	}
	
	// Metodo che controlla se c'e' gia' uno studente all'interno della lista studenti iscritti al corso
	public boolean isStudentThere(String s) {
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
