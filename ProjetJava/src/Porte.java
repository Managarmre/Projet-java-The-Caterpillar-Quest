import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public class Porte extends ElementFixe {

	public Porte( int x, int y, Shape hitbox ) {
		super( x, y, 64, 32, hitbox, "./sprites/porte.png" );
	}

	
}
