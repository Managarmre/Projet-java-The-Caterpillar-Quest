import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public class Plateforme extends ElementFixe {

	public Plateforme( int x, int y, Shape hitbox ) {
		super( x, y, 32, 32, hitbox, "./sprites/plateforme.png" );		
	}
		
}
