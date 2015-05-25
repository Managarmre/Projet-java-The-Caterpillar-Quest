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
 * Repr�sente une gu�pe.
 * Le sprite de la gu�pe se trouve dans le dossier './sprites/guepe.png'.
 * La gu�pe effectuera des allers-retours entre sa position de d�part et sa position d'arriv�e. 
 * La gu�pe ne peut pas traverser les �l�ments fixes de la carte, elle fera donc demi-tour si le cas se pr�sente.
 * 
 * @author Maxime Pineau
 *
 */
public class Guepe extends Ennemi {
	
	/**
	 * La direction vers laquelle la gu�pe se d�place.
	 * La valeur de cette direction permet de r�cup�rer l'animation correspondante, 
	 * car elle correspond � l'indice de cette animation dans le tableau d'animations.
	 * @author Maxime
	 *
	 */
	private enum Orientation {
		Gauche(0), 	// la gu�pe regarde vers la gauche
		Droite(1); 	// la gu�pe regarde vers la droite
		
		private int indiceAnimation;
		
		Orientation( int valeur ) {
			this.indiceAnimation = valeur;
		}
		
		/**
		 * @return L'indice correspondant � l'animation stock�e dans le tableau d'animations.
		 */
		public int getIndiceAnimation() {
			return this.indiceAnimation;
		}
	}
	
	private boolean deplacementHorizontal;
	private Orientation orientation;
		
	/**
	 * Cr�� une nouvelle gu�pe, se d�pla�ant entre le point de d�part et le point d'arriv�e.
	 * La position initiale peut �tre diff�rente du point de d�part.
	 * La gu�pe fera des allers-retours entre le point de d�part et le point d'arriv�e.
	 * @param x La position initiale x de la gu�pe.
	 * @param y La position initiale y de la gu�pe.
	 * @param depart Le point de d�part de la gu�pe.
	 * @param arrivee Le point d'arriv�e de la gupepe.
	 * @param deplacementHorizontal Vrai si la gu�pe se d�place horizontalement, et faux si elle se d�place verticalement.
	 */
	public Guepe( int x, int y, Point depart, Point arrivee, boolean deplacementHorizontal ) {
		super( x, y, 32, 32, new Rectangle(5, 9, 25, 17), "./sprites/guepe.png", depart, arrivee );
		
		this.deplacementHorizontal = deplacementHorizontal;
		
		if( this.deplacementHorizontal ) {
			
			this.animations = new Animation[2];	// 2 animations : aller et retour
			this.orientation = ( this.getPositionX() - this.getArrivee().getX() <= 0.1 ) ? Orientation.Droite : Orientation.Gauche;			
		}
		else {
			this.animations = new Animation[1];	// une seule animation pour la gu�pe verticale
			this.orientation = Orientation.Gauche; 	// une gu�pe verticale regarde toujours vers la gauche
		}
				
	}

	/** 
	 * Cr�� une nouvelle gu�pe, se d�pla�ant entre le point de d�part et le point d'arriv�e.
	 * La position initiale est le point de d�part.
	 * @param xDepart La position x de d�part de la gu�pe.
	 * @param yDepart La position y de d�part de la gu�pe.
	 * @param xArrive La position x d'arriv�e de la gu�pe.
	 * @param yArrive La position y d'arriv�e de la gu�pe.
	 * @param deplacementHorizontale Vrai si la gu�pe se d�place horizontalement, et faux si elle se d�place verticalement.
	 */
	public Guepe( int xDepart, int yDepart, int xArrive, int yArrive, boolean deplacementHorizontale ) {
		this( xDepart, yDepart, new Point(xDepart, yDepart),  new Point(xArrive, yArrive), deplacementHorizontale);
	}
	
	
	@Override
	public void initialiser() throws SlickException {
		super.initialiser();
		
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
		
		// gestion de la collision avec les �l�ments de la carte
		boolean collision = carte.elementEnCollisionAvecUnElementFixe(this);
		
		if(collision) this.setPosition( oldX, oldY );
		
		// si la gu�pe est arriv�e � destination (sur le point d'arriv�e)
		if( collision || this.estArriveDestination() ) this.faireDemiTour();
		
	}
	
	
	/**
	 * La gu�pe fait demi-tour.
	 */
	private void faireDemiTour() {
		
		// on intervertit les points de d�part et d'arriv�e
		Point tmp = this.getArrivee();
		this.setArrivee( this.getDepart() );
		this.setDepart(tmp);
		
		// on met � jour l'orientation de la gu�pe
		// une gu�pe verticale ne peut pas changer son orientation
		this.orientation = ( this.deplacementHorizontal && this.getPositionX() - this.getArrivee().getX() <= 0.1 ) ? Orientation.Droite : Orientation.Gauche;			
		
	}
	
	
	@Override
	public void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException {

		graphique.drawAnimation( this.animations[ this.orientation.getIndiceAnimation() ], this.getPositionX(), this.getPositionY() );
		
	}

	



	
}
