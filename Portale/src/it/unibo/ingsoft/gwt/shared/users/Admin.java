package it.unibo.ingsoft.gwt.shared.users;

public class Admin extends User{
	// Variabili istanza e costanti
	private static final String ADMIN_USERNAME = "admin"; 
	private static final String ADMIN_PASSWORD = "admin";
	private String name;
	private String surname;
	
	// Costruttore
	public Admin(String email, String name, String surname) {
		super(email, ADMIN_USERNAME, ADMIN_PASSWORD);
		this.name = name;
		this.surname = surname;
	}

	// Getters
	public String getName() { return this.name; }

	public String getSurname() { return this.surname; }
	
}
