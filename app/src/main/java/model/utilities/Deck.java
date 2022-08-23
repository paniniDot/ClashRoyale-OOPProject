package model.utilities;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Class Deck.
 *
 */
public class Deck {

  private final Set<String> deckSet;
  private final Set<String> cardsSet;

/**
 * initialize basic cards and decks.
 */
  public Deck() {
    this.deckSet = new HashSet<>();
    this.deckSet.add("Barbarian");
    this.deckSet.add("Wizard");
    this.deckSet.add("InfernoTower");
    this.deckSet.add("Giant");
    this.cardsSet = new HashSet<>();
    this.cardsSet.add("Fireball");
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
  public Set<String> addDeck(final String select) {
    this.deckSet.add(select);
    return deckSet;
  }
  /**
   * 
   * @param select
   * @return cardSset
   */
  public Set<String> addCardSet(final String select) {
    this.cardsSet.add(select);
    return cardsSet;
  }
 /**
  * 
  * @param select
  * @return cardsSet
  */
  public Set<String> removeCardSet(final String select) {
    cardsSet.remove(select);
    return cardsSet;
  }

  /**
   * 
   * @param select
   * @return deckSet
   */
  public Set<String> removeDeckCard(final String select) {
    deckSet.remove(select);
    return deckSet;
  }
}
