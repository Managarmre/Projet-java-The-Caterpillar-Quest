package elementsGraphiques;

import jeu.Carte;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;



/**
 * Représente une guêpe.
 * Le sprite de la guêpe se trouve dans le dossier './sprites/guepe.png'.
 * La guêpe effectuera des allers-retours entre sa position de départ et sa position d'arrivée. 
 * La guêpe ne peut pas traverser les éléments fixes de la carte, elle fera donc demi-tour si le cas se présente.
 * 
 * @author Maxime Pineau
 *
 */
public class Guepe extends Ennemi {
	
	/**
	 * La direction vers laquelle la guêpe se déplace.
	 * La valeur de cette direction permet de récupérer l'animation correspondante, 
	 * car elle correspond à l'indice de cette animation dans le tableau d'animations.
	 * @author Maxime
	 *
	 */
	private enum Orientation {
		Gauche(0), 	// la guêpe regarde vers la gauche
		Droite(1); 	// la guêpe regarde vers la droite
		
		private int indiceAnimation;
		
		Orientation( int valeur ) {
			this.indiceAnimation = valeur;
		}
		
		/**
		 * @return L'indice correspondant à l'animation stockée dans le tableau d'animations.
		 */
		public int getIndiceAnimation() {
			return this.indiceAnimation;
		}
	}
	
	private boolean deplacementHorizontal;
	private Orientation orientation;
		
	/**
	 * Créé une nouvelle guêpe, se déplaçant entre le point de départ et le point d'arrivée.
	 * La position initiale peut être différente du point de départ.
	 * La guêpe fera des allers-retours entre le point de départ et le point d'arrivée.
	 * @param x La position initiale x de la guêpe.
	 * @param y La position initiale y de la guêpe.
	 * @param depart Le point de départ de la guêpe.
	 * @param arrivee Le point d'arrivée de la gupepe.
	 * @param deplacementHorizontal Vrai si la guêpe se déplace horizontalement, et faux si elle se déplace verticalement.
	 */
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

	/** 
	 * Créé une nouvelle guêpe, se déplaçant entre le point de départ et le point d'arrivée.
	 * La position initiale est le point de départ.
	 * @param xDepart La position x de départ de la guêpe.
	 * @param yDepart La position y de départ de la guêpe.
	 * @param xArrive La position x d'arrivée de la guêpe.
	 * @param yArrive La position y d'arrivée de la guêpe.
	 * @param deplacementHorizontale Vrai si la guêpe se déplace horizontalement, et faux si elle se déplace verticalement.
	 */
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
		boolean collision = carte.elementEnCollisionAvecUnElementFixe(this);
		
		if(collision) this.setPosition( oldX, oldY );
		
		// si la guêpe est arrivée à destination (sur le point d'arrivée)
		if( collision || this.estArriveDestination() ) this.faireDemiTour();
		
	}
	
	
	/**
	 * La guêpe fait demi-tour.
	 */
	private void faireDemiTour() {
		
		// on intervertit les points de départ et d'arrivée
		Point tmp = this.getArrivee();
		this.setArrivee( this.getDepart() );
		this.setDepart(tmp);
		
		// on met à jour l'orientation de la guêpe
		// une guêpe verticale ne peut pas changer son orientation
		this.orientation = ( this.deplacementHorizontal && this.getPositionX() - this.getArrivee().getX() <= 0.1 ) ? Orientation.Droite : Orientation.Gauche;			
		
	}
	
	
	@Override
	public void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException {

		graphique.drawAnimation( this.animations[ this.orientation.getIndiceAnimation() ], this.getPositionX(), this.getPositionY() );
		
	}

	



	
}
