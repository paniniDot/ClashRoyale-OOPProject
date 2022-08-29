package model.entities.cards.troops;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import model.entities.Speeds;
import model.entities.cards.Card;
import model.entities.users.User;

/**
 * Valkyrie troop.
 */
public final class Valkyrie extends Troop {

  private static final String VALKYRIE_WORD = "valkyrie";

  private static final String SELF_WALK = VALKYRIE_WORD + File.separator + "self" + File.separator + "walking" + File.separator;
  private static final String SELF_ATT = VALKYRIE_WORD + File.separator + "self" + File.separator + "attacking" + File.separator;
  private static final String BOT_WALK = VALKYRIE_WORD + File.separator + "bot" + File.separator + "walking" + File.separator;
  private static final String BOT_ATT = VALKYRIE_WORD + File.separator + "bot" + File.separator + "attacking" + File.separator;

  /**
   * Elixir cost of the card.
   */
  public static final int ELIXIR_COST = 4;
  private static final int RANGE = 1;

  private Valkyrie(final User owner, final Vector2 position, final double maxHP, final double damage) {
    super(Valkyrie.ELIXIR_COST, position, owner, maxHP, damage, Speeds.MEDIUM, Valkyrie.RANGE);
  }

  /**
   * Create a valkyrie card based on the user level.
   * @param user
   *          who wants to deploy the valkyrie.
   * @param position
   *          x,y coordinates.
   * @return the valkyrie itself.
   */
  public static Troop create(final User user, final Vector2 position) {
      switch (user.getCurrentLevel()) {
          case LVL1: return new Valkyrie(user, position, 800, 120);
          case LVL2: return new Valkyrie(user, position, 968, 132);
          case LVL3: return new Valkyrie(user, position, 1064, 145);
          case LVL4: return new Valkyrie(user, position, 1170, 159);
          case LVL5: return new Valkyrie(user, position, 1284, 175);
          default: return new Valkyrie(user, position, 800, 120);
      }
  }

  @Override
  public Map<String, List<String>> getAnimationFiles() {
    return Map.of(
        "SELF_MOVING", List.of(Valkyrie.SELF_WALK + "0.png", Valkyrie.SELF_WALK + "1.png"),
        "SELF_FIGHTING", List.of(Valkyrie.SELF_ATT + "0.png", Valkyrie.SELF_ATT + "1.png", Valkyrie.SELF_ATT + "2.png", Valkyrie.SELF_ATT + "3.png", 
            Valkyrie.SELF_ATT + "4.png", Valkyrie.SELF_ATT + "5.png"),
        "ENEMY_MOVING", List.of(Valkyrie.BOT_WALK + "0.png", Valkyrie.BOT_WALK + "1.png"),
        "ENEMY_FIGHTING", List.of(Valkyrie.BOT_ATT + "0.png", Valkyrie.BOT_ATT + "1.png", Valkyrie.BOT_ATT + "2.png", Valkyrie.BOT_ATT + "3.png", 
            Valkyrie.BOT_ATT + "4.png", Valkyrie.BOT_ATT + "5.png", Valkyrie.BOT_ATT + "6.png"),
        "AS_CARD", List.of("cards" + File.separator + "ValkyrieCard.png"));
  }

  @Override
  public Card createAnother(final Vector2 position) {
    return create(super.getOwner(), position);
  }

}
