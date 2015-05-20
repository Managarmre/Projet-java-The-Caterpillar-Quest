import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public class Personnage extends ElementDeplacable {

	private static int vitesse = 3;
	
	public Personnage( int x, int y, Shape hitbox ) {
		super( x, y, 32, 32, hitbox, "./sprites/personnage.png" );	
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
	
	
	@Override
	public void afficher(GameContainer conteneur, Graphics graphique) throws SlickException {
				
		graphique.drawAnimation( this.animations[0], this.getPositionX(), this.getPositionY() );
	}

}
