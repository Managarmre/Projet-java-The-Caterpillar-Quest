package jeu;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import elementsGraphiques.Orientation;
import elementsGraphiques.Personnage;


/**
 * G�re les entr�es clavier pour d�placer le personnage � l'�cran.
 * Cette classe permet de r�cup�rer les entr�es clavier, et de modifier vers quelle direction le personnage se dirige en fonction de celles-ci.
 *  
 * @author Cyril Caron
 * 
 */
public class ControleurPersonnage implements KeyListener {


	private Personnage player;
	
	/**
	 * Construit un nouveau controleur d'entr�e / sortie pour le personnage pass� en param�tre.
	 * @param player Le personnage � modifier en fonction des entr�es / sorties clavier de l'utilisateur.
	 */
	public ControleurPersonnage( Personnage player ) {
		this.player = player;
	}

	
	@Override
	public void keyPressed( int key, char c ) {
		
		switch(key) {
		
			case Input.KEY_UP:
				//this.player.setOrientation(Orientation.HAUT);
				this.player.setVeutSauter(true);
				//this.player.setMoving(true);
				break;
				
			case Input.KEY_LEFT:
				this.player.setOrientation( Orientation.GAUCHE );
				this.player.setMoving(true);
			    break;
			    
			case Input.KEY_RIGHT:
				this.player.setOrientation( Orientation.DROITE );
				this.player.setMoving(true);
				System.out.println("DROITE DEBUT");
			    break;
			
			default: 
				break;
		}
		
	}

	@Override
	public void keyReleased( int key, char c ) {
		
		switch(key) {
			
			case Input.KEY_UP:
				this.player.setVeutSauter(false);
				//this.player.setMoving(false);
				break;
		
			case Input.KEY_LEFT:
				this.player.setMoving(false);		// le personnage arrete de bouger lorsque le joueur relache la touche.
			    break;
			    
			case Input.KEY_RIGHT:
				this.player.setMoving(false);		// le personnage arrete de bouger lorsque le joueur relache la touche.
				System.out.println("DROITE FIN");
				break;
			
			default: 
				break;
		}
		
	}
	
	
	
	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
