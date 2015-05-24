package elementsGraphiques;

import java.util.Iterator;

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
 * Représente le personnage jouable à l'écran
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
	private double tempsSaut = 0.5;
	
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
		
		this.sprite = new SpriteSheet( this.cheminSprite, 32, 32 );
		
		this.animations[0] = this.chargerAnimation( 0, 0, 0 );
		
	}
	
	@Override
	public void update( GameContainer conteneur, int delta, Carte carte ) throws SlickException, PartieException {
		
		
		vx = (float) (delta * 0.015 * this.speed);
		vy = (float) (delta * 0.05 * this.speed );

		ay = (float) (vy * (delta/1000.0) / tempsSaut);

		
		if(direction == Direction.DROITE){
			
			if(isMoving){ // on autorise le personnage à se déplacer une seule fois
				dx = vx; // déplacement à droite
			}				
		}
		else if(direction == Direction.GAUCHE){
			
			if(isMoving){
				dx = -vx;// déplacement à gauche
			}				
		}
		else 
			dx = 0;

		if(isMoving){
			
			if(jumping && ! isCollisionOnTop){  // on autorise le déplacement pendant le saut
				dy -= ay;
			}

		}
		
		if( this.isCollisionOnTop && isMoving) // si on se déplace sur une plateforme
			this.setPositionX(this.getPositionX() + dx);
		
		// on teste la collision en x
		if(this.estEnCollisionAvecPlateforme(carte) && ! isCollisionOnTop){ // on ignore les collisions avec le haut de la plateforme
			dx = -dx;
			this.setPositionX(this.getPositionX() + dx);
			dx = 0;
		} 

		
		if(! jumping){ //si le joueur est au sol

			if(direction == Direction.HAUT){
				
				if( isMoving){
					dy = vy;
					dx = vx;
					this.jumping = true; // le personnage va sauter

					this.setPosition(this.getPositionX() + dx, this.getPositionY() - dy);
				}
			}else{
				dy = 0; // on ne prend pas en compte le saut car le personnage est déjà en l'air
			}
			
		}else{ // le personnage est en l'air
			dy -= ay;	// on applique la gravité		
			this.setPositionY( this.getPositionY() - dy);
		}
		
		//this.setPositionY(this.getPositionY() - dy);
		
		// on teste la collision en y
		if(this.estEnCollisionAvecPlateforme(carte) && ! isCollisionOnTop){
			dy = -dy; 
			this.setPositionY(this.getPositionY() + dy);
			dy = 0;
		}

			//this.setPosition(this.getPositionX() + dx, this.getPositionY() - dy);
			
		
		// les éléments ramassables peuvent disparaître, on utilise une boucle permettant de supprimer les éléments pendant le parcours
		/*for( Iterator<ElementRamassable> iterateur = carte.getElementsRamassables().iterator(); iterateur.hasNext(); ) {
			
			ElementRamassable ramassable = iterateur.next();
			
			if( this.estEnCollisionAvec(ramassable) ) {			
				this.nbPoints += ramassable.getNbPoints();
				iterateur.remove();			// on supprime l'élément ramassable de la carte
			}
			
		}			

		// le personnage touche une porte, le jeu est terminé
		for( Porte porte : carte.getPortes() ) {
			//if( this.estEnCollisionAvec(porte) ) throw new PartieGagneeException();	
		}

				
		for( Ennemi ennemi : carte.getEnnemis() ) {	
			//if( this.estEnCollisionAvec(ennemi) ) throw new PartiePerdueException();

		}
				
					
		ElementRamassable elementRamassable = carte.getElementRamassableEnCollisionAvecElement(this); 
		if( elementRamassable != null ) {
			this.nbPoints += elementRamassable.getNbPoints();
			carte.supprimerElementRamassable(elementRamassable);
		}*/
		
		/*
		if( carte.elementEnCollisionAvecUnElementFixe(this) ) {
			jumping = false; // on dit que le personnage est au sol
			estEnCollision = true;
		}
		*/
		
		// le personnage touche une porte, le joueur gagne la partie
		//if( carte.elementEnCollisionAvecUnePortes(this) ) throw new PartieGagneeException();
		
		// le personnage touche une guêpe, le joueur perd la partie
		//if( carte.elementEnCollisionAvecUnEnnemi(this) ) throw new PartiePerdueException();
		
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
				
				if(this.getPositionY() - plateforme.getPositionY() <= 0.1){ // collision en haut
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
		
		super.afficher(conteneur, graphique);
		graphique.drawAnimation( this.animations[0], this.getPositionX(), this.getPositionY() );
	}

	public int getNbPoints() {
		return this.nbPoints;
	}


}
