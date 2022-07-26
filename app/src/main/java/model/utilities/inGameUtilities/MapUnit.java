package model.utilities.inGameUtilities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Map Grid Unit. 
 */
public class MapUnit {

  /**
   * the height of a grid unit.
   */
  public static final int HEIGHT = 15;
  /**
   * the width of a grid unit.
   */
  public static final int WIDTH = 19;

  // Utile?
  /**
   * Enumeration to differentiate units where troops can move and units where cannot.
   */
  public enum Type {
    /**
     * Units where troops can be.
     */
    TERRAIN,

    /**
     * Units where cannot.
     */
    OBSTACLE;
  }

  private final Vector2 coords;
  private final Rectangle rect;
  private final Type type;

  //coords -> coordinate all'interno della griglia di MapUnit
  //pos -> coordinata in pixel del vertice in basso a sx del rettangolo
  /**
   * 
   * @param coords
   *            coordinates inside the grid 
   *            I.e. in this grid 
   *              1   2
   *             +--+--+
   *           1 |  |  |
   *             +--+--+
   *           2 |  | x|
   *             +--+--+
   *             coords will have coords (2,2).
   * @param pos
   *            the pixel coordinates of the bottom left corner of the rectangle.
   * @param type
   *            terrain or obstacle.
   */
  public MapUnit(final Vector2 coords, final Vector2 pos, final Type type) {
    this.coords = coords;
    this.rect = new Rectangle(pos.x, pos.y, MapUnit.WIDTH, MapUnit.HEIGHT);
    this.type = type;
  } 

  /**
   * 
   * @return the coordinates of the unit inside the grid.
   */
  public Vector2 getCoordinates() {
    return this.coords;
  }

  /**
   * 
   * @return the rectangle shape of the unit.
   */
  public Rectangle getUnitRectangle() {
    return this.rect;
  }

  /**
   * 
   * @return whether is TERRAIN or OBSTACLE.
   */
  public Type getType() {
    return type;
  }

  /**
   * 
   * @return the center of the rectangle.
   */
  public Vector2 getCenter() {
    return this.rect.getCenter(new Vector2());
  }
}
