/**
 * @author Pauline
 */

package parser;
import java.util.ArrayList;

public class Carte 
{
	private Personnage personnage;
	private ArrayList<Porte> porte;
	private String fichier;
	private ArrayList<ElementRamassable> elementsRamassables;
	private ArrayList<Ennemi> ennemis;
	private ArrayList<ElementFixe> elementsFixes;
	
	/**
	 * Constructeur de la classe Carte
	 */
	public Carte() 
	{
		// initialisation des listes et de la carte par défaut
		this.elementsRamassables = new ArrayList<ElementRamassable>();
		this.ennemis = new ArrayList<Ennemi>();
		this.elementsFixes = new ArrayList<ElementFixe>();
		this.porte=new ArrayList<Porte>();
		this.fichier="default.map";
	}
	
	/**
	 * Méthode permettant d'obtenir le fichier de la carte
	 * @return String
	 */
	public String getCheminFichierCarte()
	{
		return this.fichier;
	}
	
	/**
	 * Méthode permettant d'ajouter une porte
	 * @param p la porte à ajouter
	 */
	public void ajoutPorte(Porte p)
	{
		this.porte.add(p);
	}
	
	/**
	 * Méthode permettant d'ajouter un personnage
	 * @param p le personnage
	 */
	public void ajoutPersonnage(Personnage p)
	{
		this.personnage=p;
	}
	
	/**
	 * Méthode permettant de vérifier si un personnage a déjà été ajouté ou non
	 * @return boolean
	 */
	public boolean aUnPersonnage()
	{
		return this.personnage!=null;
	}
	
	/**
	 * Méthode permettant d'ajouter un élément à ramasser
	 * @param er l'élément à ramasser
	 */
	public void ajoutElementRamassable(ElementRamassable er)
	{
		this.elementsRamassables.add(er);
	}
	
	/**
	 * Méthode permettant d'ajouter un élément fixe
	 * @param ef l'élément fixe
	 */
	public void ajoutElementFixe(ElementFixe ef)
	{
		this.elementsFixes.add(ef);
	}
	
	/**
	 * Méthode permettant d'ajouter un ennemi
	 * @param e l'ennemi
	 */
	public void ajoutEnnemi(Ennemi e)
	{
		this.ennemis.add(e);
	}	
}
