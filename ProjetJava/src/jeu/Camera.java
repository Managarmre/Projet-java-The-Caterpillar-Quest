package jeu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import elementsGraphiques.Personnage;


/**
 * Cam�ra du jeu, elle g�re la zone de d�placement du personnage.
 * La cam�ra permet de faire avancer la carte (les �l�ments de la carte) � l'�cran. <br/>
 * La carte s'avancera si le personnage arrive � la limite de sa zone de d�placement. <br/>
 * Le personnage du joueur ne peut se d�placer que sur le 1er tiers de l'�cran. <br/>
 * C'est �galement la cam�ra qui va charger les nouveaux �l�ments de la carte, et supprimer ceux trop �loign�s et non atteingnable par le personnage.
 * 
 * @author Maxime Pineau
 *
 */
public class Camera {
	
	private Personnage personnage;
	private Carte carte;
	
	private float positionCameraX;
	private final float TIERS_LARGEUR_ECRAN;
	
	/**
	 * Cr�� une cam�ra qui d�placera les �l�ments graphiques lorsque le personnage arrivera � la limite de sa zone de d�placement.
	 * @param personnage Le personnage que la cam�ra doit surveiller.
	 * @param carte La carte qui sera mis � jour selon la position du personnage (si le personnage va trop loin, on charge des �l�ments, et l'on supprime ceux inutiles).
	 */
	public Camera( Personnage personnage, Carte carte ) {
		this.personnage = personnage;
		this.carte = carte;
		this.positionCameraX = 0;
		this.TIERS_LARGEUR_ECRAN = Jeu.LARGEUR / 3;
	}
	
	/**
	 * Met � jour la position de la cam�ra en fonction de la position du personnage (et de sa zone de d�placement).
	 * Si le personnage d�passe sa zone de d�placement � droite, la cam�ra avance avec lui, d�pla�ant ainsi la carte.
	 * Si le personnage d�passe sa zone de d�placement � gauche, et sort de l'�cran, on replace le personnage sur le bord de la fen�tre.
	 * C'est �galement cette m�thode qui charge les cases suivantes de la carte en appelant le parseur, et qui les initialises.
	 * @throws SlickException Exception durant la mise � jour de la cam�ra (durant l'initialisation des nouveaux �l�ments de la cartes) .
	 */
	public void update() throws SlickException {
				
		float positionPersonnageX = this.personnage.getPositionX();
		float borneDeplacementMaximaleX = this.positionCameraX + this.TIERS_LARGEUR_ECRAN;
		
		// le personnage ne peut pas revenir en arri�re, on le replace si le cas se pr�sente.
		if( positionPersonnageX - this.positionCameraX < 0.1 ) this.personnage.setPositionX( this.positionCameraX );
		
		// si le personnage d�passe sa zone de d�placement � droite, on met � jour la position de la cam�ra afin d'avancer la carte.
		if( positionPersonnageX > borneDeplacementMaximaleX ) {
			this.positionCameraX = positionPersonnageX - this.TIERS_LARGEUR_ECRAN;
			
			
			float calcul = ( this.positionCameraX % 32 );
			
			if( calcul <= 5 ) { 

				this.carte.parseur.chargerColonneSuivante();
				this.carte.supprimerElementsTropEloignes( this.positionCameraX );
				this.carte.initialiser();
			}
			
		}
		
		
		
	}
	
	/**
	 * Place la cam�ra sur le graphique. Cette m�thode translate la zone graphique. 
	 * Les �l�ments dessin�s sur le graphiques apr�s l'appel de cette m�thode seront donc translat�s eux aussi.
	 * 
	 * @param conteneur Le conteneur graphique de la fen�tre (On pourra r�cup�rer la largeur de la fen�tre...).
	 * @param graphique Le graphique � translater.
	 */
	public void placer( GameContainer conteneur, Graphics graphique ) {
		
		// on translate le graphique vers la gauche seulement.
		graphique.translate( - this.positionCameraX, 0 );
	
	}
	
	/**
	 * Retourne la position x de la cam�ra (la position y de la cam�ra est toujours 0).
	 * @return La position x de la cam�ra.
	 */
	public float getPositionCameraX() {
		return this.positionCameraX;
	}

	
	
}
