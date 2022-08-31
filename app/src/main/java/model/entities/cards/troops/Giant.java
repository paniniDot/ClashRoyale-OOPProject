package model.entities.cards.troops;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import model.entities.Speeds;
import model.entities.cards.Card;
import model.entities.users.User;

/**
 * Giant troop.
 */
public final class Giant extends Troop {

  private static final String GIANT_WORD = "giant";

  private static final String SELF_WALK = GIANT_WORD + File.separator + "self" + File.separator + "walking" + File.separator;
  private static final String SELF_ATT = GIANT_WORD + File.separator + "self" + File.separator + "attacking" + File.separator;
  private static final String BOT_WALK = GIANT_WORD + File.separator + "bot" + File.separator + "walking" + File.separator;
  private static final String BOT_ATT = GIANT_WORD + File.separator + "bot" + File.separator + "attacking" + File.separator;

  /**
   * Elixir cost of the card.
   */
  public static final int ELIXIR_COST = 5;
  private static final int RANGE = 30;

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
          case LVL1: return new Giant(user, position, 600 * 60, 300);
          case LVL2: return new Giant(user, position, 600 * 60, 300);
          case LVL3: return new Giant(user, position, 600 * 60, 300);
          case LVL4: return new Giant(user, position, 600 * 60, 300);
          case LVL5: return new Giant(user, position, 600 * 60, 300);
          default: return new Giant(user, position, 600 * 60, 300);
      }
  }

  @Override
  public Map<String, List<String>> getAnimationFiles() {
    return Map.of(
        "SELF_MOVING", List.of(Giant.SELF_WALK + "0.png", Giant.SELF_WALK + "1.png"),
        "SELF_FIGHTING", List.of(Giant.SELF_ATT + "0.png", Giant.SELF_ATT + "1.png", Giant.SELF_ATT + "2.png"),
        "ENEMY_MOVING", List.of(Giant.BOT_WALK + "0.png", Giant.BOT_WALK + "1.png"),
        "ENEMY_FIGHTING", List.of(Giant.BOT_ATT + "0.png", Giant.BOT_ATT + "1.png", Giant.BOT_ATT + "2.png"),
        "AS_CARD", List.of("cards" + File.separator + "GiantCard.png"));
  }

  /**
   * {@inheritDoc}
   */
  public Card createAnother(final Vector2 position) {
    return create(super.getOwner(), position);
  }
}
