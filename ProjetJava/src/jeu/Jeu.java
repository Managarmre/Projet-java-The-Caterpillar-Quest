package jeu;

import java.io.IOException;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import app.Score;


/**
 * Représente le jeu joué par le joueur.
 * Cette classe contient la caméra, la carte, et l'image de fond.
 * Cette classe devra être passée en paramètre d'un AppGameContainer, afin d'afficher à l'écran la fenêtre du jeu.
 * 
 * @author Maxime Pineau
 * @see <a href="http://slick.ninjacave.com/javadoc/org/newdawn/slick/AppGameContainer.html">AppGameContainer</a>
 * @see Camera
 * @see Carte
 * @see ControleurPersonnage
 */
public class Jeu extends BasicGame {
	
	/**
	 * La largeur de la fenêtre de jeu.
	 */
	public static final int LARGEUR = 32 * 33;
	
	/**
	 * La hauteur de la fenêtre de jeu.
	 */
	public static final int HAUTEUR = 32 * 20;
	
	private Camera camera;
	private Carte carte;
	private Image fond;
	private boolean partieGagnee = false;
	
	private String nomJoueur;
	private int tempsEcoule;	// représente le temps écoulé à partir du lancement de la partie, initialisé à 0.
	private long tempsLancement;	// représente le temps du système du lancement du jeu, l'initialisation est inconnue (currentTimeMillis).
	
	/**
	 * Créé un nouveau jeu. 
	 * @param nomJoueur Le nom du joueur jouant la partie.
	 * @param cheminFichierCarte Le chemin du fichier contenant la carte à charger.
	 * @throws IOException Une erreur est survenue lors du chargement de la carte (lecture du fichier de carte).
	 */
	public Jeu( String nomJoueur, String cheminFichierCarte ) throws IOException {
		super("The Caterpillar Quest");
		
		this.carte = new Carte(cheminFichierCarte);
				
		this.camera = new Camera( this.carte.getPersonnage(), this.carte );
				
		this.tempsEcoule = 0;
		this.nomJoueur = nomJoueur;	
		
	}
	
	/**
	 * Créé un nouveau jeu en utilisant la carte par défaut.
	 * @param nomJoueur Le nom du joueur jouant la partie.
	 * @throws IOException Une erreur est survenue lors du chargement de la carte (lecture du fichier de carte).
	 */
	public Jeu( String nomJoueur ) throws IOException {
		this( nomJoueur, "default.map" );
	}
	
	/**
	 * Initialise le jeu. 
	 * Cette méthode est appelée avant le lancement de la boucle principale.
	 * C'est également dans cette méthode que l'on ajoute l'écouteur du claver, permettant ainsi de bouger le personnage.
	 * @throws SlickException Exception durant l'initialisation des composants graphiques.
	 * @see <a href="http://slick.ninjacave.com/javadoc/org/newdawn/slick/BasicGame.html#init(org.newdawn.slick.GameContainer)">BasicGame.init()</a>
	 */
	@Override
	public void init( GameContainer conteneur ) throws SlickException {
		
		this.fond = new Image("./sprites/fond.png");
		this.tempsLancement = System.currentTimeMillis();
		
		this.carte.initialiser();		
	
		// on ajoute le controleur permettant la gestion des E/S pour le personnage.
		ControleurPersonnage controller = new ControleurPersonnage( this.carte.getPersonnage() );
		conteneur.getInput().addKeyListener(controller);
	}
	
	
	/**
	 * Met à jour les données du jeu (position des éléments...).
	 * Les éléments graphiques ne sont pas affichés sur la fenêtre dans cette méthode.
	 * @throws SlickException Exception durant la mise à jour logique des composants graphiques.
	 * @see <a href="http://slick.ninjacave.com/javadoc/org/newdawn/slick/BasicGame.html#update(org.newdawn.slick.GameContainer,%20int)">BasicGame.update()</a>
	 */
	@Override
	public void update( GameContainer conteneur, int delta ) throws SlickException {

		try {
			this.carte.update( conteneur, delta );
		} 
		catch( PartieGagneeException gagnee ) {
			this.partieGagnee = true;
			conteneur.exit();		// arrête le jeu.
			
		} catch( PartieException partieException ) {
			this.partieGagnee = false;
			conteneur.exit();		// arrête le jeu.
		}
		
		this.camera.update();	// on remet la caméra en fonction de la position du personnage à l'écran.
		
		// on met à jour le temps du jeu.
		// ce temps devant commencer à 0, on soustrait le temps système de lancement (celui de la machine).
		this.tempsEcoule = (int) (System.currentTimeMillis() - this.tempsLancement) / 1000;
		
	}

	/**
	 * Met à jour l'affichage des éléments graphiques (dans la fenêtre de jeu).
	 * @throws SlickException Exception durant l'affichage des composants graphiques.
	 * @see <a href="http://slick.ninjacave.com/javadoc/org/newdawn/slick/Game.html#render(org.newdawn.slick.GameContainer,%20org.newdawn.slick.Graphics)">Game.render()</a>
	 */
	@Override
	public void render( GameContainer conteneur, Graphics graphique ) throws SlickException {

		// on affiche le fond fixe
		graphique.drawImage( this.fond, 0, 0 );
		
		// afficher le nombre de cerises et le temps
		graphique.setColor( Color.darkGray );
		graphique.drawString( "cerises : " + this.carte.getPersonnage().getNbPoints() , 32, 6  );
		graphique.drawString( "temps : " + this.tempsEcoule , 32*10, 6 );
		
		// on déplace le graphique vers la gauche pour faire bouger la carte à l'écran
		this.camera.placer( conteneur, graphique );
				
		// on affiche les éléments de la carte sur le graphique
		this.carte.afficher( conteneur, graphique );
				
	}
	
	/**
	 * Retourne le score actuel du joueur.
	 * @return Le score du joueur.
	 */
	public Score getScoreJoueur() {
		return new Score( this.nomJoueur, this.carte.getPersonnage().getNbPoints(), this.tempsEcoule );
	}
	
	/**
	 * Retourne vrai si le joueur a gagné la partie, et faux sinon. 
	 * La partie est gagnée si une exception PartieGagneeException a été lancée.
	 * @return Vrai si le joueur a gagné la partie, faux sinon.
	 */
	public boolean isPartieGagnee() {
		return this.partieGagnee;
	}

}
