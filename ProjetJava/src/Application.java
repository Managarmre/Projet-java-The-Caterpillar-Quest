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
			app.setDisplayMode( largeur,  hauteur,  false ); 	// false : ne pas mettre en plein �cran
			app.setShowFPS(false);		// n'affiche pas le "FPS" sur la fen�tre
			app.start();
			
			fenetre.getScoreJoueur().afficher();
			
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}

}
