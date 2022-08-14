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
import model.utilities.Deck;
import view.screens.DeckScreen;
/**
 * Controller implementation for the game screen.
 */
public class DeckController extends Controller {
  private static final int DIMDECK = 4;
  private JFrame frame;
  private Deck deck = new Deck();
  private TextureAtlas atlas;
  private Skin skin;

  /**
   * Constructor.
   */
  public DeckController() {
    super(Audio.getMenuMusic());
    super.registerModel(new Model());
    this.atlas = new TextureAtlas("buttons/atlas.pack");
    this.skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), atlas);
  }

  @Override
  public void update(final float dt) {
    // TODO Auto-generated method stub
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
   * 
   * setCard in DeckScreen
   */
  public List<String> setCards() {
    List cards = new List<String>(skin);
    cards.setItems(deck.getCardsList().toArray());
    return cards;
  }

  /**
   * 
   * setDeck in DeckScreen
   */
  public List<String> setDeck() {
    List decklist = new List<String>(skin);
    decklist.setItems(deck.getDeckList().toArray());
    return decklist;
  }
  
  /**
   * 
   * add card in DeckScreen and remove in CardList
   */
  public void addDeck(List deckGame, List cardsGame) {
    if (((java.util.List<Object>) deck).size() < DIMDECK) {
      deck.add(deckGame, cardsGame);
      deckGame.setItems(deck.getDeckList());
      cardsGame.setItems(deck.getCardsList());
    }
    else 
      JOptionPane.showMessageDialog(frame, "DECK PIENO(MAX 4 CARTE), RIMUOVERE PRIMA UNA CARTA");
  }
  
  /**
   * 
   * remove card in DeckScreen and add in CardList
   */
  public void removeDeck(List deckGame, List cardsGame) {
    deck.remove(deckGame, cardsGame);
    deckGame = this.setDeck();
    cardsGame = this.setCards();
  }
  
}
