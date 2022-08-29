package controller.statistics;

import controller.Controller;
import controller.FileManager;
import controller.menu.MenuController;
import launcher.ClashRoyale;
import model.Model;
import view.screens.statistics.StatScreen;

/**
 * Controller implementation of the stat screen.
 */
public class StatController extends Controller {

  private final FileManager fileManager;

  /**
   * Constructor.
   */
  public StatController() {
    super(new AudioStaticsController());
    super.registerModel(new Model());
    this.fileManager = new FileManager();
    this.fileManager.read();
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
    return  (float) wins / ((plays - wins) == 0 ? 1 : plays - wins);
  }

  /**
   * Istanciate a new MenuController which takes control of the application.
   */
  public void triggerMenu() {
    new MenuController().setCurrentActiveScreen();
  }

  @Override
  public void setCurrentActiveScreen() {
    ClashRoyale.setActiveScreen(new StatScreen(this));
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
