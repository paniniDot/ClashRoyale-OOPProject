package control.launcher;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import control.controller.MenuController;

/**
 * Implementation of Game class.
 */
public final class ClashRoyale extends Game {

  /**
   * Screen default height.
   */
  public static final int HEIGHT = 963;

  /**
   * Screen default width.
   */
  public static final int WIDTH = 689;

  /**
   * Game name.
   */
  public static final String TITLE = "Clash Royale";

  /**
   * Game frame per second.
   */
  public static final int FPS = 60;

  private static final ClashRoyale GAME = new ClashRoyale();

  private ClashRoyale() {
  }

  /**
   * 
   * @return the only one instance ever created for the game.
   */
  public static ClashRoyale getIstance() {
    return GAME;
  }

  @Override
  public void create() {
    new MenuController().setCurrentActiveScreen();
  }

  /**
   * Set a new screen as active.
   * 
   * @param screen
   *              the new screen.
   */
  public static void setActiveScreen(final Screen screen) {
    GAME.setScreen(screen);
  }
}
