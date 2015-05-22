package Jeux;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.newdawn.slick.util.DefaultLogSystem;

public class FileLogSystem extends DefaultLogSystem {

	// https://github.com/ariejan/slick2d/blob/master/src/org/newdawn/slick/util/DefaultLogSystem.java
	
	private String cheminFichierLog = "TheQuaterpillarQuest.log"; 
	
	public FileLogSystem( String cheminFichierLog ) {
		this.cheminFichierLog = cheminFichierLog;
	}
	
	public FileLogSystem() {
		this("TheQuaterpillarQuest.log");
	}
	
	private void ecrireMessage( String message ) {

		try {
			
			FileWriter out = new FileWriter( this.cheminFichierLog, true );	// true : pour append dans le fichier
			out.write( new Date()+" DEBUG:" + message + "\n" );
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
