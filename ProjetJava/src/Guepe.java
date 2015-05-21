import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Vector2f;


public class Guepe extends Ennemi {
	
	private enum Orientation {
		Gauche(0), 	// la gu�pe regarde vers la gauche
		Droite(1); 	// la gu�pe regarde vers la droite
		
		private int indiceAnimation;
		
		Orientation( int valeur ) {
			this.indiceAnimation = valeur;
		}
		
		public int getIndiceAnimation() {
			return this.indiceAnimation;
		}
	}
	
	private boolean deplacementHorizontal;
	private Orientation orientation;
	
	public Guepe( int x, int y, Point depart, Point arrivee, boolean deplacementHorizontal ) {
		super( x, y, 32, 32, null, "./sprites/guepe.png", depart, arrivee );
		
		this.deplacementHorizontal = deplacementHorizontal;
		
		if( this.deplacementHorizontal ) {
			
			this.animations = new Animation[2];	// 2 animations : aller et retour
			this.orientation = ( this.getPositionY() - this.getArrivee().getY() <= 0.1 ) ? Orientation.Droite : Orientation.Gauche;			
		}
		else {
			this.animations = new Animation[1];	// une seule animation pour la gu�pe verticale
			this.orientation = Orientation.Gauche; 	// une gu�pe verticale regarde toujours vers la gauche
		}
				
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
	public void update( GameContainer conteneur, int delta, Carte carte ) throws SlickException {
		
		float oldX = this.getPositionX();
		float oldY = this.getPositionY();
		
		// si la gu�pe est arriv�e � destination (sur le point d'arriv�)
		if( Math.abs( oldX - this.getArrivee().getX() ) < 0.1 && Math.abs( oldY - this.getArrivee().getY() ) < 0.1 ){
			
			// on intervertie les points de d�part et d'arriv�
			Point tmp = this.getArrivee();
			this.setArrivee( this.getDepart() );
			this.setDepart(tmp);
			
			// on met � jour l'orientation de la gu�pe
			// une gu�pe verticale ne peut pas changer son orientation
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

		graphique.drawAnimation( this.animations[ this.orientation.getIndiceAnimation() ], this.getPositionY(), this.getPositionX() );
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
