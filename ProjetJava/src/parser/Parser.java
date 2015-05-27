/**
 * @author Pauline
 */

package parser;
import java.io.*;
import java.util.ArrayList;

import jeu.Carte;
import elementsGraphiques.*;

public class Parser 
{
	/**
	 * Attributs de la classe Parser
	 */
	private static int nombreLignesMax=20; // attribut correspondant aux 20 premi�res lignes du fichier ; c'est � dire le nombre maximum de lignes lues
	private Carte carte; // la carte � modifier -- � mettre � jour
	private int colonne; // la colonne � lire dans le fichier
	private String cheminFichier; // le chemin du fichier � lire
	private ArrayList<char[]> lignesLues; //d�coupage des lignes en caract�re (liste de liste)
	
	/**
	 * Constructeur de la classe parser
	 * @param carte La carte � mettre � jour
	 * @throws IOException
	 */
	public Parser(Carte carte) throws IOException
	{
		this.carte = carte;
		this.cheminFichier=this.carte.getCheminFichierCarte();
		this.colonne=0;
		this.lignesLues=new ArrayList<char[]>(20); // cr�ation d'une liste de taille 20 (correspondant aux 20 premi�res lignes du fichier)
		
		// r�cup�ration des 20 premi�res lignes du fichier
		InputStream ips=new FileInputStream(this.cheminFichier); 
		InputStreamReader ipsr=new InputStreamReader(ips);
		BufferedReader br=new BufferedReader(ipsr);
		String ligne;
		int i=0;
		while ((ligne=br.readLine())!=null && i<nombreLignesMax)
		{
			ligne=ligne.replaceAll("	","   "); // on remplace les tabulations par une succession de 3 espaces mais en r�gle g�n�rale, tabulations � �viter
			this.lignesLues.add(ligne.toCharArray());
			i++;
		}
		br.close();
	}
	
	/**
	 * M�thode permettant de r�cup�rer les 32 premi�res colonnes des 20 lignes charg�es. Cette m�thode permet donc de charger l'�cran initial.
	 * @throws IOException
	 */
	public void getEcranInit() throws IOException
	{
		recupererColonnes(32);
	}
	
	/**
	 * M�thode permettant de charger la colonne suivante
	 */
	public void chargerColonneSuivante()
	{
		recupererColonnes(1);
	}

	/**
	 * M�thode permettant de r�cup�rer un certain nombre de colonnes
	 * @param maxCol le nombre de colonnes � r�cup�rer
	 */
	private void recupererColonnes(int maxCol)
	{
		int colonneAAtteindre = this.colonne+maxCol; // le num�ro de la colonne finale
		while (this.colonne<colonneAAtteindre)
		{
			for (int i=0;i<this.lignesLues.size();i++)
			{
				if (this.lignesLues.get(i).length>this.colonne) // on v�rifie que la ligne poss�de assez de colonnes avant de r�cup�rer les informations
				{
					char caractere = this.lignesLues.get(i)[this.colonne];
					lectureCaractere(caractere,i,this.colonne);	
				}
			}
			this.colonne++;
		}
	}
	
	/**
	 * M�thode permettant de v�rifier qu'un caract�re est un entier
	 * @param c le caract�re � v�rifier
	 * @return un booleen
	 */
	private boolean estEntier(char c)
	{
		return Character.isDigit(c);
	}

	/**
	 * M�thode permettant de traiter un caract�re et de modifier la carte en fonction de ce dernier
	 * @param c le caract�re � analyser
	 * @param ligne le num�ro de ligne du fichier
	 * @param colonne le num�ro de colonne du fichier
	 */
	private void lectureCaractere(char c, int ligne, int colonne)
	{
		int deplacement=3; // nombre de colonnes par d�placement par d�faut
		int recupDeplacement=colonne+1;
		int x = colonne*32; // position de d�part
		int y = ligne*32; // position de d�part
		String sens="";
		switch(c)
		{
			case '#': this.carte.ajoutElementFixe(new Plateforme(x,y)); break; // lecture d'une plateforme
			case 'P': this.carte.ajoutPorte(new Porte(x,y)); break; // lecture d'une porte
			case 'c': this.carte.ajoutElementRamassable(new Cerise(x,y)); break; // lecture d'une cerise
			case 'v':  // lecture d'une guepe se d�pla�ant verticalement
				sens = "bas"; // sens par d�faut verticalement
				if (this.lignesLues.get(ligne).length>recupDeplacement && this.estEntier(this.lignesLues.get(ligne)[recupDeplacement]))
				{
					if (this.lignesLues.get(ligne).length>recupDeplacement+1 && this.lignesLues.get(ligne)[recupDeplacement+1]=='^')
					{
						sens="haut";
					}
					deplacement=Character.getNumericValue(this.lignesLues.get(ligne)[recupDeplacement]);
				}
				else if (this.lignesLues.get(ligne).length>recupDeplacement && this.lignesLues.get(ligne)[recupDeplacement]=='^')
				{
					sens="haut";
				}
				deplacement*=32;
				if (sens=="haut")
				{
					Guepe guepeVerticale = new Guepe(x,y,x,y-deplacement,false);
					this.carte.ajoutEnnemi(guepeVerticale);
				}
				else
				{
					Guepe guepeVerticale = new Guepe(x,y,x,y+deplacement,false);
					this.carte.ajoutEnnemi(guepeVerticale);
				}
				break;
			case 'h': // lecture d'une guepe se d�pla�ant horizontalement
				sens="droite"; // sens par d�faut horizontalement
				if (this.lignesLues.get(ligne).length>recupDeplacement && this.estEntier(this.lignesLues.get(ligne)[recupDeplacement]))
				{
					if (this.lignesLues.get(ligne).length>recupDeplacement+1 && this.lignesLues.get(ligne)[recupDeplacement+1]=='<')
					{
						sens="gauche";
					}
					deplacement=Character.getNumericValue(this.lignesLues.get(ligne)[recupDeplacement]);
				}
				else if (this.lignesLues.get(ligne).length>recupDeplacement && this.lignesLues.get(ligne)[recupDeplacement]=='<')
				{
					sens="gauche";
				}
				deplacement*=32;
				if (sens=="gauche")
				{

					Guepe guepeHorizontale = new Guepe(x,y,x-deplacement,y,true);
					this.carte.ajoutEnnemi(guepeHorizontale);
				}
				else
				{
					Guepe guepeHorizontale = new Guepe(x,y,x+deplacement,y,true);
					this.carte.ajoutEnnemi(guepeHorizontale);
				}
				break;
			case 'A': // lecture de l'avatar du personnage
				
				// on s'assure qu'il n'y qu'un seul personnage sur la carte !!!
				if ( ! this.carte.aUnPersonnage() ) this.carte.setPersonnage( new Personnage(x,y) );
				break;
				
			default: // on ignore les autres caract�res
				break;
		}
	}

	// test interne
	public static class Application
	{
		public static void main(String[] args) throws IOException
		{
			Carte c = new Carte();
			Parser p = new Parser(c);
			p.getEcranInit();
			for (int i=0;i<(200-32);i++)
			{
				p.chargerColonneSuivante();
			}
		}
	}
}
