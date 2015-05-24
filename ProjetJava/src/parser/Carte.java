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
		// initialisation des listes et de la carte par d�faut
		this.elementsRamassables = new ArrayList<ElementRamassable>();
		this.ennemis = new ArrayList<Ennemi>();
		this.elementsFixes = new ArrayList<ElementFixe>();
		this.porte=new ArrayList<Porte>();
		this.fichier="default.map";
	}
	
	/**
	 * M�thode permettant d'obtenir le fichier de la carte
	 * @return String
	 */
	public String getCheminFichierCarte()
	{
		return this.fichier;
	}
	
	/**
	 * M�thode permettant d'ajouter une porte
	 * @param p la porte � ajouter
	 */
	public void ajoutPorte(Porte p)
	{
		this.porte.add(p);
	}
	
	/**
	 * M�thode permettant d'ajouter un personnage
	 * @param p le personnage
	 */
	public void ajoutPersonnage(Personnage p)
	{
		this.personnage=p;
	}
	
	/**
	 * M�thode permettant de v�rifier si un personnage a d�j� �t� ajout� ou non
	 * @return boolean
	 */
	public boolean aUnPersonnage()
	{
		return this.personnage!=null;
	}
	
	/**
	 * M�thode permettant d'ajouter un �l�ment � ramasser
	 * @param er l'�l�ment � ramasser
	 */
	public void ajoutElementRamassable(ElementRamassable er)
	{
		this.elementsRamassables.add(er);
	}
	
	/**
	 * M�thode permettant d'ajouter un �l�ment fixe
	 * @param ef l'�l�ment fixe
	 */
	public void ajoutElementFixe(ElementFixe ef)
	{
		this.elementsFixes.add(ef);
	}
	
	/**
	 * M�thode permettant d'ajouter un ennemi
	 * @param e l'ennemi
	 */
	public void ajoutEnnemi(Ennemi e)
	{
		this.ennemis.add(e);
	}	
}
