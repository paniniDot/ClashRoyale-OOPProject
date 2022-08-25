package model.actors.cards.spells;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import model.actors.Attackable;
import model.actors.cards.Card;
import model.actors.users.User;

/**
 * Fireball spell.
 */
public final class FireBall extends Spell {

  private static final String FIREBALL_WORD = "fireball";

  private static final String SELF = FIREBALL_WORD + File.separator + "self" + File.separator;
  private static final String BOT = FIREBALL_WORD + File.separator + "bot" + File.separator;

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
   * @param position
   *          x,y coordinates.
   * @return the fireball itself.
   */
  public static Spell create(final User user, final Vector2 position) {
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
        "SELF_MOVING", List.of("0.png", FireBall.SELF + "1.png", FireBall.SELF + "2.png", FireBall.SELF + "3.png", FireBall.SELF + "4.png", FireBall.SELF + "5.png", 
            FireBall.SELF + "6.png", FireBall.SELF + "7.png", FireBall.SELF + "8.png", FireBall.SELF + "9.png", FireBall.SELF + "10.png", FireBall.SELF + "11.png", 
            FireBall.SELF + "12.png", FireBall.SELF + "13.png", FireBall.SELF + "14.png", FireBall.SELF + "15.png", FireBall.SELF + "16.png", FireBall.SELF + "17.png"),
        "ENEMY", List.of(FireBall.BOT + "0.png", FireBall.BOT + "1.png", FireBall.BOT + "2.png", FireBall.BOT + "3.png", FireBall.BOT + "4.png", FireBall.BOT + "5.png", 
            FireBall.BOT + "6.png", FireBall.BOT + "7.png", FireBall.BOT + "8.png", FireBall.BOT + "9.png", FireBall.BOT + "10.png", FireBall.BOT + "11.png", 
            FireBall.BOT + "12.png", FireBall.BOT + "13.png", FireBall.BOT + "14.png", FireBall.BOT + "15.png", FireBall.BOT + "16.png", FireBall.BOT + "17.png"),
        "AS_CARD", List.of("cards/FireballCard.png"));
  }

  /**
   * {@inheritDoc}
   */
  public Card createAnother(final Vector2 position, final User owner) {
    return create(owner, position);
  }
}
