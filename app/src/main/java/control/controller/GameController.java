package control.controller;

import com.badlogic.gdx.Gdx;

import model.Model;
import model.utilities.Audio;
import view.screens.GameScreen;

/**
 * Controller implementation for the game screen.
 */
public class GameController extends Controller {

  /**
   * Constructor.
   */
  public GameController() {
    super(Audio.getBattleMusic());
    super.registerScreen(new GameScreen(this));
    super.registerModel(new Model());
    Gdx.input.setInputProcessor(super.getScreen().getMainStage());
  }

  @Override
  public void update(final float dt) {
    // TODO Auto-generated method stub

  }

}
