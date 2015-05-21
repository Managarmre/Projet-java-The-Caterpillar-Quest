import org.newdawn.slick.Color;
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
	private Point positionOrigineHitbox;
	
	public Element( Point position, int hauteur, int largeur, Shape hitbox ){
		
		this.position = position;
		this.hauteur = hauteur;
		this.largeur = largeur;
		
		// la position de la hitbox est donnée par rapport à l'image, et non à la fenêtre
		// on modifie donc cette hitbox pour quelle ait les bonnes positions
		this.positionOrigineHitbox = new Point( hitbox.getX(), hitbox.getY() );
		this.hitbox = hitbox;
		this.hitbox.setX( this.positionOrigineHitbox.getX() + position.getX() );
		this.hitbox.setY( this.positionOrigineHitbox.getY() + position.getY() );
		
	}
	
	public Element( int x, int y, int hauteur, int largeur, Shape hitbox ) {
		this( new Point(x, y) , largeur, hauteur, hitbox );
	}
	
	public abstract void initialiser() throws SlickException;
	
	public void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException {
		
		Shape hitbox = this.getHitbox();	
		graphique.setColor( Color.red );
		graphique.draw(hitbox);
		
	}
	
	public boolean estEnCollisionAvec(Element e){
		
		if( this.hitbox.intersects(e.getHitbox()) )
			return true;
		else
			return false;
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
		
		// bizarrerie du code de la librairie
		this.hitbox.setY( this.positionOrigineHitbox.getY() + x );

	}
	
	public void setPositionY( float y ) {
		this.position.setY(y);
		
		// bizarrerie du code de la librairie
		this.hitbox.setX( this.positionOrigineHitbox.getX() + y );
	}
	
	public void setPosition( float x, float y ) {
		this.setPositionX(x);
		this.setPositionY(y);
	}
	
	public void setPosition( double x, double y ) {
		this.setPosition( (float)x, (float)y );
	}
	
	public void setPosition( Point position ) {
		this.position = position;
		this.hitbox.setY( this.positionOrigineHitbox.getX() + position.getX() );
		this.hitbox.setX( this.positionOrigineHitbox.getY() + position.getY() );

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
