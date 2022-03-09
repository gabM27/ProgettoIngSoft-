package it.unibo.ingsoft.gwt.shared.users;

public class Student {
	private String name;
	private String surname;
	private String email;
	private String iD;
	
	// etc
	
	public Student(String email) {
		this.email = email;
	}

	public String getID() { return this.iD; }
	
	// Altre operazioni 
}
