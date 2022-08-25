package model.actors.cards.troops;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import model.actors.Speeds;
import model.actors.cards.Card;
import model.actors.users.User;

/**
 * Giant troop.
 */
public final class Giant extends Troop {

  /**
   * Elixir cost of the card.
   */
  public static final int ELIXIR_COST = 5;
  private static final int RANGE = 1;

  private static final String GIANT_WORD = "barbarian";

  private static final String SELF_WALK = GIANT_WORD + File.separator + "self" + File.separator + "walking" + File.separator;
  private static final String SELF_ATT = GIANT_WORD + File.separator + "self" + File.separator + "attacking" + File.separator;
  private static final String BOT_WALK = GIANT_WORD + File.separator + "bot" + File.separator + "walking" + File.separator;
  private static final String BOT_ATT = GIANT_WORD + File.separator + "bot" + File.separator + "attacking" + File.separator;

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
        "SELF_MOVING", List.of(Giant.SELF_WALK + "0.png", Giant.SELF_WALK + "1.png"),
        "SELF_FIGHTING", List.of(Giant.SELF_ATT + "0.png", Giant.SELF_ATT + "1.png", Giant.SELF_ATT + "2.png"),
        "ENEMY_MOVING", List.of(Giant.BOT_WALK + "0.png", Giant.BOT_WALK + "1.png"),
        "ENEMY_FIGHTING", List.of(Giant.BOT_ATT + "0.png", Giant.BOT_ATT + "1.png", Giant.BOT_ATT + "2.png"),
        "AS_CARD", List.of("cards/GiantCard.png"));
  }

  /**
   * {@inheritDoc}
   */
  public Card createAnother(final Vector2 position, final User owner) {
    return create(owner, position);
  }
}
