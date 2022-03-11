package it.unibo.ingsoft.gwt.shared.users;

public class User {
	// Variabili istanza
	private String email; // Email di un generico utente
	private String username; // Username di un generico utente
	private String password; // Password di un generico utente
	
	// Costruttore
	public User(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	// Getters
	public String getEmail() { return this.email; } // Ritorna l'email dell'utente
	
	public String getUsername() { return this.username; } // Ritorna l'username dell'utente
	
	public String getPassword() { return this.password; } // Ritorna la password dell'utente
	
	// Setters
	// Non e' possibile modificare email e username una volta inseriti.
	 
	public void setPassword(String newPassword) { // Modifica la passwotd di un utente
		// Per poter effettuare questa operazione bisogna necessariamente inserire la vecchia password
		this.password = newPassword;
	}
	
}
