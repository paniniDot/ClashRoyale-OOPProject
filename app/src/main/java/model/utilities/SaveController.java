package model.utilities;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.actors.cards.Card;
import model.actors.users.User;

/**
 * Class used to save and load User and Deck.
 */
public class SaveController {
  /**
   * 
   */
  public static final  Gson GSON = new GsonBuilder()
                                    .registerTypeAdapter(Card.class, new CardDeserializer())
                                    .setPrettyPrinting()
                                    .create();
  /**
   * 
   */
  public static final  FileHandle FILEUSER = Gdx.files.internal("saves/user.json");
  /**
   * 
   */
  public static final  FileHandle FILEDECK = Gdx.files.internal("saves/deck.json");
  private final Deck deck; 
  private final User user;

  /**
   * Class used to load and save User and Deck.
   */
  public SaveController() {
    if (!FILEUSER.exists()) {
      this.user = new User("P");
    } else {
      this.user = loadUser();
    }
    if (!FILEDECK.exists()) {
      this.deck = new Deck();
    } else {
      this.deck = loadDeck();
    }
  }

  public Deck getDeck() {
    return deck;
  }

  public User getUser() {
    return user;
  }

  /**
   * Load the deck from the Json file.
   * 
   * @return Deck
   */
  public static Deck loadDeck() {
    try {
      return GSON.fromJson(new FileReader(FILEDECK.file()), Deck.class);
    } catch (IOException e) {
      System.out.println("Figa");
      e.printStackTrace();
    }
    Deck d = new Deck();
    return d;
  }
  
  /**
   * Load the user from the Json file.
   * 
   * @return User
   */
  public static User loadUser() {
    try {
      return GSON.fromJson(new FileReader(FILEUSER.file()), User.class);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("FigaCapra");
    }
    User u = new User("P");
    return u;
  }
}
