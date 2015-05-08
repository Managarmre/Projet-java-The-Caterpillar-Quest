
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Score implements Comparable<Score>, Serializable {
	
	private String nomJoueur;
	private int nbPoints;
	private double duree;
	
	public Score( String nomJoueur, int nbPoint, double duree ) {
		this.nomJoueur = nomJoueur;
		this.nbPoints = nbPoint;
		this.duree = duree;
	}

	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "" + this.nomJoueur + "  nbPoints : " + this.nbPoints + ", duree : " + this.duree;
	}

	@Override
	public int compareTo(Score score) {
		/*
		 * this > arg0 	: retourne positif
		 * this = arg0 	: retourne 0
		 * this < arg0 : retourne negatif
		 */
	
		// on compare d'abord avec le nombre de points
		if( this.nbPoints > score.nbPoints ) return 1;
		else if( this.nbPoints < score.nbPoints ) return -1;
		
		// puis avec la durée
		if( this.duree > score.duree ) return 1;
		else if( this.duree == score.duree ) return 0;
		else return -1;
	}


	
	public void writeObject( ObjectOutputStream out ) throws IOException {
		out.writeObject( this.nomJoueur );
		out.writeObject( this.nbPoints );
		out.writeObject( this.duree );
	}
	
	public void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
		this.nomJoueur = (String) in.readObject();
		this.nbPoints = (int) in.readObject();
		this.duree = (double) in.readObject();
	}
	
	
}
