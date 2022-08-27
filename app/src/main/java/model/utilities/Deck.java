package model.utilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
  private final PositionInGameDeck positionInGameDeck = new PositionInGameDeck();
  private final Set<Vector2> positionFree = positionInGameDeck.getPositionFree();

  /**
   * initialize basic cards and decks.
   */
  public Deck() {
    this.deckMap = new HashMap<>();
    this.deckMap.put("Barbarian", Barbarian.create(GlobalData.USER, newPositionFree()));
    this.deckMap.put("Giant", Giant.create(GlobalData.USER, newPositionFree()));
    this.deckMap.put("InfernoTower", InfernoTower.create(GlobalData.USER, newPositionFree()));
    this.deckMap.put("Wizard", Wizard.create(GlobalData.USER, newPositionFree())); 
    this.cardsMap = new HashMap<>();
    this.cardsMap.put("Giants", Giant.create(GlobalData.USER, new Vector2(500, 100)));
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
  public Map<String, Card> getCards() {
    return cardsMap;
  }

  /**
   * 
   * @return set positionFree
   */
  public Set<Vector2> getPositionFree() {
    return positionFree;
  }
/**
 * 
 * @return the first free position and deletes it from those available
 */
  public Vector2 newPositionFree() {
    final Iterator<Vector2> i = getPositionFree().iterator();
    final Vector2 tmp  =  i.next();
    getPositionFree().remove(tmp);
    return tmp;
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
    deckMap.remove(select);
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
