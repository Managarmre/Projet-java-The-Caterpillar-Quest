import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public abstract class ElementRamassable extends ElementFixe {

	private int nbPoints = 0;
	
	public ElementRamassable(Point position, int hauteur, int largeur, Shape forme, Shape hitbox, Image imageFond, int nbPoints) {
		super(position, hauteur, largeur, forme, hitbox, imageFond);
		this.nbPoints = nbPoints;
		// TODO Auto-generated constructor stub
	}

	public int getNbPoints() {
		return nbPoints;
	}

	public void setNbPoints(int nbPoints) {
		this.nbPoints = nbPoints;
	}

}
