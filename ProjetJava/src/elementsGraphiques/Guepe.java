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
	private Orientation orientation;	
	private enum Orientation {
		Gauche(0),
		Droite(1);
		
		private int indice;
		
		Orientation( int indice ) {
			this.indice = indice;
		}
		
		public int indice() {
			return this.indice;
		}
		
	}
	
	/**
	 * La suite de points (x,y) permettant de contruire les hitbox de la gu�pes
	 */
	private static final float[] POSITIONS_HITBOX_DEPLACEMENT_GAUCHE = { 5, 13, 7, 12, 12, 9, 15, 9, 17, 11, 25, 12, 28, 14, 30, 19, 30, 25, 28, 26, 28, 24, 25, 24, 22, 22, 18, 19, 15, 16, 15, 15, 11, 15, 8, 21, 5, 21 };
	private static final float[] POSITIONS_HITBOX_DEPLACEMENT_DROITE = { 17, 9, 20, 9, 23, 12, 25, 12, 27, 14, 27, 21, 24, 21, 20, 15, 17, 15, 14, 19, 9, 22, 7, 23, 7, 24, 4, 24, 4, 26, 1, 24, 1, 20, 2, 17, 4, 17, 4, 14, 5, 14, 7, 12, 13, 12 };
	
	private boolean deplacementHorizontal;
	
	
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
		
		this.orientation = ( this.deplacementHorizontal && this.getPositionX() - this.getArrivee().getX() <= 5 ) ? Orientation.Droite : Orientation.Gauche;			
		// une gu�pe verticale regarde toujours vers la gauche
		
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
		if( this.estInitialise ) return;
		this.estInitialise = true;
		
		this.sprite = new SpriteSheet( this.cheminSprite, 32, 32 );
		
		this.animations = new Animation[2];
		
		this.animations[ Orientation.Gauche.indice() ] = this.chargerAnimation( 0, 0, 3 );
		if( this.deplacementHorizontal ) this.animations[ Orientation.Droite.indice() ] = this.chargerAnimation( 1, 0, 3 );
		
		
		this.hitboxs = new Hitbox[2];
		this.hitboxs[ Orientation.Gauche.indice() ] = new Hitbox( Guepe.POSITIONS_HITBOX_DEPLACEMENT_GAUCHE );
		this.hitboxs[ Orientation.Droite.indice() ] = new Hitbox( Guepe.POSITIONS_HITBOX_DEPLACEMENT_DROITE );
		
		this.setHitbox( this.hitboxs[ this.orientation.indice() ] );		
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
		this.orientation = ( this.deplacementHorizontal && this.getPositionX() - this.getArrivee().getX() < 0.0f ) ? Orientation.Droite : Orientation.Gauche;			
				
		// l'orientation ne change jamais pour une gu�pe verticale
		if( this.deplacementHorizontal ) this.setHitbox( this.hitboxs[ this.orientation.indice() ] );
		
	}
	
	
	@Override
	public void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException {
		
		super.afficher( conteneur, graphique );
				
		graphique.drawAnimation( this.animations[ this.orientation.indice() ], this.getPositionX(), this.getPositionY() );
				
	}

	



	
}
