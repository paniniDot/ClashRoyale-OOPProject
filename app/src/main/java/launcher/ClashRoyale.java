package launcher;

import model.screens.MenuScreen;

/**
 * Implementation of BaseGame class.
 */
public class ClashRoyale extends BaseGame {

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

  @Override
  public void create() {
    super.setScreen(new MenuScreen());
  }

}
