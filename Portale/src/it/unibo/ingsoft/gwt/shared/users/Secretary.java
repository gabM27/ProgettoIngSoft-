package it.unibo.ingsoft.gwt.shared.users;

import java.io.Serializable;
import java.util.ArrayList;

public class Secretary extends User implements Serializable{
	private static final long serialVersionUID = 1L;

	private ArrayList<String> exams;

	// Costruttore
	public Secretary(String email, String password) {
		super(email,password);
		this.exams = new ArrayList<String>(20);
	}

	public ArrayList<String> getExamsList() {
		return this.exams;
	}

	public void addExam(String newExam) {
		if (!isExamThere(newExam)) {
			this.exams.add(newExam);
		}
	}

	// Elimina un esame dalla lista
	public void deleteExam(String e) {
		if (isExamThere(e)) {
			this.exams.remove(e);
		}
	}

	// Metodo che controlla se c'e' gia' un esame all'interno della lista
	private boolean isExamThere(String e) {
		boolean isThere = false;
		for (int i = 0; i < this.exams.size(); i++) {
			if (e.equalsIgnoreCase(this.exams.get(i))) {
				isThere = true;
				break;
			} // else --> isThere = false
		}
		return isThere;
	}
}
