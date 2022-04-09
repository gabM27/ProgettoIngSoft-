package it.unibo.ingsoft.gwt.shared.domain;

import java.util.Date;

public class Exam {
	// Variabili istanza
	private String examName;
	private Date examDate;	
	
	// Costruttore
	public Exam(String examName, Date examDate) {
		this.examName = "Esame del corso di" + examName;
		this.examDate = examDate;
	}

	// Getters
	public String getExamName() { return this.examName; } // ritorna il nome dell'esame
	
	public Date getExamDate() { return this.examDate; } // ritorna la data dell'esame

	
	// Setters
	public void setExamName(String newName) { // modifica il nome dell'esame
		this.examName = newName;
		
	}
	
	public void setExamDate(Date newExamName) { // modifica la data dell'esame
		this.examDate = newExamName;
	}
	
	// toString --> stampa le informazioni dell'esame
	@Override
	public String toString() {
		return "Nome esame: " + this.examName + "\n"
				+ "Data esame: " + this.examDate;
	}
	
}
