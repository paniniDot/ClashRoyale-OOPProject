package model.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import model.actors.cards.Card;
import model.utilities.ingame.GameMap;

/**
 * bot implementation.
 */
public class Bot {

  /**
   * build bot. 
   */
  public Bot() {
  }

  /**
   * find enemy method.
   * @param playerCards
   * @param botCards
   * @param gameMap
   * @return HashMap<Card, List<Vector2>>
   */
  public Map<Card, List<Vector2>> findEnemy(final GameMap gameMap, final List<Card> playerCards, final List<Card> botCards) {
    final Map<Card, List<Vector2>> cardPaths = new HashMap<>();
    Card player = null;
    for (final Card bot: botCards) {
      double min = Double.MAX_VALUE;
      for (final Card p: playerCards) {
        final double d = this.euclideanDistance(bot.getCenter(), p.getCenter());
        if (Double.compare(min, d) > 0) {
          player = p;
          min = d;
        }
      }
      cardPaths.put(bot, gameMap.getPath(bot.getCenter(), player.getCenter()));
    }
    return cardPaths;
  }

  private double euclideanDistance(final Vector2 src, final Vector2 dst) {
    return Math.sqrt(Math.pow(src.x - dst.x, 2) + Math.pow(src.y - dst.y, 2));
  }
}
