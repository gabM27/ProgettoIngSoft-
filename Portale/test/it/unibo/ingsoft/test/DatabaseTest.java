package it.unibo.ingsoft.test;

import java.util.Date;

import static org.junit.Assert.*;
import org.junit.Test;

import it.unibo.ingsoft.gwt.server.GreetingServiceImpl;
import it.unibo.ingsoft.gwt.server.UniDB;

public class DatabaseTest {
	private static GreetingServiceImpl server = new GreetingServiceImpl();
	
	// Test per verificare l'operazione di inizializzazione dell'admin nel DB
	@Test
	public void adminInitializeTest() throws Exception {
		server.adminInitialize();
		assertTrue(UniDB.checkUserExists("admin"));
	}
	
	// Test per verificare la pulizia del database
	@Test
	public void clearDBTest() throws Exception {
		// Inserisco un nuovo utente nel DB
		UniDB.addUsers("email", "password", "student");
		server.addUserToDB("emailTEST", "passwordTEST", "student");
		// Verifico che l'utente sia stato aggiunto
		assertTrue(UniDB.checkUserExists("emailTEST"));
		// Pulisco e verifico che l'operazione di pulizia ritorni true
		assertTrue(UniDB.clearUniDB());
		// Verifico se esiste ancora l'utente inserito in precedenza
		assertFalse(UniDB.checkUserExists("emailTEST"));
		// Verifico che sia stato inizializzato l'admin come da clearDB operation.
		assertTrue(UniDB.checkUserExists("admin"));
	}
	
	
	// Test di verifica corretto inserimento nel DB
	@Test
	public void addUserTest() throws Exception {
		// adding new users
		server.addUserToDB("prova@studente.it","password","student");
		server.addUserToDB("prova@docente.it","password","professor");
		server.addUserToDB("prova@segreteria.it","password","secretary");
		server.addUserToDB("prova@errore.it","errore","errore");
		
		assertTrue(UniDB.checkUserExists("prova@studente.it"));
		assertTrue(UniDB.checkUserExists("prova@docente.it"));
		assertTrue(UniDB.checkUserExists("prova@segreteria.it"));
		assertTrue(UniDB.checkUserExists("prova@errore.it"));
	}
	
	@Test
	public void addPersonalInfoTest() throws Exception {
		// adding personal info
		server.addInfoToStudentAccount("prova@studente.it","matricola1234","username","nome","cognome",new Date());
		server.addInfoToProfessorAccount("prova@docente.it","username","nome","cognome",new Date());
		
		// Verifico che le informazioni siano state aggiunte correttamente (siano diverse da null)
		assertTrue(UniDB.checkPersonalInfoNotNull("prova@studente.it"));
		assertTrue(UniDB.checkPersonalInfoNotNull("prova@docente.it"));
	}
	
}