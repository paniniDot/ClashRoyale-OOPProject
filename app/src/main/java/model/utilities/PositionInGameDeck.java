package model.utilities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.Vector2;
/**
 * 
 * The four default card positions in the game.
 *
 */
public class PositionInGameDeck {
  private final Set<Vector2> positionFree;
  private static final int HEIGHTCARD = 100;
  private static final int POSCARD1 = 200;
  private static final int POSCARD2 = 300;
  private static final int POSCARD3 = 400;
  private static final int POSCARD4 = 500;

  /**
   * inizialize position free. 
   */
  public PositionInGameDeck() {
    this.positionFree = new HashSet<>(Arrays.asList(new Vector2(POSCARD1, HEIGHTCARD), new Vector2(POSCARD2, HEIGHTCARD), new Vector2(POSCARD3, HEIGHTCARD), new Vector2(POSCARD4, HEIGHTCARD)));
  }

  /**
   * 
   * @return positionFree
   */
  public Set<Vector2> getPositionFree() {
    return positionFree;
  }

}
