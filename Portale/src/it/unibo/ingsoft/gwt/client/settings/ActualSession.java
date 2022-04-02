package it.unibo.ingsoft.gwt.client.settings;

import it.unibo.ingsoft.gwt.shared.Status;

public class ActualSession {
	// Variabili istanza
	private String email;
	private Status actualStatus;
	private static ActualSession actualSession;
	
	private ActualSession(String email, Status newStatus) {
		this.setEmail(email);
		this.setActualStatus(newStatus);
	}

	public static synchronized ActualSession getActualSession() {
		if (actualSession == null) {
			actualSession = new ActualSession("EMAIL_BASE", Status.NOT_REGISTERED);
		}
		return actualSession;
	}

	public String getEmail() { return email; }

	public Status getActualStatus() { return actualStatus; } 
	
	public void setActualSession(String newEmail, Status newStatus) {
		setEmail(newEmail);
		setActualStatus(newStatus);
	}
	
	private void setEmail(String email) {
		this.email = email;
	}

	private void setActualStatus(Status actualStatus) {
		this.actualStatus = actualStatus;
	}
	
	
}
