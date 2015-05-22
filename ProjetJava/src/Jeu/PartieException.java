package jeu;

/**
 * Signale qu'une exception est apparue durant la partie.
 * 
 * @author Maxime Pineau
 * 
 */
public class PartieException extends Exception {

	private static final long serialVersionUID = -4735711643783744371L;

	public PartieException( String message ) {
		super(message);
	}
	
}
