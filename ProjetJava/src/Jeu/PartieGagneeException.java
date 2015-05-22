package Jeu;

/**
 * Exception lancée lorsque le joueur a gagné la partie.
 * Le joueur ne peut gagner la partie qu'en atteignant l'une des portes de la carte.
 * 
 * @author Maxime Pineau
 *
 */
public class PartieGagneeException extends PartieException {

	private static final long serialVersionUID = 2702290166431026040L;

	public PartieGagneeException() {
		super("La partie est gagnée.");
	}

}
