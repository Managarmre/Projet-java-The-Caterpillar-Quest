import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public class Personnage extends ElementDeplacable {

	public Personnage(Point position, int hauteur, int largeur, Shape forme,
			Shape hitbox, SpriteSheet[] sprites) {
		super(position, hauteur, largeur, forme, hitbox, sprites);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void seDeplacer(Point point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Point getProchainePosition() {
		// TODO Auto-generated method stub
		return null;
	}

}
