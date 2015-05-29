package elementsGraphiques;

import org.newdawn.slick.geom.Polygon;


/**
 * Représente une cerise.
 * L'image de la cerise se trouve dans le dossier './sprites/cerise.png'.
 * La cerise vaut 1 point.
 * 
 * @author Cyril Caron
 * @author Maxime Pineau
 *
 */
public class Cerise extends ElementRamassable {

	/**
	 * La suite de points (x,y) permettant de contruire la hitbox de la cerise.
	 */
	private static final float[] points = { 6, 20, 23, 7, 23, 25 }; 	// construit un triangle moullant la forme de la cerise.
	
	/**
	 * Créé une nouvelle cerise. La cerise vaut 1 point.
	 * L'image et la forme de la hitbox sont connues.
	 * @param x La position x de la cerise.
	 * @param y La position y de la cerise.
	 */
	public Cerise( int x, int y ) {
		super( x, y, 32, 32, new Polygon( Cerise.points ), "./sprites/cerise.png", 1 );
	}
	
	
}
