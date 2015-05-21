package ElementsGraphiques;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public abstract class Ennemi extends ElementDeplacable {

	private Point depart, arrivee;	

	public Ennemi( int x, int y, int hauteur, int largeur, Shape hitbox, String cheminSprite, Point depart, Point arrivee ) {
		super( x, y, hauteur, largeur, hitbox, cheminSprite );
		
		this.depart = depart;
		this.arrivee = arrivee;
	}

	public Point getDepart() {
		return depart;
	}

	public void setDepart(Point depart) {
		this.depart = depart;
	}

	public Point getArrivee() {
		return arrivee;
	}

	public void setArrivee(Point arrivee) {
		this.arrivee = arrivee;
	}
	
	
}
