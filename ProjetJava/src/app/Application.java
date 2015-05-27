package app;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jeu.Jeu;
import jeu.FileLogSystem;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;


/** 
 * Classe contenant le point d'entr�e du jeu. <br/>
 * Carte par d�faut : "default.map" <br/>
 * Fichier log Slick2d : "TheCaterpillarQuest.log" <br/>
 * Fichier des scores : "scores.save"
 * @author Maxime Pineau
 */
public class Application {

	/** 
	 * Point d'entr�e du jeu. Lance la fen�tre de jeu.
	 * Le joueur devra entrer son nom en premier param�tre, puis le chemin vers le fichier de la carte s'il le d�sir. <br/>
	 * - args[0] contiendra le nom / pseudo du joueur. <br/>
	 * - arg[1] pourra contenir le chemin du fichier. <br/>
	 * Redirige �galement les logs dans un fichier de log. <br/>
	 * � la fin de la partie, les scores des 5 meilleurs joueurs s'afficheront, ainsi que le score du joueur actuel si il a termin� la partie. <br/>
	 * Les 5 meilleurs scores seront sauvegard�s dans le fichier "scores.save". <br/>
	 * Carte par d�faut : "default.map" <br/>
	 * Fichier log Slick2d : "TheCaterpillarQuest.log" 
	 * 
	 * @param args arguments du programme.
	 * @author Maxime Pineau
	 */
	public static void main(String[] args) {

		/*
		 * R�cup�ration des param�tres
		 */
		if( args.length < 1 ) {
			System.err.println(" Erreur, il manque le premier param�tre.");
			System.err.println(" Vous devez entrer les param�tres comme ceci : ");
			System.err.println(" 	executable <nomDuJoueur> ");
			System.err.println(" 	executable <nomDuJoueur> <cheminFichierCarte>");
	        System.exit(-1);
		}
		
		String nomJoueur = args[0];
		
		String cheminFichierMap = "default.map";	// chemin par d�faut
		if( args.length >= 2 ) cheminFichierMap = args[1];
		
		// si la carte par d�faut n'existe pas, elle est recr��e.
		File fichierCarte = new File(cheminFichierMap);
		if( cheminFichierMap.equals("default.map") && ! fichierCarte.exists() ) creerFichierMapDefaut();
		
		
		System.out.println( "Nouvelle partie pour le joueur " + nomJoueur + " avec la carte " + cheminFichierMap );
		
		
		/*
		 * Lancement de la fen�tre de jeu ici
		 */
		
		// on change la sortie des logs
		Log.setLogSystem( new FileLogSystem() );	// les logs de la fen�tre vont dans un fichier de log
		
		
		try {
			
			Jeu jeu = new Jeu("Pseudo");
			
			AppGameContainer app = new AppGameContainer(jeu);
			app.setDisplayMode( Jeu.LARGEUR,  Jeu.HAUTEUR,  false ); 	// false : ne pas mettre en plein �cran
			app.setShowFPS(false);		// n'affiche pas le "FPS" sur la fen�tre
			app.setForceExit(false);	// ne pas fermer automatiquement le programme apr�s fermeture de la fen�tre
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
				
				Score scoreJoueur = jeu.getScoreJoueur();
				scores.ajouterScore(scoreJoueur);
				
				System.out.println("Votre score : ");
				System.out.println( scoreJoueur.toString() );
			}
			
			System.out.println("\nLes meilleurs scores sont : ");
			scores.afficherScores();
			
			scores.sauvegarderScores("scores.save");	// sauvegarde de la liste des scores
			
		} 
		catch( SlickException e) {
			e.printStackTrace();
			Logger.getLogger( Jeu.class.getName()).log(Level.SEVERE, null, e);
		}
		catch( GestionScoresException scoresException ) {
			System.err.println("Erreur lors de la sauvegarde des scores, impossible de sauvegarder les scores. (voir le fichier .log)");
			Log.error( scoresException.getMessage() );
		} 
		catch( FileNotFoundException notFound ) {
			System.err.println( "Erreur lors du chargement de la carte, le fichier " + cheminFichierMap + " n'existe pas." );
			Log.error( notFound.getMessage() );
		} 
		catch( IOException ioException ) {
			System.err.println("Une erreur est survenue lors du chargement du fichier de carte.");
			System.err.println("Plus d'information dans le fichier de log.");
			Log.error( ioException.getMessage() );
		}
		

	}
	
	
	
	
	private static void creerFichierMapDefaut() {
		
		File fichierCarte = new File("default.map");
		
		try {
			
			FileWriter out = new FileWriter(fichierCarte);
			out.write(carteDefaut);
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private static String carteDefaut = 
			"                                                                                                                                h6c                 v3                                                    \n" +
			"                                                                                                                                  #####              #   ####                                               \n" +
			"             v5                                                                                                    v6                 h7          #  #        v6                                              \n" +
			"                                                              v4                                                                        ##   ##      #                                                    \n" +
			"                                                                                                 v3                c                  #              #    ###                                                \n" +
			"        ###c                                                                                    c                  c                                 #                                                        \n" +
			"                                                                                                   ######     #    c                  #              #                                                          \n" +
			"        ##                                                                                                 #                       #                 #          ###                                           \n" +
			"Acv3^                                                     c      c  h3<   v6  cc                              #v4                                    #         v7^                                                             \n" +
			"####   #                                                  c                    cc                                                    #               #                                                        \n" +
			"    #         #                                         v5   ###           ##                                  #               ##                    #                                                            \n" +
			"      #                                                   #                                                                #         v               #         c   #                                        \n" +
			"          ####   ##     ccc                                     v6    ##  v4^                                #         #   #                c        #         #h8                                                  \n" +
			"               #                                       #          #          ###                  h7                       #h5             c         #                          h5<                               \n" +
			"                   #             c                 ##                                             ##########      ####   ####  ####   #  #   ####  # #                 ###########                   v5\n" +
			"                      #   #  #            h4    #                                                                                                                             ch4<                              \n" +
			"                                             #                                                #                                                                               #                       Ph3<\n" +
			"                #                                                                        #  #                                                                                                         #\n" +
			"        h5<                                                        h6            h4<  #                                                                                          #                #  v3^\n" +
			"#####################            #####  ###########                #################                                                                                                v^##########\n";
	
}
