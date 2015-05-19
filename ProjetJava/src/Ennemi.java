import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public abstract class Ennemi extends ElementDeplacable {

	private Point depart, arrivee;
	private boolean deplacementHorizontal;
	

	public Ennemi(Point position, int hauteur, int largeur, Shape forme, Shape hitbox, SpriteSheet[] sprites, Point depart, Point arrivee, boolean deplacementHorizontal) {
		
		super(position, hauteur, largeur, forme, hitbox, sprites);
		this.depart = depart;
		this.arrivee = arrivee;
		this.deplacementHorizontal = deplacementHorizontal;

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
	
	public boolean isDeplacementHorizontal() {
		return deplacementHorizontal;
	}

	public void setDeplacementHorizontal(boolean deplacementHorizontal) {
		this.deplacementHorizontal = deplacementHorizontal;
	}
	
	@Override
	public void seDeplacer(Point point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Point getProchainePosition() {
		// TODO Auto-generated method stub
		return null;
	}

}
