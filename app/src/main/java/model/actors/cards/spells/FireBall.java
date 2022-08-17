package model.actors.cards.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.actors.Attackable;
import model.actors.users.User;

/**
 * Fireball spell.
 */
public final class FireBall extends Spell {

  private final double damage;
  private final List<Attackable> targets;
  /**
   * Elixir cost of the card.
   */
  public static final int ELIXIR_COST = 4;
  private static final int RANGE = 3;
  private static final double DURATION = 0.1;

  private FireBall(final User owner, final Vector2 position, final double damage) {
    super(FireBall.ELIXIR_COST, position, owner, FireBall.DURATION, FireBall.RANGE);
    this.damage = damage;
    this.targets = new ArrayList<>();
  }

  /**
   * 
   * @return the damage given from this fireball.
   */
  public double getDamage() {
    return this.damage;
  }

  /**
   * Start the action of Fireball. 
   */
  @Override
  public void start() {
    this.targets.forEach(target -> target.reduceHPBy(this.getDamage()));
  }

  /**
   * Add target.
   *
   * @param target the target
   */
  public void addTarget(final Attackable target) {
      if (!this.targets.contains(target)) {
          this.targets.add(target);
      }
  }


  /**
   * Create a fireball card based on the user level.
   * @param user
   *          who wants to deploy the fireball.
   * @param stage
   *          {@inheritDoc}
   * @param position
   *          x,y coordinates.
   * @return the fireball itself.
   */
  public static Spell create(final User user, final Stage stage, final Vector2 position) {
    switch (user.getCurrentLevel()) {
      case LVL1: return new FireBall(user, position, 325);
      case LVL2: return new FireBall(user, position, 357);
      case LVL3: return new FireBall(user, position, 393);
      case LVL4: return new FireBall(user, position, 432);
      case LVL5: return new FireBall(user, position, 374);
      default:   return new FireBall(user, position, 325);
    }
  }

  @Override
  public Map<String, List<String>> getAnimationFiles() {
    return Map.of(
        "SELF", List.of("fireball/self/0.png", "fireball/self/1.png", "fireball/self/2.png", "fireball/self/3.png", "fireball/self/4.png", "fireball/self/5.png", "fireball/self/6.png", "fireball/self/7.png", "fireball/self/8.png", 
            "fireball/self/9.png", "fireball/self/10.png", "fireball/self/11.png", "fireball/self/12.png", "fireball/self/13.png", "fireball/self/14.png", "fireball/self/15.png", "fireball/self/16.png", "fireball/self/17.png"),
        "ENEMY", List.of("fireball/bot/0.png", "fireball/bot/1.png", "fireball/bot/2.png", "fireball/bot/3.png", "fireball/bot/4.png", "fireball/bot/5.png", "fireball/bot/6.png", "fireball/bot/7.png", "fireball/bot/8.png", 
            "fireball/bot/9.png", "fireball/bot/10.png", "fireball/bot/11.png", "fireball/bot/12.png", "fireball/bot/13.png", "fireball/bot/14.png", "fireball/bot/15.png", "fireball/bot/16.png", "fireball/bot/17.png"),
        "AS_CARD", List.of("cards/FireballCard.png"));
  }

}
