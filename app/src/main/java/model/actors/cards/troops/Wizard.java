package model.actors.cards.troops;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.actors.Speeds;
//import model.actors.TargetType;
//import model.actors.TargetType;
import model.actors.users.User;
import model.utilities.AnimationUtilities;

/** 
 * Wizard troop. 
 */
public final class Wizard extends Troop {

  private static final int ELIXIR_COST = 5;
  private static final double HIT_SPEED = 1.4;
  private static final int RANGE = 5;

  private Wizard(final User owner, final Stage stage, final Vector2 position, final double maxHP, final double damage) {
    super(stage, Wizard.ELIXIR_COST, position, owner, maxHP, damage, /*Wizard.HIT_SPEED,*/ Speeds.MEDIUM, /*TargetType.GROUND, TargetType.BOTH,*/ Wizard.RANGE);
    setAnimation(AnimationUtilities.loadAnimationFromFiles(new String[]{"wizard/selfWizard/walking/1.png",
        "wizard/selfWizard/walking/2.png", "wizard/selfWizard/walking/3.png", "wizard/selfWizard/walking/4.png"}, (float) 0.01724 * 10, true));
  } 

  /**
   * Creates a wizard card based on the user level.
   * @param user
   *          who wants to deploy the wizard.
   * @param stage
   *          {@inheritDoc}
   * @param position
   *          x,y coordinates.
   * @return the wizard itself.
   */
  public static Troop create(final User user, final Stage stage, final Vector2 position) {
    switch (user.getCurrentLevel()) {
        case LVL1: return new Wizard(user, stage, position, 340, 130);
        case LVL2: return new Wizard(user, stage, position, 374, 143);
        case LVL3: return new Wizard(user, stage, position, 411, 157);
        case LVL4: return new Wizard(user, stage, position, 452, 172);
        case LVL5: return new Wizard(user, stage, position, 496, 189);
        default: return new Wizard(user, stage, position, 340, 130);
        }
  }

  /*
  @Override
  public TargetType getSelfType() {
    // TODO Auto-generated method stub
    return null;
  }*/
}
