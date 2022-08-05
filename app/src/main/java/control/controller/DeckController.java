package control.controller;

import com.badlogic.gdx.Gdx;

import model.Model;
import model.utilities.Audio;
import view.screens.DeckScreen;
/**
 * Controller implementation for the game screen.
 */
public class DeckController extends Controller {
  /**
   * Constructor.
   */
  public DeckController() {
    super(Audio.getBattleMusic());
    super.registerScreen(new DeckScreen(this));
    super.registerModel(new Model());
    Gdx.input.setInputProcessor(super.getScreen().getMainStage());
  }

  @Override
  public void update(float dt) {
    // TODO Auto-generated method stub
  }
}
