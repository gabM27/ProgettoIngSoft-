package it.unibo.ingsoft.gwt.shared.users;

import java.io.Serializable;

public class User implements Serializable{
	// Variabili istanza
	private String email; // Email di un generico utente
	private String username; // Username di un generico utente
	private String password; // Password di un generico utente
	
	// Costruttore
	public User(String email) {
		this.email = email;
	}
	
	// Getters
	public String getEmail() { return this.email; } // Ritorna l'email dell'utente
	
	public String getUsername() { return this.username; } // Ritorna l'username dell'utente
	
	public String getPassword() { return this.password; } // Ritorna la password dell'utente
	
	// Setters
	// Non e' possibile modificare email e username una volta inseriti.
	
	public void setUsername(String newUsername) { // Modifica l'username di un utente
		this.username = newUsername;
	}
	
	public void setPassword(String newPassword) { // Modifica la password di un utente
		this.password = newPassword;
	}
	
}
