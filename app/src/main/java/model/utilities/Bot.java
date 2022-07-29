package model.utilities;

import java.util.HashMap;
import java.util.List;
import com.badlogic.gdx.math.Vector2;
import model.actors.cards.Card;
import model.utilities.inGameUtilities.GameMap;

/**
 * bot implementation.
 */
public class Bot {

  private HashMap<Card, List<Vector2>> map;

  /**
   * build bot. 
   */
  public Bot() {
    map = new HashMap<>();
  }

  /**
   * find enemy method.
   * @param playerCards
   * @param botCards
   * @param gameMap
   * @return HashMap<Card, List<Vector2>>
   */
  public HashMap<Card, List<Vector2>> findEnemy(final GameMap gameMap, final List<Card> playerCards, final List<Card> botCards) {
    Card player = null;
    for (final Card bot: botCards) {
      final double min = Integer.MAX_VALUE;
      for (final Card p: playerCards) {
        final double d = this.euclideanDistance(bot.getCenter(), p.getCenter());
        if (Double.compare(min, d) > 0) {
          player = p;
        }
      }
      this.map.put(bot, gameMap.getPath(bot.getCenter(), player.getCenter()));
    }
    return this.map;
  }

  private double euclideanDistance(final Vector2 src, final Vector2 dst) {
    return Math.sqrt(Math.pow(src.x - dst.x, 2) + Math.pow(src.y - dst.y, 2));
  }
}
