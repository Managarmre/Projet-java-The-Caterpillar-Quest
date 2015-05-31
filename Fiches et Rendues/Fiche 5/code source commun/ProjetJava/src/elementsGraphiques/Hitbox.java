package elementsGraphiques;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

/**
 * Cette classe permet de représenter les hitbox des éléments graphiques.
 * Elle permet de simplifier le positionnement de ces hitbox sur les éléments (le replacement des hitbox lors du déplacement des éléments graphiques).
 * 
 * @see Element
 * @see Ennemi
 * @author Maxime Pineau
 */
public class Hitbox {
	
	private Shape formeHitbox;		
	private Point decalageOrigine;	// décalage par rapport à l'origine (l'élément)
	
	
	/**
	 * Créé une nouvelle hitbox ayant une certaine forme, et décalée d'une certaine position.
	 * @param formeHitbox La forme de la hitbox, possédant une position x et y d'origine.
	 * @param decalageOrigine Le décalage de la hitbox, on replace la forme en fonction de ce décalage.
	 */
	public Hitbox( Shape formeHitbox, Point decalageOrigine ) {
		this.formeHitbox = formeHitbox;
		this.decalageOrigine = decalageOrigine;
	}
	
	/**
	 * Créé une nouvelle hitbox ayant une certaine forme, et décalée d'une certaine position.
	 * @param formeHitbox La forme de la hitbox, possédant une position x et y d'origine.
	 * @param decalageOrigineX Le décalage de la hitbox en X, on replace la forme en fonction de ce décalage.
	 * @param decalageOrigineY Le décalage de la hitbox en Y, on replace la forme en fonction de ce décalage.
	 */
	public Hitbox( Shape formeHitbox, float decalageOrigineX, float decalageOrigineY ) {
		this( formeHitbox, new Point(decalageOrigineX, decalageOrigineY) );
	}
	
	/**
	 * Créé une nouvelle hitbox ayant une certaine forme.
	 * Les positions de la forme de la hitbox sont à données par rapport à l'élément (la position (0,0) de la forme correspond au bord en haut à gauche de l'élément),
	 * et non pas par rapport à la fenêtre.
	 * On considère que le décalage de la hitbox correspond à la position initiale de la forme.
	 * @param formeHitbox La forme de la hitbox, possédant une position x et y d'origine.
	 */
	public Hitbox( Shape formeHitbox ) {
		this( formeHitbox, formeHitbox.getX(), formeHitbox.getY() );
	}
	
	/**
	 * Créé une nouvelle hitbox, en construisant une forme à partir de la suite de points (x, y) donnée en paramètres.
	 * Les positions des points sont à données par rapport à l'élément (la position (0,0) de la forme correspond au bord en haut à gauche de l'élément),
	 * et non pas par rapport à la fenêtre.
	 * On considère que le décalage de la hitbox correspond à la position initiale de la forme.
	 * @param points La suite de points x y permettant de construire la forme de la hitbox [ x1, y1, x2, y2 ..., xn, yn ].
	 */
	public Hitbox( float[] points ) {
		this( new Polygon(points) );
	}
	
	/**
	 * Vérifie si la hitbox touche une autre hitbox passée en paramètre.
	 * @param hitbox L'autre hitbox à vérifier.
	 * @return Vrai si les deux hitbox se croisent / se touchent, faux sinon.
	 */
	public boolean intersects( Hitbox hitbox ) {
		return this.formeHitbox.intersects( hitbox.getForme() );
	}
	
	/**
	 * Retourne la forme géométrique représentant la forme de la hotbox.
	 * @return La forme géométrique de la hitbox.
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
	 * La hitbox est placée sur la position x, puis elle est décalée selon son décalage en x.
	 * @param x La nouvelle position X de la hotbox.
	 */
	public void setX( float x ) {
		this.formeHitbox.setX( this.decalageOrigine.getX() + x );
	}
	
	/**
	 * Modifie la position Y de la hitbox.
	 * La hitbox est placée sur la position Y, puis elle est décalée selon son décalage en Y.
	 * @param y La nouvelle position Y de la hitbox
	 */
	public void setY( float y ) {
		this.formeHitbox.setY( this.decalageOrigine.getY() + y );
	}
	
	/**
	 * Modifie la position de la hitbox
	 * La hitbox est placée sur la position attendue, puis elle est décalée selon son décalage.
	 * @param x La nouvelle position X de la hitbox
	 * @param y La nouvelle position Y de la hitbox
	 */
	public void setPosition( float x, float y ) {
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * Modifie la position de la hitbox. 
	 * La hitbox est placée sur la position attendue, puis elle est décalée selon son décalage.
	 * @param position La nouvelle position de la hitbox
	 */
	public void setPosition( Point position ) {
		this.setPosition( position.getX(), position.getY() );
	}
	
	
}
