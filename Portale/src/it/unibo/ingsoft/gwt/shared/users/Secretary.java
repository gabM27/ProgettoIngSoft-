package it.unibo.ingsoft.gwt.shared.users;

import java.io.Serializable;

public class Secretary extends User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	// Costruttore
	public Secretary(String email, String password) {
		super(email,password);
	}
	
	
	
}
