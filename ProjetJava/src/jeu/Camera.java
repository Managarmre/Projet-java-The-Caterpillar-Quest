package jeu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import elementsGraphiques.Personnage;


/**
 * Cam�ra du jeu.
 * La cam�ra permet de faire avancer la carte (les �l�ments de la carte) � l'�cran. <br/>
 * La carte s'avancera si le personnage arrive � la limite de sa zone de d�placement. <br/>
 * Le personnage du joueur ne peut se d�placer que sur le 1er tiers de l'�cran. <br/>
 * 
 * @author Maxime Pineau
 *
 */
public class Camera {
	
	private Personnage personnage;

	private float positionCameraX;
	private final float TIERS_LARGEUR_ECRAN;
	
	/**
	 * Cr�e une cam�ra, qui d�placera les �l�ments graphiqueslorsque le personnage arrivera � la limite de sa zone de d�placement.
	 * @param personnage Le personnage que la cam�ra doit surveiller.
	 */
	public Camera( Personnage personnage ) {
		this.personnage = personnage;
		this.positionCameraX = 0;
		this.TIERS_LARGEUR_ECRAN = Jeu.LARGEUR / 3;
	}
	
	/**
	 * Met � jour la position de la cam�ra en fonction de la position du personnage (et de sa zone de d�placement).
	 */
	public void update() {
		
		float positionPersonnageX = this.personnage.getPositionX();
		float borneDeplacementMaximaleX = this.positionCameraX + this.TIERS_LARGEUR_ECRAN;
		
		// si le personnage d�passe sa zone de d�placement, on met � jour la position de la cam�ra
		if( positionPersonnageX > borneDeplacementMaximaleX ) this.positionCameraX = positionPersonnageX - this.TIERS_LARGEUR_ECRAN;
		
	}
	
	/**
	 * Place la cam�ra sur le graphique. Cette m�thode translate la zone graphique. 
	 * Les �l�ments dessin�s sur le graphiques apr�s cette m�thode seront donc translat� eux aussi.
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
