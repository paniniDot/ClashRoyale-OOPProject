package model.entities.cards.troops;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import model.entities.Speeds;
import model.entities.cards.Card;
import model.entities.users.User;

/** 
 * Wizard troop. 
 */
public final class Wizard extends Troop {

  /**
   * Elixir cost of the card.
   */
  public static final int ELIXIR_COST = 5;
  private static final double RANGE = 100;

  private Wizard(final User owner, final Vector2 position, final double maxHP, final double damage) {
    super(Wizard.ELIXIR_COST, position, owner, maxHP, damage, Speeds.MEDIUM, Wizard.RANGE);
  } 

  /**
   * Creates a wizard card based on the user level.
   * 
   * @param user
   *          who wants to deploy the wizard.
   * @param position
   *          x,y coordinates.
   * @return the wizard itself.
   */
  public static Troop create(final User user, final Vector2 position) {
    switch (user.getCurrentLevel()) {
        case LVL1: return new Wizard(user, position, 200 * 60, 100);
        case LVL2: return new Wizard(user, position, 200 * 60, 100);
        case LVL3: return new Wizard(user, position, 200 * 60, 100);
        case LVL4: return new Wizard(user, position, 200 * 60, 100);
        case LVL5: return new Wizard(user, position, 200 * 60, 100);
        default: return new Wizard(user, position, 200 * 60, 100);
        }
  }

  @Override
  public Map<String, List<String>> getAnimationFiles() {
    return Map.of(
        "SELF_MOVING", List.of("wizard/self/walking/1.png", "wizard/self/walking/2.png", "wizard/self/walking/3.png", "wizard/self/walking/4.png"),
        "SELF_FIGHTING", List.of("wizard/self/fighting/0.png", "wizard/self/fighting/1.png", "wizard/self/fighting/2.png", "wizard/self/fighting/3.png", 
            "wizard/self/fighting/4.png", "wizard/self/fighting/5.png", "wizard/self/fighting/6.png", "wizard/self/fighting/7.png", "wizard/self/fighting/8.png"),
        "ENEMY_MOVING", List.of("wizard/enemy/walking/0.png", "wizard/enemy/walking/1.png", "wizard/enemy/walking/2.png"),
        "ENEMY_FIGHTING", List.of("wizard/enemy/fighting/0.png", "wizard/enemy/fighting/1.png", "wizard/enemy/fighting/2.png", "wizard/enemy/fighting/3.png", 
            "wizard/enemy/fighting/4.png", "wizard/enemy/fighting/5.png", "wizard/enemy/fighting/6.png"),
        "AS_CARD", List.of("cards/WizardCard.png"));
  }

  /**
   * {@inheritDoc}
   */
  public Card createAnother(final Vector2 position) {
    return create(super.getOwner(), position);
  }

}
