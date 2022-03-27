package it.unibo.ingsoft.gwt.shared.usersfacade;

import it.unibo.ingsoft.gwt.client.Portale;
import it.unibo.ingsoft.gwt.shared.domain.Course;
import it.unibo.ingsoft.gwt.shared.domain.Department;

/*
 * Classe facciata utilizzata da uno studente 
 * per interagire con le operazioni
 * a lui consentite, senza dipendere direttamente 
 * dalle classi di basso livello che processano le 
 * operazioni stesse.
 * 
 * Uno studente puo':
 * 1. Visualizzare i corsi disponibili.
 * 
 * 2. Iscriversi ad un corso.
 * 
 * 3. Registrarsi per un esame di un corso.
 * 
 * 4. Visualizzare le proprie informazioni personali.
 * 
 * 5. Visualizzare i voti degli esami svolti.
 * 
 */

public class StudentFacade {
	
	// Costruttore
	public StudentFacade() {
	}
	
	/*
	 * Stampa le informazioni di tutti i corsi (per ogni dipartimento dell'università).
	 * */
//	protected String printAllCourses() {
//		String s = "";
//		for (Department d : Portale.uni.getUniDB().getDepartmentsMap()) {
//			for (Course c : d.getCoursesList()) {
//				s += c.toString();
//			}
//		}
//		return s;
//	}
	
	/*
	 * Registra uno studente al corso che si passa in input.
	 * Il corso viene verificato da un metodo nella classe University.
	 */
	protected void courseRegistration(String courseName) {
		// check course name in university tramite apposito metodo.
	}
	
}
