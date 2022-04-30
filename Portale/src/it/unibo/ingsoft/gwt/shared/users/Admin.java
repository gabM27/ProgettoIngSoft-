package it.unibo.ingsoft.gwt.shared.users;

public class Admin extends User {
	// Variabili istanza e costanti
	private static final String ADMIN_USERNAME = "admin"; 
	private static final long serialVersionUID = 1L;
	
	// Costruttore
	public Admin() {
		super("admin", "admin");
	}
	
	// Ritorna l'username
	public static String getAdminUsername() { return ADMIN_USERNAME; }

}
