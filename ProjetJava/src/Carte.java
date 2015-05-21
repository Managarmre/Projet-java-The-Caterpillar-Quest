import java.util.ArrayList;

public class Carte {
	
	private Personnage personnage;
	private ArrayList<Porte> porte;
	
	private String fichier;
	
	private ArrayList<ElementRamassable> elementsRamassables;
	private ArrayList<Ennemi> ennemis;
	private ArrayList<ElementFixe> elementsFixes;
	
	
	public Carte() 
	{
		this.elementsRamassables = new ArrayList<ElementRamassable>();
		this.ennemis = new ArrayList<Ennemi>();
		this.elementsFixes = new ArrayList<ElementFixe>();
		this.porte=new ArrayList<Porte>();
		this.fichier="default.map";
	}
	
	public String getCheminFichierCarte()
	{
		return this.fichier;
	}
	
	public void ajoutPorte(Porte p)
	{
		this.porte.add(p);
	}
	
	public void ajoutPersonnage(Personnage p)
	{
		this.personnage=p;
	}
	
	public boolean aUnPersonnage()
	{
		return this.personnage!=null;
	}
	
	public void ajoutElementRamassable(ElementRamassable er)
	{
		this.elementsRamassables.add(er);
	}
	
	public void ajoutElementFixe(ElementFixe ef)
	{
		this.elementsFixes.add(ef);
	}
	
	public void ajoutEnnemi(Ennemi e)
	{
		this.ennemis.add(e);
	}	
}
