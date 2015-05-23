package jeu;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import app.Score;


/**
 * Repr�sente le jeu jou� par le joueur.
 * Cette classe contient la cam�ra, la carte, et l'image de fond.
 * Cette classe devra �tre pass� en param�tre d'un AppGameContainer, afin d'afficher � l'�cran la fen�tre du jeu.
 * 
 * @author Maxime Pineau
 * @see <a href="http://slick.ninjacave.com/javadoc/org/newdawn/slick/AppGameContainer.html">AppGameContainer</a>
 * @see Camera
 * @see Carte
 * @see ControleurPersonnage
 */
public class Jeu extends BasicGame {
	
	/**
	 * La largeur de la fen�tre de jeu.
	 */
	public static final int LARGEUR = 32 * 33;
	
	/**
	 * La hauteur de la fen�tre de jeu.
	 */
	public static final int HAUTEUR = 32 * 20;
		
	private GameContainer conteneur;
	private Camera camera;
	private Carte carte;
	private Image fond;
	private boolean partieGagnee = false;
	
	private String nomJoueur;
	private int tempsEcoule;
	private long tempsLancement;
	
	/**
	 * Cr�e un nouveau jeu. 
	 * @param nomJoueur Le nom du joueur jouant la partie.
	 */
	public Jeu( String nomJoueur ) {
		super("The Quaterpillar Quest");
		
		this.carte = new Carte();
		this.camera = new Camera( this.carte.getPersonnage() );
		this.tempsEcoule = 0;
		this.nomJoueur = nomJoueur;
	}
	
	/**
	 * Initialise le jeu. 
	 * Cette m�thode est appel� avant le lancement de la boucle principale.
	 * C'est �galement dans cette m�thode que l'on ajoute l'�couteur du claver, permettant ainsi de bouger le personnage.
	 * @see <a href="http://slick.ninjacave.com/javadoc/org/newdawn/slick/BasicGame.html#init(org.newdawn.slick.GameContainer)">BasicGame.init()</a>
	 */
	@Override
	public void init( GameContainer conteneur ) throws SlickException {
		
		this.conteneur = conteneur;	
		this.fond = new Image("./sprites/fond.png");
		this.tempsLancement = System.currentTimeMillis();
		
		this.carte.initialiser(conteneur);		
	
		// on ajoute le controleur permettant la gestion des E/S pour le personnage.
		ControleurPersonnage controller = new ControleurPersonnage( this.carte.getPersonnage() );
		conteneur.getInput().addKeyListener(controller);
	}
	
	
	/**
	 * Met � jour les donn�es du jeu (position des �l�ments...).
	 * Les �l�ments graphiques ne sont pas affich�s sur la fen�tre dans cette m�thode.
	 * @see <a href="http://slick.ninjacave.com/javadoc/org/newdawn/slick/BasicGame.html#update(org.newdawn.slick.GameContainer,%20int)">BasicGame.update()</a>
	 */
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

	/**
	 * Met � jour l'affichage des �l�ments graphiques (dans la fen�tre de jeu).
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
		
		// on d�place le graphique vers la gauche pour faire bouger la carte � l'�cran
		this.camera.placer( conteneur, graphique );
		
		// on affiche les �l�ments de la carte sur le graphique
		this.carte.afficher( conteneur, graphique );
				
	}
	
	/**
	 * Retourne le score actuel du joueur.
	 * @return Le score du joueur.
	 */
	public Score getScoreJoueur() {
		return new Score( this.carte.getPersonnage().getNbPoints(), this.tempsEcoule );
	}
	
	/**
	 * Retourne vrai si le joueur a gagn� la partie, et faux sinon. 
	 * La partie est gagn�e si une exception PartieGagneeException a �t� lanc�e.
	 * @return Vrai si le joueur a gagn� la partie, faux sinon.
	 */
	public boolean isPartieGagnee() {
		return this.partieGagnee;
	}

}
