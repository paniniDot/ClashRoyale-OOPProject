package model.utilities;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.List;

/**
 * 
 * Class Deck
 *
 */
public class Deck {

  private ArrayList<String> deckList;
  private ArrayList<String> cardsList;

  /**
   * 
   */
  public Deck() {
    this.deckList = new ArrayList<String>();   //dovrà essere caricato dal file
    this.deckList.add("Mega Knight");
    this.deckList.add("Ice Wizard");
    this.deckList.add("Inferno Dragon");
    this.deckList.add("Ram Rider");
    this.cardsList = new ArrayList<String>();  //idem
    this.cardsList.add("Archer Queen");
    this.cardsList.add("Golden Knight");
    this.cardsList.add("Skeleton King");
    this.cardsList.add("Mighty Miner");
    this.cardsList.add("Miner");
    this.cardsList.add("Princess");
  }
  /**
   * 
   * @return
   */
  public ArrayList<String> getDeckList() {
    return deckList;
  }
  /**
   * 
   * @param deckList
   */
  public void setDeckList(ArrayList<String> deckList) {
    this.deckList = deckList;
  }
  /**
   * 
   * @return
   */
  public ArrayList<String> getCardsList() {
    return cardsList;
  }
  /**
   * 
   * @param cardsList
   */
  public void setCardsList(ArrayList<String> cardsList) {
    this.cardsList = cardsList;
  }
  /**
   * 
   * @param selected
   * @param cardsGame 
   */
  public void remove(List deckGame, List cardsGame) {
    this.deckList.remove((String)deckGame.getSelected());
    this.cardsList.add((String) deckGame.getSelected());
  }
  /**
   * 
   * @param selected
   */
  public void add(List deckGame, List cardsGame) {
    this.deckList.add((String) cardsGame.getSelected());
    this.cardsList.remove((String)cardsGame.getSelected());

  }
}
