import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;


public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int largeur = 32 * 33;
		int hauteur = 32 * 20;
		
		
		try {
			
			Fenetre fenetre = new Fenetre("The Quaterpillar Quest");
			
			AppGameContainer app = new AppGameContainer(fenetre);
			app.setDisplayMode( largeur,  hauteur,  false );
			app.start();
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}

}
