package model.actors.towers;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import model.actors.Speeds;
import model.actors.users.User;

/**
 * The central tower.
 */
public final class KingTower extends Tower {

  private static final double RANGE = 100;

  private KingTower(final Vector2 position, final User owner, final double damage, final double hp) {
    super(position, owner, RANGE, false, damage, hp, Speeds.SLOW);
  }

  /**
   * Builds a new KingTower based on the owner level.
   * 
   * @param owner
   *            {@inheritDoc}.
   * @param position
   *            {@inheritDoc}.
   * @return a new KingTower object.
   */
  public static KingTower create(final User owner, final Vector2 position) {
    switch (owner.getCurrentLevel()) {
      case LVL1: return new KingTower(position, owner, 100, 200 * 60);
      case LVL2: return new KingTower(position, owner, 100, 200 * 60);
      case LVL3: return new KingTower(position, owner, 100, 200 * 60);
      case LVL4: return new KingTower(position, owner, 100, 200 * 60);
      case LVL5: return new KingTower(position, owner, 100, 200 * 60);
      default: return new KingTower(position, owner, 100, 200 * 60);
    }
  }

  @Override
  public Map<String, List<String>> getAnimationFiles() {
    return Map.of(
        "SELF", List.of("towers/self/king_tower.png"),
        "ENEMY", List.of("towers/enemy/king_tower.png"));
  }
}
