package it.unibo.ingsoft.gwt.server;

import java.io.File;
import java.util.Date;
import java.util.Map.Entry;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import com.google.gwt.user.client.Window;

import it.unibo.ingsoft.gwt.shared.Status;
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
	
	// Pulisce il db
	public static boolean clearUniDB() {
		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");
		usersMap.clear();
		HTreeMap<String, Department> departmentsMap = db.getHashMap("departmentsMap");
		departmentsMap.clear();
		adminUserInitialize();
		
		db.commit();
		usersMap.close();
		departmentsMap.close();
		db.close();
		return true;
	}
	
	// Gestisce la richiesta di login
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
	
	// Aggiunge un oggetto dipartimento alla departmentsMap nel db uniDB
	public static boolean addDepartment(Department d) {
		DB db = getUniDB();
		HTreeMap<String, Department> departmentsMap = db.getHashMap("departmentsMap");
		departmentsMap.put(d.getName(), d);
		
		db.commit();
		departmentsMap.close();
		db.close();
		return true;
	}
	
	// Aggiunge un oggetto User alla usersMap nel db uniDB
	public static String addUsers(User u) {
//		String returnString = "";
		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");
		System.out.println("NON ANCORA");
		usersMap.putIfAbsent(u.getEmail(), u);
		System.out.println("AGGIUNTO UTENTE " + u.getEmail());
//		if (!checkEmail(u.getEmail())) {
//			usersMap.put(u.getEmail(), u);
//			
//			returnString =  "Added new account with email: " + u.getEmail();
//		} else {
//			System.out.println("EMAIL GIA' PRESENTE");
//			returnString = "Email gia' presente, impossibile aggiungere il nuovo account.";
//		}

		db.commit();
		usersMap.close();
		db.close();
		return "Added new account with email: " + u.getEmail() + "\nClasse: " + u.getClass();
	}

	// Aggiunge le informazioni personali agli utenti di tipo STUDENTE
	public static String addPersonalInfoToStudent(String email,String iD, String username, String name,
							String surname, Date birthday) {
		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");
		
			for (String key : usersMap.keySet()) {
				if (email.equalsIgnoreCase(key) && usersMap.getPeek(email) instanceof Student) {
					Student s = (Student) usersMap.getPeek(email);
					// DEBUG
					Window.alert("PRIMA INSERIMENTO:\n" + s.toString());
					s.setID(iD);
					s.setUsername(username);
					s.setName(name);
					s.setSurname(surname);
					s.setBirthday(birthday);
					// Aggiorno il valore all'interno della mappa
					usersMap.replace(email, s);
					// DEBUG
					Window.alert("DOPO INSERIMENTO:\n"+ s.toString());
				}
			}
			
		db.commit();
		usersMap.close();
		db.close();
		return "Added personal info to STUDENT account with email: " + email + ".";
	}
	
	// Aggiunge le informazioni personali agli utenti di tipo DOCENTE
	public static String addPersonalInfoToProfessor(String email, String username, String name, 
							String surname, Date birthday) {
		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");
		
		for (String key : usersMap.keySet()) {
			if (email.equalsIgnoreCase(key) && usersMap.getPeek(email) instanceof Professor) {
				Professor p = (Professor) usersMap.getPeek(email);
				// DEBUG
				Window.alert("PRIMA INSERIMENTO:\n" + p.toString());
				p.setUsername(username);
				p.setName(name);
				p.setSurname(surname);
				p.setBirthday(birthday);
				// Aggiorno il valore all'interno della mappa
				usersMap.replace(email, p);
				// DEBUG
				Window.alert("DOPO INSERIMENTO:\n"+ p.toString());
			}
		}
		
		db.commit();
		usersMap.close();
		db.close();
		return "Added personal info to PROFESSOR account with email: " + email+ ".";
	}
}
