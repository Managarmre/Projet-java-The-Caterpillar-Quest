package app;

/** 
 * Classe contenant le point d'entrée du jeu. <br/>
 * Carte par défaut : "default.map" <br/>
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
		Log.setLogSystem( new FileLogSystem() );	// les logs de la fenêtre vont dans un fichier de log
		
		Jeu jeu = new Jeu("Pseudo");
		
		try {
			
			AppGameContainer app = new AppGameContainer(jeu);
			app.setDisplayMode( Jeu.LARGEUR,  Jeu.HAUTEUR,  false ); 	// false : ne pas mettre en plein écran
			app.setShowFPS(false);		// n'affiche pas le "FPS" sur la fenêtre
			app.setForceExit(false);	// ne pas fermer automatiquement le programme après fermeture de la fenêtre
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
