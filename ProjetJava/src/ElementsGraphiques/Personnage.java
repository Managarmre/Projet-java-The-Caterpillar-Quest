package ElementsGraphiques;

import java.util.Iterator;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


import org.newdawn.slick.geom.Rectangle;

import Jeu.Carte;
import Jeu.PartieException;
import Jeu.PartieGagneeException;
import Jeu.PartiePerdueException;



public class Personnage extends ElementDeplacable {


	private int nbPoints = 0;
	private boolean  isMoving = false, jumping = false, estEnCollision = false;

	private Direction direction;

	
	private float speed = 10f;
	


	private float vx = 0.0f;
	private float vy = 0.0f; // A toi de choisir constante1 en faisant des essais pour que le mouvement te convienne
	private float ay = 0.0f; // Même commentaire que pour constante1
	private float dx = 0.0f;
	private float dy = 0.0f;

	
	public Personnage( int x, int y ) {
		super( x, y, 32, 32, new Rectangle(0, 0, 32, 32), "./sprites/personnage.png" );	
		this.direction = Direction.IMMOBILE;
		this.animations = new Animation[6];
	}
	
	public void gestionCollision(){
		
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
		
		ay = (float) (vy * (delta/1000.0) / 0.5);
		
		if(direction == Direction.DROITE){
			
			if(isMoving){
				dx = vx;
				if(jumping){
					dy -= ay;
				}				
				this.setPosition(this.getPositionX() + dx, this.getPositionY() - dy);
			}				
		}
		else if(direction == Direction.GAUCHE){
			
			if(isMoving){
				dx = -vx;
				if(jumping){
					dy -= ay;
				}
				this.setPosition(this.getPositionX() + dx, this.getPositionY() - dy);
			}				
		}
		else 
			dx = 0;
		
		if(! jumping){ //si le joueur est au sol

			if(direction == Direction.HAUT && isMoving){
				dy = vy;
				dx = vx;
				this.jumping = true; // le personnage va sauter
				isMoving = false;
				this.setPosition(this.getPositionX() + dx, this.getPositionY() - dy);
			}else{
				dy = 0; // on ne prend pas en compte le saut car le personnage est déjà en l'air
			}
			
		}else{ // le personnage est en l'air
				dy -= ay;
				this.setPosition(this.getPositionX() + dx, this.getPositionY() - dy);
		}
			
			
		
			
			if( this.getPositionY() > 32*20 ){

				throw new PartiePerdueException();
			}
			
			// les éléments ramassables peuvent disparaîtres, on utilise une boucle permettant de supprimer les éléments pendant le parcours
			for( Iterator<ElementRamassable> iterateur = carte.getElementsRamassables().iterator(); iterateur.hasNext(); ) {
				
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
			
		
		
		for( ElementFixe plateforme : carte.getElementsFixes() ) {	
			if( this.estEnCollisionAvec(plateforme) ){
				jumping = false; // on dit que le personnage est au sol
				estEnCollision = true;

			}
				
		}
		
				
		for( Ennemi ennemi : carte.getEnnemis() ) {	
			//if( this.estEnCollisionAvec(ennemi) ) throw new PartiePerdueException();
		}
		
							
	}
	
	public void gravity(){

		
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
		
		super.afficher( conteneur, graphique);
		graphique.drawAnimation( this.animations[0], this.getPositionX(), this.getPositionY() );
	}

	public int getNbPoints() {
		return this.nbPoints;
	}

	@Override
	public boolean collision(Carte carte) {
		// TODO Auto-generated method stub
		return false;
	}

}
