package jeu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import elementsGraphiques.Personnage;


/**
 * Caméra du jeu.
 * La caméra permet de faire avancer la carte (les éléments de la carte) à l'écran. <br/>
 * La carte s'avancera si le personnage arrive à la limite de sa zone de déplacement. <br/>
 * Le personnage du joueur ne peut se déplacer que sur le 1er tiers de l'écran. <br/>
 * 
 * @author Maxime Pineau
 *
 */
public class Camera {
	
	private Personnage personnage;

	private float positionCameraX;
	private final float TIERS_LARGEUR_ECRAN;
	
	/**
	 * Crée une caméra, qui déplacera les éléments graphiqueslorsque le personnage arrivera à la limite de sa zone de déplacement.
	 * @param personnage Le personnage que la caméra doit surveiller.
	 */
	public Camera( Personnage personnage ) {
		this.personnage = personnage;
		this.positionCameraX = 0;
		this.TIERS_LARGEUR_ECRAN = Jeu.LARGEUR / 3;
	}
	
	/**
	 * Met à jour la position de la caméra en fonction de la position du personnage (et de sa zone de déplacement).
	 */
	public void update() {
		
		float positionPersonnageX = this.personnage.getPositionX();
		float borneDeplacementMaximaleX = this.positionCameraX + this.TIERS_LARGEUR_ECRAN;
		
		// si le personnage dépasse sa zone de déplacement, on met à jour la position de la caméra
		if( positionPersonnageX > borneDeplacementMaximaleX ) this.positionCameraX = positionPersonnageX - this.TIERS_LARGEUR_ECRAN;
		
	}
	
	/**
	 * Place la caméra sur le graphique. Cette méthode translate la zone graphique. 
	 * Les éléments dessinés sur le graphiques après cette méthode seront donc translaté eux aussi.
	 * 
	 * @param conteneur Le conteneur graphique de la fenêtre (On pourra récupérer la largeur de la fenêtre...).
	 * @param graphique Le graphique à translater.
	 */
	public void placer( GameContainer conteneur, Graphics graphique ) {
		
		// on translate le graphique vers la gauche seulement.
		graphique.translate( - this.positionCameraX, 0 );
	
	}
	
	/**
	 * Retourne la position x de la caméra (la position y de la caméra est toujours 0).
	 * @return La position x de la caméra.
	 */
	public float getPositionCameraX() {
		return this.positionCameraX;
	}
	
	
}
