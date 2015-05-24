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
	 * @param x colonne de d�part
	 * @param y ligne de d�part
	 * @param arriveex colonne d'arriv�e (apr�s d�placement)
	 * @param arriveey ligne d'arriv�e (apr�s d�placement)
	 * @param deplacementHorizontal sens de d�placement de la guepe (true = horizontal / false = vertical)
	 * @param s le sens de d�placement
	 */
	public Guepe( int x, int y, int arriveex, int arriveey, boolean deplacementHorizontal, String s ) 
	{
		super( x, y, arriveex, arriveey ,s);	
		this.deplacementHorizontal = deplacementHorizontal;
	}
	
}
