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
	
	private static int LARGEUR = 32 * 33;
	private static int HAUTEUR = 32 * 20;
	
	public FenetreTest(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void init( GameContainer container ) throws SlickException {
		// TODO Auto-generated method stub
		
		SpriteSheet spriteSheet = new SpriteSheet("sprites/plateforme.png", 32, 32);
		
		this.animation = new Animation();
		this.animation.addFrame( spriteSheet.getSprite(0, 0), 100);
		
	}
	
	@Override
	public void render( GameContainer container, Graphics g ) throws SlickException {
		// TODO Auto-generated method stub
		
		g.setColor(new Color(50, 50, 50, 180));
		g.fillRect(0, 0, 300, 70);
		g.setColor(Color.white);
		
		g.drawImage( new Image("./sprites/fond.png"), 0, 0);
		
		g.drawAnimation( this.animation, 0, 0 );
		g.drawAnimation( this.animation, LARGEUR - 32, HAUTEUR - 32 );
	}
	
	@Override
	public void update( GameContainer container, int delta ) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	public static void main( String[] args ) {
		try {
			new AppGameContainer(new FenetreTest("test"), LARGEUR, HAUTEUR, false).start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
