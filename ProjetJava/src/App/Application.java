package app;

import java.util.logging.Level;
import java.util.logging.Logger;

import jeu.Jeu;
import jeu.FileLogSystem;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;



public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		// on change la sortie des logs
		Log.setLogSystem( new FileLogSystem() );	// les logs de la fen�tre vont dans un fichier de log
		
		Jeu fenetre = new Jeu("The Quaterpillar Quest");
		
		try {
			
			AppGameContainer app = new AppGameContainer(fenetre);
			app.setDisplayMode( Jeu.LARGEUR,  Jeu.HAUTEUR,  false ); 	// false : ne pas mettre en plein �cran
			app.setShowFPS(true);		// n'affiche pas le "FPS" sur la fen�tre
			app.setTargetFrameRate(60); // limite � 60 FPS
			app.setForceExit(false);	// ne pas fermer automatiquement le programme apr�s fermeture de la fen�tre
			
			app.start();
			
			
		} 
		catch (SlickException e) {
			e.printStackTrace();
			Logger.getLogger( Jeu.class.getName()).log(Level.SEVERE, null, e);
		}
		
		
		if( fenetre.isPartieGagnee() ) {

			fenetre.getScoreJoueur().afficher();
			
		}
		
		
	}

}
