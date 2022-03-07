package it.unibo.ingsoft.gwt.shared.domain;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import it.unibo.ingsoft.gwt.shared.Docente;
import it.unibo.ingsoft.gwt.shared.Studente;

public class Corso {
	// Variabili istanza
	private String nome;
	private List<Docente> docenti;
	private List<Studente> iscritti; // iscritti al corso --> sostengono esame; se non lo sostengono voto esame = NC
	private Esame esame; // VINCOLO: è possibile avere un UNICO esame per corso
	private List<Voto> voti; // iscritti e voti hanno lo stesso indice per collegare uno studente al relativo voto
	
	// Costruttore
	public Corso(String nome) {
		this.nome = nome;
		this.docenti = new Vector<Docente>(2,2); // Primo parametro = capacità iniziale; secondo parametro = capacità incremento
		this.iscritti = new Vector<Studente>(100,20); // Primo parametro = capacità iniziale; secondo parametro = capacità incremento
		this.voti = new Vector<Voto>(100,20); // Primo parametro = capacità iniziale; secondo parametro = capacità incremento
	}
	
	// Getters 
	public String getNome() { return this.nome; } // ritorna il nome del corso
	
	public List<Docente> getListaDocenti() { return this.docenti; } // ritorna la lista dei docenti
	
	public List<Studente> getListaIscritti() { return this.iscritti; } // ritorna la lista degli iscritti
	
	public String printInfoEsame() { // stampa le info dell'esame
		return this.esame.toString() + "\n"
				+ "Numero studenti iscritti: " + this.iscritti.size();
	}
	
	public List<Voto> getListaVoti() { return this.voti; } // ritorna la lista intera di voti
	
	public Voto getVotoStudente(int indexStudente) { return this.voti.get(indexStudente); } // ritorna un singolo voto di uno studente specifico
	
	// Setters
	public void setNome(String newNome) { // modifica il nome del corso
		this.nome = newNome;
	}
	
	// Creazione esame (operazione consentita solo a un utente di tipo Docente)
	public void settingEsame(Date dataEsame) {
		this.esame = new Esame(this.nome, dataEsame);
	}
	
	// Aggiunge un docente al corso
	public void aggiungiDocente(Docente d) {
		this.docenti.add(d);
	}
	
	// Aggiunge un iscritto (ed un voto) al corso
	public void aggiungiStudente(Studente s) {
		this.iscritti.add(s);
		this.voti.add(this.iscritti.indexOf(s), // indice dello studente
				new Voto(s.getMatricola())); // nuovo voto, relativo al nuovo studente
	}
	
}
