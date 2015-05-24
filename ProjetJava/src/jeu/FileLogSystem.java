package jeu;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.newdawn.slick.util.LogSystem;

/**
 * Classe permettant de rediriger les logs de Slick2D vers un fichier de log. 
 * Pour rediriger les logs, il faut utiliser Log.setLogSystem(...).
 * Pour plus d'information, voir https://github.com/ariejan/slick2d/blob/master/src/org/newdawn/slick/util/DefaultLogSystem.java.
 *  
 * @author Maxime Pineau
 * @see <a href="http://slick.ninjacave.com/javadoc/org/newdawn/slick/util/LogSystem.html">LogSystem</a>
 * 
 */
public class FileLogSystem implements LogSystem {

	private String cheminFichierLog;; 
	
	/**
	 * Créé une instance de FileLogSystem qui redirigera les logs de Slick2D vers le fichier 'cheminFichierLog'.
	 * @param cheminFichierLog Le chemin du fichier vers lequel les logs seront redirigés.
	 */
	public FileLogSystem( String cheminFichierLog ) {
		this.cheminFichierLog = cheminFichierLog;
	}
	
	/**
	 * Créé une instance de FileLogSystem qui redirigera les logs de Slick2D vers le fichier 'TheQuaterpillarQuest.log'.
	 */
	public FileLogSystem() {
		this("TheCaterpillarQuest.log");
	}
	
	
	/**
	 * Permet d'écrire un message dans le fichier log.
	 * @param message Le message à écrire dans le fichier log.
	 */
	private void ecrireMessage( String message ) {

		try {
			
			FileWriter out = new FileWriter( this.cheminFichierLog, true );	// true : pour append dans le fichier
			out.write( new Date() + " DEBUG:" + message + "\n" );
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void debug( String message ) {
		this.ecrireMessage(message);
	}

	@Override
	public void error( Throwable erreur ) {
		this.ecrireMessage( erreur.getMessage() );
	}

	@Override
	public void error( String message ) {
		this.ecrireMessage(message);
	}

	@Override
	public void error(String message, Throwable arg1) {
		this.ecrireMessage(message);
	}

	@Override
	public void info( String message ) {
		this.ecrireMessage(message);
	}

	@Override
	public void warn( String message ) {
		this.ecrireMessage(message);
	}

	@Override
	public void warn( String message, Throwable erreur ) {
		this.ecrireMessage(message);
	}

}
