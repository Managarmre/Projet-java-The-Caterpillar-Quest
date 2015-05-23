package jeu;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import elementsGraphiques.Cerise;
import elementsGraphiques.ElementFixe;
import elementsGraphiques.ElementRamassable;
import elementsGraphiques.Ennemi;
import elementsGraphiques.Guepe;
import elementsGraphiques.Personnage;
import elementsGraphiques.Plateforme;
import elementsGraphiques.Porte;



/**
 * Classe repr�santant la carte du jeu.
 * Cette classe contient tout les �l�ments graphique. Ces �l�ments graphiques se trouvent dans le package 'elementsGraphiques'.
 * Celle-ci doit �tre charg�e � partir d'un fichier .map, se chargement est effectuer � l'aide de la classe Parser.
 * 
 * @author Maxime Pineau
 * @see elementsGraphiques
 */
public class Carte {
	
	private Personnage personnage;
		
	private ArrayList<Porte> portes;
	private ArrayList<ElementRamassable> elementsRamassables;
	private ArrayList<Ennemi> ennemis;
	private ArrayList<ElementFixe> elementsFixes;
	
	
	/**
	 * Cr�e une nouvelle carte.
	 */
	public Carte() {
				
		this.elementsRamassables = new ArrayList<ElementRamassable>();
		this.ennemis = new ArrayList<Ennemi>();
		this.elementsFixes = new ArrayList<ElementFixe>();
		this.portes = new ArrayList<Porte>();
		
		this.personnage = new Personnage( 10, 32 * 10 );
		
		this.testerCarte();	// ----------------------------------------------------------- � remplacer par l'appel du parseur
	}


	private void testerCarte() {
		
		Plateforme plateforme;
		for( int i = 0; i < 33; i++ ) {
			plateforme = new Plateforme( 32*i, 32*19 );
			this.elementsFixes.add(plateforme);
		}
		
		for( int i = 10; i < 28; i++ ) {
			plateforme = new Plateforme( 32*i, 32*15 );
			this.elementsFixes.add(plateforme);
		}
		
		this.portes.add( new Porte(32*30, 32*17) );
		
		// d�placements horizontale
		this.ennemis.add( new Guepe( 400, 100, 600, 100, true ) );	// gauche -> droite
		this.ennemis.add( new Guepe( 600, 150, 400, 150, true ) );	// droite -> gauche
		
		this.ennemis.add( new Guepe(600, 300, 300, 300, true) );
		
		// d�placement verticale
		this.ennemis.add( new Guepe( 100, 500, 100, 1000, false ) );	// haut -> bas
		this.ennemis.add( new Guepe( 200, 400, 200, 100, false ) );		// bas -> haut
		
		// d�placement diagonale
		this.ennemis.add( new Guepe( 400, 400, 500, 800, true ) );
		this.ennemis.add( new Guepe( 400, 400, 1000, 400, true ) );
		
		
		Cerise cerise = new Cerise( 32*12, 32*18 );
		this.elementsRamassables.add(cerise);
		
	}
	
	
	/**
	 * Initialise la carte et ses �l�ments graphiques (ajout des images, sprites...).
	 * Cette m�thode est � appeler dans la fonction Jeu.init().
	 * @param conteneur	Le conteneur du jeu
	 * @throws SlickException Lanc�e lorsqu'une erreur est d�tect�e par la librairie Slick2D (image non trouv�e...).
	 */
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
		
		for( Porte porte : this.portes ) {
			porte.initialiser();
		}
		
		this.personnage.initialiser();
		
	}


	/**
	 * Met � jours les donn�es de la cartes (positions des �l�ments...).
	 * @param conteneur Le conteneur du jeu.
	 * @param delta Le temps qui s'est pass� depuis la derni�re mise � jour en millisecondes. 
	 * @throws SlickException Lanc�e lorsqu'une erreur est d�tect�e par la librairie Slick2D.
	 * @throws PartieException Indique que la partie a �t� gagn�e ou perdue.
	 */
	public void update( GameContainer conteneur, int delta ) throws SlickException, PartieException {
		
		for( Ennemi ennemi : this.ennemis ) {
			ennemi.update( conteneur, delta, this );
		}
		
		this.personnage.update( conteneur, delta, this );
		
	}
	
	/**
	 * Affiche les �l�ments de la carte dans la fen�tre graphique.
	 * @param conteneur Le conteneur du jeu.
	 * @param graphique Le graphique du jeu.
	 * @throws SlickException Lanc�e lorsqu'une erreur est d�tect�e par la librairie Slick2D.
	 */
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
		
		for( Porte porte : this.portes ) {
			porte.afficher( conteneur, graphique );
		}
		
		this.personnage.afficher( conteneur, graphique );
		
	}
	
	
	
	
	
	public Personnage getPersonnage() {
		return personnage;
	}

	
	public ArrayList<Porte> getPortes() {
		return this.portes;
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
	
	
	public void supprimerElementRamassable( ElementRamassable ramassable ) {
		this.elementsRamassables.remove(ramassable);
	}
	
	public void supprimerEnnemi( Ennemi ennemi ) {
		this.ennemis.remove(ennemi);
	}
	
	public void supprimerElementFixe( ElementFixe fixe ) {
		this.elementsFixes.remove(fixe);
	}
	
	
	//public boolean elementEnCollisionAvecPorte( )
	
	
}
