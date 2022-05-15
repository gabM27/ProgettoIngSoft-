package it.unibo.ingsoft.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import it.unibo.ingsoft.gwt.client.settings.GreetingService;
import it.unibo.ingsoft.gwt.server.GreetingServiceImpl;
import it.unibo.ingsoft.gwt.server.UniDB;
import it.unibo.ingsoft.gwt.shared.Status;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

@TestMethodOrder(OrderAnnotation.class)
public class DatabaseTest {

	static GreetingService server;

	@BeforeAll
	public static void setupInit() {
		server = new GreetingServiceImpl();
		UniDB.clearUniDB();
	}

	@AfterAll
	public static void reset() {
		UniDB.clearUniDB();
	}

	@Test
	@Order(1)
	@DisplayName("Test per verificare l'operazione di inizializzazione dell'admin nel DB")
	public void adminInitializeTest() throws Exception {

		setupInit();
		server.adminInitialize();
		Assertions.assertTrue(UniDB.checkUserExists("admin"));
	}

	@Test
	@Order(2)
	@DisplayName(" Test per verificare la pulizia del database")
	public void clearDBTest() throws Exception {
		// Inserisco un nuovo utente nel DB
		UniDB.addUsers("email", "password", "student");
		server.addUserToDB("emailTEST", "passwordTEST", "student");
		// Verifico che l'utente sia stato aggiunto
		Assertions.assertTrue(UniDB.checkUserExists("emailTEST"));
		// Pulisco e verifico che l'operazione di pulizia ritorni true
		Assertions.assertTrue(UniDB.clearUniDB());
		// Verifico se esiste ancora l'utente inserito in precedenza
		Assertions.assertFalse(UniDB.checkUserExists("emailTEST"));
		// Verifico che sia stato inizializzato l'admin come da clearDB operation.
		Assertions.assertTrue(UniDB.checkUserExists("admin"));
	}

	@Test
	@Order(3)
	@DisplayName("Test di verifica corretto inserimento nel DB")
	public void addUserTest() throws Exception {
		// adding new users
		server.addUserToDB("prova@studente.it","password","student");
		server.addUserToDB("prova@docente.it","password","professor");
		server.addUserToDB("prova@segreteria.it","password","secretary");

		Assertions.assertTrue(UniDB.checkUserExists("prova@studente.it"));
		Assertions.assertTrue(UniDB.checkUserExists("prova@docente.it"));
		Assertions.assertTrue(UniDB.checkUserExists("prova@segreteria.it"));
		Assertions.assertFalse(UniDB.checkUserExists("prova@errore.it"));
	}

	@Test
	@Order(4)
	@DisplayName("Test di verifica corretto inserimento informazioni personali")
	public void addPersonalInfoTest() throws Exception {
		// adding personal info
		server.addInfoToStudentAccount("prova@studente.it","matricola1234","username","nome","cognome",new Date());
		server.addInfoToProfessorAccount("prova@docente.it","username","nome","cognome",new Date());

		// Verifico che le informazioni siano state aggiunte correttamente (siano diverse da null)
		Assertions.assertTrue(UniDB.checkPersonalInfoNotNull("prova@studente.it"));
		Assertions.assertTrue(UniDB.checkPersonalInfoNotNull("prova@docente.it"));
	}

	@Test
	@Order(5)
	@DisplayName("Test di verifica corretta gestione richiesta login")
	public void loginRequestTest() throws Exception {

		Assertions.assertTrue(server.doLogin("admin", "admin") == Status.ADMIN);

		server.addUserToDB("student", "student", "student");
		Assertions.assertTrue(server.doLogin("student", "student") == Status.STUDENT);

		server.addUserToDB("prof", "prof", "professor");
		Assertions.assertTrue(server.doLogin("prof", "prof") == Status.PROFESSOR);

		server.addUserToDB("sec", "sec", "secretary");
		Assertions.assertTrue(server.doLogin("sec", "sec") == Status.SECRETARY);

		Assertions.assertTrue(server.doLogin("student", "passwordErrata") == Status.WRONG_PASS);

		Assertions.assertTrue(server.doLogin("utenteNonRegistrato", "password") == Status.NOT_REGISTERED);

	}

	@Test
	@Order(6)
	@DisplayName("Test di verifica corretto inserimento dipartimento nel DB")
	public void addDepartmentTest() throws Exception {

		server.addDepartmentToDB("DIPARTIMENTO_PROVA");

		Assertions.assertTrue(UniDB.checkDepartmentExists("DIPARTIMENTO_PROVA"));
	}

	@Test
	@Order(7)
	@DisplayName("Test di verifica corretto inserimento corso nel DB")
	public void addCourseTest() throws Exception {

		server.addCourseToDepartment("DIPARTIMENTO_PROVA", "corsoProva", "prof", null, null, "descrizione", "");

		Assertions.assertTrue(UniDB.checkCourseExists("corsoProva"));
	}

	@Test
	@Order(8)
	@DisplayName("Test di verifica corretta cancellazione di un corso nel DB")
	public void deleteCourseTest() throws Exception {

		server.deleteCourseFromDB("DIPARTIMENTO_PROVA", "corsoProva");

		Assertions.assertFalse(UniDB.checkCourseExists("corsoProva"));
	}

	@Test
	@Order(10)
	@DisplayName("Test di verifica corretto cambiamento info di un corso nel DB")
	public void changeCourseTest() throws Exception {

		server.addCourseToDepartment("DIPARTIMENTO_PROVA", "corsoProva", "prof", null, null, "descrizione", "");
		server.changeCourseFromDB("corsoProva","prof", null, null, "nuovaDescrizione", "nuovoSecondoProf");

		Assertions.assertTrue(UniDB.checkCourseExists("corsoProva"));
		Assertions.assertTrue(UniDB.checkInfoCourse("corsoProva", "nuovaDescrizione", "nuovoSecondoProf"));

	}

	@Test
	@Order(11)
	@DisplayName("Test di verifica corretto inserimento esame nel DB")
	public void addExamTest() throws Exception {

		server.addExam("corsoProva", null, "09:00", "alta", "aula");
		Assertions.assertTrue(UniDB.checkExamExists("corsoProva"));
	}

	@Test
	@Order(12)
	@DisplayName("Test di verifica corretto cambiamento info di un esame nel DB")
	public void changeExamInfoTest() throws Exception {

		server.updateExamInfo("corsoProva", null, "10:00", "bassa", "nuovaAula");
		Assertions.assertTrue(UniDB.checkInfoExam("corsoProva", "10:00","bassa","nuovaAula"));
	}

	@Test
	@Order(13)
	@DisplayName("Test di verifica corretta cancellazione di un esame nel DB")
	public void deleteExamTest() throws Exception {

		server.deleteExamFromDB("corsoProva");
		Assertions.assertFalse(UniDB.checkExamExists("corsoProva"));
	}

	@Test
	@Order(14)
	@DisplayName("Test di verifica corretta iscrizione di uno studente a un corso e al relativo esame")
	public void signUpCourseANDExamTest() throws Exception {

		server.addCourseToDepartment("DIPARTIMENTO_PROVA", "corsoProva", "prof", null, null, "descrizione", "");
		server.addExam("corsoProva", null, "09:00", "alta", "aula");
		server.signUpStudentToACourse("corsoProva", "student");
		server.signUpStudentToAnExam("corsoProva", "student");

		Assertions.assertTrue(UniDB.checkStudentInCourse("corsoProva", "student"));
		Assertions.assertTrue(UniDB.checkExamInStudent("student", "Esame del corso di corsoProva"));
	}


	@Test
	@Order(15)
	@DisplayName("Test di verifica corretta cancellazione iscrizione di uno studente a un corso e al relativo esame")
	public void deleteSignUpCourseANDExamTest() throws Exception {

		server.deleteStudentExamRegistration("corsoProva", "student");
		server.deleteStudentCourseRegistration("corsoProva", "student");

		Assertions.assertFalse(UniDB.checkStudentInCourse("corsoProva", "student"));
		Assertions.assertFalse(UniDB.checkExamInStudent("student", "Esame del corso di corsoProva"));
	}

	@Test
	@Order(16)
	@DisplayName("Test di verifica corretto cambiamento di visibilità dei voti per un esame di un corso")
	public void changeMarksVisibilityTest() throws Exception {

		server.setExamsMarksVisibilityVisible("corsoProva");

		Assertions.assertTrue(UniDB.checkMarksVisibilityInCourse("corsoProva"));
	}

}
