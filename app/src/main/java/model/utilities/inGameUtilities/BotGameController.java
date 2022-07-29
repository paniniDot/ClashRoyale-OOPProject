package model.utilities.inGameUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import model.actors.cards.Card;
import model.utilities.CardQueue;
import model.utilities.ElixirController;

/**
 * An implementation of GameController in which the user plays 
 * against a bot.
 */
public class BotGameController extends GameController {

  private final CardQueue botCards;
  private final List<Card> botDeployedCards;
  private final List<Card> botChoosableCards;
  //private final List<Tower> botActiveTowers;
  private final ElixirController elixirController;

  /**
   * 
   * @param playerCards
   *              the player deck.
   * @param botCards
   *              {@inheritDoc}.
   */
  public BotGameController(final List<Card> playerCards, final List<Card> botCards) {
    super(playerCards);
    this.botCards = new CardQueue(botCards);
    this.botDeployedCards = new ArrayList<>();
    this.botChoosableCards = new ArrayList<>();
    IntStream.range(0, GameController.CHOOSABLE_CARDS).forEach(i -> this.botChoosableCards.add(this.botCards.getCard()));
    this.elixirController = new ElixirController();
  }

  /**
   * Deploys a card of the bot.
   * @param card
   *           the card to be deployed.
   */
  public void deployBotCard(final Card card) {
    if (this.botChoosableCards.contains(card)) {
      this.botChoosableCards.remove(card);
      this.elixirController.decrementElixir(card.getCost());
      this.botDeployedCards.add(card);
      this.botCards.addCard(card);
    }
  }

  /**
   * Removes a card from the map.
   * @param card
   *           the card to be removed.
   */
  public void removeBotCardFromMap(final Card card) {
    if (this.botDeployedCards.contains(card)) {
      this.botDeployedCards.remove(card);
    }
  }

  /**
   * 
   * @return the current elixir left to the bot.
   */
  public int getBotElixirLeft() {
    return this.elixirController.getElixirCount();
  }
}
