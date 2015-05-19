import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public abstract class ElementFixe extends Element {

	private Image image;
	
	public ElementFixe(Point position, int hauteur, int largeur, Shape forme, Shape hitbox, Image imageFond) {
		super(position, hauteur, largeur, forme, hitbox);
		this.image = imageFond;
	}

}
