package it.unibo.ingsoft.gwt.shared.domain;

import java.util.List;
import java.util.Vector;

public class Università {
	// Variabili istanza
	private String nomeAteneo;
	private String città;
	private List<Dipartimento> dipartimenti;
	
	// Costruttore
	public Università(String nomeAteneo, String città) {
		this.nomeAteneo = nomeAteneo;
		this.città = città;
		this.dipartimenti = new Vector<Dipartimento>(8,4); // Primo parametro = capacità iniziale; secondo parametro = capacità incremento
	}
	
	// Getters
	public String nomeAteneo() { return this.nomeAteneo; } // ritorna il nome dell'ateneo
	
	public String città() { return this.città; } // ritorna il nome della città relativa all'università
	
	public List<Dipartimento> getListaDipartimenti() { return this.dipartimenti; } // ritorna la lista dei dipartimenti
	
	// Setters
	public void setNomeAteneo(String newNome) { // modifica il nome dell'ateneo
		this.nomeAteneo = newNome;
	}

	// Aggiunge un dipartimento alla lista
	public void aggiungiDipartimento(Dipartimento d) {
		this.dipartimenti.add(d);
	}
	
}
