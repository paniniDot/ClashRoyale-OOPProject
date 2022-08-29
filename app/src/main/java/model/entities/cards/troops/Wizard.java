package model.entities.cards.troops;

import java.io.File;
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
 
  private static final String WIZARD_WORD = "wizard";

  private static final String SELF_WALK = WIZARD_WORD + File.separator + "self" + File.separator + "walking" + File.separator;
  private static final String SELF_ATT = WIZARD_WORD + File.separator + "self" + File.separator + "attacking" + File.separator;
  private static final String BOT_WALK = WIZARD_WORD + File.separator + "bot" + File.separator + "walking" + File.separator;
  private static final String BOT_ATT = WIZARD_WORD + File.separator + "bot" + File.separator + "attacking" + File.separator;

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
        "SELF_MOVING", List.of(Wizard.SELF_WALK + "1.png", Wizard.SELF_WALK + "2.png", Wizard.SELF_WALK + "3.png", Wizard.SELF_WALK + "4.png"),
        "SELF_FIGHTING", List.of(Wizard.SELF_ATT + "0.png", Wizard.SELF_ATT + "1.png", Wizard.SELF_ATT + "2.png", Wizard.SELF_ATT + "3.png", 
            Wizard.SELF_ATT + "4.png", Wizard.SELF_ATT + "5.png", Wizard.SELF_ATT + "6.png", Wizard.SELF_ATT + "7.png", Wizard.SELF_ATT + "8.png"),
        "ENEMY_MOVING", List.of(Wizard.BOT_WALK + "0.png", Wizard.BOT_WALK + "1.png", Wizard.BOT_WALK + "2.png"),
        "ENEMY_FIGHTING", List.of(Wizard.BOT_ATT + "0.png", Wizard.BOT_ATT + "1.png", Wizard.BOT_ATT + "2.png", Wizard.BOT_ATT + "3.png", 
            Wizard.BOT_ATT + "4.png", Wizard.BOT_ATT + "5.png", Wizard.BOT_ATT + "6.png"),
        "AS_CARD", List.of("cards" + File.separator + "WizardCard.png"));
  }

  /**
   * {@inheritDoc}
   */
  public Card createAnother(final Vector2 position) {
    return create(super.getOwner(), position);
  }
}
