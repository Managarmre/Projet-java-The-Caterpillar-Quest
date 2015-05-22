package Jeu;

/**
 * Exception lanc�e lorsque le joueur a perdu la partie. 
 * Le joueur peut perdre une partie en touchant une gu�pe, ou en tombant (le faisant sortir de la fen�tre).
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
