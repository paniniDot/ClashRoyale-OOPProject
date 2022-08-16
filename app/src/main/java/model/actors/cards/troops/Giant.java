package model.actors.cards.troops;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import model.actors.Speeds;
import model.actors.users.User;

/**
 * Giant troop.
 */
public final class Giant extends Troop {

  private static final int ELIXIR_COST = 5;
  private static final int RANGE = 1;

  private Giant(final User owner, final Vector2 position, final double maxHP, final double damage) {
    super(Giant.ELIXIR_COST, position, owner, maxHP, damage, Speeds.SLOW, Giant.RANGE);
  }

  /**
   * Create a giant card based on the user level.
   * 
   * @param user
   *          who wants to deploy the giant.
   * @param position
   *          x,y coordinates.
   * @return the giant itself.
   */
  public static Troop create(final User user, final Vector2 position) {
      switch (user.getCurrentLevel()) {
          case LVL1: return new Giant(user, position, 2000, 126);
          case LVL2: return new Giant(user, position, 2200, 138);
          case LVL3: return new Giant(user, position, 2420, 152);
          case LVL4: return new Giant(user, position, 2660, 167);
          case LVL5: return new Giant(user, position, 2920, 183);
          default: return new Giant(user, position, 2000, 126);
      }
  }

  @Override
  public Map<String, List<String>> getAnimationFiles() {
    return Map.of(
        "SELF_MOVING", List.of("giant/self/walking/0.png", "giant/self/walking/1.png"),
        "SELF_FIGHTING", List.of("giant/self/attacking/0.png", "giant/self/attacking/1.png", "giant/self/attacking/2.png"),
        "ENEMY_MOVING", List.of("giant/bot/walking/0.png", "giant/bot/walking/1.png"),
        "ENEMY_FIGHTING", List.of("giant/bot/attacking/0.png", "giant/bot/attacking/1.png", "giant/bot/attacking/2.png"),
        "AS_CARD", List.of());
  }
}
