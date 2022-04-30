package it.unibo.ingsoft.gwt.shared.domain;

import it.unibo.ingsoft.gwt.shared.users.Secretary;

public class University {
	// Variabili istanza
	private String athenaeumName; // Nome dell'ateneo
	private String city;// Nome della citta' in cui si trova l'ateneo
	private String telephonNumber; // Numero di telefono 
	private String address; // Indirizzo 
	private Secretary secretary; // Segreteria dell'universita'
	
	// Costruttore
	public University(String athenaeumName, String city,String number,String address) {
		this.athenaeumName = athenaeumName;
		this.city = city;
		this.telephonNumber=number;
		this.address=address;
		}
	
	// Getters
	public String getAthenaeumName() { return this.athenaeumName; } // ritorna il nome dell'ateneo
	
	public String getCity() { return this.city; } // ritorna il nome della citta' relativa all'università
	
	public String getTelephoneNumber() {return this.telephonNumber;}
	
	public String getAddress() {return this.address;}
	
	// Setters
	public void setAthenaeumName(String newName) { // modifica il nome dell'ateneo
		this.athenaeumName = newName;
	}

	public void setTelephoneNumber(String newNumber) { // modifica il numero di telefono dell'università
		this.telephonNumber = newNumber;
	}
	
	public void setAddress(String newAddress) { // modifica l'indirizzo dell'università
		this.address = newAddress;
	}
}
