package model.actors.towers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.actors.Speeds;
import model.actors.users.User;

/**
 * The central tower.
 */
public final class KingTower extends Tower {

  private static final double RANGE = 150;
  private static final Speeds HIT_SPEED = Speeds.SLOW;

  private KingTower(final Vector2 position, final Stage stage, final User owner, final double damage, final double hp) {
    super(position, stage, owner, KingTower.RANGE, false, damage, hp, KingTower.HIT_SPEED);
  }

  /**
   * Builds a new KingTower based on the owner level.
   * 
   * @param owner
   *            {@inheritDoc}.
   * @param stage
   *            {@inheritDoc}.
   * @param position
   *            {@inheritDoc}.
   * @return a new KingTower object.
   */
  public static KingTower create(final User owner, final Stage stage, final Vector2 position) {
    switch (owner.getCurrentLevel()) {
      case LVL1: return new KingTower(position, stage, owner, 50, 2400);
      case LVL2: return new KingTower(position, stage, owner, 54, 2568);
      case LVL3: return new KingTower(position, stage, owner, 58, 2736);
      case LVL4: return new KingTower(position, stage, owner, 62, 2904);
      case LVL5: return new KingTower(position, stage, owner, 67, 3096);
      default: return new KingTower(position, stage, owner, 50, 2400);
    }
  }
}