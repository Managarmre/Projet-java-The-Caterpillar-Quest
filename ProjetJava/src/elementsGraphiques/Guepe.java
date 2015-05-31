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

import app.Ressources;



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
	 * @author Maxime Pineau
	 *
	 */
	private Orientation orientation;	
	private enum Orientation {
		Gauche(0),
		Droite(1);
		
		private int indice;	// représente l'indice du tableau dans lequel on va stocker la hitbox et l'animation correspondante à l'orientation.
		
		Orientation( int indice ) {
			this.indice = indice;
		}
		
		public int indice() {
			return this.indice;
		}
		
	}
	
	/**
	 * La suite de points (x,y) permettant de contruire les hitbox de la guêpes.
	 */
	private static final float[] POSITIONS_HITBOX_DEPLACEMENT_GAUCHE = { 5, 13, 7, 12, 12, 9, 15, 9, 17, 11, 25, 12, 28, 14, 30, 19, 30, 25, 28, 26, 28, 24, 25, 24, 22, 22, 18, 19, 15, 16, 15, 15, 11, 15, 8, 21, 5, 21 };
	private static final float[] POSITIONS_HITBOX_DEPLACEMENT_DROITE = { 17, 9, 20, 9, 23, 12, 25, 12, 27, 14, 27, 21, 24, 21, 20, 15, 17, 15, 14, 19, 9, 22, 7, 23, 7, 24, 4, 24, 4, 26, 1, 24, 1, 20, 2, 17, 4, 17, 4, 14, 5, 14, 7, 12, 13, 12 };
	
	private boolean deplacementHorizontal;	// indique si la guêpe se déplace horizontalement ou verticalement.
	
	
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
		super( x, y, 32, 32, new Rectangle(5, 9, 25, 17), Ressources.Sprites.GUEPE, depart, arrivee );
		
		this.deplacementHorizontal = deplacementHorizontal;
		
		this.orientation = ( this.deplacementHorizontal && this.getPositionX() - this.getArrivee().getX() <= 5 ) ? Orientation.Droite : Orientation.Gauche;			
		// une guêpe verticale regarde toujours vers la gauche
		
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
		if( this.estInitialise ) return;	// on initialise qu'une et une seule fois.
		this.estInitialise = true;
		
		this.sprite = new SpriteSheet( this.cheminSprite, 32, 32 );
		
		this.animations = new Animation[2];
		this.animations[ Orientation.Gauche.indice() ] = this.chargerAnimation( 0, 0, 3 );
		if( this.deplacementHorizontal ) this.animations[ Orientation.Droite.indice() ] = this.chargerAnimation( 1, 0, 3 );
		
		// on a une hitbox par animations.
		this.hitboxs = new Hitbox[2];
		this.hitboxs[ Orientation.Gauche.indice() ] = new Hitbox( Guepe.POSITIONS_HITBOX_DEPLACEMENT_GAUCHE );
		this.hitboxs[ Orientation.Droite.indice() ] = new Hitbox( Guepe.POSITIONS_HITBOX_DEPLACEMENT_DROITE );
		
		// on initialise la hitbox actuelle de l'élément à partir de l'orientation initiale de la guêpe.
		this.setHitbox( this.hitboxs[ this.orientation.indice() ] );		
	}
	

	@Override
	public void update( GameContainer conteneur, int delta, Carte carte ) throws SlickException {
		
		// on sauvegarde les anciennes positions
		float oldX = this.getPositionX();
		float oldY = this.getPositionY();
		
		// on calcule les prochaines positions en utilisant un vecteur pour déterminer la direction.
		Vector2f vecteurDirection = new Vector2f( this.getArrivee().getX() - oldX, this.getArrivee().getY() - oldY );
		
		Vector2f vecteur = new Vector2f( 0.1f * delta, 0 );	// le vecteur de base indiquant de combien la guêpe peut se déplacer.
		vecteur.add( vecteurDirection.getTheta() );		// on replace ce vecteur sur le vecteur de direction, permettant de déplacer la guêpe vers la bonne direction.
		
		float newX = this.getPositionX() + vecteur.getX();
		float newY = this.getPositionY() + vecteur.getY();
		
		this.setPosition( newX, newY );
		
		// gestion de la collision avec les éléments de la carte
		boolean collision = carte.elementEnCollisionAvecUnElementFixe(this);
		
		if(collision) this.setPosition( oldX, oldY );	// une collision est détectée, on revient à la position précédente.
		
		// si la guêpe est arrivée à destination (sur le point d'arrivée), ou qu'elle est arrêtée en chemin.
		if( collision || this.estArriveDestination() ) this.faireDemiTour();
		
	}
	
	
	/**
	 * La guêpe fait demi-tour. On échange les points de départ et d'arrivée.
	 */
	private void faireDemiTour() {
		
		// on intervertit les points de départ et d'arrivée
		Point tmp = this.getArrivee();
		this.setArrivee( this.getDepart() );
		this.setDepart(tmp);
		
		// on met à jour l'orientation de la guêpe
		// une guêpe verticale ne peut pas changer son orientation
		this.orientation = ( this.deplacementHorizontal && this.getPositionX() - this.getArrivee().getX() < 0.0f ) ? Orientation.Droite : Orientation.Gauche;			
				
		// l'orientation ne change jamais pour une guêpe verticale
		if( this.deplacementHorizontal ) this.setHitbox( this.hitboxs[ this.orientation.indice() ] );
		
	}
	
	
	@Override
	public void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException {
		
		super.afficher( conteneur, graphique );
				
		graphique.drawAnimation( this.animations[ this.orientation.indice() ], this.getPositionX(), this.getPositionY() );
				
	}

	



	
}
