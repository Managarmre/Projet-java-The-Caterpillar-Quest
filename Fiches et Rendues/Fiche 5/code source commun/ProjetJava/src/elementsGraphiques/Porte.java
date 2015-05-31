package elementsGraphiques;

import org.newdawn.slick.geom.Rectangle;

import app.Ressources;

/**
 * Représente une porte.
 * La partie se termine lorsque le personnage du joueur arrive à une porte.
 * L'image de la porte se trouve dans le dossier './sprites/porte.png'.
 * 
 * @author Cyril Caron
 * @author Maxime Pineau
 *
 */
public class Porte extends ElementFixe {

	/**
	 * Créé une nouvelle porte
	 * @param x La position x de la porte.
	 * @param y La position y de la porte.
	 */
	public Porte( int x, int y ) {
		super( x, y - 32, 64, 32, new Rectangle(8, 16, 16, 48), Ressources.Sprites.PORTE );
		// on diminue y de 32 car : la porte est représenté dans la carte.map par un caractère de 32x32, ce qui décale la position.
	}

	
}
