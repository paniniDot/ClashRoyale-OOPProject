package model.actors.cards.troops;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import model.actors.Speeds;
import model.actors.users.User;

/**
 * Barbarian troop.
 */
public final class Barbarian extends Troop {

  /**
   * Elixir cost of the card.
   */
  public static final int ELIXIR_COST = 5;
  private static final int RANGE = 1;

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
          case LVL1: return new Barbarian(user, position, 300, 75);
          case LVL2: return new Barbarian(user, position, 330, 82);
          case LVL3: return new Barbarian(user, position, 363, 90);
          case LVL4: return new Barbarian(user, position, 438, 99);
          case LVL5: return new Barbarian(user, position, 480, 109);
          default: return new Barbarian(user, position, 330, 82);
      }
  }

  @Override
  public Map<String, List<String>> getAnimationFiles() {
    return Map.of(
        "SELF_MOVING", List.of("barbarian/self/walking/0.png", "barbarian/self/walking/1.png", "barbarian/self/walking/2.png", "barbarian/self/walking/3.png", "barbarian/self/walking/4.png", "barbarian/self/walking/5.png", "barbarian/self/walking/6.png"),
        "SELF_FIGHTING", List.of("barbarian/self/attacking/0.png", "barbarian/self/attacking/1.png", "barbarian/self/attacking/2.png", "barbarian/self/attacking/3.png"),
        "ENEMY_MOVING", List.of("barbarian/bot/walking/0.png", "barbarian/bot/walking/1.png"),
        "ENEMY_FIGHTING", List.of("barbarian/bot/attacking/0.png", "barbarian/bot/attacking/1.png", "barbarian/bot/attacking/2.png", "barbarian/bot/attacking/3.png"),
        "AS_CARD", List.of("cards/BarbariansCard.png"));
  }
}
