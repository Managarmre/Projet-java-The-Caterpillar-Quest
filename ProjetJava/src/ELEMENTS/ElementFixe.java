package elementsGraphiques;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;


/**
 * Repr�sente un �l�ment fixe  non d�pla�able sur la fen�tre de jeu.
 * Cet �l�ment sera repr�sent� par une image non anim�.
 * 
 * @author Cyril Caron
 * @author Maxime Pineau
 *
 */
public abstract class ElementFixe extends Element {

	/**
	 * Le chemin vers l'image repr�sentant l'�l�ment.
	 */
	protected String cheminImage;
	
	
	/**
	 * L'image charg�e en m�moire. Cet Objet est � initialiser dans la m�thode initialisation().
	 */
	protected Image image;
	
	/**
	 * Cr�e un nouvel �l�ment fixe.
	 * @param x La position x de l'�l�ment fixe.
	 * @param y La position y de l'�l�ment fixe.
	 * @param hauteur La hauteur de l'�l�ment fixe.
	 * @param largeur La largeur de l'�l�ment fixe.
	 * @param hitbox La hitbox de l'�l�ment fixe.
	 * @param cheminImage Le chemin vers l'image repr�sentant l'�l�ment fixe.
	 */
	public ElementFixe( int x, int y, int hauteur, int largeur, Shape hitbox, String cheminImage ) {
		super( x, y, hauteur, largeur, hitbox );
		this.cheminImage = cheminImage;
	}
	
	@Override
	public void initialiser() throws SlickException {
		
		this.image = new Image( this.cheminImage );
	
	}
	
	@Override
	public void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException {
		
		graphique.drawImage( this.image, this.getPositionX(), this.getPositionY() );	
		
	}
		
}
