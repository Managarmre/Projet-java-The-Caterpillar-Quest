import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public abstract class ElementDeplacable extends Element {

	private SpriteSheet[] sprites;
	
	public ElementDeplacable(Point position, int hauteur, int largeur, Shape forme, Shape hitbox, SpriteSheet[] sprites) {
		super(position, hauteur, largeur, forme, hitbox);
		this.sprites = sprites;
	}
	
	public abstract void seDeplacer(Point point);
	
	public abstract Point getProchainePosition();

}
