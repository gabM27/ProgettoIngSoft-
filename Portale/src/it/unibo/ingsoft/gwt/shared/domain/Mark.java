package it.unibo.ingsoft.gwt.shared.domain;

public class Mark {
	// Variabili istanza
	private final String studentID; // matricola dello studente studente
	private int grade; // valutazione: -1 --> non sostenuto; 18 <-> 30 superato; 0 <-> 17 non superato.
	
	// Costruttore
	public Mark(String studentId) {
		this.studentID = studentId;
	}
	
	// Getters
	public String getStudentID() { return this.studentID; } // ritorna la matricola dello studente associato al voto
	
	public int getGrade() { return this.grade; } // ritorna la valutazione del voto
	
	// Setters
	public void setGrade(int grade) { // modifica la valutazione del voto
		this.grade = grade;
	}

}
