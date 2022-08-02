package model.actors.cards.troops;

import java.util.Optional;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import model.actors.Attackable;
import model.actors.Speeds;
//import model.actors.TargetType;
import model.actors.cards.Card;
import model.actors.users.User;

/**
 * Defines a troop. 
 */
public abstract class Troop extends Card implements Attackable {

  private double currentHP;
  private final double damage;
  //private final double hitSpeed;
  private final Speeds speed;  //movement and hit speed
/*
 *private final TargetType selfType;
  private final TargetType enemyType;
  */
  private final double range;
  private Optional<Attackable> currentTarget;

  /**
   * @param stage
   *          {@inheritDoc}
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
   * @param hitSpeed
   *          number of hits per second.
   * @param speed
   *          {@inheritDoc}
   * @param range
   *          the distance between this troop and other entities to being targeted by it.
   */
  protected Troop(final Stage stage, final int cost, final Vector2 position, final User owner, final double maxHP, final double damage, /*final double hitSpeed,*/ final Speeds speed, /*final TargetType selfType, final TargetType enemyType,*/ final double range) {
    super(stage, cost, position, owner);
    this.currentHP = maxHP; 
    this.damage = damage;
    //this.hitSpeed = hitSpeed;
    this.speed = speed;
    /*this.selfType = selfType;
    this.enemyType = enemyType;
    */
    this.range = range;
    this.currentTarget = Optional.empty();
  }

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

  //verrà fatto una volta definita la meccanica in-game
  //public void setCurrentTarget(Attackable target) {};

  /**
   * @return an optional containing an attackable entity who is targeted by this troop.
   */
  public Optional<Attackable> getCurrentTarget() {
    return this.currentTarget;
  }

  /**
   * Resets the current target.
   */
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

  @Override
  public Vector2 getPosition() {
    return super.getPosition();
  }

  @Override
  public void setPosition(final Vector2 newPos) {
    super.setPosition(newPos.x, newPos.y);
  }
  /**
   * update angle of the troop.
   */
  public void setRotation(final Vector2 dst) {
    super.rotate(dst);
  }
/*
  @Override
  public TargetType getSelfType() {
    return this.selfType;
  }
*/
  /**
   * @return the type of enemies that this troop can target.
   */
  /*public TargetType getEnemyType() {
    return this.enemyType;
  }*/
}

