
import org.newdawn.slick.geom.Shape;


public abstract class ElementRamassable extends ElementFixe {

	private int nbPoints;
	
	public ElementRamassable( int x, int y, int hauteur, int largeur, Shape hitbox, String cheminImage, int nbPoints ) {
		super( x, y, hauteur, largeur, hitbox, cheminImage );
		this.nbPoints = nbPoints;
	}

	public int getNbPoints() {
		return nbPoints;
	}

	public void setNbPoints(int nbPoints) {
		this.nbPoints = nbPoints;
	}

	
	
}
