package elementsGraphiques;

import jeu.Carte;
import jeu.PartieException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;



/**
 * Représente un élément graphique pouvant se déplacer.
 * Cet élément est animé, cette animation sera représentée dans un fichier image appelé sprite.
 * 
 * @author Cyril Caron
 * @author Maxime Pineau
 *
 */
public abstract class ElementDeplacable extends Element {
	
	/**
	 * Le chemin vers le fichier sprite de l'élément déplaçable.
	 */
	protected String cheminSprite;
	
	/**
	 * Le fichier sprite chargé en mémoire. Cet objet contient également les dimensions de chaque image sur le fichier sprite.
	 * Cet objet devra être initialisé dans la méthode initialiser() à redéfinir.
	 */
	protected SpriteSheet sprite;
		
	/**
	 * Tableau contenant toutes les animations possibles pour l'élément déplaçable.
	 * On pourra imaginer une animation pour se déplacer à droite, et une autre pour se déplacer à gauche.
	 * Ce tableau sera à initialiser dans le constructeur.
	 */
	protected Animation[] animations;
	
	
	/**
	 * Un élément déplaçable pourra avoir plusieurs hitbox selon l'animation de cet élément.
	 * La hitbox actuelle sera à remplacer par l'une de ces hitbox.
	 */
	protected Hitbox[] hitboxs;
	
	/**
	 * Créé un nouvel élément déplacable.
	 * @param x La position x de l'élément déplaçable.
	 * @param y La position y de l'élément déplaçable.
	 * @param hauteur La hauteur de l'élément déplaçable.
	 * @param largeur La largeur de l'élément déplaçable.
	 * @param hitbox La hitbox de l'élément déplaçable.
	 * @param cheminSprite Le chemin vers le fichier sprite de cet élément.
	 */
	public ElementDeplacable( int x, int y, int hauteur, int largeur, Shape hitbox, String cheminSprite ) {
		super( x, y, hauteur, largeur, hitbox );
		
		this.cheminSprite = cheminSprite;
	}
	
	/**
	 * Met à jour les données de l'élément déplaçable.
	 * @param conteneur Le conteneur du jeu.
	 * @param delta Le temps qui s'est passé depuis la dernière mise à jour en millisecondes. 
	 * @param carte La carte contenant les éléments graphique du jeu (permet de vérifiier les collisions).
	 * @throws SlickException Lancée lorsqu'une erreur est détectée par la librairie Slick2D.
	 * @throws PartieException Indique que la partie a été gagnée ou perdue.
	 */
	public abstract void update( GameContainer conteneur, int delta, Carte carte ) throws SlickException, PartieException;
	
	
	/**
	 * Charge une animation contenue dans un fichier sprite à une certaine ligne.
	 * Les images de l'animation seront celles contenues entre les numéros des colonnes donnés en paramètres.
	 * Cette méthode est à appeler lors de l'initialisation de l'élément (redéfinition de la méthode initialisation() ).
	 * @param numeroLigne Le numéro de la ligne contenant les images de l'animation.
	 * @param debutColonne Le numéro de la première colonne contenant les images de l'animation.
	 * @param finColonne Le numéro de la dernière colonne contenant les images de l'animation.
	 * @return L'animation correspondante.
	 */
	protected Animation chargerAnimation( int numeroLigne, int debutColonne, int finColonne ) {
		
		Animation animation = new Animation();
		
		for( int i = debutColonne; i <= finColonne; i++ ) {
			animation.addFrame( this.sprite.getSprite(i, numeroLigne), 100 );
		}
		
		return animation;		
	}
	
}
