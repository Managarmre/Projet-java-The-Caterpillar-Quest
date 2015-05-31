package elementsGraphiques;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


/**
 * Représente un ennemi.
 * Cet ennemi pourra se déplacer entre un point de départ et un point d'arrivée.
 * 
 * @author Cyril Caron
 * @author Maxime Pineau
 *
 */
public abstract class Ennemi extends ElementDeplacable {

	private Point depart, arrivee;	

	/**
	 * Créé un nouvel ennemi.
	 * @param x La position x de l'ennemi.
	 * @param y La position y de l'ennemi.
	 * @param hauteur La hauteur de l'ennemi.
	 * @param largeur La largeur de l'ennemi.
	 * @param hitbox La hitbox de l'ennemi.
	 * @param cheminSprite Le chemn vers le fichier sprite contenant l'animation.
	 * @param depart Le point de départ de l'ennemi.
	 * @param arrivee Le point d'arrivée de l'ennemi.
	 */
	public Ennemi( int x, int y, int hauteur, int largeur, Shape hitbox, String cheminSprite, Point depart, Point arrivee ) {
		super( x, y, hauteur, largeur, hitbox, cheminSprite );
		
		this.depart = depart;
		this.arrivee = arrivee;
	}

	/**
	 * Retourne vrai si l'ennemi est arrivé à son point d'arrivée, faux sinon.
	 * @return Vrai si l'ennemi est arrivé à son point d'arrivée, faux sinon.
	 * @author Maxime Pineau
	 */
	public boolean estArriveDestination() {
		// on laisse une marge de 0.7f pixel pour la comparaison (on ne compare pas à 0, flottants)
		return Math.abs( this.getPositionX() - this.getArrivee().getX() ) < 0.7f && Math.abs( this.getPositionY() - this.getArrivee().getY() ) < 0.7f;
	}
	
	/**
	 * Retourne le point de départ de l'ennemi.
	 * @return Le point de départ.
	 */
	public Point getDepart() {
		return depart;
	}

	
	/**
	 * Modifie le point de départ de l'ennemi.
	 * @param depart Le nouveau point de départ.
	 */
	public void setDepart(Point depart) {
		this.depart = depart;
	}

	/**
	 * Retourne le point d'arrivée de l'ennemi.
	 * @return Le point d'arrivée.
	 */
	public Point getArrivee() {
		return arrivee;
	}

	/**
	 * Modifie le point d'arrivée de l'ennemi.
	 * @param arrivee Le nouveau point d'arrivée.
	 */
	public void setArrivee(Point arrivee) {
		this.arrivee = arrivee;
	}
	
	
}
