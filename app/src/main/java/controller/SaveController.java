package controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.entities.cards.Card;
import model.entities.users.User;

/**
 * Class used to save and load User and Deck.
 */
public class SaveController  {
  private final static  Gson gson = new GsonBuilder().create();
  private final static  FileHandle fileuser = Gdx.files.internal("saves/user.json");
  private final User user;

  /**
   * Class used to load and save User and Deck.
   */
  public SaveController() {
    if (!fileuser.exists()) {
      this.user = new User("P");
    } else {
      this.user = loadUser();
    }
  }


/**
 * 
 * @return user
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
    if (!fileuser.exists()) {
      User u = new User("P");
      return u;
    }
    try {
      return gson.fromJson(new FileReader(fileuser.file()), User.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * save user in file json.
   * @param user
   */
  public static void saveUser(final User user) {
    try {
      final FileWriter writer;
      writer = new FileWriter(fileuser.file());
      gson.toJson(user, writer);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
