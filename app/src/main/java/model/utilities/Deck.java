package model.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.badlogic.gdx.math.Vector2;

import model.GlobalData;
import model.actors.cards.Card;
import model.actors.cards.spells.FireBall;
import model.actors.cards.troops.Barbarian;
import model.actors.cards.troops.Giant;
import model.actors.cards.troops.Wizard;
import model.actors.users.User;

/**
 * 
 * Class Deck.
 *
 */
public class Deck {

  private final Map<String, Card> deckMap;
  private final Map<String, Card> cardsMap;

/**
 * initialize basic cards and decks.
 */
  public Deck() {
    
    this.deckMap = new HashMap<>();
  //  this.deckMap.put("Barbarian", Barbarian.create(GlobalData.USER, new Vector2(100, 100)));
    //this.deckMap.put("Wizard", Wizard.create(GlobalData.USER, new Vector2(200, 100))); 
    //this.deckMap.put("FireBall", FireBall.create(GlobalData.USER, new Vector2(300, 100)));
    //this.deckMap.put("Giant", Giant.create(GlobalData.USER, new Vector2(400, 100)));
    this.cardsMap = new HashMap<>();
    //this.cardsMap.put("Giant", Giant.create(GlobalData.USER, new Vector2(400, 100)));

    
    
  }
 
  /**
   * 
   * @return getDeckMap
   */
  public Map<String,Card> getDeckSet() {
    return deckMap;
  }
  
  public List<String> namesCardsDeck() {
    return deckMap.entrySet().stream()
        .flatMap(e -> Stream.of(e.getKey()))
        .collect(Collectors.toList());
  }
  
  public List<String> namesCardsCard() {
    return cardsMap.entrySet().stream()
        .flatMap(e -> Stream.of(e.getKey()))
        .collect(Collectors.toList());
  }

  /**
   * 
   * @return getCardsMap
   */
  public Map<String,Card> getCardsSet() {
    return cardsMap;
  }

  /**
   * 
   * @param select
   * @return deckMap
   */
  public Map<String,Card> addDeck(final String select) {
    final Card c = cardsMap.get(select);
    this.deckMap.put(select, c);
    return deckMap;
  }
  /**
   * 
   * @param select
   * @return cardsMap
   */
  public Map<String,Card> addCardSet(final String select) {
    final Card c = deckMap.get(select);
    this.deckMap.put(select, c);
    return cardsMap;
  }
 /**
  * 
  * @param select
  * @return cardsMap
  */
  public Map<String,Card> removeCardSet(final String select) {
    cardsMap.remove(select);
    return cardsMap;
  }

  /**
   * 
   * @param select
   * @return deckMap
   */
  public Map<String,Card> removeDeckCard(final String select) {
    deckMap.remove(select);
    return deckMap;
  }
}
