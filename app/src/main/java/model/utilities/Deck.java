package model.utilities;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Class Deck
 *
 */
public class Deck {

  private Set<String> deckSet;
  private Set<String> cardsSet;


  public Deck() {
    this.deckSet = new HashSet<String>();   //dovrà essere caricato dal file
    this.deckSet.add("Mega Knight");
    this.deckSet.add("Ice Wizard");
    this.deckSet.add("Inferno Dragon");
    this.deckSet.add("Ram Rider");
    this.cardsSet = new HashSet<String>();  //idem
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
   * @param addCard to the Deck
   * @return 
   */
  public Set<String> addDeck(String select) {
    this.deckSet.add(select);
    return deckSet;
  }
  
  /**
   * 
   * @param add card to the cardSet
   * @return 
   */
  public Set<String> addCardSet(String select) {
    this.cardsSet.add(select);
    return cardsSet;
  }
  /**
   * 
   * @param remove card in cardSet 
   */
  public Set<String> removeCardSet(String select) {
    cardsSet.remove(select);
    return cardsSet;
  }

  /**
   * 
   * @param remove card in deckSet
   * @return
   */
  public Set<String> removeDeckCard(String select) {
    deckSet.remove(select);
    return deckSet;
  }
}
