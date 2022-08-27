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
  public static final  Gson GSON = new GsonBuilder().create();
  /**
   * 
   */
  public static final  FileHandle FILEUSER = Gdx.files.internal("saves/user.json");
  /**
   * 
   */
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
  }


/**
 * 
 * @return
 */
  public User getUser() {
    return user;
  }


  /**
   * Load the user from the Json file.
   * 
   * @return User
   */
  public static User loadUser() {
    if (!FILEUSER.exists()) {
      User u = new User("P");
      return u;
    }
    try {
      return GSON.fromJson(new FileReader(FILEUSER.file()), User.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
