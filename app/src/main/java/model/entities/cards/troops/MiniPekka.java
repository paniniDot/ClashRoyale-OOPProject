package model.entities.cards.troops;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import model.entities.Speeds;
import model.entities.cards.Card;
import model.entities.users.User;

/**
 * MiniPekka troop.
 */
public final class MiniPekka extends Troop {

  private static final String PEKKA_WORD = "miniPekka";

  private static final String SELF_WALK = PEKKA_WORD + File.separator + "self" + File.separator + "walking" + File.separator;
  private static final String SELF_ATT = PEKKA_WORD + File.separator + "self" + File.separator + "attacking" + File.separator;
  private static final String BOT_WALK = PEKKA_WORD + File.separator + "bot" + File.separator + "walking" + File.separator;
  private static final String BOT_ATT = PEKKA_WORD + File.separator + "bot" + File.separator + "attacking" + File.separator;

  /**
   * Elixir cost of the card.
   */
  public static final int ELIXIR_COST = 5;
  private static final int RANGE = 60;

  private MiniPekka(final User owner, final Vector2 position, final double maxHP, final double damage) {
    super(MiniPekka.ELIXIR_COST, position, owner, maxHP, damage, Speeds.SLOW, MiniPekka.RANGE);
  } 

  /**
   * Create a miniPekka card based on the user level.
   * @param user
   *          who wants to deploy the miniPekka.
   * @param position
   *          x,y coordinates.
   * @return the miniPekka itself.
   */
  public static Troop create(final User user, final Vector2 position) {
      switch (user.getCurrentLevel()) {
          case LVL1: return new MiniPekka(user, position, 300*60, 150);
          case LVL2: return new MiniPekka(user, position, 350*60, 150);
          case LVL3: return new MiniPekka(user, position, 400*60, 150);
          case LVL4: return new MiniPekka(user, position, 450*60, 150);
          case LVL5: return new MiniPekka(user, position, 500*60, 150);
          default: return new MiniPekka(user, position, 300*60, 150);
      }
  }

  @Override
  public Map<String, List<String>> getAnimationFiles() {
    return Map.of(
        "SELF_MOVING", List.of(MiniPekka.SELF_WALK + "0.png", MiniPekka.SELF_WALK + "1.png", MiniPekka.SELF_WALK + "2.png", MiniPekka.SELF_WALK + "3.png"),
        "SELF_FIGHTING", List.of(MiniPekka.SELF_ATT + "0.png", MiniPekka.SELF_ATT + "1.png", MiniPekka.SELF_ATT + "2.png"),
        "ENEMY_MOVING", List.of(MiniPekka.BOT_WALK + "0.png", MiniPekka.BOT_WALK + "1.png", MiniPekka.BOT_WALK + "2.png", MiniPekka.BOT_WALK + "3.png"),
        "ENEMY_FIGHTING", List.of(MiniPekka.BOT_ATT + "0.png", MiniPekka.BOT_ATT + "1.png", MiniPekka.BOT_ATT + "2.png"),
        "AS_CARD", List.of("cards" + File.separator + "MiniPekkaCard.png"));
  }

  @Override
  public Card createAnother(final Vector2 position) {
    return create(super.getOwner(), position);
  }

}
