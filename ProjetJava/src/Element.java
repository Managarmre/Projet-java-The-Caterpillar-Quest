
public abstract class Element 
{
	private int ligne;
	private int colonne;
	
	public Element(int l, int c)
	{
		this.ligne=l;
		this.colonne=c;
	}
	
	public String toString()
	{
		return this.ligne+" "+this.colonne;
	}
}
