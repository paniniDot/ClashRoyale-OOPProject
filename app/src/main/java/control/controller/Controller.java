package control.controller;

import control.BaseGame;

import model.Model;
import model.utilities.Audio;
import view.screens.BaseScreen;

/**
 * The controller for the game.
 */
public abstract class Controller {

  private BaseScreen screen;
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
   * Register a new Screen.
   * @param screen
   *              the new screen observer to be registered.
   */
  public void registerScreen(final BaseScreen screen) {
    this.screen = screen;
  }

  /**
   * Set the registered screen as active.
   */
  public void setCurrentActiveScreen() {
    BaseGame.setActiveScreen(this.screen);
  }

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
   * 
   * @return the screen this controller pilot.
   */
  public BaseScreen getScreen() {
    return this.screen;
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
