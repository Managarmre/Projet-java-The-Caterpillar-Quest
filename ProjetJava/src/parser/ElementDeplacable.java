/**
 * @author Pauline
 */

package parser;

public abstract class ElementDeplacable extends Element {
	
	protected String cheminSprite;
	
	
	public ElementDeplacable( int x, int y, int hauteur, int largeur, String cheminSprite ) {
		super( x, y, hauteur, largeur);
		
		this.cheminSprite = cheminSprite;
	}
}
