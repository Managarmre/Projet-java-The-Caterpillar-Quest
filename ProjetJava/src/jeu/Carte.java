package jeu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import app.Ressources;

import parser.Parser;

import elementsGraphiques.Element;
import elementsGraphiques.ElementFixe;
import elementsGraphiques.ElementRamassable;
import elementsGraphiques.Ennemi;
import elementsGraphiques.Personnage;
import elementsGraphiques.Porte;



/**
 * Classe repr�santant la carte du jeu.
 * Cette classe contient tout les �l�ments graphiques. Ces �l�ments graphiques se trouvent dans le package 'elementsGraphiques'.
 * Celle-ci doit �tre charg�e � partir d'un fichier .map, ce chargement est effectu� � l'aide de la classe Parser.
 * 
 * @author Maxime Pineau
 * @see elementsGraphiques
 */
public class Carte {
	
	private String cheminfichierCarte;	// le chemin du fichier repr�sentant la carte.
	public Parser parseur; 				// permet de charger la carte dynamiquement depuis un fichier textuel 	
	
	private Personnage personnage;
		
	private ArrayList<Porte> portes;
	private ArrayList<ElementRamassable> elementsRamassables;
	private ArrayList<Ennemi> ennemis;
	private ArrayList<ElementFixe> elementsFixes;
	
	
	/**
	 * Cr�� une nouvelle carte.
	 * @throws IOException Une erreur est survenue lors du chargement de la carte (lecture du fichier de carte).
	 */
	public Carte( String cheminCarte ) throws IOException {
		
		this.cheminfichierCarte = cheminCarte;
				
		this.elementsRamassables = new ArrayList<ElementRamassable>();
		this.ennemis = new ArrayList<Ennemi>();
		this.elementsFixes = new ArrayList<ElementFixe>();
		this.portes = new ArrayList<Porte>();
		
		this.parseur = new Parser(this);
		this.parseur.getEcranInit();	
		// le personnage est initialis� lors de l'appel du parseur
		
	}

	/**
	 * Cr�� une nouvelle carte en utilisant le fichier de carte par d�faut.
	 * @throws IOException Une erreur est survenue lors du chargement de la carte (lecture du fichier de carte).
	 */
	public Carte() throws IOException {
		this(Ressources.Cartes.DEFAULT);
	}
	
	
	
	/**
	 * Initialise la carte et ses �l�ments graphiques (ajout des images, sprites...).
	 * Cette m�thode est � appeler dans la fonction Jeu.init().
	 * @throws SlickException Lanc�e lorsqu'une erreur est d�tect�e par la librairie Slick2D (image non trouv�e...).
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
	 * Met � jour les donn�es de la cartes (position des �l�ments...).
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
		
		/* 
			L'ordre d'affichage est important ici, 
			car les �l�ments affich�s avant les autres seront en dessous des nouveaux �l�ments affich�s.		
		*/
		
		for( Porte porte : this.portes ) {
			porte.afficher( conteneur, graphique );
		}
		
		for( ElementFixe fixe : this.elementsFixes ) {
			fixe.afficher( conteneur, graphique );
		}	
				
		for( ElementRamassable ramassable : this.elementsRamassables ) {
			ramassable.afficher( conteneur, graphique );
		}
				
		for( Ennemi ennemi : this.ennemis ) {
			ennemi.afficher( conteneur, graphique );
		}
		
		this.personnage.afficher( conteneur, graphique );
		
	}
	
		
	/**
	 * Retourne le personnage du joueur pr�sent sur la carte.
	 * @return Le personnage du joueur.
	 */
	public Personnage getPersonnage() {
		return personnage;
	}


	/**
	 * Supprime un �l�ment ramassable de la carte.
	 * @param ramassable L'�l�ment ramassable � supprimer.
	 * @return Vrai si l'�l�ment a bien �t� supprim�, faux sinon
	 */
	public boolean supprimerElementRamassable( ElementRamassable ramassable ) {
		return this.elementsRamassables.remove(ramassable);
	}
	
	
	/**
	 * Supprime de la carte les �l�ments trop �loign�s par rapport � une position (repr�sente le bord gauche de la fen�tre).
	 * La position du bord est stock�e dans la classe Camera, qui centre la fen�tre en fonction du d�placement du personnage (fait bouger la carte).
	 * C'est �galement la classe Camera qui appelle cette m�thode.
	 * @param positionCameraX La position de la cam�ra, repr�sentant le bord gauche de la fen�tre.
	 * @see Camera
	 */
	public void supprimerElementsTropEloignes( float positionCameraX ) {
		
		// on supprime tous les �l�ments ayant d�pass�s d'une case de l'�cran vers la gauche.
		// sauf pour les ennemis, o� l'on regarde les positions de d�part et d'arriv�e
		
		// la longueur d'une case fait 32 pixel.
		
		ElementFixe fixe;
		for( Iterator<ElementFixe> iterateur = this.elementsFixes.iterator(); iterateur.hasNext(); ) {
			fixe = iterateur.next();
			if( fixe.getPositionX() < positionCameraX - 32 ) iterateur.remove();
		}
		
		ElementRamassable ramassable;
		for( Iterator<ElementRamassable> iterateur = this.elementsRamassables.iterator(); iterateur.hasNext(); ) {
			ramassable = iterateur.next();
			// si l'�l�ment sort de l'�cran, on le supprime
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
			// si l'ennemi ne se d�place plus sur la zone visible de l'�cran, on le supprime.
			// l'ennemi se d�place entre 2 points, il faut donc v�rifier les coordonn�es X des deux points.
			if( ennemi.getDepart().getX() < positionCameraX - 32 && ennemi.getArrivee().getX() < positionCameraX - 32 ) iterateur.remove();
		}
		
	}
	

		
	/**
	 * Retourne l'�l�ment de la liste avec lequel l'�l�ment � v�rifier est en collision.
	 * On v�rifiera tous les �l�ments de la liste pass�e en param�tre.
	 * Si il y en a un qui est en collision avec l'�l�ment � v�rifier, on le retourne.
	 * Si aucun �l�ment n'est en collision avec l'�l�ment � v�rifier, on retourne null.
	 * @param listeElements La liste de tous les �l�ments possiblement en collision avec l'�l�ment � v�rifier.
	 * @param elementAVerifier L'�l�ment � v�rifier
	 * @return L'�l�ment de la liste qui est en collision avec l'�l�ment � v�rifier pass� en param�tre, null si aucun �l�ment de la liste n'est en collision avec l'�l�ment � v�rifier.
	 */
	private <E extends Element> E elemenEnCollisionAvecUnElementListe( ArrayList<E> listeElements, Element elementAVerifier ) {
		
		for( E element : listeElements ) {
			
			if( element.estEnCollisionAvec(elementAVerifier) ) return element;
		}
		
		return null;
	}
		
	/**
	 * Retourne le premier �l�ment fixe de la carte qui est en collision avec l'�l�ment � v�rifier, null s'il n'y en a aucun.
	 * @param elementAVerifier L'�l�ment � v�rifier.
	 * @return Le premier �l�ment fixe de la carte qui est en collision avec l'�l�ment � v�rifier, null s'il n'y en a aucun.
	 */
	public ElementFixe getElementFixeEnCollisionAvecElement( Element elementAVerifier ) {
		return this.elemenEnCollisionAvecUnElementListe( this.elementsFixes, elementAVerifier );
	}
		
	/**
	 * Retourne le premier ennemi de la carte qui est en collision avec l'�l�ment � v�rifier, null s'il n'y en a aucun.
	 * @param elementAVerifier L'�l�ment � v�rifier.
	 * @return Le premier ennemi de la carte qui est en collision avec l'�l�ment � v�rifier, null s'il n'y en a aucun.
	 */
	public Ennemi getEnnemiEnCollisionAvecElement( Element elementAVerifier ) {
		return this.elemenEnCollisionAvecUnElementListe( this.ennemis, elementAVerifier );
	}
	
	/**
	 * Retourne la premi�re porte de sortie de la carte qui est en collision avec l'�l�ment � v�rifier, null s'il n'y en a aucun.
	 * @param elementAVerifier L'�l�ment � v�rifier.
	 * @return La premi�re porte de la carte qui est en collision avec l'�l�ment � v�rifier, null s'il n'y en a aucun.
	 */
	public Porte getPorteEnCollisionAvecElement( Element elementAVerifier ) {
		return this.elemenEnCollisionAvecUnElementListe( this.portes, elementAVerifier );
	}
	
	/**
	 * Retourne le premier �l�ment ramassable de la carte qui est en collision avec l'�l�ment � v�rifier, null s'il n'y en a aucun.
	 * @param elementAVerifier L'�l�ment � v�rifier.
	 * @return Le premier �l�ment ramassable de la carte qui est en collision avec l'�l�ment � v�rifier, null s'il n'y en a aucun.
	 */
	public ElementRamassable getElementRamassableEnCollisionAvecElement( Element elementAVerifier ) {
		return this.elemenEnCollisionAvecUnElementListe( this.elementsRamassables, elementAVerifier );
	}
	
	
	
	/**
	 * Retourne vrai si un �l�ment fixe est en collision avec l'�l�ment � v�rifier, faux s'il n'y en a pas.
	 * @param elementAVerifier L'�l�ment � v�rifier.
	 * @return Vrai si un �l�ment fixe est en collision avec l'�l�ment � v�rifier, faux s'il n'y en a pas.
	 */
	public boolean elementEnCollisionAvecUnElementFixe( Element elementAVerifier ) {
		return this.getElementFixeEnCollisionAvecElement(elementAVerifier) != null;
	}
	
	/**
	 * Retourne vrai si un ennemi est en collision avec l'�l�ment � v�rifier, faux s'il n'y en a pas.
	 * @param elementAVerifier L'�l�ment � v�rifier.
	 * @return Vrai si un ennemi est en collision avec l'�l�ment � v�rifier, faux s'il n'y en a pas.
	 */
	public boolean elementEnCollisionAvecUnEnnemi( Element elementAVerifier ) {
		return this.getEnnemiEnCollisionAvecElement(elementAVerifier) != null;
	}
	
	/**
	 * Retourne vrai si une porte est en collision avec l'�l�ment � v�rifier, faux s'il n'y en a pas.
	 * @param elementAVerifier L'�l�ment � v�rifier.
	 * @return Vrai si une porte est en collision avec l'�l�ment � v�rifier, faux s'il n'y en a pas.
	 */
	public boolean elementEnCollisionAvecUnePorte( Element elementAVerifier  ) {
		return this.getPorteEnCollisionAvecElement(elementAVerifier) != null;
	}
	
	/**
	 * Retourne vrai si un �l�ment ramassable est en collision avec l'�l�ment � v�rifier, faux s'il n'y en a pas.
	 * @param elementAVerifier L'�l�ment � v�rifier.
	 * @return Vrai si un �l�ment ramassable est en collision avec l'�l�ment � v�rifier, faux s'il n'y en a pas.
	 */
	public boolean elementEnCollisionAvecUnElementRamassable( Element elementAVerifier  ) {
		return this.getElementRamassableEnCollisionAvecElement(elementAVerifier) != null;
	}

	
	/**
	 * Retourne la liste des �l�ments fixes de la carte.
	 * @return La liste des �l�ments fixes de la carte.
	 */
	public ArrayList<ElementFixe> getElementsFixes() {
		return this.elementsFixes;
	}
	
	
	
	
	/**
	 * Retourne le chemin du fichier repr�sentant la carte.
	 * @return Le chemin du fichier repr�sentant la carte.
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
	 * Retourne vrai si le personnage de la carte existe (a �t� instanci�), faux sinon.
	 * @return vrai si le personnage de la carte existe, faux sinon.
	 */
	public boolean aUnPersonnage() {
		return this.personnage!=null;
	}
	
	/**
	 * Ajoute une porte � la carte, l'initialisation de cette porte devra �tre effectu�e avant l'appel des m�thodes update et afficher.
	 * @param porte La porte � ajouter dans la carte.
	 */
	public void ajoutPorte( Porte porte ) {
		this.portes.add(porte);
	}
	
	/**
	 * Ajoute un �l�ment ramassable � la carte, l'initialisation de cet �l�ment devra �tre effectu�e avant l'appel des m�thodes update et afficher.
	 * @param elementRamassable L'�l�ment ramassable � ajouter dans la carte.
	 */
	public void ajoutElementRamassable( ElementRamassable elementRamassable ) {
		this.elementsRamassables.add(elementRamassable);
	}
	
	/**
	 * Ajoute un �l�ment fixe � la carte, l'initialisation de cet �l�ment devra �tre effectu�e avant l'appel des m�thodes update et afficher.
	 * @param elementFixe L'�l�ment fixe � ajouter dans la carte.
	 */
	public void ajoutElementFixe( ElementFixe elementFixe ) {
		this.elementsFixes.add(elementFixe);
	}
	
	/**
	 * Ajoute un ennemi � la carte, l'initialisation de cet ennemi devra �tre effectu�e avant l'appel des m�thodes update et afficher.
	 * @param ennemi L'ennemi � ajouter dans la carte.
	 */
	public void ajoutEnnemi( Ennemi ennemi ) {
		this.ennemis.add(ennemi);
	}
	
	
	
}
