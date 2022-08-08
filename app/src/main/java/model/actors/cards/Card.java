package model.actors.cards;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

import model.actors.users.User;
/**
 * Defines a game card.
 */
public abstract class Card {

  private final UUID id;
  private final int cost;
  private final User owner;
  private Vector2 position;
  /** 
   * Constructor.
   * 
   * @param cost
   *          elixir cost of the card.
   * @param position
   *          x,y coordinates where the card is placed.
   * @param owner
   *          the user who deployed the card.
   */
  protected Card(final int cost, final Vector2 position, final User owner) {
    this.id = UUID.randomUUID();
    this.cost = cost;
    this.owner = owner;
    this.position = position;
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



}
