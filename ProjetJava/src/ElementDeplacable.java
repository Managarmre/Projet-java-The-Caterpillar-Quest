import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


public abstract class ElementDeplacable extends Element {
	
	protected String cheminSprite;
	protected SpriteSheet sprite;
		
	protected Animation[] animations;
	
	
	public ElementDeplacable( int x, int y, int hauteur, int largeur, Shape hitbox, String cheminSprite ) {
		super( x, y, hauteur, largeur, hitbox );
		
		this.cheminSprite = cheminSprite;
	}
	
	
	public abstract void seDeplacer(Point point);
	public abstract void update( GameContainer conteneur, int delta ) throws SlickException;
	public abstract Point getProchainePosition();
	
		
	protected Animation chargerAnimation( int numeroLigne, int debutColonne, int finColonne ) {
		
		Animation animation = new Animation();
		
		for( int i = debutColonne; i <= finColonne; i++ ) {
			animation.addFrame( this.sprite.getSprite(i, numeroLigne), 100 );
		}
		
		return animation;		
	}
	
}
