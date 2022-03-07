package it.unibo.ingsoft.gwt.shared.domain;

public class Voto {
	// Variabili istanza
	private final String matricolaStudente;
	private int valutazione; // -1 --> non sostenuto; 18 <-> 30 superato; 0 <-> 17 non superato.
	
	// Costruttore
	public Voto(String matricolaStudente) {
		this.matricolaStudente = matricolaStudente;
	}
	
	
	// Getters
	public String getMatricolaStudente() { return this.matricolaStudente; } // ritorna la matricola dello studente associato al voto
	
	public int getValutazione() { return this.valutazione; } // ritorna la valutazione del voto
	
	// Setters
	public void setValutazione(int valutazione) { // modifica la valutazione del voto
		this.valutazione = valutazione;
	}

}
