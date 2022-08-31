package controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.entities.users.User;

/**
 * Class used to save and load User and Deck.
 */
public final class SaveController  {

  private static final Gson GSON = new GsonBuilder().create();
  private static final String USER_DIR_PATH = System.getProperty("user.home") + File.separator + "royaleData" + File.separator;
  private static final String FILE_NAME = "user.json";

  private SaveController() {
  }

  private static boolean checkDirectoryExistance() {
    return new File(USER_DIR_PATH).exists();
  }

  private static void createDirectory() {
    new File(USER_DIR_PATH).mkdir();
  }

  private static boolean checkFileExistance() {
    return new File(USER_DIR_PATH + File.separator + FILE_NAME).exists();
  }

  private static void createFile() {
    try {
      new File(USER_DIR_PATH + File.separator + FILE_NAME).createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Load the user from the Json file.
   * 
   * @return a {@link User} loaded from file.
   */
  public static User loadUser() {
    if (SaveController.checkDirectoryExistance() && SaveController.checkFileExistance()) {
      try {
        return GSON.fromJson(new FileReader(new File(USER_DIR_PATH + File.separator + FILE_NAME)), User.class);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      SaveController.createDirectory();
      SaveController.createFile();
      return new User("Dream");
    }
    return null;
  }

  /**
   * Serialize a user in a json file.
   * 
   * @param user the {@link User} to be saved.
   */
  public static void saveUser(final User user) {
    try (FileWriter writer = new FileWriter(new File(USER_DIR_PATH + File.separator + FILE_NAME))) {
      GSON.toJson(user, writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
