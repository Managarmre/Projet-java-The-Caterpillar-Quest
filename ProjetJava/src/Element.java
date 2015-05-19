import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public abstract class Element {

	private Point position;
	private int hauteur = 0, largeur = 0;
	private Shape forme;
	private Shape hitbox;
	
	public Element(Point position, int hauteur, int largeur, Shape forme, Shape hitbox){
		this.position = position;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.forme = forme;
		this.hitbox = hitbox;
	}
	
	public void afficher(){
		
	}
	
	public boolean estEnCollisionAvec(Element e){
		
		if(this.hitbox.intersects(e.getHitbox()))
			return false;
		else
			return true;
	}

	public Point getPosition() {
		return position;
	}

	public int getHauteur() {
		return hauteur;
	}

	public int getLargeur() {
		return largeur;
	}

	public Shape getForme() {
		return forme;
	}

	public Shape getHitbox() {
		return hitbox;
	}
}
