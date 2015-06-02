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
 * Classe contenant le point d'entrée du jeu. <br/>
 * Carte par défaut : "default.map" <br/>
 * Fichier log Slick2d : "TheCaterpillarQuest.log" <br/>
 * Fichier des scores : "scores.save"
 * @author Maxime Pineau
 */
public class Application {

	/** 
	 * Point d'entrée du jeu. Lance la fenêtre de jeu.
	 * Le joueur devra entrer son nom en premier paramètre, puis le chemin vers le fichier de la carte s'il le désire. <br/>
	 * - args[0] contiendra le nom / pseudo du joueur. <br/>
	 * - arg[1] pourra contenir le chemin du fichier. <br/>
	 * Redirige également les logs dans un fichier de log. <br/>
	 * À la fin de la partie, les scores des 5 meilleurs joueurs s'afficheront, ainsi que le score du joueur actuel s'il a terminé la partie. <br/>
	 * Les 5 meilleurs scores seront sauvegardés dans le fichier "scores.save". <br/>
	 * Carte par défaut : "default.map" <br/>
	 * Fichier log Slick2d : "TheCaterpillarQuest.log" 
	 * 
	 * @param args arguments du programme.
	 * @author Maxime Pineau
	 */
	public static void main(String[] args) {

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
		
		String cheminFichierMap = Ressources.Cartes.DEFAULT;	// chemin par défaut
		if( args.length >= 2 ) cheminFichierMap = args[1];
		
		// si la carte par défaut n'existe pas, elle est recréée.
		File fichierCarte = new File(cheminFichierMap);
		if( cheminFichierMap.equals(Ressources.Cartes.DEFAULT) && ! fichierCarte.exists() ) {
			System.err.println("La carte pas défaut n'existe pas, regénération de celle-ci.");
			creerFichierMapDefaut();
		}
		
		
		System.out.println( "Nouvelle partie pour le joueur " + nomJoueur + " avec la carte " + cheminFichierMap );
		
		
		/*
		 * Lancement de la fenêtre de jeu ici
		 */
		
		// on change la sortie des logs
		Log.setLogSystem( new FileLogSystem() );	// les logs de la fenêtre vont dans un fichier de log
		
		
		try {
			
			Jeu jeu = new Jeu( nomJoueur, cheminFichierMap );
			
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
		
		File fichierCarte = new File(Ressources.Cartes.DEFAULT);
		
		try {
			
			FileWriter out = new FileWriter(fichierCarte);
			out.write(carteDefaut);
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private static String carteDefaut = 
			"                                                                                                                                                    v3                                                    " +
			"                                                                                                                                                                                                  " +
			"             v5                                                                                     v3             v6                 h7          #  #        v6                                              " +
			"                                                              v4                                                                        ##   ##      #                                                    " +
			"                                                                                                                   c                  #              #    ###                                                " +
			"        ###c                                                                                        c              c                                 #                                                        " +
			"                                                                                                    #####     #v4  c                  #              #                                                          " +
			"        ##                                                                                                 #                       #                 #          ###                                           " +
			"Acv3^                                                     c      c  h3<   v6  cc                                                                     #         v7^                                                             " +
			"####   #                                                  c                    cc                                                                    #                                                        " +
			"    #         #                                         v5   ###           ##                             #    #               ##    #               #                                                            " +
			"      #                                                   #                                                                #         v               #         c   #                                        " +
			"          ####   ##     ccc                                     v6    ##  v4^                                #         #   #                c        #         #h8                                                  " +
			"               #                                       #          #          ###                  h7                       #h5            c          #                          h5<                               " +
			"                   #             c                 ##                                             ##########      ####   ####  ####   #  #   ####  # #                 ###########                   v5" +
			"                      #   #  #            h4    #                                                                                                                             ch4<                              " +
			"                                             #                                                #                                                                                                       Ph3<" +
			"                #                                                                        #  #                                                                                                         #" +
			"        h5<                                                        h6            h4<  #                                                                                       #  #                #  v3^" +
			"#####################            #####  ###########                #################                                                                                                v^##########";

}
