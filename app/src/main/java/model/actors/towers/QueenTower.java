package model.actors.towers;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import model.actors.Speeds;
import model.actors.users.User;

/**
 * The corner towers.
 */
public final class QueenTower extends Tower {

  private static final double RANGE = 100;
  private static final Speeds HIT_SPEED = Speeds.MEDIUM;

  private QueenTower(final Vector2 position, final User owner, final double damage, final double hp) {
    super(position, owner, QueenTower.RANGE, true, damage, hp, QueenTower.HIT_SPEED);
  }

  /**
   * Builds a new QueenTower based on the owner level.
   * 
   * @param owner
   *            {@inheritDoc}.
   * @param position
   *            {@inheritDoc}.
   * @return a new QueenTower object.
   */
  public static QueenTower create(final User owner, final Vector2 position) {
    switch (owner.getCurrentLevel()) {
      case LVL1: return new QueenTower(position, owner, 100, 200 * 60);
      case LVL2: return new QueenTower(position, owner, 100, 200 * 60);
      case LVL3: return new QueenTower(position, owner, 100, 200 * 60);
      case LVL4: return new QueenTower(position, owner, 100, 200 * 60);
      case LVL5: return new QueenTower(position, owner, 100, 200 * 60);
      default: return new QueenTower(position, owner, 100, 200 * 60);
    }
  }

  @Override
  public Map<String, List<String>> getAnimationFiles() {
    return Map.of(
        "SELF", List.of("towers/self/queen_tower.png"),
        "ENEMY", List.of("towers/enemy/queen_tower.png"));
  }
}
