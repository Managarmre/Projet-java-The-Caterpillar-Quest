package elementsGraphiques;

import org.newdawn.slick.geom.Rectangle;

public class Plateforme extends ElementFixe {

	public Plateforme( int x, int y ) {
		super( x, y, 32, 32, new Rectangle( 0, 0, 32, 32 ), "./sprites/plateforme.png" );
	}
		
}
