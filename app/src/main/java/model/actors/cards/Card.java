package model.actors.cards;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import com.badlogic.gdx.math.Vector2;
import model.actors.Attackable;
import model.actors.Speeds;
import model.actors.users.User;

/**
 * Defines a game card.
 */
public abstract class Card implements Attackable {

  private final UUID id;
  private final int cost;
  private final User owner;
  private Vector2 position;
  private double currentHP;
  private final double damage;
  //private final double hitSpeed;
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
    this.id = UUID.randomUUID();
    this.cost = cost;
    this.owner = owner;
    this.position = position;
    this.currentHP = maxHP; 
    this.damage = damage;
    //this.hitSpeed = hitSpeed;
    this.speed = speed;
    this.range = range;
    this.currentTarget = Optional.empty();
  }

  /**
   * 
   * @return the self unique identifier for this card.
   */
  public UUID getSelfId() {
    return this.id;
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
    return Objects.hash(cost, id, owner, position);
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
    return cost == other.cost && Objects.equals(id, other.id) && Objects.equals(owner, other.owner)
        && Objects.equals(position, other.position);
  }

  /**
   * Create another card of the same type.
   * 
   * @param position the position of the card.
   * 
   * @param owner the card owner.
   * 
   * @return the card created
   */
  public abstract Card createAnother(Vector2 position, User owner);
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
   * @return the hit speed.
   */
 /* public double getHitSpeed() {
    return this.hitSpeed;
  }
*/
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
    if (this.getCurrentTarget().isPresent()) {
      System.out.println("truppa " + this + " attacca " + this.currentTarget.get());
    }
    this.currentTarget.ifPresent(target -> target.reduceHPBy(this.damage));
    /*if(this.currentTarget.isPresent()) {
      if(this.currentTarget.get().isDead()) {
        this.currentTarget.get().
      }
    }*/
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
