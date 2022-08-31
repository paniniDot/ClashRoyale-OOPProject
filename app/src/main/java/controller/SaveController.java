package controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.entities.users.User;

/**
 * Class used to save and load User and Deck.
 */
public class SaveController  {

  private static final  Gson GSON = new GsonBuilder().create();
  private static final  FileHandle USER_FILES = Gdx.files.internal("saves/user.json");

  private final User user;

  /**
   * Class used to load and save User and Deck.
   */
  public SaveController() {
    if (!USER_FILES.exists()) {
      this.user = new User("P");
    } else {
      this.user = loadUser();
    }
  }


/**
 * 
 * @return an object of {@link User}.
 */
  public User getUser() {
    return user;
  }


  /**
   * Load the user from the Json file.
   * 
   * @return a {@link User} loaded from file.
   */
  public static User loadUser() {
    if (!USER_FILES.exists()) {
      return new User("Panini");
    }
    try {
      return GSON.fromJson(new FileReader(USER_FILES.file()), User.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Serialize a user in a json file.
   * 
   * @param user the {@link User} to be saved.
   */
  public static void saveUser(final User user) {
    try (FileWriter writer = new FileWriter(USER_FILES.file())) {
      GSON.toJson(user, writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
