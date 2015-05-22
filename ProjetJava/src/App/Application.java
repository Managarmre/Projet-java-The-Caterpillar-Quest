package App;

/** 
 * Classe contenant le point d'entrée su jeu.
 * @author Maxime
 */
public class Application {

	/** 
	 * Point d'entrée du jeu. 
	 * Le joueur devra entrer son nom en premier paramètre, puis le chemin vers le fichier de la carte si il le désir.
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
		
		String cheminFichierMap = "";	// chemin par défaut
		if( args.length >= 2 ) cheminFichierMap = args[1];
		
		// verification sur l'existance du fichier à faire
		/*
		File fichierCarte = new File(cheminFichierMap);
		if( ! fichierCarte.exists() ) {
			System.err.println(" Erreur, la carte n'existe pas.");
	        System.exit(-2);
		}
		*/
		
		
		/*
		 * Lancement de la fenêtre de jeu ici
		 */
		
		
		
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
		
		//scores.ajouterScore( new Score("test", 2, 8000) );
				
		scores.afficherScores();
		
		// sauvegarde de la liste des scores
		try {
			scores.sauvegarderScores("scores.save");
			
		} catch (GestionScoresException e) {
			System.err.println( "Erreur lors de la sauvegarde des scores : " + e.getMessage() );
		}
		
	}

}
