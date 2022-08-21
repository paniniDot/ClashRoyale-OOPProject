package control.controller;

import control.BaseGame;
import control.utilities.FileManager;
import model.Model;
import model.utilities.Audio;
import view.screens.StatScreen;

/**
 * Controller implementation of the stat screen.
 */
public class StatController extends Controller {

  private final FileManager fileManager;

  /**
   * Constructor.
   */
  public StatController() {
    super(Audio.getMenuMusic());
    super.registerModel(new Model());
    this.fileManager = new FileManager();
    this.fileManager.read();
    //this.towers = 
  }

  /**
   * @return matches played.
   */
  public int getPlays() {
    return this.fileManager.getPlays();
  }

/**
 * @return matches win.
 */
  public int getWins() {
    return this.fileManager.getWins();
  }

/**
 * @return towers destroyed.
 */
  public int getTowers() {
    return this.fileManager.getTowersDestroyed();
  }

  /**
   * @return win/lose ratio.
   */
  public float getRatio() {
    final var wins = this.getWins();
    final var plays = this.getPlays();
    return wins / ((plays - wins) == 0 ? 1 : plays - wins);
  }

  /**
   * Istanciate a new MenuController which takes control of the application.
   */
  public void triggerMenu() {
    new MenuController().setCurrentActiveScreen();
  }

  @Override
  public void setCurrentActiveScreen() {
    BaseGame.setActiveScreen(new StatScreen(this));
  }

  @Override
  public void update(final float dt) {
  }

  /**
   * Return to menu screen.
   */
  public void returnButton() {
    triggerMenu();
  }

}
