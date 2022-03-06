package it.unibo.ingsoft.gwt.shared;

public class Studente {
	private String nome;
	private String cognome;
	private String email;
	private String matricola;
	
	// etc
	
	public Studente(String email) {
		this.email = email;
	}

	public String getMatricola() { return this.matricola; }
	
	// Altre operazioni 
}
