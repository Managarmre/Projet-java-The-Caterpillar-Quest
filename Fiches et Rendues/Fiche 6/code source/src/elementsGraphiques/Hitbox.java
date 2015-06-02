package elementsGraphiques;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

/**
 * Cette classe permet de repr�senter les hitbox des �l�ments graphiques.
 * Elle permet de simplifier le positionnement de ces hitbox sur les �l�ments (le replacement des hitbox lors du d�placement des �l�ments graphiques).
 * 
 * @see Element
 * @see Ennemi
 * @author Maxime Pineau
 */
public class Hitbox {
	
	private Shape formeHitbox;		
	private Point decalageOrigine;	// d�calage par rapport � l'origine (l'�l�ment)
	
	
	/**
	 * Cr�� une nouvelle hitbox ayant une certaine forme, et d�cal�e d'une certaine position.
	 * @param formeHitbox La forme de la hitbox, poss�dant une position x et y d'origine.
	 * @param decalageOrigine Le d�calage de la hitbox, on replace la forme en fonction de ce d�calage.
	 */
	public Hitbox( Shape formeHitbox, Point decalageOrigine ) {
		this.formeHitbox = formeHitbox;
		this.decalageOrigine = decalageOrigine;
	}
	
	/**
	 * Cr�� une nouvelle hitbox ayant une certaine forme, et d�cal�e d'une certaine position.
	 * @param formeHitbox La forme de la hitbox, poss�dant une position x et y d'origine.
	 * @param decalageOrigineX Le d�calage de la hitbox en X, on replace la forme en fonction de ce d�calage.
	 * @param decalageOrigineY Le d�calage de la hitbox en Y, on replace la forme en fonction de ce d�calage.
	 */
	public Hitbox( Shape formeHitbox, float decalageOrigineX, float decalageOrigineY ) {
		this( formeHitbox, new Point(decalageOrigineX, decalageOrigineY) );
	}
	
	/**
	 * Cr�� une nouvelle hitbox ayant une certaine forme.
	 * Les positions de la forme de la hitbox sont � donn�es par rapport � l'�l�ment (la position (0,0) de la forme correspond au bord en haut � gauche de l'�l�ment),
	 * et non pas par rapport � la fen�tre.
	 * On consid�re que le d�calage de la hitbox correspond � la position initiale de la forme.
	 * @param formeHitbox La forme de la hitbox, poss�dant une position x et y d'origine.
	 */
	public Hitbox( Shape formeHitbox ) {
		this( formeHitbox, formeHitbox.getX(), formeHitbox.getY() );
	}
	
	/**
	 * Cr�� une nouvelle hitbox, en construisant une forme � partir de la suite de points (x, y) donn�e en param�tres.
	 * Les positions des points sont � donn�es par rapport � l'�l�ment (la position (0,0) de la forme correspond au bord en haut � gauche de l'�l�ment),
	 * et non pas par rapport � la fen�tre.
	 * On consid�re que le d�calage de la hitbox correspond � la position initiale de la forme.
	 * @param points La suite de points x y permettant de construire la forme de la hitbox [ x1, y1, x2, y2 ..., xn, yn ].
	 */
	public Hitbox( float[] points ) {
		this( new Polygon(points) );
	}
	
	/**
	 * V�rifie si la hitbox touche une autre hitbox pass�e en param�tre.
	 * @param hitbox L'autre hitbox � v�rifier.
	 * @return Vrai si les deux hitbox se croisent / se touchent, faux sinon.
	 */
	public boolean intersects( Hitbox hitbox ) {
		return this.formeHitbox.intersects( hitbox.getForme() );
	}
	
	/**
	 * Retourne la forme g�om�trique repr�sentant la forme de la hotbox.
	 * @return La forme g�om�trique de la hitbox.
	 */
	public Shape getForme() {
		return this.formeHitbox;
	}
	
	
	/**
	 * Retourne la position X de la hitbox.
	 * @return La position X de la hitbox.
	 */
	public float getX() {
		return this.formeHitbox.getX();
	}
	
	/**
	 * Retourne la position Y de la hitbox.
	 * @return La position Y de la hitbox.
	 */
	public float getY() {
		return this.formeHitbox.getY();
	}
	
	/**
	 * Modifie la position X de la hitbox.
	 * La hitbox est plac�e sur la position x, puis elle est d�cal�e selon son d�calage en x.
	 * @param x La nouvelle position X de la hotbox.
	 */
	public void setX( float x ) {
		this.formeHitbox.setX( this.decalageOrigine.getX() + x );
	}
	
	/**
	 * Modifie la position Y de la hitbox.
	 * La hitbox est plac�e sur la position Y, puis elle est d�cal�e selon son d�calage en Y.
	 * @param y La nouvelle position Y de la hitbox
	 */
	public void setY( float y ) {
		this.formeHitbox.setY( this.decalageOrigine.getY() + y );
	}
	
	/**
	 * Modifie la position de la hitbox
	 * La hitbox est plac�e sur la position attendue, puis elle est d�cal�e selon son d�calage.
	 * @param x La nouvelle position X de la hitbox
	 * @param y La nouvelle position Y de la hitbox
	 */
	public void setPosition( float x, float y ) {
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * Modifie la position de la hitbox. 
	 * La hitbox est plac�e sur la position attendue, puis elle est d�cal�e selon son d�calage.
	 * @param position La nouvelle position de la hitbox
	 */
	public void setPosition( Point position ) {
		this.setPosition( position.getX(), position.getY() );
	}
	
	
}
