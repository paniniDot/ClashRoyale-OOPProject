package model.entities.cards.troops;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import model.entities.Speeds;
import model.entities.cards.Card;
import model.entities.users.User;

/**
 * Barbarian troop.
 */
public final class Barbarian extends Troop {

  private static final String BARBARIAN_WORD = "barbarian";

  private static final String SELF_WALK = BARBARIAN_WORD + File.separator + "self" + File.separator + "walking" + File.separator;
  private static final String SELF_ATT = BARBARIAN_WORD + File.separator + "self" + File.separator + "attacking" + File.separator;
  private static final String BOT_WALK = BARBARIAN_WORD + File.separator + "bot" + File.separator + "walking" + File.separator;
  private static final String BOT_ATT = BARBARIAN_WORD + File.separator + "bot" + File.separator + "attacking" + File.separator;

  /**
   * Elixir cost of the card.
   */
  public static final int ELIXIR_COST = 4;
  private static final int RANGE = 30;

  private Barbarian(final User owner, final Vector2 position, final double maxHP, final double damage) {
    super(Barbarian.ELIXIR_COST, position, owner, maxHP, damage, Speeds.MEDIUM, Barbarian.RANGE);
  } 

  /**
   * Create a barbarian card based on the user level.
   * @param user
   *          who wants to deploy the barbarian.
   * @param position
   *          x,y coordinates.
   * @return the barbarian itself.
   */
  public static Troop create(final User user, final Vector2 position) {
      switch (user.getCurrentLevel()) {
          case LVL1: return new Barbarian(user, position, 400 * 60, 200);
          case LVL2: return new Barbarian(user, position, 400 * 60, 200);
          case LVL3: return new Barbarian(user, position, 400 * 60, 200);
          case LVL4: return new Barbarian(user, position, 400 * 60, 200);
          case LVL5: return new Barbarian(user, position, 400 * 60, 200);
          default: return new Barbarian(user, position, 400 * 60, 200);
      }
  }

  @Override
  public Map<String, List<String>> getAnimationFiles() {
    return Map.of(
        "SELF_MOVING", List.of(Barbarian.SELF_WALK + "0.png", Barbarian.SELF_WALK + "1.png", Barbarian.SELF_WALK + "2.png", Barbarian.SELF_WALK + "3.png", 
            Barbarian.SELF_WALK + "4.png", Barbarian.SELF_WALK + "5.png", Barbarian.SELF_WALK + "6.png"),
        "SELF_FIGHTING", List.of(Barbarian.SELF_ATT + "0.png", Barbarian.SELF_ATT + "1.png", Barbarian.SELF_ATT + "2.png", Barbarian.SELF_ATT + "3.png"),
        "ENEMY_MOVING", List.of(Barbarian.BOT_WALK + "0.png", Barbarian.BOT_WALK + "1.png"),
        "ENEMY_FIGHTING", List.of(Barbarian.BOT_ATT + "0.png", Barbarian.BOT_ATT + "1.png", Barbarian.BOT_ATT + "2.png", Barbarian.BOT_ATT + "3.png"),
        "AS_CARD", List.of("cards" + File.separator + "BarbariansCard.png"));
  }

  /**
   * {@inheritDoc}
   */
  public Card createAnother(final Vector2 position) {
    return create(super.getOwner(), position);
  }
}
