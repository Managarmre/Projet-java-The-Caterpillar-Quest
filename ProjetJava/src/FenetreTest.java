import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class FenetreTest extends BasicGame  {

	private Animation animation;
	private Animation guepe;
	
	private static int LARGEUR = 32 * 33;
	private static int HAUTEUR = 32 * 20;
	
	public int ttt = 1000;
	
	public FenetreTest(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void init( GameContainer container ) throws SlickException {
		// TODO Auto-generated method stub
		
		SpriteSheet spriteSheet = new SpriteSheet("sprites/plateforme.png", 64, 64);
		
		this.animation = new Animation();
		this.animation.addFrame( spriteSheet.getSprite(0, 0), 100);
		
		
		SpriteSheet spriteGuepe = new SpriteSheet("sprites/guepe.png", 32, 32);
		this.guepe = new Animation();
		this.guepe.addFrame( spriteGuepe.getSprite(0, 0), 100);
		this.guepe.addFrame( spriteGuepe.getSprite(1, 0), 100);
		this.guepe.addFrame( spriteGuepe.getSprite(2, 0), 100);
		this.guepe.addFrame( spriteGuepe.getSprite(3, 0), 100);
		
	}
	
	@Override
	public void render( GameContainer container, Graphics g ) throws SlickException {
		// TODO Auto-generated method stub
		
		g.drawImage( new Image("./sprites/fond.png"), 0, 0);
		
		g.drawAnimation( this.animation, 0, 0 );
		g.drawAnimation( this.animation, LARGEUR - 32, HAUTEUR - 32 );
		
		g.drawAnimation( this.guepe,  100, 100 );
	}
	
	@Override
	public void update( GameContainer container, int delta ) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	public static void main( String[] args ) {
		try {
			AppGameContainer app = new AppGameContainer(new FenetreTest("test") ); //, LARGEUR, HAUTEUR, false);
			app.setDisplayMode( LARGEUR,  HAUTEUR,  false );
			app.start();
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
