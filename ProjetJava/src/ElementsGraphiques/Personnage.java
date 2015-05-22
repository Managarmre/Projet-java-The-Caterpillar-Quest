package ElementsGraphiques;

import java.util.Iterator;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


import org.newdawn.slick.geom.Rectangle;

import Jeux.Carte;
import Jeux.PartieException;
import Jeux.PartieGagneeException;
import Jeux.PartiePerdueException;



public class Personnage extends ElementDeplacable {


	private int nbPoints = 0;
	private boolean tombe, isMoving = false, jumping = false;

	private Direction direction;

	
	private static float gravity = 0.5f, jumpStrength = -0.05f, speed = 0.5f;
	


	private float vx = 0.0f;
	private float vy = 0.0f; // A toi de choisir constante1 en faisant des essais pour que le mouvement te convienne
	private float ay = 0.015f; // Même commentaire que pour constante1
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
		
		vx += gravity * delta;
		
		
		if(isMoving){
			
			switch(direction){
			
			case DROITE: // déplacement à droite
				vx = speed;
				this.setPositionX(this.getPositionX() + vx);
				break;
			case GAUCHE: // déplacement à gauche
				vx = - speed;
				this.setPositionX(this.getPositionX() + vx);
				break;
			case HAUT: // saut

				this.setPositionY(this.getPositionY() - 0.5f);
				System.out.println("jump");
				//this.setPositionY(this.getPositionY() - 0.5f);
				
				break;
			}

			
			this.setPosition( this.getPositionX() + dx, this.getPositionY() + dy );

			if( this.getPositionY() > 32*20 ) throw new PartiePerdueException();
			
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
				if( this.estEnCollisionAvec(porte) ) throw new PartieGagneeException();	
			}
			
		}
				
		for( Ennemi ennemi : carte.getEnnemis() ) {	
			if( this.estEnCollisionAvec(ennemi) ) throw new PartiePerdueException();
		}
			

		/*for(Ennemi e : carte.getEnnemis()){
			if( carte.getPersonnage().estEnCollisionAvec(e) )
				System.out.println("");
				//mourir();
		}
		for(ElementRamassable c : carte.getElementsRamassables()){
			if( carte.getPersonnage().estEnCollisionAvec(c))
				System.out.println("");
				//ramasserCerise();
		}				
		if(carte.getPersonnage().estEnCollisionAvec(carte.getPorte()))
			System.out.println("");
			//gagner();
		if(carte.getPersonnage().estEnCollisionAvec(carte.getElementsFixes()))*/
				
	}
	
	public static float getSpeed() {
		return speed;
	}

	public static void setSpeed(int speed) {
		Personnage.speed = speed;
	}

	public boolean isTombe() {
		return tombe;
	}

	public void setTombe(boolean tombe) {
		this.tombe = tombe;
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
