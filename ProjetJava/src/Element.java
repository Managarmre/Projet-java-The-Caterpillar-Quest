import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public abstract class Element {

	private Point position;
	private int hauteur;
	private int largeur;
	private Shape hitbox;
	
	public Element( Point forme, int hauteur, int largeur, Shape hitbox ){
		
		this.position = forme;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.hitbox = hitbox;
	}
	
	public Element( int x, int y, int hauteur, int largeur, Shape hitbox ) {
		this( new Point( x, y) , largeur, hauteur, hitbox );
	}
	
	public abstract void initialiser() throws SlickException;
	public abstract void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException;
	
	public boolean estEnCollisionAvec(Element e){
		
	//	if(this.hitbox.intersects(e.getHitbox()))
			return true;
	/*	else
			return false;*/
	}


	public float getPositionX() {
		return this.position.getX();
	}
	
	public float getPositionY() {
		return this.position.getY();
	}
	

	public Point getPosition() {
		return this.position;
	}
	
	public void setPositionX( float x ) {
		this.position.setX(x);
	}
	
	public void setPositionY( float y ) {
		this.position.setY(y);
	}
	
	public void setPosition( Point position ) {
		this.position = position;
	}
	
	public void setPosition( float x, float y ) {
		this.position.setX( x );
		this.position.setY( y );
	}

	public int getHauteur() {
		return this.hauteur;
	}

	public int getLargeur() {
		return this.largeur;
	}


	public Shape getHitbox() {
		return hitbox;
	}
}
