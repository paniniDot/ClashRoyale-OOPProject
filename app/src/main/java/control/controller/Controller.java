package control.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import control.BaseGame;

import model.Model;
import model.utilities.Audio;
import view.screens.BaseScreen;

/**
 * The controller for the game.
 */
public abstract class Controller {

  private Model model;
  private final Audio audio;

  /**
   * Constructor.
   * 
   * @param audio
   *              the audio for the screen subscribed to this controller.
   */
  public Controller(final Audio audio) {
    this.audio = audio;
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
}
