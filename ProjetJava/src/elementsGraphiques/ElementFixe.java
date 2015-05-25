package elementsGraphiques;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;


/**
 * Représente un élément fixe  non déplaçable sur la fenêtre de jeu.
 * Cet élément sera représenté par une image non animée.
 * 
 * @author Cyril Caron
 * @author Maxime Pineau
 *
 */
public abstract class ElementFixe extends Element {

	/**
	 * Le chemin vers l'image représentant l'élément.
	 */
	protected String cheminImage;
	
	
	/**
	 * L'image chargée en mémoire. Cet Objet est à initialiser dans la méthode initialisation().
	 */
	protected Image image;
	
	/**
	 * Créé un nouvel élément fixe.
	 * @param x La position x de l'élément fixe.
	 * @param y La position y de l'élément fixe.
	 * @param hauteur La hauteur de l'élément fixe.
	 * @param largeur La largeur de l'élément fixe.
	 * @param hitbox La hitbox de l'élément fixe.
	 * @param cheminImage Le chemin vers l'image représentant l'élément fixe.
	 */
	public ElementFixe( int x, int y, int hauteur, int largeur, Shape hitbox, String cheminImage ) {
		super( x, y, hauteur, largeur, hitbox );
		this.cheminImage = cheminImage;
	}
	
	@Override
	public void initialiser() throws SlickException {
		super.initialiser();
		
		this.image = new Image( this.cheminImage );
	}
	
	@Override
	public void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException {
		
		graphique.drawImage( this.image, this.getPositionX(), this.getPositionY() );	
		
	}
		
}
