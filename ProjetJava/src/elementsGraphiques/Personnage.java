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
	private Direction orientation;
	private int situationAnimation;

	
	private float speed = 10f;
	
	private float vx = 0.0f; // vitesse en x
	private float vy = 0.0f; // vitesse en y

	private float ay = 0.0f; // valeur de l'accélération
	private float dx = 0.0f; // valeur du déplacement du personnage en X
	private float dy = 0.0f; // valeur du déplacement du personnage en Y
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
	
	
	private static final int FIXE_GAUCHE = 0;
	private static final int FIXE_DROITE = 1;
	private static final int DEPLACEMENT_GAUCHE = 2;
	private static final int DEPLACEMENT_DROITE = 3;
	private static final int SAUT_GAUCHE = 4;
	private static final int SAUT_DROITE = 5;
	
	
	/**
	 * @param x La position en x du personnage
	 * @param y La position en y du personnage
	 */
	public Personnage( int x, int y ) {
		super( x, y, 32, 32, new Rectangle(0, 0, 31, 32), "./sprites/perso.png" );	
		this.direction = Direction.IMMOBILE;
		this.orientation = Direction.DROITE;
		
		this.situationAnimation = 1;
		
	}
	
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		if(direction == Direction.DROITE || direction == Direction.GAUCHE){
			if(direction == Direction.DROITE && this.orientation==Direction.GAUCHE){
				//de gauche à  droite
				this.orientation = Direction.DROITE;
				this.situationAnimation++;
			}
			else if(direction == Direction.GAUCHE && this.orientation==Direction.DROITE){
				//de droite à  gauche
				this.orientation = Direction.GAUCHE;
				this.situationAnimation--;
			}
		}
		this.direction = direction;
	}

	@Override
	public void initialiser() throws SlickException {
		super.initialiser();
		
		this.sprite = new SpriteSheet( this.cheminSprite, 32, 32 );
		
		this.animations = new Animation[6];		
		this.animations[ Personnage.FIXE_GAUCHE ] = this.chargerAnimation( 0, 0, 1 );	// fixe gauche
		this.animations[ Personnage.FIXE_DROITE ] = this.chargerAnimation( 1, 0, 1 );	// fixe droite
		this.animations[ Personnage.DEPLACEMENT_GAUCHE ] = this.chargerAnimation( 2, 0, 1 );	// deplacement gauche
		this.animations[ Personnage.DEPLACEMENT_DROITE ] = this.chargerAnimation( 3, 0, 1 ); 	// deplacement droite
		this.animations[ Personnage.SAUT_GAUCHE ] = this.chargerAnimation( 4, 0, 0 );	// saut gauche
		this.animations[ Personnage.SAUT_DROITE ] = this.chargerAnimation( 4, 1, 1 );	// saut droite
				
		this.hitboxs = new Hitbox[6];
		this.hitboxs[ Personnage.FIXE_GAUCHE ] = new Hitbox( Personnage.POSITIONS_HITBOX_GAUCHE );
		this.hitboxs[ Personnage.FIXE_DROITE ] = new Hitbox( Personnage.POSITIONS_HITBOX_DROITE );
		this.hitboxs[ Personnage.DEPLACEMENT_GAUCHE ] = new Hitbox( Personnage.POSITIONS_HITBOX_DEPLACEMENT_GAUCHE );
		this.hitboxs[ Personnage.DEPLACEMENT_DROITE ] = new Hitbox( Personnage.POSITIONS_HITBOX_DEPLACEMENT_DROITE );
		this.hitboxs[ Personnage.SAUT_GAUCHE ] = new Hitbox( Personnage.POSITIONS_HITBOX_SAUT_GAUCHE );
		this.hitboxs[ Personnage.SAUT_DROITE ] = new Hitbox( Personnage.POSITIONS_HITBOX_SAUT_DROITE );
		
		this.setHitbox( this.hitboxs[ this.situationAnimation ] );
	}
	
	@Override
	public void update( GameContainer conteneur, int delta, Carte carte ) throws SlickException, PartieException {
		
		int oldSituation = this.situationAnimation;
		
		Point oldPosition;
		
		this.vx = (float) ( 14 * 0.015 * this.speed );
		this.vy = (float) ( 14 * 0.05 * this.speed );
		
		// accération, à ajouter à dy pour créer la gravité ou retirer à dy pour créer le saut
		this.ay = (float) ( this.vy * (14/1000.0) / this.tempsSaut );	
		

		// on autorise le personnageà se déplacer une seule fois
		if( this.direction == Direction.DROITE && this.isMoving ) this.dx = this.vx; 	// déplacement à droite
		else if( direction == Direction.GAUCHE && this.isMoving ) this.dx = - this.vx; 	// déplacement à gauche
		else this.dx = 0;
		
		oldPosition = this.getPosition(); // on sauvegarde l'ancienne position
		this.setPositionX( this.getPositionX() + this.dx ); 
		if( this.estEnCollisionAvecPlateforme(carte) && ! this.isCollisionOnTop ) this.setPositionX( oldPosition.getX() );
		

		// si le personnage est en l'air (pour se déplacer dans les airs)
		if( this.jumping ) this.dy -= this.ay; 	// le personnage est en l'air
		else { //si le joueur est au sol

			if( this.direction == Direction.HAUT && this.isMoving ) {
			
				this.dy = this.vy;
				this.setJumping(true); // le personnage va sauter
				
			}
		}
		
		oldPosition = this.getPosition();
		this.setPositionY( this.getPositionY() - this.dy );
		if( this.estEnCollisionAvecPlateforme(carte) && ! isCollisionOnTop ) { // si on est en collision avec autre chose que le sommet de la plateforme
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
		
		if( this.situationAnimation != oldSituation ) System.out.println( oldSituation + " " + this.situationAnimation );
		this.setHitbox( this.hitboxs[this.situationAnimation] );
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
				if( this.getPositionY() - plateforme.getPositionY() < 0.0f ) {
					this.setPositionY(plateforme.getPositionY() - this.getHauteur());
					isCollisionOnTop = true;
					this.setJumping(false);
				}else{ // collision sur les autres côtés
					System.out.println("Collision sur le côté");
					this.setJumping(true);
					isCollisionOnTop = false;
					this.setMoving(false);
				}					
					
				return true;		
			}
				
		}

		this.setJumping(true); // le personnage est en l'air
		
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
		if(this.isMoving == false && isMoving == true && this.jumping == false){
			//le perso avance
			this.situationAnimation += 2;
		}else if(this.isMoving == true && isMoving == false && this.jumping == false){
			//le perso s'arrête
			this.situationAnimation -= 2;
		}
		this.isMoving = isMoving;
	}
	
	public boolean isJumping() {
		return jumping;
	}


	public void setJumping(boolean jumping) {
		if(this.jumping == false && jumping == true && this.isMoving == false){
			//le perso saute
			if(this.situationAnimation+4 <= 5) this.situationAnimation += 4;
			else this.situationAnimation += 2;
		}else if(this.jumping == true && jumping == false && this.isMoving == false){
			//le perso retombe
			this.situationAnimation -= 4;
		}
		else if(this.jumping == true && jumping == false && this.isMoving == true){
			//le perso retombe en bougeant
			this.situationAnimation -= 2;
		}
		else if(this.jumping == false && jumping == true && this.isMoving == true){
			//le perso saute en bougeant
			this.situationAnimation += 2;
		}
		this.jumping = jumping;
	}


	@Override
	public void afficher(GameContainer conteneur, Graphics graphique) throws SlickException {
		
		super.afficher( conteneur, graphique );
		
		graphique.drawAnimation( this.animations[this.situationAnimation], this.getPositionX(), this.getPositionY() );		
	}

	public int getNbPoints() {
		return this.nbPoints;
	}


}
