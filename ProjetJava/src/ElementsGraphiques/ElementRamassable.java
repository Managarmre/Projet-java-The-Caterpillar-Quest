package elementsGraphiques;

import org.newdawn.slick.geom.Shape;


/**
 * Repr�sente un �l�ment ramassable par le personnage du joueur.
 * Cet �l�ment vaut un certain nombre de points qui seront attribu� au joueur lorsque cet �l�ment est ramass�.
 * 
 * @author Cyril Caron
 * @author Maxime Pineau
 *
 */
public abstract class ElementRamassable extends ElementFixe {

	private int nbPoints;
	
	/**
	 * Cr�e un nouvel �l�ment ramassable.
	 * @param x La position x de l'�l�ment ramassable.
	 * @param y La position y de l'�l�ment ramassable.
	 * @param hauteur La hauteur de l'�l�ment ramassable.
	 * @param largeur La largeur de l'�l�ment ramassable.
	 * @param hitbox La hitbox de l'�l�ment ramassable.
	 * @param cheminImage Le chemin vers l'image repr�sentant l'�l�ment ramassable.
	 * @param nbPoints Le nombre de points que vaut cet �l�ment lorsqu'il est ramass�.
	 */
	public ElementRamassable( int x, int y, int hauteur, int largeur, Shape hitbox, String cheminImage, int nbPoints ) {
		super( x, y, hauteur, largeur, hitbox, cheminImage );
		this.nbPoints = nbPoints;
	}

	/**
	 * Retourne le nombre de points que vaut l'�l�ment ramassable.
	 * @return le nombre de points que vaut l'�l�ment ramassable.
	 */
	public int getNbPoints() {
		return nbPoints;
	}

	/**
	 * Modifie le nombre de points que vaut l'�l�ment ramassable.
	 * @param nbPoints Le nouveau nombre de points que vaudra l'�l�ment ramassable.
	 */
	public void setNbPoints(int nbPoints) {
		this.nbPoints = nbPoints;
	}

	
	
}
