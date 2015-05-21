import java.util.ArrayList;


public class Carte 
{
	private String cheminFichierCarte;
	private ArrayList<Guepe> guepe;
	private ArrayList<Cerise> cerise;
	
	private Personnage personnage;
	
	private ArrayList<Plateforme> plateforme;
	private ArrayList<Porte> porte;
	
	public Carte( String cheminCarte )
	{
		this.cheminFichierCarte = cheminCarte;
		this.guepe=new ArrayList<Guepe>();
		this.plateforme=new ArrayList<Plateforme>(20);
		this.cerise=new ArrayList<Cerise>(20);
		this.porte=new ArrayList<Porte>(20);
	}
	
	public Carte()
	{
		this("default.map");
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
		this.personnage = p;
	}
	
	public boolean aUnPersonnage()
	{
		return this.personnage != null;
	}
	
	public String getCheminFichierCarte()
	{
		return this.cheminFichierCarte;
	}
}
