/**
 * @author Pauline
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;

public class Parser 
{
	/**
	 * Attributs de la classe Parser
	 */
	private static int nombreLignesMax=20; // attribut correspondant aux 20 premières lignes du fichier ; c'est à dire le nombre maximum de lignes lues
	private Carte carte; // la carte à modifier -- à mettre à jour
	private int colonne; // la colonne à lire dans le fichier
	private String cheminFichier; // le chemin du fichier à lire
	private ArrayList<char[]> lignesLues; //découpage des lignes en caractère (liste de liste)
	
	/**
	 * Constructeur de la classe parser
	 * @param carte La carte à mettre à jour
	 * @throws IOException
	 */
	public Parser(Carte carte) throws IOException
	{
		this.carte = carte;
		this.cheminFichier=this.carte.getCheminFichierCarte();
		this.colonne=0;
		this.lignesLues=new ArrayList<char[]>(20); // création d'une liste de taille 20 (correspondant aux 20 premières lignes du fichier)
		
		// récupération des 20 premières lignes du fichier
		InputStream ips=new FileInputStream(this.cheminFichier); 
		InputStreamReader ipsr=new InputStreamReader(ips);
		BufferedReader br=new BufferedReader(ipsr);
		String ligne;
		int i=0;
		while ((ligne=br.readLine())!=null && i<nombreLignesMax)
		{
			ligne=ligne.replaceAll("	","   "); // on remplace les tabulations par une succession de 3 espaces mais en règle générale, tabulations à éviter
			this.lignesLues.add(ligne.toCharArray());
			i++;
		}
		br.close();
	}
	
	/**
	 * Méthode permettant de récupérer les 32 premières colonnes des 20 lignes chargées. Cette méthode permet donc de charger l'écran initial.
	 * @throws IOException
	 */
	public void getEcranInit() throws IOException
	{
		recupererColonnes(32);
	}
	
	/**
	 * Méthode permettant de charger la colonne suivante
	 */
	public void chargerColonneSuivante()
	{
		recupererColonnes(1);
	}

	/**
	 * Méthode permettant de récupérer un certain nombre de colonnes
	 * @param maxCol le nombre de colonnes à récupérer
	 */
	public void recupererColonnes(int maxCol)
	{
		int colonneAAtteindre = this.colonne+maxCol;
		while (this.colonne<colonneAAtteindre)
		{
			for (int i=0;i<this.lignesLues.size();i++)
			{
				if (this.lignesLues.get(i).length>this.colonne)
				{
					char caractere = this.lignesLues.get(i)[this.colonne];
					lectureCaractere(caractere,i,this.colonne);	
				}
			}
			this.colonne++;
		}
	}
	
	/**
	 * Méthode permettant de vérifier qu'un caractère est un entier
	 * @param c le caractère à vérifier
	 * @return un booleen
	 */
	public boolean estEntier(char c)
	{
		return Character.isDigit(c);
	}

	/**
	 * Méthode permettant de traiter un caractère et de modifier la carte en fonction de ce dernier
	 * @param c le caractère à analyser
	 * @param ligne le numéro de ligne du fichier
	 * @param colonne le numéro de colonne du fichier
	 */
	public void lectureCaractere(char c, int ligne, int colonne)
	{
		int deplacement=3;
		int recupDeplacement=colonne+1;
		switch(c)
		{
			case '#': this.carte.addPlateforme(new Plateforme(ligne,colonne)); break;
			case 'P': this.carte.addPorte(new Porte(ligne,colonne)); break;
			case 'c': this.carte.addCerise(new Cerise(ligne,colonne)); break;
			case 'v': 
				if (this.lignesLues.get(ligne).length>recupDeplacement && this.estEntier(this.lignesLues.get(ligne)[recupDeplacement]))
				{
					deplacement=Character.getNumericValue(this.lignesLues.get(ligne)[recupDeplacement]);
				}
				Guepe guepeVerticale = new Guepe(colonne,ligne,deplacement,false);
				this.carte.addGuepe(guepeVerticale);
				break;
			case 'h': 
				if (this.lignesLues.get(ligne).length>recupDeplacement && this.estEntier(this.lignesLues.get(ligne)[recupDeplacement]))
				{
					deplacement=Character.getNumericValue(this.lignesLues.get(ligne)[recupDeplacement]);
				}
				Guepe guepeHorizontale = new Guepe(colonne,ligne,deplacement,true);
				this.carte.addGuepe(guepeHorizontale);
				break;
			case 'A': 
				if (!this.carte.aUnPersonnage()) // un seul personnage sur la carte !!!
				{
					this.carte.addPersonnage(new Personnage(ligne,colonne));
				}
				break;
			default: 
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
			for (int i=0;i<72;i++)
			{
				p.chargerColonneSuivante();
			}
		}
	}
}
