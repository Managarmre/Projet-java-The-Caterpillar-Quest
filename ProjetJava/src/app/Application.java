package app;

/** 
 * Classe contenant le point d'entr�e du jeu. <br/>
 * Carte par d�faut : "default.map" <br/>
 * Fichier log Slick2d : "TheCaterpillarQuest.log" <br/>
 * Fichier des scores : "scores.save"
 * @author Maxime Pineau
 */

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
		
		Jeu jeu = new Jeu("Pseudo");
		
		try {
			
			AppGameContainer app = new AppGameContainer(jeu);
			app.setDisplayMode( Jeu.LARGEUR,  Jeu.HAUTEUR,  false ); 	// false : ne pas mettre en plein �cran
			app.setShowFPS(false);		// n'affiche pas le "FPS" sur la fen�tre
			app.setForceExit(false);	// ne pas fermer automatiquement le programme apr�s fermeture de la fen�tre
			app.setTargetFrameRate(60);
			app.start();
						
		} 
		catch (SlickException e) {
			e.printStackTrace();
			Logger.getLogger( Jeu.class.getName()).log(Level.SEVERE, null, e);
		}
		
		
		if( jeu.isPartieGagnee() ) {

			jeu.getScoreJoueur().afficher();
			
		}
		
		
	}

}
