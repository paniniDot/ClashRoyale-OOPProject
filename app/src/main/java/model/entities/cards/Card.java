package model.entities.cards;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.badlogic.gdx.math.Vector2;

import model.entities.Attackable;
import model.entities.Speeds;
import model.entities.users.User;

/**
 * Defines a game card.
 */
public abstract class Card implements Attackable {

  private final int cost;
  private final User owner;
  private Vector2 position;
  private double currentHP;
  private final double damage;
  private final Speeds speed;  //movement and hit speed
  private final double range;
  private Optional<Attackable> currentTarget;


  /**
   * Constructor.
   * 
   * @param cost
   *          elixir needed to deploy the troop.
   * @param position
   *          x,y coordinates where the troop has to be deployed.
   * @param owner
   *          who deployed the troop.
   * @param maxHP
   *          maximum health of the troop.
   * @param damage
   *          hp per hit taken by this troop.
   * @param speed
   *          {@inheritDoc}
   * @param range
   *          the distance between this troop and other entities to being targeted by it.
   */
  protected Card(final int cost, final Vector2 position, final User owner, final double maxHP, final double damage, final Speeds speed, final double range) {
    this.cost = cost;
    this.owner = owner;
    this.position = position;
    this.currentHP = maxHP; 
    this.damage = damage;
    this.speed = speed;
    this.range = range;
    this.currentTarget = Optional.empty();
  }

  /**
   * @return the owner of the card.
   */
  public User getOwner() {
    return this.owner;
  }

  /**
   * @return the cost of the card.
   */
  public int getCost() {
    return this.cost;
  }

  /**
   * Change the card position.
   * 
   * @param newPos
   *              the updated position.
   */
  public void setPosition(final Vector2 newPos) {
    this.position = newPos;
  }

  /**
   * 
   * @return a map containing the animation files for each card. 
   */
  public abstract Map<String, List<String>> getAnimationFiles();

  /**
   * 
   * @return the current position of the Card.
   */
  public Vector2 getPosition() {
    return this.position;
  }

 

  @Override
  public int hashCode() {
    return Objects.hash(cost, currentHP, currentTarget, damage, owner, position, range, speed);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Card other = (Card) obj;
    return cost == other.cost && Double.doubleToLongBits(currentHP) == Double.doubleToLongBits(other.currentHP)
        && Objects.equals(currentTarget, other.currentTarget)
        && Double.doubleToLongBits(damage) == Double.doubleToLongBits(other.damage)
        && Objects.equals(owner, other.owner) && Objects.equals(position, other.position)
        && Double.doubleToLongBits(range) == Double.doubleToLongBits(other.range) && speed == other.speed;
  }

  /**
   * Create another card of the same type.
   * 
   * @param position the position of the card.
   * 
   * @return the card created
   */
  public abstract Card createAnother(Vector2 position);
  /**
   * @return the current health of the troop.
   */
  public double getCurrentHP() {
    return this.currentHP;
  }

  /**
   * @return how the damage given from this troop.
   */
  public double getDamage() {
    return this.damage;
  }

  /**
   * @return the movement speed of this troop.
   */
  public Speeds getSpeed() {
    return this.speed;
  }

  /**
   * @return the distance before this troop targets other entities.
   */
  public double getRange() {
    return this.range;
  }

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

  /** 
   * Hits one time the current target, if any.
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
