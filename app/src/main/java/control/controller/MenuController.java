package control.controller;

import model.utilities.Audio;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import control.BaseGame;
import control.controller.game.BotGameController;
import model.Model;
import model.actors.users.User;
import view.screens.MenuScreen;

/**
 * Controller implementation for the Menu Screen.
 */
public class MenuController extends Controller {
  private FileHandle file;
  private User user;
  private Gson gson;
  /**
   * Constructor.
   */
  public MenuController() {
    super(Audio.getMenuMusic());
    super.registerModel(new Model());
    this.gson = new GsonBuilder().setPrettyPrinting().create();
    this.file = Gdx.files.internal("saves/user.json");
      if (!this.file.exists()) {
        this.user = new User("P"); 
        save();
      } else {
        this.user = load();
      }
  }

  
  public User getUser() {
    return user;
  }


  @Override
  public void update(final float dt) {
    // TODO Auto-generated method stub

  }

  /**
   * Istanciate a new GameController which takes control of the application.
   */
  public void triggerPlay() {
    new BotGameController().setCurrentActiveScreen();
  }

  /**
   * Istanciate a new DeckController which takes control of the application.
   */
  public void triggerDeck() {
    new DeckController().setCurrentActiveScreen();
  }

  /**
   * Close the application.
   */
  public void triggerQuit() {
    Gdx.app.exit();
  }

  @Override
  public void setCurrentActiveScreen() {
   BaseGame.setActiveScreen(new MenuScreen(this));
  }
  
  /**
   * 
   * @param user
   */
  public void save() {
    final FileWriter writer;
    try {
      writer = new FileWriter(file.file());
      gson.toJson(this.user, writer);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * @return User
   */
  public User load() {
    try {
      return this.gson.fromJson(new FileReader(this.file.file()), User.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
