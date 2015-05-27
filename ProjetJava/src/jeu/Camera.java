package jeu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import elementsGraphiques.Personnage;


/**
 * Caméra du jeu, elle gère la zone de déplacement du personnage.
 * La caméra permet de faire avancer la carte (les éléments de la carte) à l'écran. <br/>
 * La carte s'avancera si le personnage arrive à la limite de sa zone de déplacement. <br/>
 * Le personnage du joueur ne peut se déplacer que sur le 1er tiers de l'écran. <br/>
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
	 * Créé une caméra, qui déplacera les éléments graphiques lorsque le personnage arrivera à la limite de sa zone de déplacement.
	 * @param personnage Le personnage que la caméra doit surveiller.
	 */
	public Camera( Personnage personnage, Carte carte ) {
		this.personnage = personnage;
		this.carte = carte;
		this.positionCameraX = 0;
		this.TIERS_LARGEUR_ECRAN = Jeu.LARGEUR / 3;
	}
	
	/**
	 * Met à jour la position de la caméra en fonction de la position du personnage (et de sa zone de déplacement).
	 * Si le personnage dépasse sa zone de déplacement à droite, la caméra avance avec lui, déplaçant ainsi la carte.
	 * Si le personnage dépasse sa zone de déplacement à gauche, et sort de l'écran, on replace le personnage sur le bord de la fenêtre.
	 * @throws SlickException 
	 */
	public void update() throws SlickException {
		
		float oldPositionCameraX = this.positionCameraX;
		
		float positionPersonnageX = this.personnage.getPositionX();
		float borneDeplacementMaximaleX = this.positionCameraX + this.TIERS_LARGEUR_ECRAN;
		
		// le personnage ne peut pas revenir en arrière, on le replace si le cas se présente.
		if( positionPersonnageX - this.positionCameraX < 0.1 ) this.personnage.setPositionX( this.positionCameraX );
		
		// si le personnage dépasse sa zone de déplacement à droite, on met à jour la position de la caméra afin d'avancer la carte.
		if( positionPersonnageX > borneDeplacementMaximaleX ) {
			this.positionCameraX = positionPersonnageX - this.TIERS_LARGEUR_ECRAN;
			
			
			float calcul = ( this.positionCameraX % 32 );
			System.out.println( this.positionCameraX + " "  + oldPositionCameraX + " " + calcul );
			
			
			if( calcul <= 5 ) { //(int)this.positionCameraX % 32 == 1 ) {

				this.carte.parseur.chargerColonneSuivante();
				this.carte.initialiser();
				
			}
			
		}
		
		
		
	}
	
	/**
	 * Place la caméra sur le graphique. Cette méthode translate la zone graphique. 
	 * Les éléments dessinés sur le graphiques après cette méthode seront donc translatés eux aussi.
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
