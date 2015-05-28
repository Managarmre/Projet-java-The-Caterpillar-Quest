package elementsGraphiques;

import org.newdawn.slick.geom.Rectangle;

/**
 * Repr�sente une plateforme.
 * Le personnage pourra se d�placer sur cette plateforme.
 * L'image de la plateforme se trouve dans le dossier './sprites/plateforme.png'.
 * 
 * @author Cyril Caron
 * @author Maxime Pineau
 *
 */
public class Plateforme extends ElementFixe {

	public Plateforme( int x, int y ) {
		super( x, y, 32, 32, new Rectangle( 0, 0, 30, 30 ), "./sprites/plateforme.png" );
	}
		
}
