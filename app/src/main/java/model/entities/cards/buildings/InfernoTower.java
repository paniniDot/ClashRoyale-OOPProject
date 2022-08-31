
package model.entities.cards.buildings;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import model.entities.Attackable;
import model.entities.Speeds;
import model.entities.cards.Card;
import model.entities.users.User;

/**
 * Inferno Tower building.
 */
public final class InfernoTower extends Building {

  private static final String INFERNO_WORD = "infernoTower";

  private static final String SELF_WALK = INFERNO_WORD + File.separator + "self" + File.separator + "staying" + File.separator;
  private static final String SELF_ATT = INFERNO_WORD + File.separator + "self" + File.separator + "attacking" + File.separator;
  private static final String BOT_WALK = INFERNO_WORD + File.separator + "bot" + File.separator + "staying" + File.separator;
  private static final String BOT_ATT = INFERNO_WORD + File.separator + "bot" + File.separator + "attacking" + File.separator;

  /**
   * Elixir cost of the card.
   */
  public static final int ELIXIR_COST = 3;
  private static final int RANGE = 30;

  private final List<Attackable> targets;

  private InfernoTower(final User owner, final Vector2 position, final double maxHP, final double damage) {
    super(InfernoTower.ELIXIR_COST, position, owner, maxHP, damage, Speeds.MEDIUM, InfernoTower.RANGE);
    this.targets = Collections.emptyList();
  }

  /**
   * Reduce the HP of targets.
   */
  public void attack() {
      this.targets.forEach(target -> target.reduceHPBy(this.getDamage()));
  }

  /**
   * Add target.
   *
   * @param target the target
   */
  public void addTarget(final Attackable target) {
      if (!this.targets.contains(target)) {
          this.targets.add(target);
      }
  }

  /**
   * Create an InfernoTowre card based on user level.
   * 
   * @param user
   *          who wants to deploy the Inferno Tower.
   * @param position
   *          x,y coordinates.
   * @return the InfernoTower itself.
   */
  public static Building create(final User user, final Vector2 position) {
    switch (user.getCurrentLevel()) {
      case LVL1: return new InfernoTower(user, position, 12_000, 100);
      case LVL2: return new InfernoTower(user, position, 200 * 60, 100);
      case LVL3: return new InfernoTower(user, position, 200 * 60, 100);
      case LVL4: return new InfernoTower(user, position, 200 * 60, 100);
      case LVL5: return new InfernoTower(user, position, 200 * 60, 100);
      default:   return new InfernoTower(user, position, 200 * 60, 100);
      }
  }

  @Override
  public Map<String, List<String>> getAnimationFiles() {
    return  Map.of(
        "SELF_MOVING", List.of(InfernoTower.SELF_WALK + "0.png", InfernoTower.SELF_WALK + "1.png", InfernoTower.SELF_WALK + "2.png", InfernoTower.SELF_WALK + "3.png"),
        "SELF_FIGHTING", List.of(InfernoTower.SELF_ATT + "0.png", InfernoTower.SELF_ATT + "1.png", InfernoTower.SELF_ATT + "2.png", InfernoTower.SELF_ATT + "3.png"),
        "ENEMY_MOVING", List.of(InfernoTower.BOT_WALK + "0.png", InfernoTower.BOT_WALK + "1.png", InfernoTower.BOT_WALK + "2.png", InfernoTower.BOT_WALK + "3.png"),
        "ENEMY_FIGHTING", List.of(InfernoTower.BOT_ATT + "0.png", InfernoTower.BOT_ATT + "1.png", InfernoTower.BOT_ATT + "2.png", InfernoTower.BOT_ATT + "3.png"),
        "AS_CARD", List.of("cards" + File.separator + "InfernoTowerCard.png"));
  }

  /**
   * {@inheritDoc}
   */
  public Card createAnother(final Vector2 position) {
    return create(super.getOwner(), position);
  }
}
