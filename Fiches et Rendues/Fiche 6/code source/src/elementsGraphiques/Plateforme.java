package elementsGraphiques;

import org.newdawn.slick.geom.Rectangle;

import app.Ressources;

/**
 * Représente une plateforme.
 * Le personnage pourra se déplacer sur cette plateforme.
 * L'image de la plateforme se trouve dans le dossier './sprites/plateforme.png'.
 * 
 * @author Cyril Caron
 * @author Maxime Pineau
 *
 */
public class Plateforme extends ElementFixe {

	public Plateforme( int x, int y ) {
		super( x, y, 32, 32, new Rectangle( 0, 0, 31, 30 ), Ressources.Sprites.PLATEFORME );
	}
		
}
