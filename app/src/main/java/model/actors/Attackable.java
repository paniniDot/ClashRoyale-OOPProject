package model.actors;

import java.util.Optional;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

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
   * @return the current target of the entity that implements this interface.
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
   * @return whether the entity is dead or not.
   */
  boolean isDead();

  /**
   * @return the type of the entity (i.e. air troop, ground troop).
   */
  //TargetType getSelfType();

  /**
   * @return self unique identifier.
   */
  UUID getSelfId();

}
