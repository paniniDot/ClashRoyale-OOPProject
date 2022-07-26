package model.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import model.actors.cards.Card;

/**
 * Defines the logic to be used inside the game.
 */
public class InGameLogic {

  private static final int CHOOSABLE_CARDS = 4;

  private final CardQueue playerCards;
  private final List<Card> playerDeployedCards;
  private final List<Card> playerChoosableCards;
  //private final List<Tower> playerActiveTowers;
  private final CardQueue botCards;
  private final List<Card> botDeployedCards;
  private final List<Card> botChoosableCards;
  //private final List<Tower> botActiveTowers;

  /**
   * 
   * @param playerCards
   *              the player deck.
   * @param botCards
   *              the bot deck.
   */
  public InGameLogic(final List<Card> playerCards, final List<Card> botCards) {
    this.playerCards = new CardQueue(playerCards);
    this.playerDeployedCards = new ArrayList<>();
    this.playerChoosableCards = new ArrayList<>();
    IntStream.range(0, CHOOSABLE_CARDS).forEach(i -> this.playerChoosableCards.add(this.playerCards.getCard()));
    this.botCards = new CardQueue(botCards);
    this.botDeployedCards = new ArrayList<>();
    this.botChoosableCards = new ArrayList<>();
    IntStream.range(0, CHOOSABLE_CARDS).forEach(i -> this.botChoosableCards.add(this.botCards.getCard()));
  }

  private void deployCard(final Card card, final CardQueue queue, final List<Card> deployedCards, final List<Card> choosableCards) {
    if (choosableCards.contains(card)) {
      deployedCards.remove(card);
      choosableCards.add(queue.getCard());
      deployedCards.add(card);
      queue.addCard(card);
    }
  }
  /**
   * Deploys a card of the player.
   * @param card
   *           the card to be deployed.
   */
  public void deployPlayerCard(final Card card) {
    this.deployCard(card, this.playerCards, this.playerDeployedCards, this.playerChoosableCards);
  }

  /**
   * Deploys a card of the bot.
   * @param card
   *           the card to be deployed.
   */
  public void deployBotCard(final Card card) {
    this.deployCard(card, this.botCards, this.botDeployedCards, this.playerChoosableCards);
  }
}

