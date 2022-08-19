package model.actors.cards.buildings;

import java.util.Optional;
import com.badlogic.gdx.math.Vector2;
import model.actors.cards.Card;
import model.actors.users.User;
import model.actors.Attackable;

/**
 * Building card.
 */
public abstract class Building extends Card implements Attackable {

  private double currentHP; //Equals to the remaining duration.   NECESSARY?
  private final double damage;
  private final double hitSpeed;
  private final double range;
  private Optional<Attackable> currentTarget;

  /**
   * Constructor.
   * 
   * @param cost
   *            Elixir cost.
   * @param position
   *            x, y coordinates where has been deployed.
   * @param owner
   *            the user who deployed it.
   * @param maxHP
   *            max health of the building.
   * @param damage
   *            hp taken per hit.
   * @param hitSpeed
   *            hits per second.
   * @param range
   *            the range of influence.
   */
  protected Building(final int cost, final Vector2 position, final User owner, final double maxHP, final double damage, final double hitSpeed, final double range) {
    super(cost, position, owner);
    this.currentHP = maxHP;
    this.damage = damage;
    this.hitSpeed = hitSpeed;
    this.range = range;
    this.currentTarget = Optional.empty();
  }


  /**
   * @return current remaining hp.
   */
  public double getCurrentHP() {
    return currentHP;
  }


  /**
   * @return damage given per hit.
   */
  public double getDamage() {
    return damage;
  }


  /**
   * @return hits per second.
   */
  public double getHitSpeed() {
    return hitSpeed;
  }


  @Override
  public Vector2 getPosition() {
    return super.getPosition();
  }

  @Override
  public void setPosition(final Vector2 newPos) {
    super.setPosition(newPos);
  }

  @Override
  public double getRange() {
    return range;
  }

  /**
   * Set a new target.
   * @param target
   *             the new target.
   */
  public void setCurrentTarget(final Attackable target) {
    if (this.currentTarget.isEmpty() || !this.currentTarget.get().equals(target)) {
      this.currentTarget = Optional.of(target);
    }
  };

  /**
   * @return the current target, if any.
   */
  public Optional<Attackable> getCurrentTarget() {
    return currentTarget;
  }

  /**
   * Resets the current target.
   */
  public void resetCurrentTarget() {
    this.currentTarget = Optional.empty();
  }

  /**
   * Hits once the current target.
   */
  public void attackCurrentTarget() {
    this.currentTarget.ifPresent(target -> target.reduceHPBy(this.damage));
  }

  @Override
  public void reduceHPBy(final double damage) {
    this.currentHP = this.currentHP < damage ? 0 : this.currentHP - damage;
  }

  @Override
  public boolean isDead() {
    return this.currentHP <= 0;
  }

}

