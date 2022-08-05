package model.actors.cards.buildings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.actors.users.User;
import model.actors.Attackable;

/**
 * Inferno Tower building.
 */
public class InfernoTower extends Building {
  
  private static final int ELIXIR_COST = 3;
  private static final int RANGE = 3;
  private static final double HIT_SPEED = 0.4;

  private final List<Attackable> targets;

  private InfernoTower(final User owner, final Stage stage, final Vector2 position, final double maxHP, final double damage) {
    super(stage, InfernoTower.ELIXIR_COST, position, owner, maxHP, damage, InfernoTower.HIT_SPEED, InfernoTower.RANGE);
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
   * @param user
   *          who wants to deploy the Inferno Tower.
   * @param stage
   *          {@inheritDoc}
   * @param position
   *          x,y coordinates.
   * @return the InfernoTower itself.
   */
  public static Building create(final User user, final Stage stage, final Vector2 position) {
    switch (user.getCurrentLevel()) {
      case LVL1: return new InfernoTower(user, stage, position, 800, 210);
      case LVL2: return new InfernoTower(user, stage, position, 880, 231);
      case LVL3: return new InfernoTower(user, stage, position, 968, 254);
      case LVL4: return new InfernoTower(user, stage, position, 1064, 279);
      case LVL5: return new InfernoTower(user, stage, position, 1168, 307);
      default:   return new InfernoTower(user, stage, position, 800, 210);
      }
  }
}
