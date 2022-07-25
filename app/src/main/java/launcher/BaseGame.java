package launcher;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * Abstract class that defines the Game itself.
 *
 */
public abstract class BaseGame extends Game {

  private static BaseGame game;

  /**
   * BaseGame constructor.
   */
  public BaseGame() {
    game = this;
  }

  /** 
   * Allows to switch from a screen to another. 

   * @param s 
   *        {@inheritDoc}
   */
  public static void setActiveScreen(final Screen s) {
    game.setScreen(s);
  }

}
