package elementsGraphiques;

import jeu.Carte;
import jeu.PartieException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;



/**
 * Repr�sente un �l�ment graphique pouvant se d�placer.
 * Cet �l�ment est anim�, cette animation sera repr�sent�e dans un fichier image appel� sprite.
 * 
 * @author Cyril Caron
 * @author Maxime Pineau
 *
 */
public abstract class ElementDeplacable extends Element {
	
	/**
	 * Le chemin vers le fichier sprite de l'�l�ment d�pla�able.
	 */
	protected String cheminSprite;
	
	/**
	 * Le fichier sprite charg� en m�moire. Cet objet contient �galement les dimensions de chaque image sur le fichier sprite.
	 * Cet objet devra �tre initialiser dans la m�thode initialiser() � red�finir.
	 */
	protected SpriteSheet sprite;
		
	/**
	 * Tableau contenant toutes les animations possibles pour l'�l�ment d�pla�able.
	 * On pourra imaginer une animation pour se d�placer � droite, et une autre pour se d�placer � gauche.
	 * Ce tableau sera � initialiser dans le constructeur.
	 */
	protected Animation[] animations;
	
	
	/**
	 * Un �l�ment d�pla�able pourra avoir plusieurs hitbox selon l'animation de cet �l�ment.
	 * La hitbox actuelle sera � remplacer par l'une de ces hitboxs.
	 */
	protected Shape[] hitboxs;
	
	/**
	 * Cr�� un nouvel �l�ment d�placable.
	 * @param x La position x de l'�l�ment d�pla�able.
	 * @param y La position y de l'�l�ment d�pla�able.
	 * @param hauteur La hauteur de l'�l�ment d�pla�able.
	 * @param largeur La largeur de l'�l�ment d�pla�able.
	 * @param hitbox La hitbox de l'�l�ment d�pla�able.
	 * @param cheminSprite Le chemin vers le fichier sprite de cet �l�ment.
	 */
	public ElementDeplacable( int x, int y, int hauteur, int largeur, Shape hitbox, String cheminSprite ) {
		super( x, y, hauteur, largeur, hitbox );
		
		this.cheminSprite = cheminSprite;
	}
	
	/**
	 * Met � jour les donn�es de l'�l�ment d�pla�able.
	 * @param conteneur Le conteneur du jeu.
	 * @param delta Le temps qui s'est pass� depuis la derni�re mise � jour en millisecondes. 
	 * @param carte La carte contenant les �l�ments graphique du jeu (permet de v�rifiier les collisions).
	 * @throws SlickException Lanc�e lorsqu'une erreur est d�tect�e par la librairie Slick2D.
	 * @throws PartieException Indique que la partie a �t� gagn�e ou perdue.
	 */
	public abstract void update( GameContainer conteneur, int delta, Carte carte ) throws SlickException, PartieException;
	
	
	/**
	 * Charge une animation contenue dans un fichier sprite � une certaine ligne.
	 * Les images de l'animation seront celles contenues entres les num�ros des colonnes donn�es en param�tres.
	 * Cette m�thode est � appeler lors de l'initialisation de l'�l�ment (red�finition de la m�thode initialisation() ).
	 * @param numeroLigne Le num�ro de la ligne contenant les images de l'animation.
	 * @param debutColonne Le num�ro de la premi�re colonne contenant les images de l'animation.
	 * @param finColonne Le num�ro de la derni�re colonne contenant les images de l'animation.
	 * @return L'animation correspondante.
	 * @author Maxime Pineau
	 */
	protected Animation chargerAnimation( int numeroLigne, int debutColonne, int finColonne ) {
		
		Animation animation = new Animation();
		
		for( int i = debutColonne; i <= finColonne; i++ ) {
			animation.addFrame( this.sprite.getSprite(i, numeroLigne), 100 );
		}
		
		return animation;		
	}
	
}
