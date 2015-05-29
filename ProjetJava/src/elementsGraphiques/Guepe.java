package elementsGraphiques;

import jeu.Carte;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
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
		
		private int indice;
		
		Orientation( int valeur ) {
			this.indice = valeur;
		}
		
		/**
		 * @return L'indice correspondant à l'animation stockée dans le tableau d'animations.
		 */
		public int getIndice() {
			return this.indice;
		}
	}
	
	private boolean deplacementHorizontal;
	private Orientation orientation;
	
	/**
	 * La suite de points (x,y) permettant de contruire les hitbox de la guêpes
	 */
	private static final float[] POSITIONS_HITBOX_DEPLACEMENT_GAUCHE = { 5, 13, 7, 12, 12, 9, 15, 9, 17, 11, 25, 12, 28, 14, 30, 19, 30, 25, 28, 26, 28, 24, 25, 24, 22, 22, 18, 19, 15, 16, 15, 15, 11, 15, 8, 21, 5, 21 };
	private static final float[] POSITIONS_HITBOX_DEPLACEMENT_DROITE = { 17, 9, 20, 9, 23, 12, 25, 12, 27, 14, 27, 21, 24, 21, 20, 15, 17, 15, 14, 19, 9, 22, 7, 23, 7, 24, 4, 24, 4, 26, 1, 24, 1, 20, 2, 17, 4, 17, 4, 14, 5, 14, 7, 12, 13, 12 };
	
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
			this.orientation = ( this.getPositionX() - this.getArrivee().getX() <= 0.1 ) ? Orientation.Droite : Orientation.Gauche;			
		}
		else this.orientation = Orientation.Gauche; 	// une guêpe verticale regarde toujours vers la gauche
				
		//this.animations = new Animation[2];	// 2 animations : aller et retour
		
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
		super.initialiser();
		
		this.sprite = new SpriteSheet( this.cheminSprite, 32, 32 );
		
		this.animations = new Animation[2];
		
		this.animations[0] = this.chargerAnimation( 0, 0, 3 );
		if( this.deplacementHorizontal ) this.animations[1] = this.chargerAnimation( 1, 0, 3 );
		
		
		this.hitboxs = new Hitbox[2];
		this.hitboxs[0] = new Hitbox( Guepe.POSITIONS_HITBOX_DEPLACEMENT_GAUCHE );
		this.hitboxs[1] = new Hitbox( Guepe.POSITIONS_HITBOX_DEPLACEMENT_DROITE );
		
		this.setHitbox( this.hitboxs[ this.orientation.getIndice() ] );		
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
		Orientation oldOrientation = this.orientation;
		this.orientation = ( this.deplacementHorizontal && this.getPositionX() - this.getArrivee().getX() <= 0.1 ) ? Orientation.Droite : Orientation.Gauche;			
				
		if( this.orientation != oldOrientation ) this.setHitbox( this.hitboxs[ this.orientation.getIndice() ] );
		
	}
	
	
	@Override
	public void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException {
		
		super.afficher( conteneur, graphique );
				
		graphique.drawAnimation( this.animations[ this.orientation.getIndice() ], this.getPositionX(), this.getPositionY() );
				
	}

	



	
}
