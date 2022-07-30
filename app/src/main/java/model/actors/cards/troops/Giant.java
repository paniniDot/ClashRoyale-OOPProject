package model.actors.cards.troops;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.actors.Speeds;
import model.actors.users.User;

/**
 * Giant troop.
 */
public final class Giant extends Troop {

  private static final int ELIXIR_COST = 5;
  private static final int RANGE = 1;

  private Giant(final User owner, final Stage stage, final Vector2 position, final double maxHP, final double damage) {
    super(stage, Giant.ELIXIR_COST, position, owner, maxHP, damage, Speeds.SLOW, Giant.RANGE);
  }

  /**
   * Create a giant card based on the user level.
   * @param user
   *          who wants to deploy the giant.
   * @param stage
   *          {@inheritDoc}
   * @param position
   *          x,y coordinates.
   * @return the giant itself.
   */
  public static Troop create(final User user, final Stage stage, final Vector2 position) {
      switch (user.getCurrentLevel()) {
          case LVL1: return new Giant(user, stage, position, 2000, 126);
          case LVL2: return new Giant(user, stage, position, 2200, 138);
          case LVL3: return new Giant(user, stage, position, 2420, 152);
          case LVL4: return new Giant(user, stage, position, 2660, 167);
          case LVL5: return new Giant(user, stage, position, 2920, 183);
          default: return new Giant(user, stage, position, 2000, 126);
      }
  }
}
