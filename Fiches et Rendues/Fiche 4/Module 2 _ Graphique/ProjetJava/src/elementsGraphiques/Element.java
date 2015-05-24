package elementsGraphiques;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;


/**
 * Repr�sente un �l�ment graphique sur le fen�tre.
 * Cet �l�ment poss�dera une position, une hitbox pour la gestion des collisions, et une m�thode d'affichage. <br/>
 * La positions de la hitbox sont � donn�es par rapport � l'�l�ment, et non par rapport � la fen�tre.
 * La position de la hitbox est ensuite recalcul� par rapport � la fen�tre. 
 * 
 * @author Cyril Caron
 * @author Maxime Pineau
 *
 */
public abstract class Element {

	private Point position;
	private int hauteur;
	private int largeur;
	
	// on sauvegarde la position de la hitbox relative � l'�l�ment pour pouvoir la placer � la bonne position.
	private Point positionOrigineHitbox;
	private Shape hitbox;
	
	/**
	 * Cr�e un nouvel �l�ment graphique.
	 * Les positions de la hitbox sont � donn�es par rapport � l'�l�ment, et non par rapport � la fen�tre.
	 * @param position La position de cet �l�ment dans la fen�tre de jeu.
	 * @param hauteur La hauteur de l'�l�ment.
	 * @param largeur La largeur de l'�l�ment.
	 * @param hitbox La hitbox de l'�l�ment.
	 */
	public Element( Point position, int hauteur, int largeur, Shape hitbox ){
		
		this.position = position;
		this.hauteur = hauteur;
		this.largeur = largeur;
		
		// la position de la hitbox est donn�e par rapport � l'image, et non � la fen�tre
		// on modifie donc cette hitbox pour quelle ait les bonnes positions
		this.positionOrigineHitbox = new Point( hitbox.getX(), hitbox.getY() );
		this.hitbox = hitbox;
		this.hitbox.setX( this.positionOrigineHitbox.getX() + position.getX() );	// on replace la hitbox sur l'�l�ment
		this.hitbox.setY( this.positionOrigineHitbox.getY() + position.getY() );	
		
	}
	
	/**
	 * Cr�e un nouvel �l�ment graphique.
	 * Les positions de la hitbox sont � donn�es par rapport � l'�l�ment, et non par rapport � la fen�tre.
	 * @param x La position x de l'�l�ment sur la fen�tre de jeu.
	 * @param y La position y de l'�l�ment sur la fen�tre de jeu.
	 * @param hauteur La hauteur de l'�l�ment.
	 * @param largeur La largeur de l'�l�ment.
	 * @param hitbox La hitbox de l'�l�ment.
	 */
	public Element( int x, int y, int hauteur, int largeur, Shape hitbox ) {
		this( new Point(x, y) , largeur, hauteur, hitbox );
	}
	
	
	/**
	 * Initialisation de l'�l�ment graphique. On initialisera tout ce qui pourrait lancer une exception SlickException ici.
	 * @throws SlickException Lanc�e lorsqu'une erreur est d�tect� par la librairie Slick2D (image non trouv�e...).
	 */
	public abstract void initialiser() throws SlickException;
	
	
	/**
	 * Affiche l'�l�ment graphique dans la fen�tre de jeu.
	 * @param conteneur Le conteneur du jeu.
	 * @param graphique Le graphique ou l'on va afficher l'�l�ment.
	 * @throws SlickException Lanc�e lorsqu'une erreur est d�tect� par la librairie Slick2D (image non trouv�e...).
	 */
	public abstract void afficher( GameContainer conteneur, Graphics graphique ) throws SlickException;
	/*
		
		Shape hitbox = this.getHitbox();	
		graphique.setColor( Color.red );
		graphique.draw(hitbox);
		
	}
	*/
	
	/**
	 * Retourne vrai si cet �l�ment est en collision avec l'�l�ment e.
	 * Cette collision est d�tect�e en utilisant les hitbox des �l�ments graphiques.
	 * @param e L'�l�ment graphqiue avec lequel on doit v�rifier si l'�l�ment actuel est en collision.
	 * @return Vrai si cet �l�ment graphique et l'�l�ment e sont en collision, faux sinon.
	 */
	public boolean estEnCollisionAvec(Element e){
		return this.hitbox.intersects( e.getHitbox() );
	}

	/**
	 * Retourne la position x de l'�l�ment sur la fen�tre de jeu.
	 * @return La position x de l'�l�ment.
	 */
	public float getPositionX() {
		return this.position.getX();
	}
	
	/**
	 * Retourne la position y de l'�l�ment sur la fen�tre de jeu.
	 * @return La position y de l'�l�ment.
	 */
	public float getPositionY() {
		return this.position.getY();
	}
	
	/**
	 * Retourne la position de l'�l�ment sur la fen�tre
	 * @return La position de l'�l�ment, stock� dans un objet Point
	 */
	public Point getPosition() {
		return new Point( this.position.getX() , this.position.getY() );
	}
	
	
	/**
	 * Met � jour la position x de l'�l�ment graphique.
	 * Cette m�thode met �galemet � jour la position de la hitbox.
	 * @param x La nouvelle position x de l'�l�ment graphique.
	 * @author Maxime Pineau
	 */
	public void setPositionX( float x ) {
		this.position.setX(x);
		this.hitbox.setX( this.positionOrigineHitbox.getX() + x );	// on replace la hitbox sur l'�l�ment

	}
	
	/**
	 * Met � jour la position y de l'�l�ment graphique.
	 * Cette m�thode met �galemet � jour la position de la hitbox.
	 * @param y La nouvelle position y de l'�l�ment graphique.
	 * @author Maxime Pineau
	 */
	public void setPositionY( float y ) {
		this.position.setY(y);
		this.hitbox.setY( this.positionOrigineHitbox.getY() + y );	// on replace la hitbox sur l'�l�ment
	}
	
	/**
	 * Met � jour la position de l'�l�ment graphique.
	 * Cette m�thode met �galemet � jour la position de la hitbox.
	 * @param x La nouvelle position x de l'�l�ment graphique.
	 * @param y La nouvelle position y de l'�l�ment graphique.
	 * @author Maxime Pineau
	 */
	public void setPosition( float x, float y ) {
		this.setPositionX(x);
		this.setPositionY(y);
	}
	
	/**
	 * Met � jour la position de l'�l�ment graphique.
	 * Cette m�thode met �galemet � jour la position de la hitbox.
	 * @param x La nouvelle position x de l'�l�ment graphique.
	 * @param y La nouvelle position y de l'�l�ment graphique.
	 * @author Cyril Caron
	 */
	public void setPosition( double x, double y ) {
		this.setPosition( (float)x, (float)y );
	}
	/**
	 * Met � jour la position de l'�l�ment graphique.
	 * Cette m�thode met �galemet � jour la position de la hitbox.
	 * @param position La nouvelle position de l'�l�ment graphique.
	 * @author Maxime Pineau
	 */
	public void setPosition( Point position ) {
		this.position = position;
		this.hitbox.setX( this.positionOrigineHitbox.getX() + position.getX() );	// on replace la hitbox sur l'�l�ment
		this.hitbox.setY( this.positionOrigineHitbox.getY() + position.getY() );

	}
	
	
	/**
	 * Retourne la hauteur de l'�l�ment graphique.
	 * @return La hauteur de l'�l�ment.
	 */
	public int getHauteur() {
		return this.hauteur;
	}

	/**
	 * Retourne la largeur de l'�l�ment graphique.
	 * @return La largeur de l'�l�ment.
	 */
	public int getLargeur() {
		return this.largeur;
	}

	/**
	 * Retourne la hitbox de l'�l�ment graphique.
	 * @return La hitbox de l'�l�ment.
	 */
	public Shape getHitbox() {
		return hitbox;
	}
}
