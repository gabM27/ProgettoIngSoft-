package it.unibo.ingsoft.gwt.shared.domain;

import java.util.List;
import java.util.Vector;

public class Dipartimento {
	// Variabili istanza
	private String nome;
	private String indirizzoSede;
	private String numeroTelefono;
	private List<Corso> corsi;
	
	// Costruttore
	public Dipartimento(String nome, String indirizzoSede, String numeroTelefono){
		this.nome = nome;
		this.indirizzoSede = indirizzoSede;
		this.numeroTelefono = numeroTelefono;
		this.corsi = new Vector<Corso>(15,5); // Primo parametro = capacità iniziale; secondo paramtetro = capacità incremento
	}
	
	// Getters
	public String getNome() { return this.nome; } // ritorna il nome del dipartimento
	
	public String getIndirizzoSede() { return this.indirizzoSede; } // ritorna l'indirizzo della sede del dipartimento

	public String getNumeroTelefono() { return this.numeroTelefono; } // ritorna il numero di telefono del dipartimento
	
	public List<Corso> getListaCorsi() { return this.corsi; } // ritorna la lista dei corsi disponibili 

	// Setters
	public void setNome(String newNome) { // modifica il nome del dipartimento
		this.nome = newNome;
	}
	
	public void setIndirizzoSede(String newIndirizzo) { // modifica l'indirizzo del dipartimento
		this.indirizzoSede = newIndirizzo;
	}
	
	public void setNumeroTelefono(String numeroTelefono) { // modifica il numero telefonico del dipartimento
		this.numeroTelefono = numeroTelefono;
	}
	
	// Aggiunge un corso al dipartimento
	public void aggiungiCorso(Corso c) {
		this.corsi.add(c);
	}
}
