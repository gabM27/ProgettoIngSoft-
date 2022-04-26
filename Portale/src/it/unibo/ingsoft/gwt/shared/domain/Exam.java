package it.unibo.ingsoft.gwt.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class Exam implements Serializable{
	// Variabili istanza
	private static final long serialVersionUID = 1L;
	private String examName;
	private Date examDate;	
	private String orario;
	private String livelloDurezza;
	private String nomeAula;
	
	// Costruttore
	public Exam(String courseName, Date examDate) {
		this.examName = "Esame del corso di " + courseName;
		this.examDate = examDate;
	}

	// Getters
	public String getExamName() { return this.examName; } // ritorna il nome dell'esame
	
	public Date getExamDate() { return this.examDate; } // ritorna la data dell'esame

	public String getOrario() { return this.orario; } // ritorna l'orario dell'esame
	
	public String getLivelloDurezza() { return this.livelloDurezza; } // ritorna il livello durezza dell'esame
	
	public String getNomeAula() { return this.nomeAula; } // ritorna il nome dell'aula
	
	// Setters
	public void setExamName(String newName) { // modifica il nome dell'esame
		this.examName = newName;
		
	}
	
	public void setExamDate(Date newExamName) { // modifica la data dell'esame
		this.examDate = newExamName;
	}
	
	public void setOrario(String newOrario) { // modifica l'orario dell'esame
		this.orario = newOrario;
	}
	
	public void setLivelloDurezza(String newLivello) { // modifica il livello durezza dell'esame
		this.livelloDurezza = newLivello;
	}
	
	public void setNomeAula(String newAula) { // modifica il nome dell'aula dell'esame
		this.nomeAula = newAula;
	}
	
	// toString --> stampa le informazioni dell'esame
	@Override
	public String toString() {
		return "Nome esame: " + this.examName + "\n"
				+ "Data esame: " + this.examDate + ".\n"
				+ "Orario esame: " + this.orario + ".\n"
				+ "Livello difficolta' esame: " + this.livelloDurezza + ".\n"
				+ "Aula svolgimento esame: " + this.nomeAula;
	}
	
}
