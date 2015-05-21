
import org.newdawn.slick.geom.Point;


public abstract class Element {

	private Point position;
	private int hauteur;
	private int largeur;
	
	public Element( Point forme, int hauteur, int largeur){
		
		this.position = forme;
		this.hauteur = hauteur;
		this.largeur = largeur;
		System.out.println(this.getClass());
	}
	
	public Element( int x, int y, int hauteur, int largeur) {
		this( new Point( x, y) , largeur, hauteur);
	}
	
}
