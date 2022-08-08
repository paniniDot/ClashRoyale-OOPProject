package model.actors.cards.spells;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.actors.users.User;
/**
 * Fireball spell.
 */
public final class FireBall extends Spell {

  private final double damage;

  private static final int ELIXIR_COST = 4;
  private static final int RANGE = 3;
  private static final double DURATION = 0.1;

  private FireBall(final User owner, final Stage stage, final Vector2 position, final double damage) {
    super(FireBall.ELIXIR_COST, position, owner, FireBall.DURATION, FireBall.RANGE);
    this.damage = damage;
  }

  /**
   * 
   * @return the damage given from this fireball.
   */
  public double getDamage() {
    return this.damage;
  }
  /**
   * Start the action of Fireball. 
   */
  @Override
  public void start() {
    //TODO
  }

  /**
   * Create a fireball card based on the user level.
   * @param user
   *          who wants to deploy the fireball.
   * @param stage
   *          {@inheritDoc}
   * @param position
   *          x,y coordinates.
   * @return the fireball itself.
   */
  public static Spell create(final User user, final Stage stage, final Vector2 position) {
    switch (user.getCurrentLevel()) {
      case LVL1: return new FireBall(user, stage, position, 325);
      case LVL2: return new FireBall(user, stage, position, 357);
      case LVL3: return new FireBall(user, stage, position, 393);
      case LVL4: return new FireBall(user, stage, position, 432);
      case LVL5: return new FireBall(user, stage, position, 374);
      default:   return new FireBall(user, stage, position, 325);
    }
  }

  @Override
  public Map<String, List<String>> getAnimationFiles() {
    // TODO Auto-generated method stub
    return null;
  }

}
