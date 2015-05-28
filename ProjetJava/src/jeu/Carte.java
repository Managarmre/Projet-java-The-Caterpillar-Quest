package jeu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import parser.Parser;

import elementsGraphiques.Element;
import elementsGraphiques.ElementFixe;
import elementsGraphiques.ElementRamassable;
import elementsGraphiques.Ennemi;
import elementsGraphiques.Personnage;
import elementsGraphiques.Porte;



/**
 * Classe représantant la carte du jeu.
 * Cette classe contient tout les éléments graphique. Ces éléments graphiques se trouvent dans le package 'elementsGraphiques'.
 * Celle-ci doit être chargée à partir d'un fichier .map, se chargement est effectuer à l'aide de la classe Parser.
 * 
 * @author Maxime Pineau
 * @see elementsGraphiques
 */
public class Carte {
	
	private String cheminfichierCarte;	// le chemin du fichier représentant la carte.
	public Parser parseur; 				// permet de charger la carte dynamiquement depuis un fichier textuel 	
	
	private Personnage personnage;
		
	private ArrayList<Porte> portes;
	private ArrayList<ElementRamassable> elementsRamassables;
	private ArrayList<Ennemi> ennemis;
	private ArrayList<ElementFixe> elementsFixes;
	
	
	/**
	 * Crée une nouvelle carte.
	 * @throws IOException 
	 */
	public Carte( String cheminCarte ) throws IOException {
		
		this.cheminfichierCarte = cheminCarte;
		
		//this.personnage = new Personnage( 0, 0 );
		
		this.elementsRamassables = new ArrayList<ElementRamassable>();
		this.ennemis = new ArrayList<Ennemi>();
		this.elementsFixes = new ArrayList<ElementFixe>();
		this.portes = new ArrayList<Porte>();
		
		this.parseur = new Parser(this);
		this.parseur.getEcranInit();
		
	}

	public Carte() throws IOException {
		this("default.map");
	}
	
	
	
	/**
	 * Initialise la carte et ses éléments graphiques (ajout des images, sprites...).
	 * Cette méthode est à appeler dans la fonction Jeu.init().
	 * @throws SlickException Lancée lorsqu'une erreur est détectée par la librairie Slick2D (image non trouvée...).
	 */
	public void initialiser() throws SlickException {
		
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
	 * Met à jours les données de la cartes (positions des éléments...).
	 * @param conteneur Le conteneur du jeu.
	 * @param delta Le temps qui s'est passé depuis la dernière mise à jour en millisecondes. 
	 * @throws SlickException Lancée lorsqu'une erreur est détectée par la librairie Slick2D.
	 * @throws PartieException Indique que la partie a été gagnée ou perdue.
	 */
	public void update( GameContainer conteneur, int delta ) throws SlickException, PartieException {
		
		for( Ennemi ennemi : this.ennemis ) {
			ennemi.update( conteneur, delta, this );
		}
		
		this.personnage.update( conteneur, delta, this );
		
	}
	
	
	/**
	 * Affiche les éléments de la carte dans la fenêtre graphique.
	 * @param conteneur Le conteneur du jeu.
	 * @param graphique Le graphique du jeu.
	 * @throws SlickException Lancée lorsqu'une erreur est détectée par la librairie Slick2D.
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
	
		
	/**
	 * Retourne le personnage du joueur présent sur la carte.
	 * @return Le personnage du joueur.
	 */
	public Personnage getPersonnage() {
		return personnage;
	}


	/**
	 * Supprime un élément ramassable de la carte.
	 * @param ramassable L'élément ramassable à supprimer.
	 * @return Vrai si l'élément a bien été supprimé, faux sinon
	 */
	public boolean supprimerElementRamassable( ElementRamassable ramassable ) {
		return this.elementsRamassables.remove(ramassable);
	}
	
	
	/**
	 * Supprime de la carte les éléments trop éloignés par rapport à une position (représente le bord gauche de la fenêtre).
	 * La position du bord est stockée dans la classe Camera, qui centre la fenêtre en fonction du déplacement du personnage (fait bouger la carte).
	 * C'est également la classe Camera qui appelle cette méthode.
	 * @param positionCameraX La position de la caméra, représentant le bord gauche de la fenêtre.
	 * @see Camera
	 */
	public void supprimerElementsTropEloignes( float positionCameraX ) {
		
		// on supprime tout les éléments ayant dépassé d'une case de l'écran vers la gauche.
		// sauf pour les ennemis, ou l'on regarde les positions de départ et d'arrivée
		
		// la longueur d'une case fait 32 pixel.
		
		ElementFixe fixe;
		for( Iterator<ElementFixe> iterateur = this.elementsFixes.iterator(); iterateur.hasNext(); ) {
			fixe = iterateur.next();
			if( fixe.getPositionX() < positionCameraX - 32 ) iterateur.remove();
		}
		
		ElementRamassable ramassable;
		for( Iterator<ElementRamassable> iterateur = this.elementsRamassables.iterator(); iterateur.hasNext(); ) {
			ramassable = iterateur.next();
			// si l'élément sort de l'écran, on le supprime
			if( ramassable.getPositionX() < positionCameraX - 32 ) iterateur.remove();
		}
		
		Porte porte;
		for( Iterator<Porte> iterateur = this.portes.iterator(); iterateur.hasNext(); ) {
			porte = iterateur.next();
			if( porte.getPositionX() < positionCameraX - 32 ) iterateur.remove();
		}
		
		Ennemi ennemi;
		for( Iterator<Ennemi> iterateur = this.ennemis.iterator(); iterateur.hasNext(); ) {
			ennemi = iterateur.next();
			// si l'ennemi ne se déplace plus sur la zone visible de l'écran, on le supprime.
			// l'ennemi se déplace entre 2 points, il faut donc vérifier les coordonnées X des deux points.
			if( ennemi.getDepart().getX() < positionCameraX - 32 && ennemi.getArrivee().getX() < positionCameraX - 32 ) iterateur.remove();
		}
		
	}
	

		
	/**
	 * Retourne l'élément de la liste avec lequel l'élément à vérifier est en collision.
	 * On vérifiera tout les éléments de la liste passé en paramètre.
	 * Si il y en a un qui est en collision avec l'élément à vérifier, on le retourne.
	 * Si aucun éléments n'est en collision avec l'émément à vérifier, on retourne null.
	 * @param listeElements La liste de tous les éléments poossiblement en collision avec l'élément à vérifier.
	 * @param elementAVerifier L'élément à vérifier
	 * @return L'élément de la liste qui est en collision avec l'élément à vérifier passé en paramètre, null si aucun élément de la liste n'est en collision avec l'élément à vérifier.
	 */
	private <E extends Element> E elemenEnCollisionAvecUnElementListe( ArrayList<E> listeElements, Element elementAVerifier ) {
		
		for( E element : listeElements ) {
			
			if( element.estEnCollisionAvec(elementAVerifier) ) return element;
		}
		
		return null;
	}
		
	/**
	 * Retourne le premier élément fixe de la carte qui est en collision avec l'élément à vérifier, null s'il n'y en a aucun.
	 * @param elementAVerifier L'élément à vérifier.
	 * @return Le premier élément fixe de la carte qui est en collision avec l'élément à vérifier, null s'il n'y en a aucun.
	 */
	public ElementFixe getElementFixeEnCollisionAvecElement( Element elementAVerifier ) {
		return this.elemenEnCollisionAvecUnElementListe( this.elementsFixes, elementAVerifier );
	}
		
	/**
	 * Retourne le premier ennemi de la carte qui est en collision avec l'élément à vérifier, null s'il n'y en a aucun.
	 * @param elementAVerifier L'élément à vérifier.
	 * @return Le premier ennemi de la carte qui est en collision avec l'élément à vérifier, null s'il n'y en a aucun.
	 */
	public Ennemi getEnnemiEnCollisionAvecElement( Element elementAVerifier ) {
		return this.elemenEnCollisionAvecUnElementListe( this.ennemis, elementAVerifier );
	}
	
	/**
	 * Retourne la première porte de sortie de la carte qui est en collision avec l'élément à vérifier, null s'il n'y en a aucun.
	 * @param elementAVerifier L'élément à vérifier.
	 * @return La première porte de la carte qui est en collision avec l'élément à vérifier, null s'il n'y en a aucun.
	 */
	public Porte getPorteEnCollisionAvecElement( Element elementAVerifier ) {
		return this.elemenEnCollisionAvecUnElementListe( this.portes, elementAVerifier );
	}
	
	/**
	 * Retourne le premier élément rammassable de la carte qui est en collision avec l'élément à vérifier, null s'il n'y en a aucun.
	 * @param elementAVerifier L'élément à vérifier.
	 * @return Le premier élément ramassable de la carte qui est en collision avec l'élément à vérifier, null s'il n'y en a aucun.
	 */
	public ElementRamassable getElementRamassableEnCollisionAvecElement( Element elementAVerifier ) {
		return this.elemenEnCollisionAvecUnElementListe( this.elementsRamassables, elementAVerifier );
	}
	
	
	
	/**
	 * Retourne vrai si un élément fixe est en collision avec l'élément à vérifier, faux s'il n'y en a pas.
	 * @param elementAVerifier L'élément à vérifier.
	 * @return Vrai si un élément fixe est en collision avec l'élément à vérifier, faux s'il n'y en a pas.
	 */
	public boolean elementEnCollisionAvecUnElementFixe( Element elementAVerifier ) {
		return this.getElementFixeEnCollisionAvecElement(elementAVerifier) != null;
	}
	
	/**
	 * Retourne vrai si un ennemi est en collision avec l'élément à vérifier, faux s'il n'y en a pas.
	 * @param elementAVerifier L'élément à vérifier.
	 * @return Vrai si un ennemi est en collision avec l'élément à vérifier, faux s'il n'y en a pas.
	 */
	public boolean elementEnCollisionAvecUnEnnemi( Element elementAVerifier ) {
		return this.getEnnemiEnCollisionAvecElement(elementAVerifier) != null;
	}
	
	/**
	 * Retourne vrai si une porte est en collision avec l'élément à vérifier, faux s'il n'y en a pas.
	 * @param elementAVerifier L'élément à vérifier.
	 * @return Vrai si une porte est en collision avec l'élément à vérifier, faux s'il n'y en a pas.
	 */
	public boolean elementEnCollisionAvecUnePorte( Element elementAVerifier  ) {
		return this.getPorteEnCollisionAvecElement(elementAVerifier) != null;
	}
	
	/**
	 * Retourne vrai si un élément ramassable est en collision avec l'élément à vérifier, faux s'il n'y en a pas.
	 * @param elementAVerifier L'élément à vérifier.
	 * @return Vrai si un élément ramassable est en collision avec l'élément à vérifier, faux s'il n'y en a pas.
	 */
	public boolean elementEnCollisionAvecUnElementRamassable( Element elementAVerifier  ) {
		return this.getElementRamassableEnCollisionAvecElement(elementAVerifier) != null;
	}

	
	/**
	 * Retourne la liste des éléments fices de la carte.
	 * @return La liste des éléments fixes de la carte.
	 */
	public ArrayList<ElementFixe> getElementsFixes() {
		return this.elementsFixes;
	}
	
	
	
	
	/**
	 * Retourne le chemin du fichier représentant la carte.
	 * @return Le chemin du fichier représentant la carte.
	 */
	public String getCheminFichierCarte() {
		return this.cheminfichierCarte;
	}
		
	/**
	 * Modifie le personnage de la carte.
	 * @param personnage Le nouveau personnage de la carte.
	 */
	public void setPersonnage( Personnage personnage ) {
		this.personnage=personnage;
	}
	
	/**
	 * Retourne vrai si le personnage de la carte existe (a été instancié), faux sinon.
	 * @return vrai si le personnage de la carte existe, faux sinon.
	 */
	public boolean aUnPersonnage() {
		return this.personnage!=null;
	}
	
	/**
	 * Ajoute une porte à la carte, l'initialisation de cette porte devra être effectué avant l'appel des méthodes update et afficher.
	 * @param porte La porte à ajouter dans la carte.
	 */
	public void ajoutPorte( Porte porte ) {
		this.portes.add(porte);
	}
	
	/**
	 * Ajoute un élément ramassable à la carte, l'initialisation de cette porte devra être effectué avant l'appel des méthodes update et afficher.
	 * @param elementRamassable L'élément ramassable à ajouter dans la carte.
	 */
	public void ajoutElementRamassable( ElementRamassable elementRamassable ) {
		this.elementsRamassables.add(elementRamassable);
	}
	
	/**
	 * Ajoute un élément fixe à la carte, l'initialisation de cette porte devra être effectué avant l'appel des méthodes update et afficher.
	 * @param elementFixe L'élément fixe à ajouter dans la carte.
	 */
	public void ajoutElementFixe( ElementFixe elementFixe ) {
		this.elementsFixes.add(elementFixe);
	}
	
	/**
	 * Ajoute un ennemi à la carte, l'initialisation de cette porte devra être effectué avant l'appel des méthodes update et afficher.
	 * @param ennemi L'ennemi à ajouter dans la carte.
	 */
	public void ajoutEnnemi( Ennemi ennemi ) {
		this.ennemis.add(ennemi);
	}
	
	
	
}
