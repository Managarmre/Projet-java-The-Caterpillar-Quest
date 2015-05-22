import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;


public class Personnage extends ElementDeplacable {


	private int nbPoints = 0;
	private boolean tombe, isMoving = false, jumping = false;

	private Direction direction;
	
	private static float gravity = 0.5f, jumpStrength = -0.05f, speed = 0.5f;
	
	private float vx = 0, vy = 0;
	
	public Personnage( int x, int y ) {
		super( x, y, 32, 32, null, "./sprites/personnage.png" );	
		this.direction = Direction.IMMOBILE;
		this.animations = new Animation[6];
	}

	@Override
	public void seDeplacer(Point point) {
		// TODO Auto-generated method stub

	}
	

	@Override
	public Point getProchainePosition() {
		// TODO Auto-generated method stub
		return null;
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
						
			
			//this.setPositionY(this.getPositionY() + vy);
						
		}
			
		if(jumping){

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
				
		graphique.drawAnimation( this.animations[0], this.getPositionX(), this.getPositionY() );
	}

	public int getNbPoints() {
		return this.nbPoints;
	}

}
