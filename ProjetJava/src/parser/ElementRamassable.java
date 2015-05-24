/**
 * @author Pauline
 */

package parser;

public abstract class ElementRamassable extends ElementFixe {

	private int nbPoints;
	
	public ElementRamassable( int x, int y, int hauteur, int largeur, String cheminImage, int nbPoints ) {
		super( x, y, hauteur, largeur, cheminImage );
		this.nbPoints = nbPoints;
	}	
}
