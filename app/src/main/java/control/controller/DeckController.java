package control.controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import control.BaseGame;
import model.Model;
import model.utilities.Audio;
import model.utilities.Deck;
import view.screens.DeckScreen;
/**
 * Controller implementation for the game screen.
 */
public class DeckController extends Controller {
  private Deck deck;
  private TextureAtlas atlas;
  private Skin skin;
  private List decklist;
  private List cards;
  private static final int DIMDECK = 4;
  private JFrame frame;
  private Gson gson;
  private FileHandle file;


  /**
   * Constructor.
   */
  public DeckController() {
    super(Audio.getMenuMusic());
    super.registerModel(new Model());
    this.atlas = new TextureAtlas("buttons/atlas.pack");
    this.skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), atlas);
    this.decklist = new List<String>(skin);
    this.cards = new List<String>(skin);
    this.gson = new GsonBuilder().setPrettyPrinting().create();
    this.file = Gdx.files.internal("saves/deck.json");
    if (!this.file.exists()) {
      this.deck = new Deck();
      save(this.deck);
    } else {
      this.deck = load();
    }
  }
/**
 * 
 * @return getDeck
 */
  public Deck getDeck() {
    return deck;
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
   * setCard in DeckScreen
   */
  public List<String> setCards() {
    this.cards.setItems(deck.getCardsSet().toArray());
    return cards;
  }

  /**
   * 
   * setDeck in DeckScreen
   */
  public List<String> setDeck() {
    this.decklist.setItems(deck.getDeckSet().toArray());
    return decklist;
  }
  
  /**
   * 
   * add card in DeckScreen and remove in CardList
   * @return 
   */
  public List<String> addDeck(String select) {
    decklist.setItems(deck.addDeck(select).toArray());
      return decklist;
    }
  
  /**
   * 
   * add card in DeckScreen and remove in CardList
   * @return 
   */
  public List<String> addCard(String select) {
    cards.setItems(deck.addCardSet(select).toArray());
      return cards;
    }
  
  /**
   * 
   * remove card in DeckScreen and add in CardList
   * @return 
   */
  public List<String> removeCard(String card) {
    this.cards.setItems(deck.removeCardSet(card).toArray());
    return cards;    
  }
  
  /**
   * 
   * remove card in DeckScreen and add in CardList
   * @return 
   */
  public List<String> removeDeckCard(String card) {
    this.decklist.setItems(deck.removeDeckCard(card).toArray());
    return decklist;    
  }

  /**
   * check dim deck < 4 for insert new card
   * @return
   */
  public boolean full() {
    if (deck.getDeckSet().size() < DIMDECK)
      return true;
    JOptionPane.showMessageDialog(frame, "DECK PIENO(MAX 4 CARTE), RIMUOVERE PRIMA UNA CARTA");
    return false;
  }
  
  public boolean empty() {
    if (!deck.getDeckSet().isEmpty())
      return true;
    JOptionPane.showMessageDialog(frame, "DECK VUOTO");
    return false;
  }
  
  /**
   * 
   * @param user
   */
  public void save(final Deck deck) {
    final FileWriter writer;
    try {
      writer = new FileWriter(file.file());
      gson.toJson(deck, writer);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * @return User
   */
  public Deck load() {
    try {
      return this.gson.fromJson(new FileReader(this.file.file()), Deck.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  public void returnButton() {
    if (getDeck().getDeckSet().size() == DIMDECK) {
    save(getDeck());
    triggerMenu();    
    }
    else
      JOptionPane.showMessageDialog(frame, "INSERIRE 4 CARTE NEL DECK PER POTER GIOCARE");
  }
}
