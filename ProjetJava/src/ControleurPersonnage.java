import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;


public class ControleurPersonnage implements KeyListener {

	public ControleurPersonnage( ) {
		
	}
	
	@Override
	public void keyPressed( int key, char c ) {

		System.out.println(c);
		
		switch(key) {
		
			case Input.KEY_UP:
				break;
				
			case Input.KEY_LEFT:
			    break;
			    
			case Input.KEY_RIGHT:
			    break;
			
			default: 
				break;
		}
		
	}

	@Override
	public void keyReleased( int key, char c ) {
		// TODO Auto-generated method stub
		
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
