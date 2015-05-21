package Jeux;

public class PartieGagneeException extends PartieException {

	private static final long serialVersionUID = 2702290166431026040L;

	public PartieGagneeException() {
		super("La partie est gagnée.");
	}

}
