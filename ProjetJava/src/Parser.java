/**
 * @author Pauline & Titouan
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
		for (int i=0;i<nombreLignesMax;i++)
		{
			ligne=br.readLine();
			this.lignesLues.add(ligne.toCharArray());
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
	public void recupererColonnes(int maxCol)
	{
		int colonneAAtteindre = this.colonne+maxCol;		
		while (this.colonne<colonneAAtteindre)
		{
			for (int i=0;i<nombreLignesMax;i++)
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
	 * M�thode permettant de traiter un caract�re et de modifier la carte en fonction de ce dernier
	 * @param c le caract�re � analyser
	 * @param ligne le num�ro de ligne du fichier
	 * @param colonne le num�ro de colonne du fichier
	 */
	public void lectureCaractere(char c, int ligne, int colonne)
	{
		switch(c)
		{
			case '#': this.carte.addPlateforme(new Plateforme(ligne,colonne));break;
			case 'P': this.carte.addPorte(new Porte(ligne,colonne));break;
			case 'c': this.carte.addCerise(new Cerise(ligne,colonne));break;
			case 'x': this.carte.addGuepe(new Guepe(ligne,colonne));break;
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
			for (int i=0;i<6;i++)
			{
				p.chargerColonneSuivante();
			}
		}
	}
}
