package Jeux;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import ElementsGraphiques.Personnage;

public class Camera {
	
	private Personnage personnage;

	private float positionCameraX;
	private final float TIERS_LARGEUR_ECRAN;
	
	public Camera( Personnage personnage ) {
		this.personnage = personnage;
		this.positionCameraX = 0;
		this.TIERS_LARGEUR_ECRAN = Fenetre.LARGEUR / 3;
	}
	
	public void update() {
		
		// mise à jour de la position de la caméra selon la position du personnage et de l'ancien état de la caméra
		float positionPersonnageX = this.personnage.getPositionX();
		float borneDeplacementMaximaleX = this.positionCameraX + this.TIERS_LARGEUR_ECRAN;
		
		if( positionPersonnageX > borneDeplacementMaximaleX ) this.positionCameraX = positionPersonnageX - this.TIERS_LARGEUR_ECRAN;
		
	}
	
	public void placer( GameContainer conteneur, Graphics graphique ) {
		
		graphique.translate( - this.positionCameraX, 0 );
		
		float borne = conteneur.getWidth() / 3;
		
		
		
		
	}
	
	
	
	
}
