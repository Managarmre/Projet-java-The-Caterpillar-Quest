package App;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Représente le score d'un joueur.
 * Ce score est composé du nombre de points du joueur, ainsi que tu temps passé par le joueur pour terminer le niveau. <br/>
 * Un score pourra être sauvegarder et charger (Serializable). <br/>
 * Un score pourra également être comparer à autre. 
 * Le score ayant le nombre de points de plus grand sera le score suppérieur. En cas d'égalité, le score suppérieur sera celui possédant le plus petit temps.
 * 
 * @author Maxime
 * 
 */
public class Score implements Comparable<Score>, Serializable {
	
	private static final long serialVersionUID = -443166922353306614L;
	
	private String nomJoueur;
	private int nbPoints;
	private int tempsEcoule;
	
	/**
	 * Crée un score pour un joueur selon son nombre de points et le temps écoulé en secondes pour terminé un niveau.
	 * @param nomJoueur Le nom du joueur.
	 * @param nbPoint Le nombre de points que le joueur réussi à obtenir.
	 * @param tempsEcoule Le temps passé par le joueur pour terminer le niveau en seconde.
	 */
	public Score( String nomJoueur, int nbPoint, int tempsEcoule ) {
		this.nomJoueur = nomJoueur;
		this.nbPoints = nbPoint;
		this.tempsEcoule = tempsEcoule;
	}

	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "" + this.nomJoueur + "  nbPoints : " + this.nbPoints + ", duree : " + this.tempsEcoule;
	}

	@Override
	public int compareTo(Score score) {
		/*
		 * this > arg0 	: retourne positif
		 * this = arg0 	: retourne 0
		 * this < arg0 : retourne negatif
		 */
	
		// on compare d'abord avec le nombre de points
		if( this.nbPoints > score.nbPoints ) return 1;
		else if( this.nbPoints < score.nbPoints ) return -1;
		
		// puis avec la durée
		if( this.tempsEcoule < score.tempsEcoule ) return 1;
		else if( this.tempsEcoule == score.tempsEcoule ) return 0;
		else return -1;
	}


	
	/**
	 * Permet de sauvegarder (écrire) le score dans le flux de sortie out.
	 * @param out Le flux de sortie ou sera sauvegardé (écrit) le score. 
	 * @throws IOException Exception d'entrée / sortie lancée lors d'un problème d'écriture.
	 */
	public void writeObject( ObjectOutputStream out ) throws IOException {
		out.writeObject( this.nomJoueur );
		out.writeObject( this.nbPoints );
		out.writeObject( this.tempsEcoule );
	}
	
	/**
	 * Permet de charger (lire) un score à partir d'un flux d'entré in.
	 * @param in Le flux d'entré permettant de lire et charger le score.
	 * @throws IOException Exception d'entrée / sortie lancée lors d'un problème de lecture.
	 * @throws ClassNotFoundException Exception lancée lorsqu'il n'est pas possible de lire les attributs du score dans le fichier.
	 */
	public void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
		this.nomJoueur = (String) in.readObject();
		this.nbPoints = (int) in.readObject();
		this.tempsEcoule = (int) in.readObject();
	}
	
	
}
