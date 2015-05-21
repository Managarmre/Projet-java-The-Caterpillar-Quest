package ElementsGraphiques;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import Jeux.Carte;


public class Guepe extends Ennemi {
	
	private enum Orientation {
		Gauche(0), 	// la guêpe regarde vers la gauche
		Droite(1); 	// la guêpe regarde vers la droite
		
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
		super( x, y, 32, 32, new Rectangle(5, 9, 25, 17), "./sprites/guepe.png", depart, arrivee );
		
		this.deplacementHorizontal = deplacementHorizontal;
		
		if( this.deplacementHorizontal ) {
			
			this.animations = new Animation[2];	// 2 animations : aller et retour
			this.orientation = ( this.getPositionX() - this.getArrivee().getX() <= 0.1 ) ? Orientation.Droite : Orientation.Gauche;			
		}
		else {
			this.animations = new Animation[1];	// une seule animation pour la guêpe verticale
			this.orientation = Orientation.Gauche; 	// une guêpe verticale regarde toujours vers la gauche
		}
				
	}

	public Guepe( int xDepart, int yDepart, int xArrive, int yArrive, boolean deplacementHorizontale ) {
		this( xDepart, yDepart, new Point(xDepart, yDepart),  new Point(xArrive, yArrive), deplacementHorizontale);
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
		
		Vector2f vecteurDirection = new Vector2f( this.getArrivee().getX() - oldX, this.getArrivee().getY() - oldY );
		
		Vector2f vecteur = new Vector2f( 0.1f * delta, 0 );
		vecteur.add( vecteurDirection.getTheta() );
		
		float newX = this.getPositionX() + vecteur.getX();
		float newY = this.getPositionY() + vecteur.getY();
		
		this.setPosition( newX, newY );
		
		// gestion de la collision avec les éléments de la carte
		boolean collision = this.collision(carte);
		
		if( collision ) this.setPosition( oldX, oldY );
		
		// si la guêpe est arrivée à destination (sur le point d'arrivé)
		if( collision || this.estArriveDestination() ) this.faireDemiTour();
		
	}
	
	public boolean collision( Carte carte ) {
		
		for( ElementFixe element : carte.getElementsFixes() ) {
			if( this.estEnCollisionAvec(element) ) return true;
		}
			
		return false;
	}
	
	public boolean estArriveDestination() {
		return Math.abs( this.getPositionX() - this.getArrivee().getX() ) < 0.1 && Math.abs( this.getPositionY() - this.getArrivee().getY() ) < 0.1;
	}
	
	private void faireDemiTour() {
		
		// on intervertie les points de départ et d'arrivé
		Point tmp = this.getArrivee();
		this.setArrivee( this.getDepart() );
		this.setDepart(tmp);
		
		// on met à jour l'orientation de la guêpe
		// une guêpe verticale ne peut pas changer son orientation
		this.orientation = ( this.deplacementHorizontal && this.getPositionX() - this.getArrivee().getX() <= 0.1 ) ? Orientation.Droite : Orientation.Gauche;			
		
	}
	
	@Override
	public void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException {
		super.afficher( conteneur, graphique );
		graphique.drawAnimation( this.animations[ this.orientation.getIndiceAnimation() ], this.getPositionX(), this.getPositionY() );
		
	}

	



	
}
