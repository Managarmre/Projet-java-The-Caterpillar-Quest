package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Repr�sente le score d'un joueur.
 * Ce score est compos� du nombre de points du joueur, ainsi que tu temps pass� par le joueur pour terminer le niveau. <br/>
 * Un score pourra �tre sauvegarder et charger (Serializable). <br/>
 * Un score pourra �galement �tre comparer � autre. 
 * Le score ayant le nombre de points de plus grand sera le score supp�rieur. En cas d'�galit�, le score supp�rieur sera celui poss�dant le plus petit temps.
 * 
 * @author Maxime Pineau
 * 
 */
public class Score implements Comparable<Score>, Serializable {
	
	private static final long serialVersionUID = -443166922353306614L;
	
	private String nomJoueur;
	private int nbPoints;
	private int tempsEcoule;
	
	/**
	 * Cr�e un score pour un joueur selon son nombre de points et le temps �coul� en secondes pour terminer un niveau.
	 * @param nomJoueur Le nom du joueur.
	 * @param nbPoint Le nombre de points que le joueur r�ussi � obtenir.
	 * @param tempsEcoule Le temps pass� par le joueur pour terminer le niveau en seconde.
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
		
		// puis avec la dur�e
		if( this.tempsEcoule < score.tempsEcoule ) return 1;
		else if( this.tempsEcoule == score.tempsEcoule ) return 0;
		else return -1;
	}


	
	/**
	 * Permet de sauvegarder (�crire) le score dans le flux de sortie out.
	 * @param out Le flux de sortie ou sera sauvegard� (�crit) le score. 
	 * @throws IOException Exception d'entr�e / sortie lanc�e lors d'un probl�me d'�criture.
	 */
	public void writeObject( ObjectOutputStream out ) throws IOException {
		out.writeObject( this.nomJoueur );
		out.writeObject( this.nbPoints );
		out.writeObject( this.tempsEcoule );
	}
	
	/**
	 * Permet de charger (lire) un score � partir d'un flux d'entr� in.
	 * @param in Le flux d'entr� permettant de lire et charger le score.
	 * @throws IOException Exception d'entr�e / sortie lanc�e lors d'un probl�me de lecture.
	 * @throws ClassNotFoundException Exception lanc�e lorsqu'il n'est pas possible de lire les attributs du score dans le fichier.
	 */
	public void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
		this.nomJoueur = (String) in.readObject();
		this.nbPoints = (int) in.readObject();
		this.tempsEcoule = (int) in.readObject();
	}
	
	
}
