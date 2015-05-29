package elementsGraphiques;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class Hitbox {
	
	private Shape formeHitbox;
	private Point decalageOrigine;	// décalage par rapport à l'origine (l'élément)
	
	
	public Hitbox( Shape formeHitbox, Point decalageOrigine ) {
		this.formeHitbox = formeHitbox;
		this.decalageOrigine = decalageOrigine;
	}
	
	public Hitbox( Shape formeHitbox, float decalageOrigineX, float decalageOrigineY ) {
		this( formeHitbox, new Point(decalageOrigineX, decalageOrigineY) );
	}
	
	public Hitbox( Shape formeHitbox ) {
		this( formeHitbox, formeHitbox.getX(), formeHitbox.getY() );
	}
	
	public Hitbox( float[] points ) {
		this( new Polygon(points) );
	}
	
	
	
	public boolean intersects( Hitbox hitbox ) {
		return this.formeHitbox.intersects( hitbox.getForme() );
	}
	
	public Shape getForme() {
		return this.formeHitbox;
	}
	
	
	public float getX() {
		return this.formeHitbox.getX();
	}
	
	public float getY() {
		return this.formeHitbox.getY();
	}
	
	public void setX( float x ) {
		this.formeHitbox.setX( this.decalageOrigine.getX() + x );
	}
	
	public void setY( float y ) {
		this.formeHitbox.setY( this.decalageOrigine.getY() + y );
	}
	
	public void setPosition( float x, float y ) {
		this.setX(x);
		this.setY(y);
	}
	
	public void setPosition( Point position ) {
		this.setPosition( position.getX(), position.getY() );
	}
	
	
}
