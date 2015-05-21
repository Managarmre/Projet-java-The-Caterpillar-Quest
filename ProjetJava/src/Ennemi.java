import java.awt.Point;


public abstract class Ennemi extends ElementDeplacable {

	private Point depart, arrivee;	

	public Ennemi( int x, int y, int hauteur, int largeur, String cheminSprite, int arriveex, int arriveey ) {
		super( x, y, hauteur, largeur, cheminSprite );
		
		this.depart = new Point(x,y);
		this.arrivee = new Point(arriveex,arriveey);
	}
	
}
