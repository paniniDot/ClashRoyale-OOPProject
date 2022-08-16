package control.controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Model;
import model.actors.users.User;
import model.utilities.Audio;
import model.utilities.Deck;

/**
 * The controller for the game.
 */
public abstract class Controller {

  private Model model;
  private final Audio audio;
  private static Gson gson;
  private static FileHandle file;
  private static User user;
  private Deck deck;
  /**
   * Constructor.
   * 
   * @param audio
   *              the audio for the screen subscribed to this controller.
   */
  public Controller(final Audio audio) {
    this.audio = audio;
    this.gson = new GsonBuilder().setPrettyPrinting().create();
    this.file = Gdx.files.internal("saves/user.json");
    if (!this.file.exists()) {
      this.user = new User("P");
      save();
    } else {
      this.user = load();
    }
    this.deck = this.user.getDeck();
  }

  /**
   * Update screen or model once each dt.
   * @param dt
   *          elapsed time since last update.
   */
  public abstract void update(float dt);

  /**
   * Set the stage passed as argument to receive all user input events.
   * 
   * @param stage 
   *              the stage that will listen for input events.
   */
  public void setInputProcessor(final Stage stage) {
    Gdx.input.setInputProcessor(stage);
  }

  /**
   * Set the registered screen as active.
   */
  public abstract void setCurrentActiveScreen();

  /**
   * Register a new Model.
   * @param model
   *              the new model to be registered.
   */
  public void registerModel(final Model model) {
    this.model = model;
  }

  /**
   * 
   * @return the model used by this.
   */
  public Model getModel() {
    return this.model;
  }

  /**
   * Start a new music.
   */
  public void playMusic() {
    this.audio.play();
  }

  /**
   * Stop the music.
   */
  public void stopMusic() {
    this.audio.stop();
  }
  
  /**
   * 
   * @param user
   */
  public static void save() {
    final FileWriter writer;
    try {
      writer = new FileWriter(file.file());
      gson.toJson(user, writer);
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
  
  /**
   * 
   * @return getDeck
   */
    public User getUser() {
      return user;
    }

    public Deck getUDeck() {
      return user.getDeck();
    }
}
