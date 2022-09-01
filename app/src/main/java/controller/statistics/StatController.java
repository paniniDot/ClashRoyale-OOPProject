package controller.statistics;

import controller.Controller;
import controller.audio.AudioStaticsController;
import controller.menu.MenuController;

import launcher.ClashRoyale;
import model.GlobalData;
import model.Model;

import view.screens.statistics.StatScreen;

/**
 * Controller implementation of the statistics screen.
 */
public class StatController extends Controller {

  /**
   * Constructor.
   */
  public StatController() {
    super(new AudioStaticsController());
    super.playMusic();
    super.registerModel(new Model());
  }

  /**
   * @return matches played.
   */
  public int getPlays() {
    return GlobalData.USER.getPlays();
  }

/**
 * @return matches win.
 */
  public int getWins() {
    return GlobalData.USER.getWins();
  }

/**
 * @return towers destroyed.
 */
  public int getTowers() {
    return GlobalData.USER.getDestroyedTowers();
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
   * Instantiate a new MenuController which takes control of the application.
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
