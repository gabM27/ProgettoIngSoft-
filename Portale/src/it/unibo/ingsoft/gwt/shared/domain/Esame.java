package it.unibo.ingsoft.gwt.shared.domain;

import java.util.Date;

public class Esame {
	// Variabili istanza
	private String nomeEsame;
	private Date dataEsame;	
	
	// Costruttore
	public Esame(String nomeEsame, Date dataEsame) {
		this.nomeEsame = "Esame del corso di" + nomeEsame;
		this.dataEsame = dataEsame;
	}

	// Getters
	public String getNomeEsame() { return this.nomeEsame; } // ritorna il nome dell'esame
	
	public Date getDataEsame() { return this.dataEsame; } // ritorna la data dell'esame

	
	// Setters
	public void setNomeEsame(String newNome) { // modifica il nome dell'esame
		this.nomeEsame = newNome;
		
	}
	
	public void setDataEsame(Date newDataEsame) { // modifica la data dell'esame
		this.dataEsame = newDataEsame;
	}
	
	// toString --> stampa le informazioni dell'esame
	@Override
	public String toString() {
		return "Nome esame: " + this.nomeEsame + "\n"
				+ "Data esame: " + this.dataEsame;
	}
	
}
