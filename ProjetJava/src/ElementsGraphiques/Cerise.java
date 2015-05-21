package ElementsGraphiques;

import org.newdawn.slick.geom.Polygon;


public class Cerise extends ElementRamassable {

	public static float[] points = { 6, 20, 23, 7, 23, 25 };
	
	public Cerise( int x, int y ) {
		super( x, y, 32, 32, new Polygon( Cerise.points.clone() ), "./sprites/cerise.png", 1 );
	}
	
	
}
