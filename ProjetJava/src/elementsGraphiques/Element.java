package elementsGraphiques;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


/**
 * Représente un élément graphique sur le fenêtre.
 * Cet élément possèdera une position, une hitbox pour la gestion des collisions, et une méthode d'affichage. <br/>
 * La positions de la hitbox sont à données par rapport à l'élément, et non par rapport à la fenêtre.
 * La position de la hitbox est ensuite recalculé par rapport à la fenêtre. 
 * 
 * @author Cyril Caron
 * @author Maxime Pineau
 *
 */
public abstract class Element {

	private Point position;
	private int hauteur;
	private int largeur;
	
	// on sauvegarde la position de la hitbox relative à l'élément pour pouvoir la placer à la bonne position.
	private Point positionOrigineHitbox;
	protected Shape hitbox;
	
	private boolean estInitialise;
	
	/**
	 * Crée un nouvel élément graphique.
	 * Les positions de la hitbox sont à données par rapport à l'élément, et non par rapport à la fenêtre.
	 * @param position La position de cet élément dans la fenêtre de jeu.
	 * @param hauteur La hauteur de l'élément.
	 * @param largeur La largeur de l'élément.
	 * @param hitbox La hitbox de l'élément.
	 */
	public Element( Point position, int hauteur, int largeur, Shape hitbox ){
		
		this.position = position;
		this.hauteur = hauteur;
		this.largeur = largeur;
		
		// la position de la hitbox est donnée par rapport à l'image, et non à la fenêtre
		// on modifie donc cette hitbox pour quelle ait les bonnes positions
		this.positionOrigineHitbox = new Point( hitbox.getX(), hitbox.getY() );
		this.hitbox = hitbox;
		this.hitbox.setX( this.positionOrigineHitbox.getX() + position.getX() );	// on replace la hitbox sur l'élément
		this.hitbox.setY( this.positionOrigineHitbox.getY() + position.getY() );	
		
		this.estInitialise = false;
		
	}
	
	/**
	 * Crée un nouvel élément graphique.
	 * Les positions de la hitbox sont à données par rapport à l'élément, et non par rapport à la fenêtre.
	 * @param x La position x de l'élément sur la fenêtre de jeu.
	 * @param y La position y de l'élément sur la fenêtre de jeu.
	 * @param hauteur La hauteur de l'élément.
	 * @param largeur La largeur de l'élément.
	 * @param hitbox La hitbox de l'élément.
	 */
	public Element( int x, int y, int hauteur, int largeur, Shape hitbox ) {
		this( new Point(x, y) , largeur, hauteur, hitbox );
	}
	
	
	/**
	 * Initialisation de l'élément graphique. On initialisera tout ce qui pourrait lancer une exception SlickException ici.
	 * @throws SlickException Lancée lorsqu'une erreur est détecté par la librairie Slick2D (image non trouvée...).
	 */
	public void initialiser() throws SlickException {
		if( this.estInitialise ) return;
		this.estInitialise = true;
	}
	
	
	/**
	 * Affiche l'élément graphique dans la fenêtre de jeu.
	 * @param conteneur Le conteneur du jeu.
	 * @param graphique Le graphique ou l'on va afficher l'élément.
	 * @throws SlickException Lancée lorsqu'une erreur est détecté par la librairie Slick2D (image non trouvée...).
	 */
	public /*abstract*/ void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException
	{
		Shape hitbox = this.getHitbox();	
		graphique.setColor( Color.red );
		graphique.draw(hitbox);
		
	}
	
	
	/**
	 * Retourne vrai si cet élément est en collision avec l'élément e.
	 * Cette collision est détectée en utilisant les hitbox des éléments graphiques.
	 * @param e L'élément graphqiue avec lequel on doit vérifier si l'élément actuel est en collision.
	 * @return Vrai si cet élément graphique et l'élément e sont en collision, faux sinon.
	 */
	public boolean estEnCollisionAvec(Element e){
		return this.hitbox.intersects( e.getHitbox() );
	}

	/**
	 * Retourne la position x de l'élément sur la fenêtre de jeu.
	 * @return La position x de l'élément.
	 */
	public float getPositionX() {
		return this.position.getX();
	}
	
	/**
	 * Retourne la position y de l'élément sur la fenêtre de jeu.
	 * @return La position y de l'élément.
	 */
	public float getPositionY() {
		return this.position.getY();
	}
	
	/**
	 * Retourne la position de l'élément sur la fenêtre
	 * @return La position de l'élément, stocké dans un objet Point
	 */
	public Point getPosition() {
		return new Point( this.position.getX() , this.position.getY() );
	}
	
	
	/**
	 * Met à jour la position x de l'élément graphique.
	 * Cette méthode met égalemet à jour la position de la hitbox.
	 * @param x La nouvelle position x de l'élément graphique.
	 * @author Maxime Pineau
	 */
	public void setPositionX( float x ) {
		this.position.setX(x);
		this.hitbox.setX( this.positionOrigineHitbox.getX() + x );	// on replace la hitbox sur l'élément

	}
	
	/**
	 * Met à jour la position y de l'élément graphique.
	 * Cette méthode met égalemet à jour la position de la hitbox.
	 * @param y La nouvelle position y de l'élément graphique.
	 * @author Maxime Pineau
	 */
	public void setPositionY( float y ) {
		this.position.setY(y);
		this.hitbox.setY( this.positionOrigineHitbox.getY() + y );	// on replace la hitbox sur l'élément
	}
	
	/**
	 * Met à jour la position de l'élément graphique.
	 * Cette méthode met égalemet à jour la position de la hitbox.
	 * @param x La nouvelle position x de l'élément graphique.
	 * @param y La nouvelle position y de l'élément graphique.
	 * @author Maxime Pineau
	 */
	public void setPosition( float x, float y ) {
		this.setPositionX(x);
		this.setPositionY(y);
	}
	
	/**
	 * Met à jour la position de l'élément graphique.
	 * Cette méthode met égalemet à jour la position de la hitbox.
	 * @param x La nouvelle position x de l'élément graphique.
	 * @param y La nouvelle position y de l'élément graphique.
	 * @author Cyril Caron
	 */
	public void setPosition( double x, double y ) {
		this.setPosition( (float)x, (float)y );
	}
	/**
	 * Met à jour la position de l'élément graphique.
	 * Cette méthode met égalemet à jour la position de la hitbox.
	 * @param position La nouvelle position de l'élément graphique.
	 * @author Maxime Pineau
	 */
	public void setPosition( Point position ) {
		this.position = position;
		this.hitbox.setX( this.positionOrigineHitbox.getX() + position.getX() );	// on replace la hitbox sur l'élément
		this.hitbox.setY( this.positionOrigineHitbox.getY() + position.getY() );

	}
	
	
	/**
	 * Retourne la hauteur de l'élément graphique.
	 * @return La hauteur de l'élément.
	 */
	public int getHauteur() {
		return this.hauteur;
	}

	/**
	 * Retourne la largeur de l'élément graphique.
	 * @return La largeur de l'élément.
	 */
	public int getLargeur() {
		return this.largeur;
	}

	/**
	 * Retourne la hitbox de l'élément graphique.
	 * @return La hitbox de l'élément.
	 */
	public Shape getHitbox() {
		return hitbox;
	}
}
