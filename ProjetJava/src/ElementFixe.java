/**
 * @author Pauline
 */
public abstract class ElementFixe extends Element {

	protected String cheminImage;
	
	public ElementFixe( int x, int y, int hauteur, int largeur, String cheminImage ) {
		super( x, y, hauteur, largeur);
		this.cheminImage = cheminImage;
	}
}
