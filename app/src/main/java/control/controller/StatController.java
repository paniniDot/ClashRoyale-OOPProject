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

  private final int plays;
  private final int wins;
  private final int towers;
  private final FileManager fileManager;

  /**
   * Constructor.
   */
  public StatController() {
    super(Audio.getMenuMusic());
    super.registerModel(new Model());
    this.fileManager = new FileManager(5, 5, 5);
    this.fileManager.save();

    this.plays = this.fileManager.getPlays();
    this.wins = this.fileManager.getWins();
    this.towers = this.fileManager.getTowersDestroyed();
  }

  /**
   * @return matches played.
   */
  public int getPlays() {
    return plays;
  }

/**
 * @return matches win.
 */
  public int getWins() {
    return wins;
  }

/**
 * @return towers destroyed.
 */
  public int getTowers() {
    return towers;
  }

  /**
   * @return win/lose ratio.
   */
  public float getRatio() {
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
