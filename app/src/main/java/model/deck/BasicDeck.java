package model.deck;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.gdx.math.Vector2;
/**
 * 
 * base for the deck each deck must always contain the positions for the four cards.
 *
 */
public class BasicDeck {

  private static final int HEIGHTCARD = 100;
  private static final int POSCARD1 = 200;
  private static final int POSCARD2 = 300;
  private static final int POSCARD3 = 400;
  private static final int POSCARD4 = 500;

  private final Set<Vector2> positionFree;

  /**
   * Constructor for position free, protected in order to cannot instantiate it from outside its package. 
   */
  protected BasicDeck() {
    this.positionFree = new HashSet<>(Arrays.asList(new Vector2(POSCARD1, HEIGHTCARD), new Vector2(POSCARD2, HEIGHTCARD), new Vector2(POSCARD3, HEIGHTCARD), new Vector2(POSCARD4, HEIGHTCARD)));
  }

  /**
   * 
   * @return positionFree
   */
  public Set<Vector2> getPositionFree() {
    return positionFree;
  }

  /**
   * 
   * @return the first free position and deletes it from those available
   */
  public Vector2 newPositionFree() {
    final Iterator<Vector2> i = getPositionFree().iterator();
    final Vector2 tmp  =  i.next();
    getPositionFree().remove(tmp);
    return tmp;
  }

}
