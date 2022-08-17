package model.utilities;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Class Deck.
 *
 */
public class Deck {

  private Set<String> deckSet;
  private Set<String> cardsSet;

/**
 * initialize basic cards and decks.
 */
  public Deck() {
    this.deckSet = new HashSet<>();
    this.deckSet.add("Mega Knight");
    this.deckSet.add("Ice Wizard");
    this.deckSet.add("Inferno Dragon");
    this.deckSet.add("Ram Rider");
    this.cardsSet = new HashSet<>();
    this.cardsSet.add("Archer Queen");
    this.cardsSet.add("Golden Knight");
    this.cardsSet.add("Skeleton King");
    this.cardsSet.add("Mighty Miner");
    this.cardsSet.add("Miner");
    this.cardsSet.add("Princess");
  }
 
  /**
   * 
   * @return getDeckSet
   */
  public Set<String> getDeckSet() {
    return deckSet;
  }

  /**
   * 
   * @return getCardsSet
   */
  public Set<String> getCardsSet() {
    return cardsSet;
  }

  /**
   * 
   * @param select
   * @return deckSet
   */
  public Set<String> addDeck(String select) {
    this.deckSet.add(select);
    return deckSet;
  }
  /**
   * 
   * @param select
   * @return cardSset
   */
  public Set<String> addCardSet(String select) {
    this.cardsSet.add(select);
    return cardsSet;
  }
 /**
  * 
  * @param select
  * @return cardsSet
  */
  public Set<String> removeCardSet(String select) {
    cardsSet.remove(select);
    return cardsSet;
  }

  /**
   * 
   * @param select
   * @return deckSet
   */
  public Set<String> removeDeckCard(String select) {
    deckSet.remove(select);
    return deckSet;
  }
}
