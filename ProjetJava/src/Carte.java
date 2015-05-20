import java.util.ArrayList;

import org.newdawn.slick.AppletGameContainer.Container;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;


public class Carte {
	
	private Personnage personnage;
	private Porte porte;
	
	private ArrayList<ElementRamassable> elementsRamassables;
	private ArrayList<Ennemi> ennemis;
	private ArrayList<ElementFixe> elementsFixes;
	
	
	public Carte() {
				
		this.elementsRamassables = new ArrayList<ElementRamassable>();
		this.ennemis = new ArrayList<Ennemi>();
		this.elementsFixes = new ArrayList<ElementFixe>();
		
		this.personnage = new Personnage( 10, 10, null );
		this.porte = new Porte( 200, 20, null );
		
		this.remplir();	// ----------------------------------------------------------- à retirer
	}


	public void remplir() {
		
		Plateforme plateforme;
		Point position;
		for( int i = 0; i < 33; i++ ) {
			plateforme = new Plateforme( 32*i, 32*19, null );
			this.elementsFixes.add(plateforme);
		}
		
		Ennemi guepe = new Guepe( 100, 100, null, null, null, true );
		this.ennemis.add(guepe);
		this.ennemis.add( new Guepe( 132, 100, null, null, null, true ) );
		this.ennemis.add( new Guepe( 164, 100, null, null, null, true ) );
		
		Cerise cerise = new Cerise( 200, 200, null );
		this.elementsRamassables.add(cerise);
		
	}
	
	
	public void initialiser(GameContainer conteneur) throws SlickException {
		
		for( ElementFixe fixe : this.elementsFixes ) {
			fixe.initialiser();
		}
		
		for( Ennemi ennemi : this.ennemis ) {
			ennemi.initialiser();
		}
				
		for( ElementRamassable ramassable : this.elementsRamassables ) {
			ramassable.initialiser();
		}
		
		this.personnage.initialiser();
		this.porte.initialiser();
		
		ControleurPersonnage controller = new ControleurPersonnage(this.personnage);
		conteneur.getInput().addKeyListener(controller);
		
	}


	public void update( GameContainer conteneur, int delta ) throws SlickException {
		
		for( Ennemi ennemi : this.ennemis ) {
			ennemi.update( conteneur, delta, this );
		}
		
		this.personnage.update( conteneur, delta, this );
		
		
	}
	
	public void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException {
				
		for( ElementFixe fixe : this.elementsFixes ) {
			fixe.afficher( conteneur, graphique );
		}
		
		for( Ennemi ennemi : this.ennemis ) {
			ennemi.afficher( conteneur, graphique );
		}
		
		for( ElementRamassable ramassable : this.elementsRamassables ) {
			ramassable.afficher( conteneur, graphique );
		}
		
		this.porte.afficher( conteneur, graphique );
		this.personnage.afficher( conteneur, graphique );
		
	}
	
	
	
	public Personnage getPersonnage() {
		return personnage;
	}


	public Porte getPorte() {
		return porte;
	}


	public ArrayList<ElementRamassable> getElementsRamassables() {
		return elementsRamassables;
	}


	public ArrayList<Ennemi> getEnnemis() {
		return ennemis;
	}


	public ArrayList<ElementFixe> getElementsFixes() {
		return elementsFixes;
	}
	
	
	
	
	
	
}
