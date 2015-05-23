package app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;


/**
 * Classe permettant de sauvegarder des scores.
 * Cette classe permet de charger et de sauvegarder un certain nombre de scores � partir d'un fichier.
 * Le nombre de score maximale � sauvegarder sera � choisir par l'utilisateut dans le constructeur. 
 * Les scores seront stock�s dans une liste. Celle-ci sera tri�e. 
 * Seul les 'n' meilleurs scores seront stock�s, 'n' �tant le nombre de score maximale de la liste.
 * 
 * @author Maxime Pineau
 * @see Score
 */
public class GestionScores implements Serializable {
	
	private static final long serialVersionUID = 1217692534656696222L;
	
	/**
	 * Le nombre de scores maximale qu'il est possible de stocker.
	 */
	private int nombreScoresMax;	
	
	/**
	 * La liste contenant les scores.
	 */
	private ArrayList<Score> liste;
	
	/**
	 * Cr�e un gestionnaire de scores pouvant contenir 'nombreScoresMax' scores.
	 * @param nombreScoresMax Le nombre de scores maximale pouvant �tre stock�.
	 */
	public GestionScores( int nombreScoresMax ) {
	
		this.nombreScoresMax = nombreScoresMax;
		this.liste = new ArrayList<Score>();	
	
	}
	
	/**
	 * Cr�e un gestionnaire de score pouvant contenir 5 scores au maximum. 
	 */
	public GestionScores() {
		this(5);
	}
	
	
	/**
	 * Ajoute un score dans la liste des scores. 
	 * Le score n'est pas ajout� si la liste est pleine et si le score est inf�rieur � tout les autres scores stock�s.
	 * 
	 * @param score Le score � ajouter.
	 */
	public void ajouterScore( Score score ) {
		
		this.liste.add(score);	// on ajoute le score dans la liste
				
		Collections.sort( this.liste );	// on trie la liste 
				
		// on retire les scores en trop dans la liste
		while( this.liste.size() > this.nombreScoresMax ) {
			this.liste.remove(0);	// le premier score correspond au score le plus petit
		}
		
	}
	
	
	/**
	 * Affiche les scores stock�s sur le terminal.
	 */
	public void afficherScores() {
		
		ListIterator<Score> iterator = this.liste.listIterator( this.liste.size() );
		
		while( iterator.hasPrevious() ) {
			System.out.println( iterator.previous().toString() );
		}
		
	}
	
	
	/**
	 * Permet de sauvegarder (�crire) le gestionnaire de scores dans le flux de sortie out.
	 * @param out Le flux de sortie ou sera sauvegard� (�crit) le gestionnaire. 
	 * @throws IOException Exception d'entr�e / sortie lanc�e lors d'un probl�me d'�criture.
	 */
	public void writeObject( ObjectOutputStream out ) throws IOException {
		
		out.writeObject( this.nombreScoresMax );
		
		for( Score score : this.liste ) {
			out.writeObject(score);
		}
	
	}
	
	/**
	 * Permet de charger (lire) un gestionnaire de scores � partir d'un flux d'entr� in.
	 * @param in Le flux d'entr� permettant de lire et charger le gestionnaire.
	 * @throws IOException Exception d'entr�e / sortie lanc�e lors d'un probl�me de lecture.
	 * @throws ClassNotFoundException Exception lanc�e lorsqu'il n'est pas possible de lire les attributs du gestionnaire dans le fichier.
	 */
	public void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
		
		this.nombreScoresMax = (int) in.readObject();
		
		Score score = null;
		
		this.liste = new ArrayList<Score>();
				
		while(true) {
			
			try{		
				score = (Score) in.readObject();
				this.ajouterScore(score);
			}
			catch( Exception e ) {
				return;	// on sort de la boucle, il n'y a plus de score � charger.
			}
		}
		
	}
	
	
	/**
	 * Charge et retourne le gestionnaire de scores stock� dans le fichier pass� en param�tre.
	 * @param cheminFichier Le chemin du fichier dans lequel on lit et charge le gestionnaire de scores.
	 * @return retourne le gestionnaire de scores lu.
	 * @throws GestionScoresException Lanc�e lorsqu'une exception apparait lors de la lecture du fichier.
	 */
	public static GestionScores chargerScores( String cheminFichier ) throws GestionScoresException {
		
		try {
		
			FileInputStream fis = new FileInputStream(cheminFichier);
			ObjectInputStream in = new ObjectInputStream(fis);
			
			GestionScores scores = (GestionScores) in.readObject();
			
			in.close();
			
			return scores;
		
		}
		catch( FileNotFoundException fnfe ) {
			throw new GestionScoresException("Le fichier n'existe pas");
		}
		catch( IOException ioe ) {
			throw new GestionScoresException("Erreur d'entr�e / sortie, impossible de charger le fichier des scores.");
		} 
		catch (ClassNotFoundException e) {
			throw new GestionScoresException("Erreur lors du chargement. Les scores n'ont pas pu �tre charg�s car le fichier est illisible.");
		}
		
	}
	
	/**
	 * Sauvegarde les scores dans le fichier pass� en param�tre.
	 * @param cheminFichier Le fichier dans lequel les scores devrotn �tre sauvegard�s.
	 * @throws GestionScoresException Lanc�e lorsqu'une exception apparait lors de l'�criture des scores dans le fichier.
	 */
	public void sauvegarderScores( String cheminFichier ) throws GestionScoresException {
		
		try {
			FileOutputStream fos = new FileOutputStream(cheminFichier);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			
			out.writeObject(this);	
			
			out.close();
		}
		catch( FileNotFoundException fnfe ) {
			throw new GestionScoresException("Le fichier n'existe pas");
		}
		catch( IOException ioe ) {
			throw new GestionScoresException("Erreur d'entr�e / sortie, impossible de charger le fichier des scores.");
		}
		
	}
	
	
	/**
	 * Classe permettant de tester la classe GestionScore.
	 * @author Maxime
	 */
	public static class Test {
		public static void main( String[] args ) {
			
			GestionScores scores = new GestionScores();
			
			for( int i = 0; i < 10; i++ ) {
				scores.ajouterScore( new Score("test", i, i*10) );
				scores.ajouterScore( new Score("test", i, i*100) );
			}
						
			scores.afficherScores();			
		}
	}
	
	
}
