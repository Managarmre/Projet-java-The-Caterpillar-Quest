package Jeux;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import ElementsGraphiques.Cerise;
import ElementsGraphiques.ElementFixe;
import ElementsGraphiques.ElementRamassable;
import ElementsGraphiques.Ennemi;
import ElementsGraphiques.Guepe;
import ElementsGraphiques.Personnage;
import ElementsGraphiques.Plateforme;
import ElementsGraphiques.Porte;


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
		

		this.personnage = new Personnage( 10, 32*18 );
		this.porte = new Porte( 200, 20 );
		this.personnage = new Personnage( 10, 32*18 );
		this.porte = new Porte( 32*31, 32*17 );

		
		this.remplir();	// ----------------------------------------------------------- à remplacer par l'appel du parseur
	}


	public void remplir() {
		
		Plateforme plateforme;
		for( int i = 0; i < 33; i++ ) {
			plateforme = new Plateforme( 32*i, 32*19 );
			this.elementsFixes.add(plateforme);
		}
		
		/*
		Ennemi guepe = new Guepe( 250, 250, new Point(250, 50), new Point(250, 300), true );
		this.ennemis.add(guepe);
		this.ennemis.add( new Guepe( 300, 300, new Point(600, 300), new Point(100, 300), false ) );
		this.ennemis.add( new Guepe( 200, 800, new Point(500, 250), new Point(200, 150), true ) );
		this.ennemis.add( new Guepe( 200, 150, new Point(200, 1000), new Point(200, 150), true ) );
		this.ennemis.add( new Guepe( 400, 400, 1000, 400, false ) );
		*/
		
		// déplacements horizontale
		this.ennemis.add( new Guepe( 400, 100, 600, 100, true ) );	// gauche -> droite
		this.ennemis.add( new Guepe( 600, 150, 400, 150, true ) );	// droite -> gauche
		
		this.ennemis.add( new Guepe(600, 300, 300, 300, true) );
		
		// déplacement verticale
		this.ennemis.add( new Guepe( 100, 500, 100, 1000, false ) );	// haut -> bas
		this.ennemis.add( new Guepe( 200, 400, 200, 100, false ) );	// bas -> haut
		
		// déplacement diagonale
		this.ennemis.add( new Guepe( 400, 400, 500, 800, true ) );
		this.ennemis.add( new Guepe( 400, 400, 1000, 400, true ) );
		
		
		Cerise cerise = new Cerise( 32*12, 32*18 );
		this.elementsRamassables.add(cerise);
		
	}
	
	
	public void initialiser(GameContainer conteneur) throws SlickException {
		
		for( ElementFixe fixe : this.elementsFixes ) {
			fixe.initialiser();
		}
		
		for( ElementRamassable ramassable : this.elementsRamassables ) {
			ramassable.initialiser();
		}
		
		for( Ennemi ennemi : this.ennemis ) {
			ennemi.initialiser();
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
		
		for( ElementRamassable ramassable : this.elementsRamassables ) {
			ramassable.afficher( conteneur, graphique );
		}
		
		for( Ennemi ennemi : this.ennemis ) {
			ennemi.afficher( conteneur, graphique );
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
