import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public class Personnage extends ElementDeplacable {

	private static int vitesse = 3;

	private int nbCerises = 0;

	
	public Personnage( int x, int y, Shape hitbox ) {
		super( x, y, 32, 32, hitbox, "./sprites/personnage.png" );	
		
		this.animations = new Animation[6];
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
	
	public void gestionCollision(){
		
	}
	

	@Override
	public void initialiser() throws SlickException {
		
		this.sprite = new SpriteSheet( this.cheminSprite, 32, 32 );
		
		this.animations[0] = this.chargerAnimation( 0, 0, 0 );
		
	}
	
	
	@Override
	public void update( GameContainer conteneur, int delta ) throws SlickException {
		
		
	}
	
	
	@Override
	public void afficher(GameContainer conteneur, Graphics graphique) throws SlickException {
				
		graphique.drawAnimation( this.animations[0], this.getPositionX(), this.getPositionY() );
	}

	

}
