package model.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.badlogic.gdx.math.Vector2;

import model.GlobalData;
import model.actors.cards.Card;
import model.actors.cards.buildings.InfernoTower;
import model.actors.cards.troops.Barbarian;
import model.actors.cards.troops.Giant;
import model.actors.cards.troops.Wizard;

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
    this.deckMap.put("Barbarian", Barbarian.create(GlobalData.USER, new Vector2(100, 100)));
    this.deckMap.put("Giant", Giant.create(GlobalData.USER, new Vector2(200, 100)));
    this.cardsMap = new HashMap<>();
    this.cardsMap.put("Giants", Giant.create(GlobalData.USER, new Vector2(200, 100)));
    this.deckMap.put("InfernoTower", InfernoTower.create(GlobalData.USER, new Vector2(300, 100)));
    this.deckMap.put("Wizard", Wizard.create(GlobalData.USER, new Vector2(400, 100))); 
  }

  /**
   * 
   * @return getDeckMap
   */
  public Map<String, Card> getDeck() {
    return deckMap;
  }

  /**
   * 
   * @return getCardsMap
   */
  public Map<String,Card> getCards() {
    return cardsMap;
  }

  /**
   * 
   * @param select
   * @return deckMap
   */
  public Map<String, Card> addDeck(final String select) {
    
    deckMap.put(select, cardsMap.get(select));
    return deckMap;
  }
  /**
   * 
   * @param select
   * @return cardsMap
   */
  public Map<String, Card> addCard(final String select) {
    cardsMap.put(select, deckMap.get(select));
    return cardsMap;
  }
  /**
   * 
   * @param select
   * @return cardsMap
   */
  public Map<String, Card> removeCard(final String select) {
    cardsMap.remove(select);
    return cardsMap;
  }

  /**
   * 
   * @param select
   * @return deckMap
   */
  public Map<String, Card> removeDeckCard(final String select) {
    System.out.println(deckMap);
    deckMap.remove(select);
    System.out.println("rimossa" + select + deckMap);
    return deckMap;
  }

  /**
   * 
   * @return List<Card> from map
   */
  public List<Card> cardList() {
    return deckMap.entrySet().stream()
        .map(e -> e.getValue())
        .collect(Collectors.toList());
  }
  
  /**
   * 
   * @return List<String> from key deckmap
   */
  public List<String> namesCardsDeck() {
    return deckMap.entrySet().stream()
        .map(e -> e.getKey())
        .collect(Collectors.toList());
  }
  /**
   * 
   * @return List<String> from key cardsmap
   */
  public List<String> namesCardsCard() {
    return cardsMap.entrySet().stream()
        .map(e -> e.getKey())
        .collect(Collectors.toList());
  }
  
}
