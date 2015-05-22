package Jeux;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import ElementsGraphiques.Personnage;

public class Camera {
	
	private Personnage personnage;

	private float positionCameraX;
	
	public Camera( Personnage personnage ) {
		this.personnage = personnage;
		this.positionCameraX = Fenetre.LARGEUR / 3;
	}
	
	public void update() {
		
	}
	
	public void placer( GameContainer conteneur, Graphics graphique ) {
		
		float borne = conteneur.getWidth() / 3;
		float positionPersonnageX = this.personnage.getPositionX();
		
		
		if( positionPersonnageX > this.positionCameraX ) {
			
			this.positionCameraX = positionPersonnageX;
			graphique.translate( - this.positionCameraX, 0 );		// test
		}
	}
	
	
	
	
}
