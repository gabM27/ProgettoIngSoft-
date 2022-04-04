package it.unibo.ingsoft.gwt.shared.domain;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import it.unibo.ingsoft.gwt.shared.users.Professor;
import it.unibo.ingsoft.gwt.shared.users.Student;

public class Course {
	// Variabili istanza
	private String name;
	private List<Professor> professors;
	private List<Student> students; // iscritti al corso --> sostengono esame; se non lo sostengono voto esame = NC (-1)
	private Exam exam; // VINCOLO: è possibile avere un UNICO esame per corso
	private List<Mark> marks; // iscritti e voti hanno lo stesso indice per collegare uno studente al relativo voto
	
	// Costruttore
	public Course(String name) {
		this.name = name;
		this.professors = new Vector<Professor>(2,2); // Primo parametro = capacita' iniziale; secondo parametro = capacita' incremento
		this.students = new Vector<Student>(100,20); // Primo parametro = capacita' iniziale; secondo parametro = capacita' incremento
		this.marks = new Vector<Mark>(100,20); // Primo parametro = capacita' iniziale; secondo parametro = capacita' incremento
	}
	
	// Getters 
	public String getName() { return this.name; } // ritorna il nome del corso
	
	public List<Professor> getProfessorsList() { return this.professors; } // ritorna la lista dei docenti
	
	public List<Student> getStudentsList() { return this.students; } // ritorna la lista degli iscritti
	
	public String printExamInfo() { // stampa le info dell'esame
		return this.exam.toString() + "\n"
				+ "Numero studenti iscritti: " + this.students.size();
	}
	
	public List<Mark> getMarks() { return this.marks; } // ritorna la lista intera di voti
	
	public Mark getStudentMark(int studentIndex) { return this.marks.get(studentIndex); } // ritorna un singolo voto di uno studente specifico
	
	// Setters
	public void setNome(String newName) { // modifica il nome del corso
		this.name = newName;
	}
	
	// Creazione esame (operazione consentita solo a un utente di tipo Docente)
	public void examSetting(Date examDate) {
		this.exam = new Exam(this.name, examDate);
	}
	
	// Aggiunge un docente al corso
	public void addProfessor(Professor p) {
		this.professors.add(p);
	}
	
	// Aggiunge un iscritto (ed un voto) al corso
	public void addStudent(Student s) {
		this.students.add(s);
		this.marks.add(this.students.indexOf(s), // indice dello studente
				new Mark(s.getID())); // nuovo voto, relativo al nuovo studente
	}
	
	@Override
	public String toString() {
		return "Nome del corso: " + this.name + ".\n"; 
	}
}
