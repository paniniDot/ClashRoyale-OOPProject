package model.actors.cards.troops;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.actors.Speeds;
import model.actors.users.User;

/**
 * Barbarian troop.
 */
public final class Barbarian extends Troop {

  private static final int ELIXIR_COST = 5;
  private static final int RANGE = 1;

  private Barbarian(final User owner, final Stage stage, final Vector2 position, final double maxHP, final double damage) {
    super(stage, Barbarian.ELIXIR_COST, position, owner, maxHP, damage, Speeds.MEDIUM, Barbarian.RANGE);
  } 

  /**
   * Create a barbarian card based on the user level.
   * @param user
   *          who wants to deploy the barbarian.
   * @param stage
   *          {@inheritDoc}
   * @param position
   *          x,y coordinates.
   * @return the barbarian itself.
   */
  public static Troop create(final User user, final Stage stage, final Vector2 position) {
      switch (user.getCurrentLevel()) {
          case LVL1: return new Barbarian(user, stage, position, 300, 75);
          case LVL2: return new Barbarian(user, stage, position, 330, 82);
          case LVL3: return new Barbarian(user, stage, position, 363, 90);
          case LVL4: return new Barbarian(user, stage, position, 438, 99);
          case LVL5: return new Barbarian(user, stage, position, 480, 109);
          default: return new Barbarian(user, stage, position, 330, 82);
      }
  }
}
