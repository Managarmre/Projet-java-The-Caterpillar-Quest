import java.util.ArrayList;


public class Carte 
{
	private ArrayList<Guepe> guepe;
	private ArrayList<Cerise> cerise;
	private ArrayList<Personnage> personnage;
	private ArrayList<Plateforme> plateforme;
	private ArrayList<Porte> porte;
	
	public Carte()
	{
		this.guepe=new ArrayList<Guepe>();
		this.plateforme=new ArrayList<Plateforme>(20);
		this.cerise=new ArrayList<Cerise>(20);
		this.porte=new ArrayList<Porte>(20);
		this.personnage=new ArrayList<Personnage>(1);
	}
	
	public void addGuepe(Guepe g)
	{
		this.guepe.add(g);
	}

	public void addPlateforme(Plateforme p)
	{
		this.plateforme.add(p);
	}

	public void addCerise(Cerise c)
	{
		this.cerise.add(c);
	}

	public void addPorte(Porte p)
	{
		this.porte.add(p);
	}

	public void addPersonnage(Personnage p)
	{
		this.personnage.add(p);
	}
	
	public boolean aUnPersonnage()
	{
		return this.personnage.size()==1;
	}
}
