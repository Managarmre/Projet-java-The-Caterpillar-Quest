package app;

/**
 * Représente le score d'un joueur.
 * Ce score est composé du nombre de points du joueur, ainsi que du temps passé par le joueur pour terminer le niveau. <br/>
 * Un score pourra être sauvegardé et chargé (Serializable). <br/>
 * Un score pourra également être comparé à autre. 
 * Le score ayant le nombre de points le plus grand sera le score supérieur. En cas d'égalité, le score supérieur sera celui possédant le plus petit temps.
 * 
 * @author Maxime Pineau
 */

public class Score {

	private int nbPoints;
	private int tempsJeu;
	
	/**
	 * Créé un score pour un joueur selon son nombre de points et le temps écoulé en secondes pour terminer un niveau.
	 * @param nbPoints Le nombre de points que le joueur réussit à obtenir.
	 * @param tempsJeu Le temps passé par le joueur pour terminer le niveau (en seconde).
	 */
	public Score(int nbPoints, int tempsJeu){
		
		this.nbPoints = nbPoints;
		this.tempsJeu = tempsJeu;
	}
	
	/**
	 * Méthode d'affichage des objets de la classe Score
	 */
	public void afficher() {
		System.out.println( this.nbPoints + " " + this.tempsJeu );
	}
	
}
