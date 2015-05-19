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
		
		return false;
	}
}
