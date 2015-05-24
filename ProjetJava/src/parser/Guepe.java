/**
 * @author Pauline
 */

package parser;

import java.awt.Point;

public class Guepe extends Ennemi 
{
	private boolean deplacementHorizontal;
	
	/**
	 * Constructeur de la classe Guepe
	 * @param x colonne de départ
	 * @param y ligne de départ
	 * @param arriveex colonne d'arrivée (après déplacement)
	 * @param arriveey ligne d'arrivée (après déplacement)
	 * @param deplacementHorizontal sens de déplacement de la guepe (true = horizontal / false = vertical)
	 * @param s le sens de déplacement
	 */
	public Guepe( int x, int y, int arriveex, int arriveey, boolean deplacementHorizontal, String s ) 
	{
		super( x, y, arriveex, arriveey ,s);	
		this.deplacementHorizontal = deplacementHorizontal;
	}
	
}
