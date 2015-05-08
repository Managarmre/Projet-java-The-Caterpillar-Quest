
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.TreeSet;

import javax.annotation.processing.FilerException;
import javax.swing.text.html.HTMLDocument.Iterator;



public class GestionScores implements Serializable {
	
	private int tailleMax;
	private ArrayList<Score> liste;
	
	public GestionScores( int tailleMax ) {
	
		this.tailleMax = tailleMax;
		this.liste = new ArrayList<Score>();	
	
	}
	
	
	public GestionScores() {
		this(5);
	}
	
	public void ajouterScore( Score score ) {
		
		this.liste.add(score);
				
		Collections.sort( this.liste );
		
		while( this.liste.size() > this.tailleMax ) {
			this.liste.remove(0);
		}
		
	}
	
	
	public void afficherScores() {
		
		ListIterator<Score> iterator = this.liste.listIterator( this.liste.size() );
		
		while( iterator.hasPrevious() ) {
			System.out.println( iterator.previous().toString() );
		}
		
	}
	
	
	public void writeObject( ObjectOutputStream out ) throws IOException {
		out.writeObject( this.tailleMax );
		//out.writeObject( this.liste );
		for( Score score : this.liste ) {
			out.writeObject(score);
		}
	}
	
	public void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
		
		this.tailleMax = (int) in.readObject();
		
		Score score = null;
		//this.liste = (TreeSet<Score>)in.readObject();
		this.liste = new ArrayList<Score>();
				
		while(true) {
			
			try{		
				score = (Score) in.readObject();
				this.ajouterScore(score);
			}
			catch( Exception e ) {
				return;	// on sort de la boucle
			}
		}
		
	}
	
	
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
