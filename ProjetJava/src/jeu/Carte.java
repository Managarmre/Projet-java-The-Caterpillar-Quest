package jeu;

import java.io.IOException;
import java.util.ArrayList;

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
	
	private String fichier;
	public Parser parseur;
	
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
		
		this.fichier = cheminCarte;
		
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
	 * Supprime un ennemi de la carte.
	 * @param ennemi L'ennemi à supprimer.
	 * @return Vrai si l'ennemi a bien été supprimé, faux sinon
	 */
	public boolean supprimerEnnemi( Ennemi ennemi ) {
		return this.ennemis.remove(ennemi);
	}
	
	/**
	 * Supprime un élément fixe de la carte.
	 * @param fixe L'élément fixe à supprimer.
	 * @return Vrai si l'élément fixe a bien été supprimé, faux sinon.
	 */
	public boolean supprimerElementFixe( ElementFixe fixe ) {
		return this.elementsFixes.remove(fixe);
	}
	
	/**
	 * Supprime une porte de sortie de la carte.
	 * @param porte La porte à supprimer.
	 * @return Vrai si la porte a bien été supprimé, faux sinon.
	 */
	public boolean supprimerPorte( Porte porte ) {
		return this.portes.remove(porte);
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

	
	public ArrayList<ElementFixe> getElementsFixes() {
		return this.elementsFixes;
	}
	
	
	
	
	

	public String getCheminFichierCarte()
	{
		return this.fichier;
	}
	
	public void ajoutPorte(Porte p)
	{
		this.portes.add(p);
	}
	
	public void ajoutPersonnage(Personnage p)
	{
		this.personnage=p;
	}
	
	public boolean aUnPersonnage()
	{
		return this.personnage!=null;
	}
	
	public void ajoutElementRamassable(ElementRamassable er)
	{
		this.elementsRamassables.add(er);
	}
	
	public void ajoutElementFixe(ElementFixe ef)
	{
		this.elementsFixes.add(ef);
	}
	
	public void ajoutEnnemi(Ennemi e)
	{
		this.ennemis.add(e);
	}

		
	
	
}
