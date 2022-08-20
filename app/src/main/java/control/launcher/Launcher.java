package control.launcher;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * The entry-point to the game.
 */
public final class Launcher {

  private Launcher() {
  }

  /**
   * Main method.
   *
   * @param args
   *            parameters.
   */
  public static void main(final String[] args) {
    final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setTitle(ClashRoyale.TITLE);
    config.setWindowedMode(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
    config.setResizable(false);
    config.setForegroundFPS(ClashRoyale.FPS);
    new Lwjgl3Application(new ClashRoyale(), config);
  }
}
