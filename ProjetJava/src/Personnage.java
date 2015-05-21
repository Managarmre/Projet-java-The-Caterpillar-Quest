import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public class Personnage extends ElementDeplacable {

	private static int vitesse = 20;

	private int nbPoints = 0;
	private boolean tombe, isMoving = false;

	private Direction direction;

	double vx = 0.0;
	double vy = 0.0; // A toi de choisir constante1 en faisant des essais pour que le mouvement te convienne
	double ay = 0.015; // Même commentaire que pour constante1
	double dx = 0.0;
	double dy = 0.0;
	
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
			this.setPositionX(this.getPositionX() + dx);
			this.setPositionY(this.getPositionY() + dy);
			
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
				
		graphique.drawAnimation( this.animations[0], this.getPositionX(), this.getPositionY() );
	}

	public int getNbPoints() {
		return this.nbPoints;
	}

}
