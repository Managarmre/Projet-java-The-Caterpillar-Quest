import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;


public abstract class Element {

	private Shape forme;
	private Shape hitbox;
	
	public Element( Shape forme, Shape hitbox ){
		
		this.forme = forme;
		this.hitbox = hitbox;
	}
	
	public Element( int x, int y, int hauteur, int largeur, Shape hitbox ) {
		this( new Rectangle( x, y, largeur, hauteur ), hitbox );
	}
	
	public abstract void initialiser() throws SlickException;
	public abstract void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException;
	
	public boolean estEnCollisionAvec(Element e){
		
		if(this.hitbox.intersects(e.getHitbox()))
			return true;
		else
			return false;
	}


	public float getPositionX() {
		return this.forme.getX();
	}
	
	public float getPositionY() {
		return this.forme.getY();
	}
	
	
	
	public int getHauteur() {
		return (int) this.forme.getHeight();
	}

	public int getLargeur() {
		return (int) this.forme.getWidth();
	}

	public Shape getForme() {
		return forme;
	}

	public Shape getHitbox() {
		return hitbox;
	}
}
