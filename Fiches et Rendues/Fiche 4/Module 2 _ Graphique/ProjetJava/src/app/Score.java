package app;

/**
 * Repr�sente le score d'un joueur.
 * Ce score est compos� du nombre de points du joueur, ainsi que du temps pass� par le joueur pour terminer le niveau. <br/>
 * Un score pourra �tre sauvegard� et charg� (Serializable). <br/>
 * Un score pourra �galement �tre compar� � autre. 
 * Le score ayant le nombre de points le plus grand sera le score sup�rieur. En cas d'�galit�, le score sup�rieur sera celui poss�dant le plus petit temps.
 * 
 * @author Maxime Pineau
 */

public class Score {

	private int nbPoints;
	private int tempsJeu;
	
	/**
	 * Cr�� un score pour un joueur selon son nombre de points et le temps �coul� en secondes pour terminer un niveau.
	 * @param nbPoints Le nombre de points que le joueur r�ussit � obtenir.
	 * @param tempsJeu Le temps pass� par le joueur pour terminer le niveau (en seconde).
	 */
	public Score(int nbPoints, int tempsJeu){
		
		this.nbPoints = nbPoints;
		this.tempsJeu = tempsJeu;
	}
	
	/**
	 * M�thode d'affichage des objets de la classe Score
	 */
	public void afficher() {
		System.out.println( this.nbPoints + " " + this.tempsJeu );
	}
	
}
