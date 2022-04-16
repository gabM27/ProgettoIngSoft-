package it.unibo.ingsoft.gwt.server;

import java.io.File;
import java.util.Date;
import java.util.Map.Entry;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.shared.Status;
import it.unibo.ingsoft.gwt.shared.domain.Course;
import it.unibo.ingsoft.gwt.shared.domain.Department;
import it.unibo.ingsoft.gwt.shared.users.Admin;
import it.unibo.ingsoft.gwt.shared.users.Professor;
import it.unibo.ingsoft.gwt.shared.users.Secretary;
import it.unibo.ingsoft.gwt.shared.users.Student;
import it.unibo.ingsoft.gwt.shared.users.User;


public class UniDB {
	
	private static DB getUniDB() {
		DB uniDB = DBMaker.newFileDB(new File("uniDB")).make();
		return uniDB;
	}
	
	// Inizializza la usersMap con il primo utente (admin)
	public static boolean adminUserInitialize() {
		User admin = new Admin();
		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");
		usersMap.putIfAbsent(admin.getEmail(), admin);
		
		db.commit();
		usersMap.close();
		db.close();
		return true;
	}
	
	// Pulisce il db
	public static boolean clearUniDB() {
		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");
		usersMap.clear();
		HTreeMap<String, Department> departmentsMap = db.getHashMap("departmentsMap");
		departmentsMap.clear();
		HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");
		coursesMap.clear();
		
		db.commit();
		usersMap.close();
		departmentsMap.close();
		coursesMap.close();
		db.close();
		adminUserInitialize();
		return true;
	}
	
	// Aggiunge un oggetto User alla usersMap nel db uniDB
	public static String addUsers(String emailInput, String passwordInput, String accountType) {

		if (!checkEmail(emailInput)) {
		
			DB db = getUniDB();
			HTreeMap<String, User> usersMap = db.getHashMap("usersMap");
			User user = null;
			switch (accountType.toLowerCase()) {
			case "student":
				user = new Student(emailInput,passwordInput);
				usersMap.put(user.getEmail(), user);
				break;
			case "professor":
				user = new Professor(emailInput,passwordInput);
				usersMap.put(user.getEmail(), user);
				break;
			case "secretary":
				user = new Secretary(emailInput,passwordInput);
				usersMap.put(user.getEmail(), user);
				break;
			}
		
			db.commit();
			usersMap.close();
			db.close();
			
			return "Added new " + accountType + " with email: " + user.getEmail() + "\nClasse: " + user.getClass();
			
		} else {
			return "Error: entered email already exist";
		}
	}
	
	// Aggiunge le informazioni personali agli utenti di tipo STUDENTE
	public static String addPersonalInfoToStudent(String email,String iD, String username, String name,
							String surname, Date birthday) {
		boolean changed = false;
		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");
		
			for (String key : usersMap.keySet()) {
				if (key.equalsIgnoreCase(email) && usersMap.get(email) instanceof Student) {
					Student s = (Student) usersMap.get(email);
					
					s.setID(iD);
					s.setUsername(username);
					s.setName(name);
					s.setSurname(surname);
					s.setBirthday(birthday);
					// Aggiorno il valore all'interno della mappa
					usersMap.replace(email, s);
					changed = true;
					break;
				} 
			}
			
		db.commit();
		usersMap.close();
		db.close();
		if (changed) {
			return "Added or changed personal info to STUDENT account with email: " + email + ".";
		} else {
			return "Error: entered email does not exist";
		}
		
	}
	
	// Aggiunge le informazioni personali agli utenti di tipo DOCENTE
	public static String addPersonalInfoToProfessor(String email, String username, String name, 
							String surname, Date birthday) {
		boolean changed = false;
		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");
		
		for (String key : usersMap.keySet()) {
			
			if (email.equalsIgnoreCase(key) && usersMap.get(email) instanceof Professor) {
				Professor p = (Professor) usersMap.get(email);
				p.setUsername(username);
				p.setName(name);
				p.setSurname(surname);
				p.setBirthday(birthday);
				// Aggiorno il valore all'interno della collezione
				usersMap.replace(email, p);
				changed = true;
				break;
			}
		}
		
		db.commit();
		usersMap.close();
		db.close();
		if (changed) {
			return "Added or changed personal info to PROFESSOR account with email: " + email + ".";
		} else {
			return "Error: entered email does not exist";
		}
	}
	
	public static String viewPersonalInfo(String email) {
		
		if (checkEmail(email)) { // l'email e' presente nel DB
		
			DB db = getUniDB();
			HTreeMap<String, User> usersMap = db.getHashMap("usersMap");
			
			User u = usersMap.get(email);
			String ret = "L'account associato all'email non � di un docente o di uno studente.\nRiprovare.";
			if (u instanceof Student || u instanceof Professor) {
				ret = u.toString(); // Informazioni personali dello studente o del docente
			} 
			db.commit();
			usersMap.close();
			db.close();
			
			return ret;
		} else { // checkEmail ritorna false 
			return "EMAIL NON PRESENTE NEL DB";
		}
		
	}

	
	// Gestisce la richiesta di login impostando lo stato corretto
	public static Status loginRequest(String email, String password) {
		Status returnStatus = null;
		
		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");
		
		if (checkEmail(email)) {
			User u = usersMap.get(email);
			
			if (u.getPassword().equals(password)) {
				if (u instanceof Admin) {
					returnStatus = Status.ADMIN;
				} else if (u instanceof Student) {
					returnStatus = Status.STUDENT;
				} else if (u instanceof Professor) {
					returnStatus = Status.PROFESSOR;
				} else if (u instanceof Secretary) {
					returnStatus = Status.SECRETARY;
				}
			} else if (!u.getPassword().equals(password)){
				returnStatus = Status.WRONG_PASS;
			}
		} else {
			returnStatus = Status.NOT_REGISTERED;
		}
		
		db.commit();
		usersMap.close();
		db.close();
	
		return returnStatus;
	}
	
	
	// Aggiunge un oggetto dipartimento alla departmentsMap nel db uniDB
	public static String addDepartment(String nomeDipartimento) {
		
		if (!checkDepartmentName(nomeDipartimento)) {
			
			DB db = getUniDB();
			Department d = new Department(nomeDipartimento);
			
			HTreeMap<String, Department> departmentsMap = db.getHashMap("departmentsMap");
			departmentsMap.put(d.getName(), d);
			
			db.commit();
			departmentsMap.close();
			db.close();
			return "ADDED NEW DEPARTMENT.\nNew department name: " + nomeDipartimento;
		} else {
			return "Error: department already exists."; 
		}
	}
	
	// Ritorna la lista dei dipartimenti presenti nel DB in una stringa con delimitatore '_'
	public static String viewDepartmentsList() {
		String ret = "";
		DB db = getUniDB();
		HTreeMap<String, Department> departmentsMap = db.getHashMap("departmentsMap");
		
		if (!departmentsMap.isEmpty()) {
			for (Entry<String, Department> newEntry : departmentsMap.entrySet()) {
				Department newDep = newEntry.getValue();
				ret += newDep.getName() + "_";
			}
		} else {
			ret += "Nessun dipartimento inserito nel Database.";
		}
		
		db.commit();
		departmentsMap.close();
		db.close();
		return ret;
	}
	
	// Aggiunge un oggetto Course alla coursesMap
	// e aggiunge il nome del corso all'oggetto dipartimento passato in input.
	// Se il corso e' gia' presente nella lista corsi di quel dipartimento, non verra' aggiunto.
	public static String addCourse(String departmentName, String courseName, 
			Date startCourse, Date endCourse, String description, String secondProfEmail) {
		
		DB db = getUniDB();
			
		HTreeMap<String, Department> departmentsMap = db.getHashMap("departmentsMap");
		HTreeMap<String,Course> coursesMap = db.getHashMap("coursesMap");
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");
		
		// Creazione del nuovo corso
		Course c = new Course(courseName);
		// il prof che crea il corso diviene il docente principale del corso stesso
		c.setProf(ActualSession.getActualSession().getEmail());
		c.setStartDate(startCourse);
		c.setEndDate(endCourse);
		c.setDescription(description);
		
		// Controllo che il co-docente esista nella usersMap.
		// Se esiste viene settato il valore secondProf del corso
		String valSecondProf = "Co-docente inserito correttamente."; 
		if ((!secondProfEmail.equals("")) && (checkEmail(secondProfEmail))) {
			if (usersMap.get(secondProfEmail) instanceof Professor) {
				c.setSecondProf(secondProfEmail);
			} else {
				valSecondProf = "Co-docente inserito presente nel db, ma con un ruolo diverso da \"docente\".";
			}
		} else {
			valSecondProf = "Co-docente non inserito o e-mail non presente nella lista degli users.";
		}
		
		// Aggiunta del corso alla coursesMap
		coursesMap.put(c.getName(), c);
		
		// Setting nome corso nella lista dei corsi del dipartimento
		Department newDep = departmentsMap.get(departmentName);
		newDep.addCourse(c.getName());
		departmentsMap.replace(departmentName,newDep);
		
		db.commit();
		departmentsMap.close();
		db.close();

		return "ADDED OR CHANGED COURSE \"" + c.getName() + "\" IN DEPARTMENT " + departmentName + ".\n" + valSecondProf;
		
	}
	
	public static String deleteCourse(String depName, String courseName) {
		
		if ((checkDepartmentName(depName)) && (checkCourseName(courseName))) {
			DB db = getUniDB();
			
			HTreeMap<String, Department> departmentsMap = db.getHashMap("departmentsMap");
			HTreeMap<String,Course> coursesMap = db.getHashMap("coursesMap");
			
			// Rimozione del corso dalla lista di corsi del dipartimento
			Department newDep = departmentsMap.get(depName);
			newDep.removeCourse(courseName);
			
			// Aggiornamento del valore del dipartimento modificato
			departmentsMap.remove(depName);
			departmentsMap.put(depName, newDep);
		
			// Rimozione del corso dalla map nel db
			coursesMap.remove(courseName);	

			db.commit();
			departmentsMap.close();
			coursesMap.close();
			db.close();
			return "REMOVED COURSE \"" + courseName + "\" from the DB.";
		} else { 

			return "ERROR REMOVING COURSE \"" + courseName + "\".";
		}
	}
	
	public static String viewCoursesList(String departmentName) {
		String ret = "";
		DB db = getUniDB();
		HTreeMap<String, Department> departmentsMap = db.getHashMap("departmentsMap");
		
		if ((!departmentsMap.isEmpty()) && (checkDepartmentName(departmentName))) {
			Department d = departmentsMap.get(departmentName);
			if (!d.getCoursesList().isEmpty()) {
				for (String course : d.getCoursesList()) {
					ret += course + "_";
				}
			} else {
				ret += "Nessun corso inserito nel dipartimento selezionato.";
			}
		} else {
			ret += "Dipartimento selezionato inesistente.";
		}
	
		
		db.commit();
		db.close();
		return ret;
	
	}
	
	/*
	 * METODI AUSILIARI
	 */
	
	/*
	 * Ritorna true se esiste gia' un utente con l'email passata in input 
	 */
	private static boolean checkEmail(String email) {
		boolean check = false;
		
		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");
		
		for (Entry<String,User> newUser : usersMap.entrySet()) {
			if (newUser.getValue().getEmail().equalsIgnoreCase(email)) {
				check = true;
				break;
			}
		}
		db.commit();
		usersMap.close();
		db.close();
		return check;
	}
	
	/*
	 * Ritorna true se gia' esiste un dipartimento con il nome passato in input
	 * Ritorna false se non e' memorizzato nel DB nessun dipartimento con quel nome
	 */
	private static boolean checkDepartmentName(String nomeDipartimento) {
		boolean check = false;
		
		DB db = getUniDB();
		HTreeMap<String, Department> departmentsMap = db.getHashMap("departmentsMap");
		
		for (Entry<String, Department> newDepartment: departmentsMap.entrySet()) {
			if (newDepartment.getValue().getName().equalsIgnoreCase(nomeDipartimento)) {
				check = true;
				break;
			}
		}
		
		db.commit();
		departmentsMap.close();
		db.close();
		return check;
	}
	
	/*
	 * Ritorna true se gia' esiste un corso con il nome passato in input
	 * Ritorna false se non e' memorizzato nel DB nessun corso con quel nome
	 */
	private static boolean checkCourseName(String courseName) {
		boolean check = false;
		
		DB db = getUniDB();
		HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");
		
		for (Entry<String, Course> newDepartment: coursesMap.entrySet()) {
			if (newDepartment.getValue().getName().equalsIgnoreCase(courseName)) {
				check = true;
				break;
			}
		}
		
		db.commit();
		coursesMap.close();
		db.close();
		return check;
	}
	
	/*
	 * METODI PER I TEST
	 */
	// Verifica se esiste l'utente nel DB
	public static boolean checkUserExists(String email) { return checkEmail(email); }
	
	public static boolean checkPersonalInfoNotNull(String email) {
		boolean check = false;
		
		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");
		User u = usersMap.get(email);
		
		if(u instanceof Student) {
			Student s = (Student) u;
			if ((s.getEmail().equalsIgnoreCase(email)) && (s.getUsername() != null)
					&& (s.getName() != null) && (s.getSurname() != null) 
					&& (s.getID() != null) && (s.getBirthday() != null)) {
				check = true;
			}
		} else if (u instanceof Professor) {
			Professor p = (Professor) u;
			if ((p.getEmail().equalsIgnoreCase(email)) && (p.getUsername() != null)
					&& (p.getName() != null) && (p.getSurname() != null) 
					&& (p.getBirthday() != null)) {
				check = true;
			}
		} // else --> check = false
		
		db.commit();
		usersMap.close();
		db.close();
		return check;
	}
	
	
}


	




