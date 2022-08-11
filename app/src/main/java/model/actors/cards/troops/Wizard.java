package model.actors.cards.troops;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

import model.actors.Speeds;
//import model.actors.TargetType;
//import model.actors.TargetType;
import model.actors.users.User;

/** 
 * Wizard troop. 
 */
public final class Wizard extends Troop {

  private static final int ELIXIR_COST = 5;
  private static final double HIT_SPEED = 1.4;
  private static final double RANGE = 0.5;

  private Wizard(final User owner, final Vector2 position, final double maxHP, final double damage) {
    super(Wizard.ELIXIR_COST, position, owner, maxHP, damage, /*Wizard.HIT_SPEED,*/ Speeds.MEDIUM, /*TargetType.GROUND, TargetType.BOTH,*/ Wizard.RANGE);
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
        case LVL1: return new Wizard(user, position, 340, 130);
        case LVL2: return new Wizard(user, position, 374, 143);
        case LVL3: return new Wizard(user, position, 411, 157);
        case LVL4: return new Wizard(user, position, 452, 172);
        case LVL5: return new Wizard(user, position, 496, 189);
        default: return new Wizard(user, position, 340, 130);
        }
  }

  @Override
  public Map<String, List<String>> getAnimationFiles() {
    return Map.of(
        "SELF_MOVING", List.of("wizard/selfWizard/walking/1.png", "wizard/selfWizard/walking/2.png", "wizard/selfWizard/walking/3.png", "wizard/selfWizard/walking/4.png"),
        "SELF_FIGHTING", List.of(),
        "ENEMY_MOVING", List.of(),
        "ENEMY_FIGHTING", List.of(),
        "AS_CARD", List.of());
  }



}
