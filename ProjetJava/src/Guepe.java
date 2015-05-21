import java.awt.Point;

public class Guepe extends Ennemi {
		
	private boolean deplacementHorizontal;
	
	//pointdepartx pointdeparty
	// x = departx / y=departy / depart=arriveex / arrivee=arriveey
	public Guepe( int x, int y, int depart, int arrivee, boolean deplacementHorizontal, String s ) 
	{
		super( x, y, 32, 32,"./sprites/guepe.png", depart, arrivee ,s);	
		this.deplacementHorizontal = deplacementHorizontal;
	}
	
}
