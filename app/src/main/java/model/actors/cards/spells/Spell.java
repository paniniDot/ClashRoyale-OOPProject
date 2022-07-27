package model.actors.cards.spells;

import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.actors.Attackable;
//import model.actors.TargetType;
import model.actors.cards.Card;
import model.actors.users.User;

/**
 * Spell card.
 */
public abstract class Spell extends Card {

  private double leftDuration;
  private final int range;
//  private final TargetType targetType;
  private final List<Attackable> targets;

  /**
   * @param stage
   *           {@inheritDoc}
   * @param cost
   *           Elixir cost.
   * @param position
   *           x, y coordinates.
   * @param owner
   *           User who thrown it.
   * @param duration
   *           How many seconds the spell lasts.
   * @param range
   *           The range of influence.
   */
  protected Spell(final Stage stage, final int cost, final Vector2 position, final User owner, final double duration, final int range /*final TargetType targetType*/) {
    super(stage, cost, position, owner);
    this.leftDuration = duration;
    this.range = range;
    this.targets = Collections.emptyList();
//    this.targetType = targetType;
  }

  /**
   * @return time remaining.
   */
  public double getLeftDuration() {
    return leftDuration;
  }

  /**
   * @param value the amount of time to be reduced the left duration.
   */
  public void reduceDurationByValue(final int value) {
    this.leftDuration -= value;
  }

  /**
   * @return the range of action.
   */
  public int getRange() {
    return range;
  }

  /**
   * @return the entities which are affected from it.
   */
/*  public TargetType getTargetType() {
    return targetType;
  }
*/
  /**
   * Add a target.
   * @param target
   *            the target to be added. 
   */
  public void addTarget(final Attackable target) {
    if (!this.targets.contains(target)) {
      this.targets.add(target);
    }
  }

  /**
   * Remove a target.
   * @param target 
   *            the target to be removed.
   */
  public void removeTarget(final Attackable target) {
    if (this.targets.contains(target)) {
      this.targets.remove(target);
    }
  }

  /** 
   * @return a list of targets.
   */
  public List<Attackable> getTargets() {
    return targets;
  }

  /** 
   * @return true if the spell is over.
   */
  public boolean isOver() {
    return this.leftDuration <= 0;
  }

  /**
   * Start the action of spell. 
   */

  public abstract void start();

}
