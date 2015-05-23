package jeu;

/**
 * Exception lancée lorsque le joueur a perdu la partie. 
 * Le joueur peut perdre une partie en touchant une guêpe, ou en tombant (le faisant sortir de la fenêtre).
 * 
 * @author Maxime Pineau
 *
 */
public class PartiePerdueException extends PartieException {

	private static final long serialVersionUID = -3912912859528733416L;

	public PartiePerdueException() {
		super("La partie est perdue.");
	}

}
