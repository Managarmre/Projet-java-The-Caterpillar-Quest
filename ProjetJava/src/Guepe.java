import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public class Guepe extends Ennemi {
	
	private boolean deplacementHorizintal;
	
	public Guepe( int x, int y, Shape hitbox, Point depart, Point arrivee, boolean deplacementHorizontal ) {
		super( x, y, 32, 32, hitbox, "./sprites/guepe.png", depart, arrivee );
		
		this.deplacementHorizintal = deplacementHorizontal;
		
		if( this.deplacementHorizintal ) this.animations = new Animation[2];	// 2 animations : aller et retour
		else this.animations = new Animation[1];	// une seule animation pour la guêpe verticale
		
		// mise à jour du sens
		
	}


	
	@Override
	public void initialiser() throws SlickException {
		
		this.sprite = new SpriteSheet( this.cheminSprite, 32, 32 );
		
		int nombreAnimations = this.sprite.getVerticalCount();
		this.animations = new Animation[ nombreAnimations ];
		
		this.animations[0] = this.chargerAnimation( 0, 0, 3 );
		if( this.deplacementHorizintal ) this.animations[1] = this.chargerAnimation( 1, 0, 3 );
		
	}
	

	@Override
	public void update( GameContainer conteneur, int delta ) throws SlickException {
		
		
		
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
