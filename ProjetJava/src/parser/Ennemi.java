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
	 * @param arriveex la colonne d'arriv�e (apr�s d�placement)
	 * @param arriveey la ligne d'arriv�e (apr�s d�placement)
	 * @param sens le sens de d�placement
	 */
	public Ennemi(int x, int y, int arriveex, int arriveey, String sens) 
	{
		super(x, y);		
		this.depart = new Point(x,y);
		this.arrivee = new Point(arriveex,arriveey);
		this.sensDeplacement = sens;
	}
}
