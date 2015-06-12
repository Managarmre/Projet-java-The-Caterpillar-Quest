package elementsGraphiques;

import jeu.Carte;
import jeu.Jeu;
import jeu.PartieException;
import jeu.PartieGagneeException;
import jeu.PartiePerdueException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import app.Ressources;


/**
 * Cette classe repr�sente le personnage du joueur dans la fen�tre.
 * Le joueur peut d�placer ce personnage � l'aide des touche haut, gauche et droite du clavier.
 * @see jeu.ControleurPersonnage
 * @author Cyril Caron
 * @author Maxime Pineau
 */
public class Personnage extends ElementDeplacable {
	
	private int nbPoints = 0;
	
	private boolean isMoving = false;	// le personnage bouge sur l'axe X (gauche / droite)
	private boolean veutSauter;			// le personnage veut sauter et bouger sur l'axe Y (haut/bas).
	
	private boolean dansLesAirs = false;
	private boolean surLeSol = false;


	// les attributs permettant le d�placement du personnage.
	private float speed = 10f;
	private float vx = 0.0f; // vitesse en x
	private float vy = 0.0f; // vitesse en y
	private float ay = 0.0f; // valeur de l'acc�l�ration, pour la gravit� ou le saut.
	private float dx = 0.0f; // valeur du d�placement du personnage en X
	private float dy = 0.0f; // valeur du d�placement du personnage en Y
	private double tempsSaut = 0.5f;

	
	/**
	 * La suite de points (x,y) permettant de contruire les hitbox du personnage.
	 */
	private static final float[] POSITIONS_HITBOX_GAUCHE = { 6, 0, 9, 2, 14, 7, 14, 10, 13, 12, 11, 14, 14, 17, 17, 18, 21, 24, 24, 21, 27, 25, 27, 28,       25, 32, 3, 32,      2, 19, 4, 14, 1, 10, 1, 7, 2, 5, 4, 3 }; 
	private static final float[] POSITIONS_HITBOX_DROITE = { 25, 0, 30, 7, 30, 10, 27, 15, 29, 18, 28, 26,       27, 32, 6, 32,     4, 28, 4, 25, 7, 21, 10, 24, 14, 18, 17, 17, 20, 14, 17, 10, 17, 7, 20, 3 };
	private static final float[] POSITIONS_HITBOX_DEPLACEMENT_GAUCHE = { 2, 1, 4, 1, 6, 3, 11, 3, 16, 8, 16, 11, 14, 14, 15, 16, 20, 17, 20, 22, 26, 30, 27, 24,       27, 32, 5, 32,       5, 26, 6, 23, 3, 20, 6, 15, 3, 11, 3, 8, 4, 5 };  
	private static final float[] POSITIONS_HITBOX_DEPLACEMENT_DROITE = { 29, 1, 27, 5, 28, 8, 28, 11, 25, 15, 28, 19, 25, 23, 26, 26,            26, 32, 4, 32,      4, 24, 5, 20, 11, 27, 11, 17, 18, 15, 15, 11, 15, 8, 20, 3, 25, 3, 27, 1 };
	private static final float[] POSITIONS_HITBOX_SAUT_GAUCHE = { 0, 0, 2, 0, 4, 2, 8, 2, 13, 7, 13, 10, 15, 12, 19, 15, 19, 19, 24, 19, 24, 23, 28, 23,      28, 32, 18, 32,     8, 23, 8, 20, 3, 18, 3, 14, 0, 10 };
	private static final float[] POSITIONS_HITBOX_SAUT_DROITE = { 31, 0, 29, 0, 27, 2, 23, 2, 18, 7, 18, 10, 16, 12, 12, 15, 12, 19, 7, 19, 7, 23, 3, 23,     3, 32, 13, 32,    23, 23, 23, 20, 28, 18, 28, 14, 31, 10 };
	
	// les situations correspondent � des indices dans les tableaux des animations et des hitbox.
	private static final int FIXE_GAUCHE = 0;
	private static final int FIXE_DROITE = 1;
	private static final int DEPLACEMENT_GAUCHE = 2;
	private static final int DEPLACEMENT_DROITE = 3;
	private static final int SAUT_GAUCHE = 4;
	private static final int SAUT_DROITE = 5;
	
	private Orientation orientation;	// repr�sente l'orientation du personnage (� gauche ou � droite)
	
	
	private int situationAnimation;	// indice permettant de r�cup�rer l'animation et la hitbox en fonction du d�placement du personnage
	
	/**
	 * @param x La position en x du personnage
	 * @param y La position en y du personnage
	 */
	public Personnage( int x, int y ) {
		super( x, y, 32, 32, new Rectangle(0, 0, 31, 32), Ressources.Sprites.PERSO );	

		this.orientation = Orientation.DROITE;
		this.veutSauter = false;
		
		this.situationAnimation = 1;
		this.animations = new Animation[6];	
	}
	
	
	@Override
	public void initialiser() throws SlickException {
		if( this.estInitialise ) return;	// on initialise une et une seule fois
		this.estInitialise = true;
		
		this.sprite = new SpriteSheet( this.cheminSprite, 32, 32 );
		
		// initialisation des animations du personnage
		this.animations = new Animation[6];
		this.animations[ Personnage.FIXE_GAUCHE ] = this.chargerAnimation( 0, 0, 1 );	// fixe gauche
		this.animations[ Personnage.FIXE_DROITE ] = this.chargerAnimation( 1, 0, 1 );	// fixe droite
		this.animations[ Personnage.DEPLACEMENT_GAUCHE ] = this.chargerAnimation( 2, 0, 1 );	// deplacement gauche
		this.animations[ Personnage.DEPLACEMENT_DROITE ] = this.chargerAnimation( 3, 0, 1 ); 	// deplacement droite
		this.animations[ Personnage.SAUT_GAUCHE ] = this.chargerAnimation( 4, 0, 0 );	// saut gauche
		this.animations[ Personnage.SAUT_DROITE ] = this.chargerAnimation( 4, 1, 1 );	// saut droite
		
		// initialisation des hitbox du personnage, � une animation correspond une hitbox.
		this.hitboxs = new Hitbox[6];
		this.hitboxs[ Personnage.FIXE_GAUCHE ] = new Hitbox( Personnage.POSITIONS_HITBOX_GAUCHE );
		this.hitboxs[ Personnage.FIXE_DROITE ] = new Hitbox( Personnage.POSITIONS_HITBOX_DROITE );
		this.hitboxs[ Personnage.DEPLACEMENT_GAUCHE ] = new Hitbox( Personnage.POSITIONS_HITBOX_DEPLACEMENT_GAUCHE );
		this.hitboxs[ Personnage.DEPLACEMENT_DROITE ] = new Hitbox( Personnage.POSITIONS_HITBOX_DEPLACEMENT_DROITE );
		this.hitboxs[ Personnage.SAUT_GAUCHE ] = new Hitbox( Personnage.POSITIONS_HITBOX_SAUT_GAUCHE );
		this.hitboxs[ Personnage.SAUT_DROITE ] = new Hitbox( Personnage.POSITIONS_HITBOX_SAUT_DROITE );
		
		// initialisation de la hitbox actuelle du personnage en fonction de l'orientation du joueur.
		this.setHitbox( this.hitboxs[ this.situationAnimation ] );
	}
	
	
	@Override
	public void update( GameContainer conteneur, int delta, Carte carte ) throws SlickException, PartieException {
				
		this.vx = (float) ( 14 * 0.015 * this.speed );
		this.vy = (float) ( 14 * 0.05 * this.speed );
				
		this.ay = (float) ( this.vy * (14/1000.0) / this.tempsSaut );	// acc�ration, � ajouter � dy pour cr�er la gravit�.
		
		// d�placement sur X
		if( this.orientation == Orientation.DROITE && this.isMoving ) this.dx = this.vx; 	// d�placement � droite
		else if( this.orientation == Orientation.GAUCHE && this.isMoving ) this.dx = - this.vx; 	// d�placement � gauche
		else this.dx = 0;
		
		// d�placement sur Y
		if( this.veutSauter && ! this.dansLesAirs ) {
			
			this.dy = - this.vy;		// on enl�ve pour faire d�placer le personnage vers le haut.
			this.dansLesAirs = true; // le personnage va sauter
		}
		else this.dy += this.ay;	// le personnage est dans les airs, ou tombe, gravit� constante toujours appliqu�e
		
		
		// on calcule les nouvelles positions du personnage.		
		Point oldPosition = this.getPosition();
		float newPositionX = this.getPositionX() + this.dx;
		float newPositionY = this.getPositionY() + this.dy;
		
		
		/*
		 * On teste les collisions
		 */
		
		this.setPositionY(newPositionY);	// on le d�place en Y (haut, bas)
		for( ElementFixe plateforme : carte.getElementsFixes() ) {
			
			if( plateforme.estEnCollisionAvec(this) ) {
				
				// la plateforme est en dessous du personnage
				if( this.getPositionY() - plateforme.getPositionY() < 0.f ) {			
					this.setPositionY( plateforme.getPositionY() - this.getHauteur() );	// on replace le personnage sur la plateforme.
					this.surLeSol = true;			// on est sur le sol
					this.dansLesAirs = false;		// on est pas dans les airs
				}
				else { 	// on a modifi� que la position Y, la plateforme ne peut �tre qu'au dessus
					this.setPositionY( oldPosition.getY() );
					this.surLeSol = false;		// il n'y a pas de sol, on n'est pas sur le sol
					this.dansLesAirs = true;	// on est dans les airs
				}
				
				this.dy = 0;			// on a touch� quelque chose (au dessus ou en dessous), on remet l'acc�l�ration � 0
				break;
			}
			else this.dansLesAirs = true;	// pas de collision avec une plateforme, ni en haut, ni en bas, on est dans les airs.
			
		}
		
		
		this.setPositionX(newPositionX); 	// on le d�place en X (gauche, droite)
		for( ElementFixe plateforme : carte.getElementsFixes() ) {
			
			if( plateforme.estEnCollisionAvec(this) ) {
				
				if( this.surLeSol && this.getPositionY() - plateforme.getPositionY() < 0.f ) {
					continue; 	// c'est la collision avec le sol, on ne la prend pas en compte.
				}
				else {
					
					if( this.surLeSol ) {
						
						if( this.getPositionX() - plateforme.getPositionX() < 0.0f ) this.setPositionX( plateforme.getPositionX() - plateforme.getLargeur() );
						else this.setPositionX( plateforme.getPositionX() + plateforme.getLargeur() );
						
					}
					else this.setPositionX( oldPosition.getX() );
					
					this.isMoving = false;
					break;
				}
			
			}
		}
			

		// on modifie l'animation et la hitbox en fonction du d�placement actuel du personnage		
		int oldSituation = this.situationAnimation;
		
		if( this.dansLesAirs && this.orientation == Orientation.GAUCHE ) this.situationAnimation = SAUT_GAUCHE;
		else if( this.dansLesAirs && this.orientation == Orientation.DROITE ) this.situationAnimation = SAUT_DROITE;
		else if( this.isMoving && this.orientation == Orientation.GAUCHE ) this.situationAnimation = DEPLACEMENT_GAUCHE;
		else if( this.isMoving && this.orientation == Orientation.DROITE ) this.situationAnimation = DEPLACEMENT_DROITE;
		else if( this.orientation == Orientation.GAUCHE ) this.situationAnimation = FIXE_GAUCHE;
		else this.situationAnimation = FIXE_DROITE;
		
		// on met � jour la hitbox actuelle du personnage
		if( oldSituation != this.situationAnimation ) this.setHitbox( this.hitboxs[this.situationAnimation] );

		
		
		// le personnage touche une cerise, il la ramasse et obtient un certain nombre de points.
		ElementRamassable elementRamassable = carte.getElementRamassableEnCollisionAvecElement(this); 
		if( elementRamassable != null ) {
			this.nbPoints += elementRamassable.getNbPoints();
			carte.supprimerElementRamassable(elementRamassable);
		}
		
		// le personnage touche une porte, le joueur gagne la partie
		if( carte.elementEnCollisionAvecUnePorte(this) ) throw new PartieGagneeException();
		
		// le personnage touche une gu�pe, le joueur perd la partie
		if( carte.elementEnCollisionAvecUnEnnemi(this) ) throw new PartiePerdueException();

		// le personnage sort de la fen�tre, le joueur perd la partie
		if( this.getPositionY() > Jeu.HAUTEUR ) throw new PartiePerdueException();
		
	}	
	
	
	@Override
	public void afficher(GameContainer conteneur, Graphics graphique) throws SlickException {
		
		super.afficher( conteneur, graphique );
		
		graphique.drawAnimation( this.animations[this.situationAnimation], this.getPositionX(), this.getPositionY() );		
	}
	
	
	
	
	public void setOrientation( Orientation orientation ) {	
		this.orientation = orientation;
	}
	
	public void setVeutSauter( boolean veutSauter ) {
		this.veutSauter = veutSauter;
	}
	
	
	/**
	 * Retourne la vitesse de d�placement du personnage.
	 * @return La vitesse de d�placement du personnage.
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Modifie la vitesse de d�placement du personnage
	 * @param speed La nouvelle vitesse de d�placement du personnage.
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}


	/**
	 * V�rifie si le personnage bouge.
	 * @return Vrai si le personnage bouge, faux si le personange est immobile.
	 */
	public boolean isMoving() {
		return isMoving;
	}

	/**
	 * Permet de dire que le personnage est en train de bouger ou non.
	 * @param isMoving vrai si le personnage bouge, faux sinon.
	 */
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	/**
	 * Permet de dire que le personnage est dans les airs ou non.
	 * @param jumping vrai si le personnage saute, faux sinon.
	 */
	public void setJumping(boolean jumping) {
		this.dansLesAirs = jumping;
	}
	
	/**
	 * Retourne le nombre de points qu'a r�ussi � obtenir le personnage du joueur durant son d�placement.
	 * @return Le nombre de points obtenu par le personnage.
	 */
	public int getNbPoints() {
		return this.nbPoints;
	}


}



