package model.entities.cards.troops;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import model.entities.Speeds;
import model.entities.cards.Card;
import model.entities.users.User;

/** 
 * Archer troop. 
 */
public final class Archer extends Troop {

  private static final String ARCHER_WORD = "archer";

  private static final String SELF_WALK = ARCHER_WORD + File.separator + "self" + File.separator + "walking" + File.separator;
  private static final String SELF_ATT = ARCHER_WORD + File.separator + "self" + File.separator + "attacking" + File.separator;
  private static final String BOT_WALK = ARCHER_WORD + File.separator + "bot" + File.separator + "walking" + File.separator;
  private static final String BOT_ATT = ARCHER_WORD + File.separator + "bot" + File.separator + "attacking" + File.separator;

  private static final int ELIXIR_COST = 3;
  private static final double RANGE = 5;

  private Archer(final User owner, final Vector2 position, final double maxHP, final double damage) {
    super(Archer.ELIXIR_COST, position, owner, maxHP, damage, Speeds.FAST, Archer.RANGE);
  }

  /**
   * Create a archer card based on the user level.
   * @param user
   *          who wants to deploy the archer.
   * @param position
   *          x,y coordinates.
   * @return the archer itself.
   */
  public static Troop create(final User user, final Vector2 position) {
      switch (user.getCurrentLevel()) {
          case LVL1: return new Archer(user, position, 125, 33);
          case LVL2: return new Archer(user, position, 127, 44);
          case LVL3: return new Archer(user, position, 151, 48);
          case LVL4: return new Archer(user, position, 166, 53);
          case LVL5: return new Archer(user, position, 182, 58);
          default: return new Archer(user, position, 125, 33);
      }
  }

  @Override
  public Map<String, List<String>> getAnimationFiles() {
    return Map.of(
        "SELF_MOVING", List.of(Archer.SELF_WALK + "0.png", Archer.SELF_WALK + "1.png"),
        "SELF_FIGHTING", List.of(Archer.SELF_ATT + "0.png", Archer.SELF_ATT + "1.png", Archer.SELF_ATT + "2.png", Archer.SELF_ATT + "3.png", 
            Archer.SELF_ATT + "4.png", Archer.SELF_ATT + "5.png"),
        "ENEMY_MOVING", List.of(Archer.BOT_WALK + "0.png", Archer.BOT_WALK + "1.png", Archer.BOT_WALK + "2.png", Archer.BOT_WALK + "3.png"),
        "ENEMY_FIGHTING", List.of(Archer.BOT_ATT + "0.png", Archer.BOT_ATT + "1.png", Archer.BOT_ATT + "2.png", Archer.BOT_ATT + "3.png", 
            Archer.BOT_ATT + "4.png"),
        "AS_CARD", List.of("cards" + File.separator + "ArchersCard.png"));
  }

  @Override
  public Card createAnother(final Vector2 position) {
    return create(super.getOwner(), position);
  }
}
