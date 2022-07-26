package model.entities;

import java.util.Optional;

import com.badlogic.gdx.math.Vector2;

import model.entities.users.User;

/**
 * Defines entities which are allowed to be attacked from others.
 */
public interface Attackable {

  /** 
   * @return the current position of the entity that implements this interface.
   */
  Vector2 getPosition();

  /**
   * Updates the current position of the entity.
   * @param newPos
   *              the new position.
   */
  void setPosition(Vector2 newPos);

  /**
   * 
   * @return the range of action of the entity.
   */
  double getRange();

  /** 
   * @return the current target, if any, of the entity that implements this interface.
   */
  Optional<Attackable> getCurrentTarget();

  /**
   * Updates the current target of the entity.
   * @param attackable
   *              the new target.
   */
  void setCurrentTarget(Attackable attackable);

  /**
   * Resets the current target.
   */
  void resetCurrentTarget();

  /**
   * Reduces the health of the entity.
   * @param damage
   *              the amount of life to be taken.
   */
  void reduceHPBy(double damage);

  /** 
   * Hits one time the current target, if any.
   */
  void attackCurrentTarget();

  /**
   * @return whether the entity is dead or not.
   */
  boolean isDead();

  /**
   * @return the remaining Health Point
   */
  double getCurrentHP();

  /**
   * @return the owner of the Attackable.
   */
  User getOwner();

}
