package elementsGraphiques;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;


public abstract class ElementFixe extends Element {

	protected String cheminImage;
	protected Image image;
	
	public ElementFixe( int x, int y, int hauteur, int largeur, Shape hitbox, String cheminImage ) {
		super( x, y, hauteur, largeur, hitbox );
		this.cheminImage = cheminImage;
	}
	
	public void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException {
		
		super.afficher( conteneur, graphique );
		graphique.drawImage( this.image, this.getPositionX(), this.getPositionY() );	
		
	}
	
	public void initialiser() throws SlickException {
		
		this.image = new Image( this.cheminImage );
		
	}
	
}
