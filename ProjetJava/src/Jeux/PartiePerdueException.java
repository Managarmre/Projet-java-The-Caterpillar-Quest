package Jeux;

public class PartiePerdueException extends PartieException {

	private static final long serialVersionUID = -3912912859528733416L;

	public PartiePerdueException() {
		super("La partie est perdue.");
	}

}
