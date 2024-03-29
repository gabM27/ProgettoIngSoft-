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
import it.unibo.ingsoft.gwt.shared.domain.Exam;
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

	/**
	 * Inizializza la usersMap con il primo utente (admin)
	 * @return boolean
	 */
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

	/**
	 * Pulisce il Database.
	 * @return boolean
	 */
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

	/**
	 * Aggiunge un oggetto User alla usersMap nel db uniDB.
	 * @param emailInput String
	 * @param passwordInput String
	 * @param accountType String
	 * @return String
	 */
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

			return "Added new " + accountType + " with email: " + user.getEmail();

		} else {
			return "Error: entered email already exist";
		}
	}
	
	/**
	 *  Aggiunge le informazioni personali agli utenti di tipo STUDENTE
	 * @param email String
	 * @param iD String
	 * @param username String
	 * @param name String
	 * @param surname String
	 * @param birthday Date
	 * @return String
	 */
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

	/**
	 *  Aggiunge le informazioni personali agli utenti di tipo DOCENTE
	 * @param email String
	 * @param username String
	 * @param name String
	 * @param surname String
	 * @param birthday Date
	 * @return String
	 */
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

	/**
	 * Ritorna le informazioni personali di un account docente o studente
	 * @param email String
	 * @return String
	 */
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

	/**
	 * Gestisce la richiesta di login impostando lo stato corretto
	 * @param email String
	 * @param password String
	 * @return Status 
	 */
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


	/**
	 * Aggiunge un oggetto dipartimento alla departmentsMap nel db uniDB
	 * @param nomeDipartimento String
	 * @return String 
	 */
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

	/**
	 * Ritorna la lista dei dipartimenti presenti nel DB in una stringa con delimitatore '_'
	 * @return String 
	 */
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

	/**
	 * Aggiunge un oggetto Course alla coursesMap
	 * e aggiunge il nome del corso all'oggetto dipartimento passato in input.
	 * Se il corso e' gia' presente nella lista corsi di quel dipartimento, non verra' aggiunto.
	 * @param departmentName String
	 * @param courseName String
	 * @param profEmail String
	 * @param startCourse Date
	 * @param endCourse Date
	 * @param description String
	 * @param secondProfEmail String
	 * @return String 
	 */
	public static String addCourse(String departmentName, String courseName, String profEmail,
			Date startCourse, Date endCourse, String description, String secondProfEmail) {

		DB db = getUniDB();

		HTreeMap<String, Department> departmentsMap = db.getHashMap("departmentsMap");
		HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");

		// Creazione del nuovo corso
		Course c = new Course(courseName);
		// il prof che crea il corso diviene il docente principale del corso stesso
		c.setProf(profEmail);
		c.setStartDate(startCourse);
		c.setEndDate(endCourse);
		c.setDescription(description);
		c.setSecondProf(secondProfEmail);

		// Controllo che il co-docente esista nella usersMap.
		// Se esiste viene settato il valore secondProf del corso
		String valSecondProf = ""; 
		if (!secondProfEmail.equals("")) {
			valSecondProf = "Co-docente inserito.";
		} else {
			valSecondProf = "Co-docente non inserito.";
		}

		// Inserimento del corso all'interno della map dei corsi
		coursesMap.put(c.getName(), c);

		// Setting del nome del corso all'interno della lista dei corsi del docente
		Professor p = (Professor) usersMap.get(profEmail);
		p.addCourse(courseName);
		// Aggiornamento del valore del docente modificato
		usersMap.replace(p.getEmail(), p);

		// Setting nome corso nella lista dei corsi del dipartimento
		Department newDep = departmentsMap.get(departmentName);
		newDep.addCourse(c.getName()); // aggiunta corso alla lista nel dipartimento di pertinenza
		// Aggiornamento del valore del dipartimento modificato
		departmentsMap.replace(departmentName, newDep);

		db.commit();
		departmentsMap.close();
		coursesMap.close();
		usersMap.close();
		db.close();


		return "ADDED COURSE \"" + c.getName() + "\" IN DEPARTMENT " + departmentName + ".\n" + valSecondProf;
	}

	/**
	 * Modifica le informazioni di un corso (nome del corso passato come parametro in input)
	 * @param courseName String
	 * @param startCourse Date
	 * @param endCourse Date
	 * @param description String
	 * @param secondProfEmail String
	 * @return String 
	 */
	public static String changeCourseInfo(String courseName,String profEmail, Date startCourse, 
			Date endCourse, String description, String secondProfEmail) {

		if (checkCourseName(courseName)) {

			DB db = getUniDB();

			HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");

			// Modifica del corso
			Course c = new Course(courseName);
			c.setProf(profEmail);
			c.setStartDate(startCourse);
			c.setEndDate(endCourse);
			c.setDescription(description);
			c.setSecondProf(secondProfEmail);

			// Aggiornamento del corso nel db
			coursesMap.replace(courseName, c);
			// NON è necessario l'aggiornamento nella lista dei corsi del dipartimento

			db.commit();
			coursesMap.close();
			db.close();

			return "CHANGED COURSE'S INFO.";
		}

		return "COURSE DOES NOT EXISTS IN DB";
	}

	/**
	 * Elimina un corso dalla collezione dei corsi e dalla lista dei corsi del dipartimento
	 * @param depName String
	 * @param courseName String
	 * @return String 
	 */
	public static String deleteCourse(String depName, String courseName) {

		if ((checkDepartmentName(depName)) && (checkCourseName(courseName))) {
			DB db = getUniDB();

			HTreeMap<String, Department> departmentsMap = db.getHashMap("departmentsMap");
			HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");

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

	/**
	 * Ritorna la lista dei corsi 
	 * @param departmentName String
	 * @return String 
	 */
	public static String viewCoursesList(String departmentName) {
		String ret = "";
		DB db = getUniDB();
		HTreeMap<String, Department> departmentsMap = db.getHashMap("departmentsMap");

		if ((!departmentsMap.isEmpty()) && (checkDepartmentName(departmentName))) {
			Department d = departmentsMap.get(departmentName);
			if (!d.getCoursesList().isEmpty()) {
				for (String courseName : d.getCoursesList()) {
					ret += courseName + "_";
				}
			} else {
				ret += "Nessun corso inserito nel dipartimento selezionato.";
			}
		} else {
			ret += "Dipartimento selezionato inesistente.";
		}


		db.commit();
		departmentsMap.close();
		db.close();
		return ret;

	}

	/**
	 * Ritorna le informazioni di un corso (il nome del corso deve essere passato come parametro in input) 
	 * @param nomeCorso String
	 * @return String 
	 */
	public static String viewCourseInfo(String nomeCorso) {	
		String ret = "";
		DB db = getUniDB();
		HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");

		if ((!coursesMap.isEmpty()) && (checkCourseName(nomeCorso))) {
			Course c = coursesMap.get(nomeCorso);
			ret += c.toString();
		} else {
			ret += "Corso selezionato inesistente.";
		}

		db.commit();
		coursesMap.close();
		db.close();
		return ret;
	}

	/**
	 * Crea e aggiunge un esame al corso passato in input
	 * @param nomeCorso String
	 * @param dataEsame Date
	 * @param orarioEsame String
	 * @param durezzaEsame String
	 * @param nomeAula String
	 * @return String 
	 */
	public static String addExamToCourse(String nomeCorso, Date dataEsame, String orarioEsame, 
			String durezzaEsame, String nomeAula) {

		if (!checkExam(nomeCorso)) { // Verifico che l'esame non sia già stato inserito per quel corso
			DB db = getUniDB();
			HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");
			HTreeMap<String, User> usersMap = db.getHashMap("usersMap");

			Course newCourse = coursesMap.get(nomeCorso);
			// Inserisco i dati dell'esame per lo specifico corso
			newCourse.examSetting(dataEsame);
			newCourse.getExam().setOrario(orarioEsame);
			newCourse.getExam().setLivelloDurezza(durezzaEsame);
			newCourse.getExam().setNomeAula(nomeAula);

			// Aggiornamento del corso (con relativo esame) nel DB
			coursesMap.replace(nomeCorso, newCourse);

			for (Entry<String, User> newUser : usersMap.entrySet()) {
				if (newUser.getValue() instanceof Secretary) {
					Secretary sec = (Secretary) newUser.getValue();
					// Inserisco l'esame nella lista esami della segreteria
					sec.addExam(nomeCorso);
					// Aggiornamento della segreteria nel DB
					usersMap.replace(newUser.getKey(), sec);
				}
			}

			db.commit();
			coursesMap.close();
			usersMap.close();
			db.close();
			return "ADDED EXAM IN \"" + nomeCorso + "\" COURSE.";
		}

		return "EXAM ALREADY SETTED UP IN \"" + nomeCorso + "\" COURSE.";
	}

	/**
	 * Modifica informazioni di un esame in un corso (nome corso passato come parametro in input)
	 * @param nomeCorso String
	 * @param dataEsame Date
	 * @param orarioEsame String
	 * @param durezzaEsame String
	 * @param nomeAula String
	 * @return String 
	 */
	public static String changeExamInfo(String nomeCorso, Date dataEsame, String orarioEsame,
			String durezzaEsame, String nomeAula) {
		if (checkExam(nomeCorso)) {
			DB db = getUniDB();
			HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");

			Course course = coursesMap.get(nomeCorso);
			// Inserisco i dati dell'esame per lo specifico corso 
			course.examSetting(dataEsame);
			course.getExam().setOrario(orarioEsame);
			course.getExam().setLivelloDurezza(durezzaEsame);
			course.getExam().setNomeAula(nomeAula);

			// Aggiornamento del corso (con relativo esame AGGIORNATO) nel DB
			coursesMap.replace(nomeCorso, course);

			db.commit();
			coursesMap.close();
			db.close();

			return "CHANGED EXAM'S INFO in \"" + nomeCorso + "\" COURSE.";
		} // else : esame mai inserito (null) --> error.

		return "EXAM DOESN'T EXISTS: impossible updating exam's info.\nTry adding new exam.";
	}

	/**
	 * Cancellazione di un esame dal corso --> rende l'esame null
	 * @param nomeCorso String
	 * @return String 
	 */
	public static String deleteExam(String nomeCorso) {
		if (checkExam(nomeCorso)) {
			DB db = getUniDB();
			HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");
			HTreeMap<String, User> usersMap = db.getHashMap("usersMap");

			Course course = coursesMap.get(nomeCorso);
			// Elimino l'esame tramite la funzione dell'oggetto corso
			course.examDeleting();

			// Aggiornamento del corso (con relativo esame AGGIORNATO a NULL) nel DB
			coursesMap.replace(nomeCorso, course);

			for (Entry<String, User> newUser : usersMap.entrySet()) {
				if (newUser.getValue() instanceof Secretary) {
					Secretary sec = (Secretary) newUser.getValue();
					// Cancella l'esame nella lista esami della segreteria
					sec.deleteExam(nomeCorso);
					// Aggiornamento della segreteria nel DB
					usersMap.replace(newUser.getKey(), sec);
				}
			}

			db.commit();
			coursesMap.close();
			db.close();

			return "DELETED EXAM in \"" + nomeCorso + "\" COURSE.";
		} // else : esame mai inserito (già == null) --> error.

		return "EXAM DOESN'T EXISTS: impossible deleting exam.";
	}

	/**
	 * Iscrizione di uno studente a un corso
	 * @param courseName String
	 * @param studentEmail String
	 * @return String 
	 */
	public static String signUpStudentToCourse(String courseName, String studentEmail) {
		String ret = "";

		if (checkCourseName(courseName) && checkEmail(studentEmail)) {
			DB db = getUniDB();
			HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");
			HTreeMap<String, User> usersMap = db.getHashMap("usersMap");

			if (usersMap.get(studentEmail) instanceof Student) {

				Student stu = (Student) usersMap.get(studentEmail);
				Course course = coursesMap.get(courseName);

				if (!stu.isCourseThere(courseName)) {
					// Inserimento del nome del corso all'interno dell'istanza dello studente
					stu.addCourse(courseName);
					// Inserimento dell'email dello studente all'interno dell'istanza del corso
					course.addStudent(studentEmail);

					// Aggiornamento dei valori nel DB
					usersMap.replace(studentEmail, stu);
					coursesMap.replace(courseName, course);

					ret += "Studente iscritto correttamente al corso.";
				} else {
					ret += "Studente precedentemente gia' iscritto al corso";
				}


			} else {
				ret += "Utente presente nel DB con un ruolo diverso da studente.";
			}

			db.commit();
			coursesMap.close();
			usersMap.close();
			db.close();

		} else {
			ret += "Corso o studente non presente nel DB.";
		}
		return ret; 
	}

	/**
	 * Cancellazione iscrizione di uno studente a un corso
	 * @param courseName String
	 * @param studentEmail String
	 * @return String 
	 */
	public static String deleteStudentCourseRegistrationFromDB(String courseName, String studentEmail) {
		String ret = "";

		if (checkCourseName(courseName) && checkEmail(studentEmail)) {
			DB db = getUniDB();
			HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");
			HTreeMap<String, User> usersMap = db.getHashMap("usersMap");

			if (usersMap.get(studentEmail) instanceof Student) {
				Student stu = (Student) usersMap.get(studentEmail);
				Course course = coursesMap.get(courseName);

				if (stu.isCourseThere(courseName)) {
					// Cancellazione del nome del corso dalla lista all'interno dell'istanza dello studente
					stu.deleteCourse(courseName);
					// Cancellazione dell'email dello studente dalla lista all'interno dell'istanza del corso
					course.deleteStudent(studentEmail);

					// Aggiornamento dei valori nel DB
					usersMap.replace(studentEmail, stu);
					coursesMap.replace(courseName, course);

					ret += "Studente cancellato correttamente dal corso.";
				} else {
					ret += "Studente non iscritto al corso da cui si vuole cancellare l'iscrizione.";
				}

			} else {
				ret += "Utente presente nel DB con un ruolo diverso da studente.";
			}

			db.commit();
			coursesMap.close();
			usersMap.close();
			db.close();

		} else {
			ret += "Corso o studente non presente nel DB.";
		}
		return ret; 
	}

	/**
	 * Ritorna la lista dei corsi a cui è iscritto uno studente
	 * @param studentEmail String
	 * @return String 
	 */
	public static String viewStudentRegisteredCourseList(String studentEmail) {
		String ret = "";
		if (checkEmail(studentEmail)) {
			DB db = getUniDB();
			HTreeMap<String, User> usersMap = db.getHashMap("usersMap");

			if (usersMap.get(studentEmail) instanceof Student) {
				Student stu = (Student) usersMap.get(studentEmail);
				if (stu.getCourses().size() != 0) {
					for (String courseName : stu.getCourses()) {
						ret += courseName + "_";
					}
				} else {
					ret += "Lo studente non e' registrato a nessun corso.";
				}
			} else {
				ret += "Email presente nel DB ma con un ruolo diverso da studente.";
			}

		}

		return ret;
	}

	/**
	 * Ritorna la lista dei corsi a cui uno studente (email in input) è iscritto 
	 * && con esame impostato (course.getExam() != null).
	 * @param studentEmail String
	 * @return String 
	 */
	public static String viewStudentRegisteredCoursesExamSettedUpList(String studentEmail) {
		String ret = "";
		if (checkEmail(studentEmail)) {
			DB db = getUniDB();
			HTreeMap<String, User> usersMap = db.getHashMap("usersMap");

			if (usersMap.get(studentEmail) instanceof Student) {
				HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");
				for (Entry<String, Course> newCourse : coursesMap.entrySet()) {
					if ((newCourse.getValue().isStudentThere(studentEmail)) && (newCourse.getValue().getExam() != null)) {
						ret += newCourse.getKey() + "_";
					}
				}

			} else {
				ret += "Email presente nel DB, ma con un ruolo diverso da studente.";
			}
		} else {
			ret += "Email non presente nel DB.";
		}

		return ret;
	}

	/**
	 * Registra uno studente (email in input) a un corso (nome corso in input)
	 * @param courseName String
	 * @param studentEmail String
	 * @return String 
	 */
	public static String signUpStudentToACourseExam(String courseName, String studentEmail) {
		String ret = "";

		if (checkCourseName(courseName) && checkEmail(studentEmail)) {
			DB db = getUniDB();
			HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");
			HTreeMap<String, User> usersMap = db.getHashMap("usersMap");

			if (usersMap.get(studentEmail) instanceof Student) {
				Student stu = (Student) usersMap.get(studentEmail);
				Course course = coursesMap.get(courseName);

				if (!stu.isExamThere(course.getExam().getExamName())) { // controllo che lo studente non sia già iscritto all'esame

					// aggiunta esame nella lista degli esami dello studente
					stu.addExam(course.getExam().getExamName());
					/*
					 * Aggiunta studente nella lista degli studenti iscritti all'esame del corso già fatta 
					 * al momento della creazione dell'account studente.
					 */

					// Aggiornamento dei valori nel DB
					usersMap.replace(studentEmail, stu);

					ret += "Studente iscritto correttamente all'esame.";
				} else {
					ret += "Studente precedentemente gia' iscritto all'esame.";
				}

			} else {
				ret += "Utente presente nel DB con un ruolo diverso da studente.";
			}

			db.commit();
			coursesMap.close();
			usersMap.close();
			db.close();

		} else {
			ret += "Corso o studente non presente nel DB.";
		}

		return ret;
	}

	/**
	 * Metodo che ritorna la lista di esami a cui è iscritto lo studente
	 * con email passata in input.
	 * @param studentEmail String
	 * @return ret String
	 */
	public static String viewStudentRegisteredExamList(String studentEmail) {
		String ret = "";
		if (checkEmail(studentEmail)) {
			DB db = getUniDB();
			HTreeMap<String, User> usersMap = db.getHashMap("usersMap");

			if (usersMap.get(studentEmail) instanceof Student) {
				Student stu = (Student) usersMap.get(studentEmail);
				if (stu.getExams().size() != 0) {
					for (String examName : stu.getExams()) {
						ret += examName + "_";
					}
				} else {
					ret += "Lo studente non e' registrato a nessun esame.";
				}
			} else {
				ret += "Email presente nel DB ma con un ruolo diverso da studente.";
			}

		}

		return ret;
	}


	/**
	 * Cancella l'iscrizione a un esame passato in input per lo studente,
	 * la cui email viene passata in input.
	 * @param examName String 
	 * @param studentEmail String
	 * @return ret String
	 */
	public static String deleteStudentExamRegistrationFromDB(String examName, String studentEmail) {
		String ret = "";

		if (checkEmail(studentEmail)) {
			DB db = getUniDB();
			HTreeMap<String, User> usersMap = db.getHashMap("usersMap");


			if (usersMap.get(studentEmail) instanceof Student) {

				Student stu = (Student) usersMap.get(studentEmail);

				if (stu.isExamThere(examName)) { // controllo che lo studente sia iscritto all'esame

					// Cancellazione dell'esame dalla lista all'interno dell'istanza dello studente
					stu.deleteExam(examName);

					/*
					 * Cancellazione dell'email dello studente dalla lista 
					 * all'interno dell'istanza del corso NON NECESSARIA.
					 * 
					 * Se lo studente volesse iscriversi di nuovo all'esame si
					 * avrebbero dei problemi a settare correttamente gli indici 
					 * delle liste nell'istanza del Corso.
					 * 
					 * Si ricorda che in un oggetto Course ci sono due liste:
					 * - students --> studenti iscritti al corso.
					 * - examStudents --> studenti iscritti all'esame.
					 * 
					 * Quando uno studente viene aggiunto alla lista students, per
					 * comodità viene aggiunto anche alla lista examStudents con valore di default.
					 * In questo modo si tiene traccia con lo stesso indice all'interno di due liste diverse.
					 * 
					 */

					// Aggiornamento dei valori nel DB
					usersMap.replace(studentEmail, stu);

					ret += "Studente cancellato correttamente dalla lista degli iscritti all'esame.";
				} else {
					ret += "Studente non iscritto all'esame di cui si vuole cancellare l'iscrizione.";
				}

			} else {
				ret += "Utente presente nel DB con un ruolo diverso da studente.";
			}

			db.commit();
			usersMap.close();
			db.close();

		} else {
			ret += "Studente non presente nel DB.";
		}
		return ret; 
	}

	/**
	 * Metodo che ritorna la lista dei corsi di cui un docente (email passata in input)
	 * è titolare del corso
	 * @param profEmail
	 * @return coursesList String
	 */
	public static String viewProfCoursesList(String profEmail) {
		String ret = "";
		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");

		if (checkEmail(profEmail) && usersMap.get(profEmail) instanceof Professor) {
			Professor p = (Professor) usersMap.get(profEmail);
			if (p.getCoursesList() != null && !p.getCoursesList().isEmpty()) {
				for (String courseName : p.getCoursesList()) {
					ret += courseName + "_";
				}
			} else {
				ret += "Nessun corso inserito nella lista dei corsi tenuti dal docente.";
			}
		} else {
			ret += "Docente inserito inesistente.";
		}


		db.commit();
		usersMap.close();
		db.close();
		return ret;
	}

	/**
	 * Metodo che ritorna la lista di studenti iscritti a un corso dalla lista degli studenti nell'istanza Corso.
	 * @param courseName String
	 * @return studentsList String
	 */
	public static String viewRegisteredStudentsFromCourse(String courseName) {
		String ret = "";
		DB db = getUniDB();
		HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");

		if (checkCourseName(courseName)) {
			Course c = coursesMap.get(courseName);
			if (c.getStudentsList() != null) {
				if (!c.getStudentsList().isEmpty()) {
					for (String newStudent : c.getStudentsList()) {
						ret += newStudent + "_";
					}
				} else {
					ret += "Nessuno studente iscritto al corso.";
				}
			} else {
				ret += "Lista degli studenti non inizializzata correttamente.";
			}	
		} else {
			ret += "Corso non presente nel DB.";
		}

		db.commit();
		coursesMap.close();
		db.close();
		return ret;
	}

	/**
	 * Aggiunge il voto all'esame di uno studente
	 * @param studentEmail String
	 * @param courseName String
	 * @param mark Integer
	 * @return String 
	 */
	public static String addMarkToStudentExamInDB(String studentEmail, String courseName, Integer mark) {
		String ret = "";
		DB db = getUniDB();
		HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");

		if ((checkEmail(studentEmail)) && (checkCourseName(courseName))) {
			Course c = coursesMap.get(courseName);
			if (c.getStudentsList() != null) {
				if (c.isStudentThere(studentEmail)) {
					// Setting del voto dell'esame dello studente
					c.setMark(studentEmail, mark);
					// Aggiornamento del valore del corso nel DB
					coursesMap.replace(courseName, c);
					ret += "Voto inserito correttamente.";
				} else {
					ret += "Errore: studente non iscritto al corso.";
				}
			} else {
				ret += "Errore: lista degli studenti non inizializzata correttamente.";
			}
		} else {
			ret += "Errore: studente selezionato o corso selezionato non presenti nel DB.";
		}

		db.commit();
		coursesMap.close();
		db.close();
		return ret;
	}

	/**
	 * Metodo che ritorna la lista degli esami all'interno di una istanza di un utente di tipo segreteria.
	 * @param secEmail String
	 * @return ret String
	 */
	public static String viewSecretaryExamsList(String secEmail) {
		String ret = "";

		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");

		if (!usersMap.isEmpty()) {
			if (usersMap.get(secEmail) instanceof Secretary) {
				Secretary sec = (Secretary) usersMap.get(secEmail);
				for (String exam : sec.getExamsList()) {
					ret += exam + "_";
				}
			} else {
				ret += "Email passata in input associata a un utente di tipo diverso da Segreteria";
			}
		} else {
			ret += "NESSUN ACCOUNT NEL DB";
		}

		db.commit();
		usersMap.close();
		db.close();
		return ret;
	}

	/**
	 * Metodo che modifica la visibilità degli esami (un valore booleano nell'oggeto Course)
	 * @param examName String
	 * @return String
	 */
	public static String setExamsCourseVisibility(String examName) {
		DB db = getUniDB();
		HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");

		Course c = coursesMap.get(examName);

		c.setAreGradesVisibleToStudents(true);

		coursesMap.replace(c.getName(), c);

		db.commit();
		coursesMap.close();
		db.close();
		return "Visibilità modificata";
	}


	/**
	 * Metodo che ritorna la lista degli esami presenti in un'istanza di utente di tipo segreteria 
	 * @return info String
	 */
	public static String viewAllStudentsPersonalInfo() {
		String info = "";

		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");

		if (!usersMap.isEmpty()) {
			for (Entry<String, User> newUser : usersMap.entrySet()) {
				if (newUser.getValue() instanceof Student) {
					info += newUser.getValue().toString() + "\n\n";
				}
			}
		} else {
			info += "NESSUNO STUDENTE NEL DB";
		}

		db.commit();
		usersMap.close();
		db.close();
		return info;
	}

	/**
	 * Metodo che ritorna la lista dei voti degli esami di uno studente
	 * @param studentEmail String
	 * @return String
	 */
	public static String viewExamsMarksInfo(String studentEmail) {
		String ret = "";
		DB db = getUniDB();
		HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");

		if (checkEmail(studentEmail)) {
			for (Entry<String, Course> newCourse : coursesMap.entrySet()) {
				if (newCourse.getValue().isStudentThere(studentEmail)) {
					if (newCourse.getValue().getAreGradesVisibleToStudents()) {
						int index = newCourse.getValue().getStudentsList().indexOf(studentEmail);
						ret += "Corso di " + newCourse.getValue().getName() + ".\nVoto: " +
								newCourse.getValue().getExamStudentsMarks().get(index) + ""
								+ ".\n\n";
					}
				}
			}
		}

		db.commit();
		coursesMap.close();
		db.close();
		return ret;
	}


	/*
	 * METODI AUSILIARI
	 */

	/**
	 * Ritorna true se esiste gia' un utente con l'email passata in input 
	 * @param email String
	 * @return boolean
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

	/**
	 * Ritorna true se gia' esiste un dipartimento con il nome passato in input
	 * Ritorna false se non e' memorizzato nel DB nessun dipartimento con quel nome
	 * @param nomeDipartimento String
	 * @return boolean
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

	/**
	 * Ritorna true se gia' esiste un corso con il nome passato in input
	 * Ritorna false se non e' memorizzato nel DB nessun corso con quel nome
	 * @param courseName String
	 * @return boolean
	 */
	private static boolean checkCourseName(String courseName) {
		boolean check = false;

		DB db = getUniDB();
		HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");

		for (Entry<String, Course> newCourse: coursesMap.entrySet()) {
			if (newCourse.getValue().getName().equalsIgnoreCase(courseName)) {
				check = true;
				break;
			}
		}

		db.commit();
		coursesMap.close();
		db.close();
		return check;
	}

	/**
	 * Ritorna true se gia' esiste un corso con il nome passato in input && tale corso presenta già un esame instanziato.
	 * Ritorna false se non e' memorizzato nel DB nessun corso con quel nome e con un esame gia' instanziato.
	 * @param courseName String
	 * @return boolean
	 */
	private static boolean checkExam(String courseName) {
		boolean check = false;

		DB db = getUniDB();

		HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");

		for (Entry<String, Course> newCourse: coursesMap.entrySet()) {
			Course c = newCourse.getValue();
			if (c.getName().equalsIgnoreCase(courseName)) {
				if ((c.getExam() != null) 
						&& (c.getExam().getExamName().equalsIgnoreCase("Esame del corso di " + courseName))) {
					check = true;
					break;
				} // else: check --> rimane false
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

	/**
	 * Verifica se esiste l'utente nel DB
	 * @param email String
	 * @return boolean
	 */
	public static boolean checkUserExists(String email) { return checkEmail(email); }

	/**
	 * Verifica che le info personali siano diverse da null
	 * @param email String
	 * @return boolean
	 */
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

	/**
	 * Verifica che esista il dipartimento
	 * @param dep String
	 * @return boolean
	 */
	public static boolean checkDepartmentExists(String dep) { return checkDepartmentName(dep); } 

	/**
	 * Verifica che esista il corso
	 * @param course String
	 * @return boolean
	 */
	public static boolean checkCourseExists(String course) { return checkCourseName(course); }

	/**
	 * Verifica che descrizione e codocente di un corso siano uguali a quelle passate in input
	 * @param courseName String
	 * @param description String
	 * @param secondProf String
	 * @return boolean
	 */
	public static boolean checkInfoCourse(String courseName, String description, String secondProf) {
		boolean ret = false;

		DB db = getUniDB();
		HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");

		Course c = coursesMap.get(courseName);

		if (c.getDescription().equals(description) && c.getSecondProf().equals(secondProf)) {
			ret = true;
		}

		db.commit();
		coursesMap.close();
		db.close();
		return ret;
	}

	/**
	 * Verifica che esista un esame
	 * @param course String
	 * @return boolean
	 */
	public static boolean checkExamExists(String course) { return checkExam(course); }

	/**
	 * Verifica che orario, durezza e aula di un esame siano uguali a quelle passate in input
	 * @param corso String
	 * @param orario String
	 * @param durezza String
	 * @param aula String
	 * @return boolean
	 */
	public static boolean checkInfoExam(String corso, String orario, String durezza, String aula) {
		boolean ret = false;

		DB db = getUniDB();
		HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");

		Course c = coursesMap.get(corso);
		Exam e = c.getExam();

		if (e.getOrario().equals(orario) && e.getLivelloDurezza().equals(durezza) && e.getNomeAula().equals(aula)) {
			ret = true;
		}

		db.commit();
		coursesMap.close();
		db.close();
		return ret;
	}

	/**
	 * Verifica che in un corso ci sia uno studente e viceversa
	 * @param courseName String
	 * @param studentEmail String
	 * @return boolean
	 */
	public static boolean checkStudentInCourse(String courseName, String studentEmail) {
		boolean ret = false;

		DB db = getUniDB();
		HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");

		Course c = coursesMap.get(courseName);
		Student s = (Student) usersMap.get(studentEmail);

		if (c.isStudentThere(studentEmail) && s.isCourseThere(courseName)) {
			ret = true;
		}
		
		db.commit();
		coursesMap.close();
		usersMap.close();
		db.close();
		return ret;
	}
	
	/**
	 * Verifica che sia presente un esame nella lista di esami di uno studente
	 * @param studentEmail String
	 * @param examName String
	 * @return boolean
	 */ 
	public static boolean checkExamInStudent(String studentEmail, String examName) {
		boolean ret = false;

		DB db = getUniDB();
		HTreeMap<String, User> usersMap = db.getHashMap("usersMap");

		Student s = (Student) usersMap.get(studentEmail);

		if (s.isExamThere(examName)) {
			ret = true;
		}
		
		db.commit();
		usersMap.close();
		db.close();
		return ret;
	}
	
	/**
	 * Verifica che la visibilità dei voti per uno studente in un corso sia settata a true
	 * @param courseName String
	 * @return boolean
	 */
	public static boolean checkMarksVisibilityInCourse(String courseName) {
		boolean ret = false;
		
		DB db = getUniDB();
		HTreeMap<String, Course> coursesMap = db.getHashMap("coursesMap");
		
		Course c = coursesMap.get(courseName);
		
		ret = c.getAreGradesVisibleToStudents();
		
		db.commit();
		coursesMap.close();
		db.close();
		
		return ret;
	}
	
}

