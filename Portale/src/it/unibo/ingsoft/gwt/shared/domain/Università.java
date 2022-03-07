package it.unibo.ingsoft.gwt.shared.domain;

import java.util.List;
import java.util.Vector;

public class Universit� {
	// Variabili istanza
	private String nomeAteneo;
	private String citt�;
	private List<Dipartimento> dipartimenti;
	
	// Costruttore
	public Universit�(String nomeAteneo, String citt�) {
		this.nomeAteneo = nomeAteneo;
		this.citt� = citt�;
		this.dipartimenti = new Vector<Dipartimento>(8,4); // Primo parametro = capacit� iniziale; secondo parametro = capacit� incremento
	}
	
	// Getters
	public String nomeAteneo() { return this.nomeAteneo; } // ritorna il nome dell'ateneo
	
	public String citt�() { return this.citt�; } // ritorna il nome della citt� relativa all'universit�
	
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
