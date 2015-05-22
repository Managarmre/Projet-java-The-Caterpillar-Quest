package Jeu;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


import App.Score;


/**
 * @author Maxime Pineau
 *
 */
public class Fenetre extends BasicGame {
	
	public static final int LARGEUR = 32 * 33;
	public static final int HAUTEUR = 32 * 20;
		
	private GameContainer conteneur;
	private Camera camera;	
	
	private Image fond;
	private Carte carte;
	private boolean partieGagnee = false;
	
	private int tempsEcoule;
	private long tempsLancement;
	
	public Fenetre( String titre ) {
		super(titre);
		
		
		this.carte = new Carte();
		
		this.camera = new Camera( this.carte.getPersonnage() );
		
		this.tempsEcoule = 0;
	}
	
	@Override
	public void init( GameContainer conteneur ) throws SlickException {
		
		this.conteneur = conteneur;
		//this.conteneur.setShowFPS(false);	// ne plus afficher "FPS" sur la fenêtre
	
		
		this.fond = new Image("./sprites/fond.png");
		this.carte.initialiser(conteneur);
		
		ControleurPersonnage controller = new ControleurPersonnage( this.carte.getPersonnage() );
		conteneur.getInput().addKeyListener(controller);
		
		this.tempsLancement = System.currentTimeMillis();
	}
	
	
	@Override
	public void update( GameContainer conteneur, int delta ) throws SlickException {

		try {
			this.carte.update( conteneur, delta );
		} 
		catch( PartieGagneeException gagnee ) {
			this.partieGagnee = true;
			conteneur.exit();
			
		} catch( PartieException partieException ) {
			this.partieGagnee = false;
			conteneur.exit();
		}
		
		this.camera.update();
		
		this.tempsEcoule = (int) (System.currentTimeMillis() - this.tempsLancement) / 1000;
		
	}

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
	
	public Score getScoreJoueur() {
		return new Score( this.carte.getPersonnage().getNbPoints(), this.tempsEcoule );
	}
	
	public boolean isPartieGagnee() {
		return this.partieGagnee;
	}

}
