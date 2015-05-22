package App;

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
 * Cette classe permet de charger et de sauvegarder un certain nombre de scores à partir d'un fichier.
 * Le nombre de score maximale à sauvegarder sera à choisir par l'utilisateut dans le constructeur. 
 * Les scores seront stockés dans une liste. Celle-ci sera triée. 
 * Seul les 'n' meilleurs scores seront stockés, 'n' étant le nombre de score maximale de la liste.
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
	 * Crée un gestionnaire de scores pouvant contenir 'nombreScoresMax' scores.
	 * @param nombreScoresMax Le nombre de scores maximale pouvant être stocké.
	 */
	public GestionScores( int nombreScoresMax ) {
	
		this.nombreScoresMax = nombreScoresMax;
		this.liste = new ArrayList<Score>();	
	
	}
	
	/**
	 * Crée un gestionnaire de score pouvant contenir 5 scores au maximum. 
	 */
	public GestionScores() {
		this(5);
	}
	
	
	/**
	 * Ajoute un score dans la liste des scores. 
	 * Le score n'est pas ajouté si la liste est pleine et si le score est inférieur à tout les autres scores stockés.
	 * 
	 * @param score Le score à ajouter.
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
	 * Affiche les scores stockés sur le terminal.
	 */
	public void afficherScores() {
		
		ListIterator<Score> iterator = this.liste.listIterator( this.liste.size() );
		
		while( iterator.hasPrevious() ) {
			System.out.println( iterator.previous().toString() );
		}
		
	}
	
	
	/**
	 * Permet de sauvegarder (écrire) le gestionnaire de scores dans le flux de sortie out.
	 * @param out Le flux de sortie ou sera sauvegardé (écrit) le gestionnaire. 
	 * @throws IOException Exception d'entrée / sortie lancée lors d'un problème d'écriture.
	 */
	public void writeObject( ObjectOutputStream out ) throws IOException {
		
		out.writeObject( this.nombreScoresMax );
		
		for( Score score : this.liste ) {
			out.writeObject(score);
		}
	
	}
	
	/**
	 * Permet de charger (lire) un gestionnaire de scores à partir d'un flux d'entré in.
	 * @param in Le flux d'entré permettant de lire et charger le gestionnaire.
	 * @throws IOException Exception d'entrée / sortie lancée lors d'un problème de lecture.
	 * @throws ClassNotFoundException Exception lancée lorsqu'il n'est pas possible de lire les attributs du gestionnaire dans le fichier.
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
				return;	// on sort de la boucle, il n'y a plus de score à charger.
			}
		}
		
	}
	
	
	/**
	 * Charge et retourne le gestionnaire de scores stocké dans le fichier passé en paramètre.
	 * @param cheminFichier Le chemin du fichier dans lequel on lit et charge le gestionnaire de scores.
	 * @return retourne le gestionnaire de scores lu.
	 * @throws GestionScoresException Lancée lorsqu'une exception apparait lors de la lecture du fichier.
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
			throw new GestionScoresException("Erreur d'entrée / sortie, impossible de charger le fichier des scores.");
		} 
		catch (ClassNotFoundException e) {
			throw new GestionScoresException("Erreur lors du chargement. Les scores n'ont pas pu être chargés car le fichier est illisible.");
		}
		
	}
	
	/**
	 * Sauvegarde les scores dans le fichier passé en paramètre.
	 * @param cheminFichier Le fichier dans lequel les scores devrotn être sauvegardés.
	 * @throws GestionScoresException Lancée lorsqu'une exception apparait lors de l'écriture des scores dans le fichier.
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
			throw new GestionScoresException("Erreur d'entrée / sortie, impossible de charger le fichier des scores.");
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
