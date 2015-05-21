package App;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import Jeux.Fenetre;
import Jeux.FileLogSystem;


public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		// on change la sortie des logs
		Log.setLogSystem( new FileLogSystem() );	// les logs de la fenêtre vont dans un fichier de log
		
		Fenetre fenetre = new Fenetre("The Quaterpillar Quest");
		
		try {
			
			AppGameContainer app = new AppGameContainer(fenetre);
			app.setDisplayMode( Fenetre.LARGEUR,  Fenetre.HAUTEUR,  false ); 	// false : ne pas mettre en plein écran
			app.setShowFPS(false);		// n'affiche pas le "FPS" sur la fenêtre
			
			app.setForceExit(false);	// ne pas fermer automatiquement le programme après fermeture de la fenêtre
			
			app.start();
			
			
		} 
		catch (SlickException e) {
			e.printStackTrace();
			Logger.getLogger( Fenetre.class.getName()).log(Level.SEVERE, null, e);
		}
		
		
		if( fenetre.isPartieGagnee() ) {

			fenetre.getScoreJoueur().afficher();
			
		}
		
		
	}

}
