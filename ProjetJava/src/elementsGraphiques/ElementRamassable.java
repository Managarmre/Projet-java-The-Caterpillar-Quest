package elementsGraphiques;

import org.newdawn.slick.geom.Shape;


/**
 * Représente un élément ramassable par le personnage du joueur.
 * Cet élément vaut un certain nombre de points qui seront attribués au joueur lorsque cet élément est ramassé.
 * 
 * @author Cyril Caron
 * @author Maxime Pineau
 *
 */
public abstract class ElementRamassable extends ElementFixe {

	private int nbPoints;
	
	/**
	 * Créé un nouvel élément ramassable.
	 * @param x La position x de l'élément ramassable.
	 * @param y La position y de l'élément ramassable.
	 * @param hauteur La hauteur de l'élément ramassable.
	 * @param largeur La largeur de l'élément ramassable.
	 * @param hitbox La hitbox de l'élément ramassable.
	 * @param cheminImage Le chemin vers l'image représentant l'élément ramassable.
	 * @param nbPoints Le nombre de points que vaut cet élément lorsqu'il est ramassé.
	 */
	public ElementRamassable( int x, int y, int hauteur, int largeur, Shape hitbox, String cheminImage, int nbPoints ) {
		super( x, y, hauteur, largeur, hitbox, cheminImage );
		this.nbPoints = nbPoints;
	}

	/**
	 * Retourne le nombre de points que vaut l'élément ramassable.
	 * @return le nombre de points que vaut l'élément ramassable.
	 */
	public int getNbPoints() {
		return nbPoints;
	}

	/**
	 * Modifie le nombre de points que vaut l'élément ramassable.
	 * @param nbPoints Le nouveau nombre de points que vaudra l'élément ramassable.
	 */
	public void setNbPoints(int nbPoints) {
		this.nbPoints = nbPoints;
	}

	
	
}
