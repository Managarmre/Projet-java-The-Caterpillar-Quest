import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public class Guepe extends Ennemi {
	
	private boolean deplacementHorizontal;
	
	public Guepe( int x, int y, Shape hitbox, Point depart, Point arrivee, boolean deplacementHorizontal ) {
		super( x, y, 32, 32, hitbox, "./sprites/guepe.png", depart, arrivee );
		
		this.deplacementHorizontal = deplacementHorizontal;
		
		// il y a deux types d'animations pour la guêpe, l'aller et le retour
		//this.animations = new Animation[2];
		
	}

	@Override
	public void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException {

		graphique.drawAnimation( this.animations[0], this.getPositionX(), this.getPositionY() );
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
