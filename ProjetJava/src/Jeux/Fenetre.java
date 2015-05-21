package Jeux;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import App.Score;


public class Fenetre extends BasicGame {
	
	private GameContainer conteneur;
	private Camera camera;	
	
	private Image fond;
	private Carte carte;
	
	private int tempsEcoule;
	private long tempsLancement;
	
	public Fenetre( String title ) {
		super(title);
		
		this.camera = new Camera();
		this.carte = new Carte();
		
		this.tempsEcoule = 0;
	}
	
	@Override
	public void init( GameContainer conteneur ) throws SlickException {
		
		this.conteneur = conteneur;
		//this.conteneur.setShowFPS(false);	// ne plus afficher "FPS" sur la fenêtre
		
		
		this.fond = new Image("./sprites/fond.png");
		this.carte.initialiser(conteneur);
		
		this.tempsLancement = System.currentTimeMillis();
	}
	
	
	@Override
	public void update( GameContainer conteneur, int delta ) throws SlickException {

		this.carte.update( conteneur, delta );
		
		this.tempsEcoule = (int) (System.currentTimeMillis() - this.tempsLancement) / 1000;
		
	}

	@Override
	public void render( GameContainer conteneur, Graphics graphique ) throws SlickException {

		graphique.drawImage( this.fond, 0, 0 );
		this.carte.afficher( conteneur, graphique );
		
		
		// afficher le nombre de cerises et le temps
		graphique.setColor( Color.darkGray );
		graphique.drawString( "cerises : " + this.carte.getPersonnage().getNbPoints() , 32, 6 );
		graphique.drawString( "temps : " + this.tempsEcoule , 32*10, 6 );
				
	}
	
	public Score getScoreJoueur() {
		return new Score( this.carte.getPersonnage().getNbPoints(), this.tempsEcoule );
	}

}
