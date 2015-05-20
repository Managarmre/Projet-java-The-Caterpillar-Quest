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
	private Carte carte;
	
	private char pointeur;
	private static int nombreLignesMax=20; // on ne lit que les 20 premières lignes du fichier
	private int colonne;
	private String cheminFichier;
	private ArrayList<char[]> lignesLues;		// ET LES PLURIELS, ILS SE PERDENT EN CHEMIN ?
	
	public Parser( Carte carte ) throws IOException
	{
		this.carte = carte;
		
		this.cheminFichier="default.map"; //f // carte.getCheminFichierCarte();
		this.pointeur=' ';
		this.colonne=0;
		this.lignesLues=new ArrayList<char[]>(20);
		
		// récupération des 20 premières lignes du fichier
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
		
		//this.chargerColonnesSuivantes(22);	// on charge les 20 premières colonnes
	}
	
	
	// envoie des 32 premières colonnes des 20 lignes
	public void getEcranInit() throws IOException
	{
		boucle(32);
		System.out.println("FIN");
	}
	
	// envoie de la colonne suivante
	public void chargerColonneSuivante() //getColonne()
	{
		//boucle(this.colonne+1);
		this.chargerColonnesSuivantes(1);
	}
	
	
	public void chargerColonnesSuivantes( int nombreColonnes ) {
		
		boucle(this.colonne+nombreColonnes );
		
		//this.colonne = this.colonne + nombreColonnes;		
	}

	
	public void boucle(int maxCol)
	{
		Carte c = this.carte;
		
		while (this.colonne<maxCol)
		{
			for (int i=0;i<nombreLignesMax;i++)
			{
				if (this.lignesLues.get(i).length>this.colonne)
				{
					char caractere = this.lignesLues.get(i)[this.colonne];
					afficheImage(caractere,i,this.colonne);		// nom très mal choisie.....		
				}
			}
			this.colonne++;
		}
	}
	
	/*
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
	*/
	
	public void afficheImage(char c, int ligne, int colonne)
	{
		Carte cc = this.carte;
		
		
		//int i = lectureChar(c);			// <-- étape qui ne sert à rien ?
		//switch(i) 
		
		switch(c){
			case '#': cc.addPlateforme(new Plateforme(ligne,colonne));System.out.print("Plateforme - ");break;
			case 'P': cc.addPorte(new Porte(ligne,colonne));System.out.print("Porte - ");break;
			case 'c': cc.addCerise(new Cerise(ligne,colonne));System.out.print("Cerise - ");break;
			case 'x': cc.addGuepe(new Guepe(ligne,colonne));System.out.print("Guepe - ");break;
			case 'A': 
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
			
			default: 
				break;
	}
	}
	
	
	
	
		
	public static class Application
	{
		public static void main(String[] args) throws IOException
		{
			Carte c = new Carte("default.map");		// à modifier
			Parser p = new Parser(c);
			p.getEcranInit();
			for (int i=0;i<6;i++)
			{
				p.chargerColonneSuivante();
			}
		}
	}
}
