import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Fenetre extends BasicGame {
	
	private GameContainer conteneur;
	private Camera camera;	
	
	private Image fond;
	private Carte carte;
	
	public Fenetre( String title ) {
		super(title);
		
		this.camera = new Camera();
		this.carte = new Carte();
	}
	
	@Override
	public void init( GameContainer conteneur ) throws SlickException {
		
		this.conteneur = conteneur;
		//this.conteneur.setShowFPS(false);	// ne plus afficher "FPS" sur la fenêtre
		
		
		this.fond = new Image("./sprites/fond.png");
		this.carte.initialiser();
		
	}
	
	
	@Override
	public void update( GameContainer conteneur, int delta ) throws SlickException {


		for(Ennemi e : this.carte.getEnnemis())
		if( this.carte.getPersonnage().estEnCollisionAvec(e) )
			System.out.println("");
			//mourir();
		for(ElementRamassable c : carte.getElementsRamassables())
			if( carte.getPersonnage().estEnCollisionAvec(c))
				System.out.println("");
				//ramasserCerise();
		
		if(carte.getPersonnage().estEnCollisionAvec(this.carte.getPorte()))
			System.out.println("");
			//gagner();

		this.carte.update( conteneur, delta );

		
	}

	@Override
	public void render( GameContainer conteneur, Graphics graphique ) throws SlickException {

		graphique.drawImage( this.fond, 0, 0 );
		this.carte.afficher( conteneur, graphique );
	}
	

}
