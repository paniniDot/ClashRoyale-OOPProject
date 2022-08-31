package model.utilities.ingame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.badlogic.gdx.math.Vector2;

import model.GlobalData;
import model.Model;
import model.entities.Attackable;
import model.entities.cards.Card;
import model.entities.towers.KingTower;
import model.entities.towers.QueenTower;
import model.entities.towers.Tower;
import model.entities.users.User;

/**
 * Defines the logic to be used inside the game.
 */
public abstract class GameModel extends Model {

  /**
   * the number of cards that can be chosen in every moment.
   */
  protected static final int CHOOSABLE_CARDS = 4;

  private final List<Card> playerCards;
  private final List<Card> playerCardQueue;
  private final List<Card> playerDeployedCards;
  private final List<Card> playerChoosableCards;
  private final List<Tower> playerActiveTowers;

  /**
   * 
   * @param playerCards
   *              the player deck.
   * @param user
   *              the user who is playing.
   */
  public GameModel(final List<Card> playerCards, final User user) {
    this.playerCards = playerCards.stream().collect(Collectors.toList());
    this.playerCardQueue = playerCards.stream().collect(Collectors.toList());
    this.playerDeployedCards = new ArrayList<>();
    this.playerChoosableCards = new ArrayList<>();
    IntStream.range(0, CHOOSABLE_CARDS).forEach(i -> this.playerChoosableCards.add(this.playerCardQueue.remove(0)));
    this.playerActiveTowers = this.getPlayerTowers(user);
  }

  private List<Tower> getPlayerTowers(final User user) {
    final List<Tower> towers = new ArrayList<>();
    towers.add(QueenTower.create(user, new Vector2(205 + 33, 312 + 44)));
    towers.add(QueenTower.create(user, new Vector2(415 + 33, 312 + 44)));
    towers.add(KingTower.create(user, new Vector2(300 + 44, 255 + 55)));
    return towers;
  }


  /**
   * 
   * @return a list of every card used from the player during the match.
   */
  public List<Card> getPlayerDeck() {
    return Collections.unmodifiableList(this.playerCards);
  }

  /**
   * 
   * @return the queued cards of the player.
   */
  public List<Card> getPlayerCardQueue() {
    return Collections.unmodifiableList(this.playerCardQueue);
  }

  /**
   * 
   * @return a list of user currently deployed cards.
   */
  public List<Card> getPlayerDeployedCards() {
    return Collections.unmodifiableList(this.playerDeployedCards);
  }

  /**
   * 
   * @return a list of user currently choosable cards.
   */
  public List<Card> getPlayerChoosableCards() {
    return Collections.unmodifiableList(this.playerChoosableCards);
  }

  /**
   * Deploys a card of the player.
   * @param card
   *           the card to be deployed.
   */
  public void deployPlayerCard(final Card card) {
    if (this.playerChoosableCards.contains(card)) {
      this.playerChoosableCards.remove(card);
      this.playerCardQueue.add(card);
      this.playerDeployedCards.add(card);
    }
  }

  /**
   * @param origin 
                  the start position of the new card.
   * @return an {@link Optional} of the first card entered in the queue.
   * 
   */
  public Optional<Card> getPlayerNextQueuedCard(final Vector2 origin) {
    if (this.playerCardQueue.isEmpty()) {
      return Optional.empty();
    }
    final var nextCard = this.playerCardQueue.remove(0).createAnother(origin);
    this.playerChoosableCards.add(nextCard);
    return Optional.of(nextCard);
  }

  /**
   * Removes a card from the map.
   * @param card
   *           the card to be removed.
   */
  public void removeUserCardFromMap(final Card card) {
    if (this.playerDeployedCards.contains(card)) {
      this.playerDeployedCards.remove(card);
    }
  }

  /**
   * 
   * @return the currently active towers of the user.
   */
  public List<Tower> getPlayerActiveTowers() {
    return Collections.unmodifiableList(this.playerActiveTowers);
  }

  /**
   * If not already, destroys a user tower.
   * 
   * @param tower
   *            the tower to be destroyed.
   */
  public void destroyUserTower(final Tower tower) {
    if (this.playerActiveTowers.contains(tower)) {
      this.playerActiveTowers.remove(tower);
    }
  }

  /**
   * Remove a player attackable from the arena, whether is a tower or a card.
   * 
   * @param target
   *              the attackable to be removed.
   */
  protected void removeUserAttackableFromArena(final Attackable target) {
    if (this.isTower(target)) {
      this.destroyUserTower((Tower) target);
    } else {
      this.removeUserCardFromMap((Card) target);
    }
  }

  /**
   * 
   * @param target
   *              the attackable to find if is tower or not.
   * @return
   *              whether the target is a tower or not.
   */
  protected boolean isTower(final Attackable target) {
    return target.getClass().equals(QueenTower.class) || target.getClass().equals(KingTower.class);
  }

  /**
   * 
   * @param target
   *               the attackable to find if the user is the owner.
   * @return
   *               whether the user is the owner of this target or not.
   */
  protected boolean isUserTheOwner(final Attackable target) {
    return target.getOwner().equals(GlobalData.USER);
  }

  /**
   * 
   * @return a list of attackable elements of the player.
   */
  public List<Attackable> getPlayerAttackable() {
    return Stream.concat(this.playerDeployedCards.stream().map(c -> (Attackable) c), this.playerActiveTowers.stream().map(t -> (Attackable) t)).collect(Collectors.toList());
  }

  /**
   * Find targets, if any, for a user attackables looking for them in the enemy attackables (whether is a bot or real player).
   */
  public abstract void findAttackableTargets();

  /**
   * Handle the attack functionality of both user and enemy attackables (whether is a bot or real player).
   */
  public abstract void handleAttackTargets();
}
