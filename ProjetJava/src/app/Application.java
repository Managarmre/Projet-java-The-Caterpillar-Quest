package app;


import java.util.logging.Level;
import java.util.logging.Logger;

import jeu.Jeu;
import jeu.FileLogSystem;
import jeu.PartieException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;


/** 
 * Classe contenant le point d'entrée du jeu. <br/>
 * Carte par défaut : "default.map" <br/>
 * Fichier log Slick2d : "TheCaterpillarQuest.log" <br/>
 * Fichier des scores : "scores.save"
 * @author Maxime Pineau
 */
public class Application {

	/** 
	 * Point d'entrée du jeu. Lance la fenêtre de jeu.
	 * Le joueur devra entrer son nom en premier paramètre, puis le chemin vers le fichier de la carte s'il le désir. <br/>
	 * - args[0] contiendra le nom / pseudo du joueur. <br/>
	 * - arg[1] pourra contenir le chemin du fichier. <br/>
	 * Redirige également les logs dans un fichier de log. <br/>
	 * À la fin de la partie, les scores des 5 meilleurs joueurs s'afficheront, ainsi que le score du joueur actuel si il a terminé la partie. <br/>
	 * Les 5 meilleurs scores seront sauvegardés dans le fichier "scores.save". <br/>
	 * Carte par défaut : "default.map" <br/>
	 * Fichier log Slick2d : "TheCaterpillarQuest.log" 
	 * 
	 * @param args arguments du programme.
	 * @author Maxime Pineau
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * Récupération des paramètres
		 */
		if( args.length < 1 ) {
			System.err.println(" Erreur, il manque le premier paramètre.");
			System.err.println(" Vous devez entrer les paramètres comme ceci : ");
			System.err.println(" 	executable <nomDuJoueur> ");
			System.err.println(" 	executable <nomDuJoueur> <cheminFichierCarte>");
	        System.exit(-1);
		}
		
		String nomJoueur = args[0];
		System.out.println(nomJoueur);
		
		String cheminFichierMap = "default.map";	// chemin par défaut
		if( args.length >= 2 ) cheminFichierMap = args[1];
		
		// verification sur l'existance du fichier à faire
		/*
		File fichierCarte = new File(cheminFichierMap);
		if( ! fichierCarte.exists() ) {
			System.err.println(" Erreur, la carte n'existe pas.");
	        System.exit(-2);
		}
		*/
		
		System.out.println( nomJoueur + " " + cheminFichierMap);
		
		
		/*
		 * Lancement de la fenêtre de jeu ici
		 */
		// on change la sortie des logs
		Log.setLogSystem( new FileLogSystem() );	// les logs de la fenêtre vont dans un fichier de log
		
		
		
		try {
			
			Jeu jeu = new Jeu("Pseudo");
			
			AppGameContainer app = new AppGameContainer(jeu);
			app.setDisplayMode( Jeu.LARGEUR,  Jeu.HAUTEUR,  false ); 	// false : ne pas mettre en plein écran
			app.setShowFPS(false);		// n'affiche pas le "FPS" sur la fenêtre
			app.setForceExit(false);	// ne pas fermer automatiquement le programme après fermeture de la fenêtre
			app.setTargetFrameRate(60);
			app.start();
			
			
			/*
			 * Gestion des scores
			 */
			
			// chargement de le liste des scores
			GestionScores scores = null;
			try {
				scores = GestionScores.chargerScores("scores.save");
			} catch ( GestionScoresException e) {
				scores = new GestionScores();
			}
			

			if( jeu.isPartieGagnee() ) {
				
				scores.ajouterScore( jeu.getScoreJoueur() );
			}
			
			
			scores.afficherScores();
			
			scores.sauvegarderScores("scores.save");	// sauvegarde de la liste des scores
			
		} 
		catch (SlickException e) {
			e.printStackTrace();
			Logger.getLogger( Jeu.class.getName()).log(Level.SEVERE, null, e);
		}
		catch (GestionScoresException e) {
			System.err.println( "Erreur lors de la sauvegarde des scores : " + e.getMessage() );
		} catch (PartieException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}

}
