package model.actors.cards.buildings;

import com.badlogic.gdx.math.Vector2;
import model.actors.cards.Card;
import model.actors.users.User;
import model.actors.Speeds;

/**
 * Building card.
 */
public abstract class Building extends Card {

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
  protected Building(final int cost, final Vector2 position, final User owner, final double maxHP, final double damage, final Speeds speed, final double range) {
    super(cost, position, owner, maxHP, damage, speed, range);
  }

  @Override
  public void setPosition(final Vector2 newPos) {
  }

}

