package App;

/**
 * Exception lancée lors d'une erreur de chargemet ou de sauvegarde du gestionnaire de score.
 * @author Maxime Pineau
 * @see GestionScores
 */
public class GestionScoresException extends Exception {
	
	private static final long serialVersionUID = 6755239350190717500L;

	public GestionScoresException( String mes ) {
		super(mes);
	}
	
}
