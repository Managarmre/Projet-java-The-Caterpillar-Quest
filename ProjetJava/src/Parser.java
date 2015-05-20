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
	private char pointeur;
	private static int taille=20; // les 20 premières lignes du fichier
	private int colonne;
	private String fichier;
	private ArrayList<char[]> ligneLue;
	
	public Parser(String f) throws IOException
	{
		this.fichier="default.map"; //f
		this.pointeur=' ';
		this.colonne=0;
		this.ligneLue=new ArrayList<char[]>(20);
		// récupération des 20 premières lignes du fichier
		InputStream ips=new FileInputStream(this.fichier); 
		InputStreamReader ipsr=new InputStreamReader(ips);
		BufferedReader br=new BufferedReader(ipsr);
		String ligne;
		for (int i=0;i<taille;i++)
		{
			ligne=br.readLine();
			this.ligneLue.add(ligne.toCharArray());
		}
		br.close();
	}
	
	public int lectureChar(char c)
	{
		int i=-1;
		this.pointeur=c;
		switch(this.pointeur)
		{
			case '#': i=0; break;
			case 'P': i=1; break;
			case 'c': i=2; break;
			case 'x': i=3; break;
			case 'A': i=4; break;
		}
		return i;
	}
	
	public void afficheImage(char c, Carte cc, int ligne, int colonne)
	{
		int i = lectureChar(c);
		switch(i)
		{
			case -1: break;
			case 0: cc.addPlateforme(new Plateforme(ligne,colonne));System.out.print("Plateforme - ");break;
			case 1: cc.addPorte(new Porte(ligne,colonne));System.out.print("Porte - ");break;
			case 2: cc.addCerise(new Cerise(ligne,colonne));System.out.print("Cerise - ");break;
			case 3: cc.addGuepe(new Guepe(ligne,colonne));System.out.print("Guepe - ");break;
			case 4: 
				if (!cc.aUnPersonnage()) // un seul personnage sur la carte !!!
				{
					cc.addPersonnage(new Personnage(ligne,colonne));
					System.out.print("Ajout du personnage  - ");
				}
				else
				{
					System.out.print("Ignore - ");
				}
				break;
		}
	}
	
	public void boucle(int maxCol,Carte c)
	{
		while (this.colonne<maxCol)
		{
			for (int i=0;i<taille;i++)
			{
				if (this.ligneLue.get(i).length>this.colonne)
				{
					char caractere = this.ligneLue.get(i)[this.colonne];
					afficheImage(caractere,c,i,this.colonne);					
				}
			}
			this.colonne++;
		}
	}
	
	// envoie des 32 premières colonnes des 20 lignes
	public void getEcranInit(Carte c) throws IOException
	{
		boucle(32,c);
		System.out.println("FIN");
	}
		
	// envoie de la colonne suivante
	public void getColonne(Carte c)
	{
		boucle(this.colonne+1,c);
	}
		
	public static class Application
	{
		public static void main(String[] args) throws IOException
		{
			Parser p = new Parser("fichiertexte.txt");
			Carte c = new Carte();
			p.getEcranInit(c);
			for (int i=0;i<6;i++)
			{
				p.getColonne(c);
			}
		}
	}
}
