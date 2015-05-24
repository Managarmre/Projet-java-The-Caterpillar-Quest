/**
 * @author Pauline
 */

package parser;

import java.awt.Point;


public abstract class Ennemi extends ElementDeplacable 
{
	private Point depart, arrivee;	
	private String sensDeplacement;

	/**
	 * Constructeur de la classe ennemi
	 * @param x la colonne
	 * @param y la ligne
	 * @param arriveex la colonne d'arrivée (après déplacement)
	 * @param arriveey la ligne d'arrivée (après déplacement)
	 * @param sens le sens de déplacement
	 */
	public Ennemi(int x, int y, int arriveex, int arriveey, String sens) 
	{
		super(x, y);		
		this.depart = new Point(x,y);
		this.arrivee = new Point(arriveex,arriveey);
		this.sensDeplacement = sens;
	}
}
