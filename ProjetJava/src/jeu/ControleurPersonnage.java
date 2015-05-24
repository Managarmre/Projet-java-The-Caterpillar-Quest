package jeu;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import elementsGraphiques.Direction;
import elementsGraphiques.Personnage;



/**
 * Cette classe permet de r�cup�rer les entr�es clavier, et de modifier vers quelle direction le personnage se dirige en fonction de celles-ci.
 * 
 * @author Cyril Caron
 *
 */
public class ControleurPersonnage implements KeyListener {


	private Personnage player;
	
	public ControleurPersonnage( Personnage player) {
		this.player = player;
		}

	
	@Override
	public void keyPressed( int key, char c ) {

		System.out.println(c);

		switch(key) {
		
			case Input.KEY_UP:
				this.player.setDirection(Direction.HAUT);
				this.player.setMoving(true);
				break;
				
			case Input.KEY_LEFT:
				this.player.setDirection(Direction.GAUCHE);
				this.player.setMoving(true);
			    break;
			    
			case Input.KEY_RIGHT:
				this.player.setMoving(true);
				this.player.setDirection(Direction.DROITE);
				
			    break;
			
			default: 
				break;
		}
		
	}

	@Override
	public void keyReleased( int key, char c ) {
		 this.player.setMoving(false);
		
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
