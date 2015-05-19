import org.newdawn.slick.Animation;
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
	
	public abstract Point getProchainePosition();
	
	
	@Override
	public void initialiser() throws SlickException {
		
		this.sprite = new SpriteSheet( this.cheminSprite, 32, 32 );
		
		int nombreAnimations = this.sprite.getVerticalCount();
		this.animations = new Animation[ nombreAnimations ];
		
		this.chargerAnimation();
	}
	
	
	private void chargerAnimation() {
		
		int nombreLignesSprite = this.sprite.getVerticalCount();
		int nombreColonnesSprite = this.sprite.getHorizontalCount();
		
		for( int i = 0; i < nombreLignesSprite; i++ ) {
			
			this.animations[i] = new Animation();
			
			for( int j = 0; j < nombreColonnesSprite; j++ ) {
				this.animations[i].addFrame( this.sprite.getSprite(j, i), 100 );
			}
			
		}
		
	}
	
}
