package model.entities.towers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.badlogic.gdx.math.Vector2;

import model.entities.Attackable;
import model.entities.Speeds;
import model.entities.users.User;

/**
 * Tower abstract class.
 */
public abstract class Tower implements Attackable {

  private final User owner;
  private final Vector2 position;
  private final double range;
  private boolean isActive;
  private final double damage;
  private double currentHP;
  private final Speeds hitSpeed;
  private Optional<Attackable> currentTarget;

  /**
   * Builds a new Tower.
   * 
   * @param position 
   *            {@inheritDoc}.
   * @param owner
   *            the owner of the tower.
   * @param range
   *            the distance between the tower and an enemy before it starts to targeting him.
   * @param isActive
   *            King tower is activated when the first queen tower has been destroyed.
   * @param damage
   *            how much health does it take per hit.
   * @param hp
   *            the maximum health of the tower.
   * @param hitSpeed
   *            the number of hits per second.
   */
  public Tower(final Vector2 position, final User owner, final double range, final boolean isActive, final double damage, final double hp, final Speeds hitSpeed) {
    this.owner = owner;
    this.position = position;
    this.range = range;
    this.isActive = isActive;
    this.damage = damage;
    this.currentHP = hp;
    this.hitSpeed = hitSpeed;
    this.currentTarget = Optional.empty();
  }

  @Override
  public Vector2 getPosition() {
    return this.position;
  }

  @Override
  public void setPosition(final Vector2 newPos) {
  }

  @Override
  public void reduceHPBy(final double damage) {
    this.currentHP = this.currentHP < damage ? 0 : this.currentHP - damage;
  }

  @Override
  public boolean isDead() {
    return this.currentHP <= 0;
  }

  /**
   * 
   * @return the owner of the tower.
   */
  public User getOwner() {
    return this.owner;
  }

  /**
   * 
   * @return the range of activation triggering.
   */
  public double getRange() {
    return this.range;
  }

  /**
   * 
   * @return whether the tower is active or not.
   */
  public boolean isActive() {
    return this.isActive;
  }

  /**
   * set the tower to active.
   */
  public void setActive() {
    this.isActive = true;
  }

  /**
   * 
   * @return how much health does it take per hit.
   */
  public double getDamage() {
    return this.damage;
  }

  /**
   * 
   * @return the current health of the tower.
   */
  public double getCurrentHP() {
    return currentHP;
  }

  /**
   * 
   * @return the hit speed of the tower.
   */
  public Speeds getHitSpeed() {
    return hitSpeed;
  }

  /**
   * 
   * @return a map containing the file locations for each animation.
   */
  public abstract Map<String, List<String>> getAnimationFiles();

  @Override
  public Optional<Attackable> getCurrentTarget() {
    return this.currentTarget;
  }

  @Override
  public void setCurrentTarget(final Attackable attackable) {
    this.currentTarget = Optional.of(attackable);
  }

  @Override
  public void resetCurrentTarget() {
    this.currentTarget = Optional.empty();
  }


  @Override
  public void attackCurrentTarget() {
   this.currentTarget.ifPresent(t -> t.reduceHPBy(this.damage));
  }

  @Override
  public int hashCode() {
    return Objects.hash(currentHP, currentTarget, damage, hitSpeed, isActive, owner, position, range);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return true;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Tower other = (Tower) obj;
    return Double.doubleToLongBits(currentHP) == Double.doubleToLongBits(other.currentHP)
        && Objects.equals(currentTarget, other.currentTarget)
        && Double.doubleToLongBits(damage) == Double.doubleToLongBits(other.damage) && hitSpeed == other.hitSpeed
        && isActive == other.isActive && Objects.equals(owner, other.owner)
        && Objects.equals(position, other.position)
        && Double.doubleToLongBits(range) == Double.doubleToLongBits(other.range);
  }
 
}
