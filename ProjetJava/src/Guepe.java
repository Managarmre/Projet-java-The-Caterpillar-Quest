import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;


public class Guepe extends Ennemi {
	
	private enum Orientation {
		Gauche(0), 
		Droite(1);
		
		private int valeur;
		
		Orientation( int valeur ) {
			this.valeur = valeur;
		}
		
		public int getValeur() {
			return this.valeur;
		}
	}
	
	private boolean deplacementHorizontal;
	private Orientation orientation;
	
	public Guepe( int x, int y, Shape hitbox, Point depart, Point arrivee, boolean deplacementHorizontal ) {
		super( x, y, 32, 32, hitbox, "./sprites/guepe.png", depart, arrivee );
		
		this.deplacementHorizontal = deplacementHorizontal;
		
		if( this.deplacementHorizontal ) {
			
			this.animations = new Animation[2];	// 2 animations : aller et retour
			
			this.orientation = ( this.getPositionY() - this.getArrivee().getY() <= 0.1 ) ? Orientation.Droite : Orientation.Gauche;			
			
		}
		else {
			this.animations = new Animation[1];	// une seule animation pour la guêpe verticale
			this.orientation = Orientation.Gauche;
		}
		
		// mise à jour du sens
		
		
	}


	
	@Override
	public void initialiser() throws SlickException {
		
		this.sprite = new SpriteSheet( this.cheminSprite, 32, 32 );
		
		int nombreAnimations = this.sprite.getVerticalCount();
		this.animations = new Animation[ nombreAnimations ];
		
		this.animations[0] = this.chargerAnimation( 0, 0, 3 );
		if( this.deplacementHorizontal ) this.animations[1] = this.chargerAnimation( 1, 0, 3 );
		
	}
	

	@Override
	public void update( GameContainer conteneur, int delta ) throws SlickException {
		
		float oldX = this.getPositionX();
		float oldY = this.getPositionY();
		
		if( Math.abs( oldX - this.getArrivee().getX() ) < 0.1 && Math.abs( oldY - this.getArrivee().getY() ) < 0.1 ){
			Point tmp = this.getArrivee();
			this.setArrivee( this.getDepart() );
			this.setDepart(tmp);
			
			this.orientation = ( this.deplacementHorizontal && this.getPositionY() - this.getArrivee().getY() <= 0.1 ) ? Orientation.Droite : Orientation.Gauche;			
			
		}
				
		Vector2f vecteurDirection = new Vector2f( this.getArrivee().getX() - oldX, this.getArrivee().getY() - oldY );
		
		Vector2f vecteur = new Vector2f( 0.1f * delta, 0f );
		vecteur.add( vecteurDirection.getTheta() );
		
		float newX = oldX + vecteur.getX();
		float newY = oldY + vecteur.getY() ;
		
		this.setPosition( newX, newY );
		
	}
	
	
	@Override
	public void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException {
		
		//int indiceAnimation = this.deplacementHorizontal ? this.getDepart().getX() < this.getArrivee().getX() ? 1 : 0 : 0;
		//int indiceAnimation = ( this.orientation == Orientation.Gauche ) ? 0 : 1;
		
		graphique.drawAnimation( this.animations[ this.orientation.getValeur() ], this.getPositionY(), this.getPositionX() );
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
