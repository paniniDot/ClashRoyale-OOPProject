package model.utilities.inGameUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import model.actors.cards.Card;
import model.utilities.CardQueue;
import model.utilities.ElixirController;

/**
 * Defines the logic to be used inside the game.
 */
public abstract class GameController {

  /**
   * the number of cards that can be chosen in every moment.
   */
  protected static final int CHOOSABLE_CARDS = 4;

  private final CardQueue playerCards;
  private final List<Card> playerDeployedCards;
  private final List<Card> playerChoosableCards;
  //private final List<Tower> playerActiveTowers;
  private final ElixirController elixirController;

  /**
   * 
   * @param playerCards
   *              the player deck.
   */
  public GameController(final List<Card> playerCards) {
    this.playerCards = new CardQueue(playerCards);
    this.playerDeployedCards = new ArrayList<>();
    this.playerChoosableCards = new ArrayList<>();
    IntStream.range(0, CHOOSABLE_CARDS).forEach(i -> this.playerChoosableCards.add(this.playerCards.getCard()));
    this.elixirController = new ElixirController();
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
      this.elixirController.decrementElixir(card.getCost());
      this.playerDeployedCards.add(card);
      this.playerCards.addCard(card);
    }
  }

  /**
   * Removes a card from the map.
   * @param card
   *           the card to be removed.
   */
  public void removePlayerCardFromMap(final Card card) {
    if (this.playerDeployedCards.contains(card)) {
      this.playerDeployedCards.remove(card);
    }
  }

  /**
   * 
   * @return the current elixir left to the player.
   */
  public int getPlayerElixirLeft() {
    return this.elixirController.getElixirCount();
  }

}
