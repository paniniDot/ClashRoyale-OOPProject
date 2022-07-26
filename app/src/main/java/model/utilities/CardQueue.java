package model.utilities;

import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;

import model.actors.cards.Card;

/**
 * Queue used to have a fair rotation of the cards inside the deck.
 */
public class CardQueue {

  private final Queue<Card> queue;

  /**
   * 
   * @param cards
   *            the cards of the player.
   */
  public CardQueue(final Collection<Card> cards) {
    this.queue = new PriorityQueue<>();
    cards.forEach(c -> this.queue.add(c));
  }

  /**
   * @param card 
   *          the card to be added to the queue.
   */
  public void addCard(final Card card) {
    if (!this.queue.contains(card)) {
      this.queue.add(card);
    }
  }

  /**
   * @return the first card inserted in the queue.
   */
  public Card getCard() {
    return this.queue.poll();
  }
}
