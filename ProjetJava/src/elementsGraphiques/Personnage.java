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


/**
 * @author Cyril
 *
 */
public class Personnage extends ElementDeplacable {
	
	private int nbPoints = 0;
	private boolean  isMoving = false, jumping = false, isCollisionOnTop = false;;

	private Direction direction;

	
	private float speed = 10f;
	
	private float vx = 0.0f; // vitesse en x
	private float vy = 0.0f; // vitesse en y
	private float ay = 0.0f; // valeur de l'accélération
	private float dx = 0.0f; // valeur du déplacement du personnage en X
	private float dy = 0.0f; // valeur du déplacement du personnage en Y
	private double tempsSaut = 0.7;
	
	/**
	 * @param x La position en x du personnage
	 * @param y La position en y du personnage
	 */
	public Personnage( int x, int y ) {
		super( x, y, 32, 32, new Rectangle(0, 0, 32, 32), "./sprites/personnage.png" );	
		this.direction = Direction.IMMOBILE;
		this.animations = new Animation[6];
	}
	
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public void initialiser() throws SlickException {
		super.initialiser();
		
		this.sprite = new SpriteSheet( this.cheminSprite, 32, 32 );
		
		this.animations[0] = this.chargerAnimation( 0, 0, 0 );
		
	}
	
	@Override
	public void update( GameContainer conteneur, int delta, Carte carte ) throws SlickException, PartieException {
		
		Point oldPosition;
		
		this.vx = (float) ( delta * 0.015 * this.speed );
		this.vy = (float) ( delta * 0.05 * this.speed );
		
		// accérération, à ajouté à dy pour crééer la gravité, ou retirer à dy pour créer le saut
		this.ay = (float) ( this.vy * (delta/1000.0) / this.tempsSaut );	
		
		
		

		
		
		
		// on autorise le personnage à se déplacer une seule fois
		if( this.direction == Direction.DROITE && this.isMoving ) this.dx = this.vx; 	// déplacement à droite
		else if( direction == Direction.GAUCHE && this.isMoving ) this.dx = - this.vx; 	// déplacement à gauche
		else this.dx = 0;
				
		oldPosition = this.getPosition();		// on sauvegarde l'ancienne position
		this.setPositionX( this.getPositionX() + this.dx );
		if( this.estEnCollisionAvecPlateforme(carte) && ! this.isCollisionOnTop ) this.setPositionX( oldPosition.getX() );
		
		
		
		

		// si le personnage est en l'air (pour se déplacer dans les airs)
		if( this.jumping ) this.dy -= this.ay; 	// le personnage est en l'air
		else { //si le joueur est au sol

			if( this.direction == Direction.HAUT && this.isMoving ) {
			
				this.dy = this.vy;
				this.jumping = true; // le personnage va sauter
				
			}
			// else : // on ne prend pas en compte le saut car le personnage est déjà en l'air
		}
		
		oldPosition = this.getPosition();
		this.setPositionY( this.getPositionY() - this.dy );
		if( this.estEnCollisionAvecPlateforme(carte) && ! isCollisionOnTop ) {
			this.setPositionY( oldPosition.getY() );
			this.dy = 0;	// on remet l'accélération à 0
		}
		
		
					
		
		ElementRamassable elementRamassable = carte.getElementRamassableEnCollisionAvecElement(this); 
		if( elementRamassable != null ) {
			this.nbPoints += elementRamassable.getNbPoints();
			carte.supprimerElementRamassable(elementRamassable);
		}
		
		// le personnage touche une porte, le joueur gagne la partie
		if( carte.elementEnCollisionAvecUnePorte(this) ) throw new PartieGagneeException();
		
		// le personnage touche une guêpe, le joueur perd la partie
		if( carte.elementEnCollisionAvecUnEnnemi(this) ) throw new PartiePerdueException();
		
		// le personnage sort de la fenêtre, le joueur perd la partie
		if( this.getPositionY() > Jeu.HAUTEUR ) throw new PartiePerdueException();
		
	}
	
	
	
	/**
	 * Indique si le personnage est en collision avec une plateforme
	 * @param carte
	 * @return
	 */
	public boolean estEnCollisionAvecPlateforme(Carte carte){
				
		for( ElementFixe plateforme : carte.getElementsFixes() ) {	
			if( this.estEnCollisionAvec(plateforme) ){
				
				// collision en haut
				if( this.getPositionY() - plateforme.getPositionY() <= 0.1 ) {
					this.setPositionY(plateforme.getPositionY() - this.getHauteur());
					isCollisionOnTop = true;
					jumping = false;
				}else{// collision sur les autres côtés
					
					jumping = true;
					isCollisionOnTop = false;
					isMoving = false;
				}					
					
				return true;		
			}
				
		}

		jumping = true; // le personnage est en l'air
		
		return false;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	@Override
	public void afficher(GameContainer conteneur, Graphics graphique) throws SlickException {
		
		graphique.drawAnimation( this.animations[0], this.getPositionX(), this.getPositionY() );
	}

	public int getNbPoints() {
		return this.nbPoints;
	}


}
