package controller.deck;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import model.Model;
import view.screens.deck.DeckScreen;
import controller.Controller;
import controller.audio.AudioDeckController;
import controller.menu.MenuController;
import launcher.ClashRoyale;
import model.deck.PlayersDeck;

/**
 * Controller implementation for the game screen.
 */
public class DeckController extends Controller {

  private static final int DECK_SIZE = 4;

  private final List<String> cards;
  private final List<String> decklist;
  private final JFrame frame;

  /**
   * Constructor.
   */
  public DeckController() {
    super(new AudioDeckController());
    super.registerModel(new Model());
    final var skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), new TextureAtlas("buttons/atlas.pack"));
    this.decklist = new List<>(skin);
    this.cards = new List<>(skin);
    this.frame = new JFrame();
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
    ClashRoyale.setActiveScreen(new DeckScreen(this));
  }

  /**
   * setCard in DeckScreen.
   * 
   * @return the List of cards.
   */
  public List<String> listGDXCard() {
    this.cards.setItems(PlayersDeck.getInstance().namesCardsCard().stream().toArray(String[]::new));
    return cards;
  }

  /**
   * setDeck in DeckScreen.
   * 
   * @return the List of deck.
   */
  public List<String> listGDXDeck() {
    this.decklist.setItems(PlayersDeck.getInstance().namesCardsDeck().stream().toArray(String[]::new));
    return decklist;
  }

  /**
   * Add card in DeckScreen and remove from CardList.
   * 
   * @param select the card to move
   * 
   * @return List of deck. 
   */
  public List<String> addDeck(final String select) {
    PlayersDeck.getInstance().addDeck(select);
    PlayersDeck.getInstance().getDeck().get(select).setPosition(PlayersDeck.getInstance().newPositionFree());
    return listGDXDeck();
  }

  /**
   * Add card in DeckScreen and remove in CardList.
   * 
   * @param select the card to move.
   * 
   * @return the list of cards updated.
   */
  public List<String> addCard(final String select) {
    PlayersDeck.getInstance().addCard(select);
    return listGDXCard();
  }

  /**
   * Remove card in DeckScreen and add in CardList.
   * 
   * @param card the card to remove.
   * 
   * @return the list of cards updated. 
   */
  public List<String> removeCard(final String card) {
    PlayersDeck.getInstance().removeCard(card);
    return listGDXCard();
  }

  /**
   * Remove card in DeckScreen and add in CardList.
   * Adds the position of the card removed from the deck between the free positions
   * @param card the card to remove.
   * 
   * @return the deckList updated.
   */
  public List<String> removeDeckCard(final String card) {
    PlayersDeck.getInstance().getPositionFree().add(PlayersDeck.getInstance().getDeck().get(card).getPosition());
    PlayersDeck.getInstance().removeDeckCard(card);
    return listGDXDeck();
  }

  /**
   * Check dim deck < 4 for insert new card.
   * 
   * @return true if the deck has 4 cards.
   */
  public boolean full() {
    if (PlayersDeck.getInstance().getDeck().size() < DECK_SIZE) {
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
    if (!PlayersDeck.getInstance().getDeck().isEmpty()) {
      return true;
    }
    JOptionPane.showMessageDialog(frame, "DECK VUOTO");
    return false;
  }

  /**
   * Check if the deck has 4 cards, than save the deck and return to the menu. 
   */
  public void returnButton() {
    if (PlayersDeck.getInstance().getDeck().size() == DECK_SIZE) {

      triggerMenu();
    } else {
      JOptionPane.showMessageDialog(frame, "INSERIRE 4 CARTE NEL DECK PER POTER GIOCARE");
    }
  }
}
