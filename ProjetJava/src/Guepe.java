
public class Guepe extends Element
{	
	private int pointArrivee;
	private int pointDepart=0;
	private boolean deplacementHorizontal;
	
	public Guepe( int x, int y, int arrivee, boolean deplacementHorizontal )
	{
		super(y,x);
		this.pointArrivee=this.pointDepart+arrivee;
		this.deplacementHorizontal=deplacementHorizontal;
		System.out.println(y+" -- "+x+" : "+this.toString());
	}
	
	public String toString()
	{
		return "guepe "+this.deplacementHorizontal+" de "+this.pointArrivee+" cases --- ";
	}
}
