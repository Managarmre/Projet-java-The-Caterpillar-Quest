package ElementsGraphiques;

import java.util.Iterator;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import Jeux.Carte;


public class Personnage extends ElementDeplacable {

	private static int vitesse = 20;

	private int nbPoints = 0;
	private boolean tombe, isMoving = false;

	private Direction direction;

	private double vx = 0.0;
	private double vy = 0.0; // A toi de choisir constante1 en faisant des essais pour que le mouvement te convienne
	private double ay = 0.015; // Même commentaire que pour constante1
	private double dx = 0.0;
	private double dy = 0.0;
	
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
	public void update( GameContainer conteneur, int delta, Carte carte ) throws SlickException {
		
		double vx = delta * 0.010 * vitesse;
		double vy = delta * 0.010 * vitesse; // A toi de choisir constante1 en faisant des essais pour que le mouvement te convienne
		
		ay = vy * (40.0 / 1000.0) / 4.0;

		
		if(isMoving){
			
			switch(direction){
			
			case DROITE: // déplacement à droite
				dx = vx;
				//this.setPositionX(this.getPositionX() + vx);
				break;
			case GAUCHE: // déplacement à gauche
				dx = -vx;
				//this.setPositionX(this.getPositionX() - .1f * delta);
				break;
			case HAUT: // saut
				dy = vy;
				tombe = true;
				//this.setPositionY(this.getPositionY() - .1f * delta);				
				break;
			}
			
			if (!tombe) { // Personnage au sol
				//this.setPositionY(this.getPositionY() + .1f * delta);
				//dy -= ay;
				dy = 0;

			} else { // Personnage en l'air
				//this.setPositionY(this.getPositionY() - .1f * delta);
				//dy = 0.0;
				dy += ay;

			}
			
			this.setPosition( this.getPositionX() + dx, this.getPositionY() + dy );
		}
			
		
		for( Iterator<ElementRamassable> iterateur = carte.getElementsRamassables().iterator(); iterateur.hasNext(); ) {
			
			ElementRamassable ramassable = iterateur.next();
			
			if( this.estEnCollisionAvec(ramassable) ) {			
				this.nbPoints += ramassable.getNbPoints();
				iterateur.remove();			// on supprime l'élément ramassable de la carte
			}
			
		}
		
		for( Ennemi ennemi : carte.getEnnemis() ) {
			
			if( this.estEnCollisionAvec(ennemi) ) {
				System.out.println("MEURT !!!");
			}
			
		}
		
		for( Porte porte : carte.getPortes() ) {
			if( this.estEnCollisionAvec(porte) ) {
				System.out.println("Fin du jeu, tout est OK");
			}
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
	
	public static int getVitesse() {
		return vitesse;
	}

	public static void setVitesse(int vitesse) {
		Personnage.vitesse = vitesse;
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
