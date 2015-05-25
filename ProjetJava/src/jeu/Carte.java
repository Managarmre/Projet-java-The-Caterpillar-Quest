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
 * Classe repr�santant la carte du jeu.
 * Cette classe contient tout les �l�ments graphique. Ces �l�ments graphiques se trouvent dans le package 'elementsGraphiques'.
 * Celle-ci doit �tre charg�e � partir d'un fichier .map, se chargement est effectuer � l'aide de la classe Parser.
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
	 * Cr�e une nouvelle carte.
	 * @throws IOException 
	 */
	public Carte( String cheminCarte ) throws IOException {
		
		this.fichier = cheminCarte;
		
		this.personnage = new Personnage( 0, 0 );
		
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
	
	/*
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
		this.parseur.chargerColonneSuivante();
		try {
			this.initialiser(null);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
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
	 * Supprime un ennemi de la carte.
	 * @param ennemi L'ennemi � supprimer.
	 * @return Vrai si l'ennemi a bien �t� supprim�, faux sinon
	 */
	public boolean supprimerEnnemi( Ennemi ennemi ) {
		return this.ennemis.remove(ennemi);
	}
	
	/**
	 * Supprime un �l�ment fixe de la carte.
	 * @param fixe L'�l�ment fixe � supprimer.
	 * @return Vrai si l'�l�ment fixe a bien �t� supprim�, faux sinon.
	 */
	public boolean supprimerElementFixe( ElementFixe fixe ) {
		return this.elementsFixes.remove(fixe);
	}
	
	/**
	 * Supprime une porte de sortie de la carte.
	 * @param porte La porte � supprimer.
	 * @return Vrai si la porte a bien �t� supprim�, faux sinon.
	 */
	public boolean supprimerPorte( Porte porte ) {
		return this.portes.remove(porte);
	}

		
	/**
	 * Retourne l'�l�ment de la liste avec lequel l'�l�ment � v�rifier est en collision.
	 * On v�rifiera tout les �l�ments de la liste pass� en param�tre.
	 * Si il y en a un qui est en collision avec l'�l�ment � v�rifier, on le retourne.
	 * Si aucun �l�ments n'est en collision avec l'�m�ment � v�rifier, on retourne null.
	 * @param listeElements La liste de tous les �l�ments poossiblement en collision avec l'�l�ment � v�rifier.
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
	 * Retourne le premier �l�ment rammassable de la carte qui est en collision avec l'�l�ment � v�rifier, null s'il n'y en a aucun.
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
