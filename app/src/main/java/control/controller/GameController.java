package control.controller;

import com.badlogic.gdx.Gdx;

import model.Model;
import model.utilities.Audio;
import model.utilities.CountDownController;
import model.utilities.ElixirController;
import view.screens.GameScreen;

/**
 * Controller implementation for the game screen.
 */
public class GameController extends Controller {
  private final ElixirController elixir;
  private final CountDownController count;
  /**
   * Constructor.
   */
  public GameController() {
    super(Audio.getBattleMusic());
    super.registerScreen(new GameScreen(this));
    super.registerModel(new Model());
    this.elixir = new ElixirController();
    this.count = new CountDownController();
    Gdx.input.setInputProcessor(super.getScreen().getMainStage());
  }

  @Override
  public void update(final float dt) {
    if (this.count.getTime() == 0) {
      this.elixir.setRunFalse();
      this.count.setRunFalse();
      super.stopMusic();
      new MenuController().setCurrentActiveScreen();
    }
  }
  /**
   *@return the remaining seconds before game ends.
   */
  public int getLeftTime() {
    return this.count.getTime();
  }
  /**
   *@return the current elixir owned.
   */
  public int getCurrentElixir() {
    return this.elixir.getElixirCount();
  }

}
