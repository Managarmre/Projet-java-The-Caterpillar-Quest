/**
 * @author Pauline
 */

package parser;

import java.awt.Point;

public abstract class Element 
{
	/**
	 * Attributs de la classe Element
	 */
	private Point position;
	
	/**
	 * Constructeur de la classe Element
	 * @param p la position de l'�l�ment
	 */
	public Element(Point p)
	{
		this.position = p;
	}
	
	/**
	 * Constructeur de la classe Element
	 * @param x position colonne de l'�l�ment
	 * @param y position ligne de l'�l�ment
	 */
	public Element( int x, int y) 
	{
		this(new Point( x, y));
	}	
}
