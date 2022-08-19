package control.controller;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import control.BaseGame;
import model.Model;
import model.utilities.Audio;
import view.screens.DeckScreen;
/**
 * Controller implementation for the game screen.
 */
public class DeckController extends Controller {
  private TextureAtlas atlas;
  private Skin skin;
  private List cards, decklist;
  private static final int DIMDECK = 4;
  private JFrame frame;

  /**
   * Constructor.
   */
  public DeckController() {
    super(Audio.getMenuMusic());
    super.registerModel(new Model());
    this.atlas = new TextureAtlas("buttons/atlas.pack");
    this.skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), atlas);
    this.decklist = new List<>(skin);
    this.cards = new List<>(skin);
  }


  @Override
  public void update(final float dt) {
  }

  /**
   * Istanciate a new MenuController which takes control of the application.
   */
  public void triggerMenu() {
    new MenuController().setCurrentActiveScreen();
  }

  @Override
  public void setCurrentActiveScreen() {
    BaseGame.setActiveScreen(new DeckScreen(this));
  }

  /**
   * setCard in DeckScreen.
   * 
   * @return the List of cards.
   */
  public List<String> setCards() {
    this.cards.setItems(getUDeck().getCardsSet().toArray());
    return cards;
  }

  /**
   * setDeck in DeckScreen.
   * 
   * @return the List of deck.
   */
  public List<String> setDeck() {
    this.decklist.setItems(getUDeck().getDeckSet().toArray());
    return decklist;
  }

  /**
   * Add card in DeckScreen and remove from CardList.
   * 
   * @param
   * 
   * @return List of deck. 
   */
  public List<String> addDeck(final String select) {
    decklist.setItems(getUDeck().addDeck(select).toArray());
      return decklist;
    }

  /**
   * Add card in DeckScreen and remove in CardList.
   * 
   * @return 
   */
  public List<String> addCard(final String select) {
    cards.setItems(getUDeck().addCardSet(select).toArray());
      return cards;
    }

  /**
   * Remove card in DeckScreen and add in CardList.
   * 
   * @return 
   */
  public List<String> removeCard(final String card) {
    this.cards.setItems(getUDeck().removeCardSet(card).toArray());
    return cards;    
  }

  /**
   * Remove card in DeckScreen and add in CardList.
   * 
   * @return 
   */
  public List<String> removeDeckCard(final String card) {
    this.decklist.setItems(getUDeck().removeDeckCard(card).toArray());
    return decklist;
  }

  /**
   * Check dim deck < 4 for insert new card.
   * 
   * @return true if the deck has 4 cards.
   */
  public boolean full() {
    if (getUDeck().getDeckSet().size() < DIMDECK) {
      return true;
    }
    JOptionPane.showMessageDialog(frame, "DECK PIENO(MAX 4 CARTE), RIMUOVERE PRIMA UNA CARTA");
    return false;
  }

  /**
   *  Check if the deck has no cards inside.
   *
   *   @return true if the deck is empty.
   */
  public boolean empty() {
    if (!getUDeck().getDeckSet().isEmpty()) {
      return true;
    }
    JOptionPane.showMessageDialog(frame, "DECK VUOTO");
    return false;
  }

  /**
   * Check if the deck has 4 cards, than save the deck and return to the menu. 
   */
  public void returnButton() {
    if (getUDeck().getDeckSet().size() == DIMDECK) {
    save();
    triggerMenu();
    } else {
      JOptionPane.showMessageDialog(frame, "INSERIRE 4 CARTE NEL DECK PER POTER GIOCARE");
    }
  }

}