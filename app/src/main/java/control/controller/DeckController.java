package control.controller;

import com.badlogic.gdx.Gdx;

import control.BaseGame;
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
    super(Audio.getMenuMusic());
    super.registerModel(new Model());
  }

  @Override
  public void update(final float dt) {
    // TODO Auto-generated method stub
  }

  /**
   * Istanciate a new MenuController which takes control of the application.
   */
  public void triggerMenu() {
    new MenuController().setCurrentActiveScreen();
  }

  @Override
  public void setCurrentActiveScreen() {
    BaseGame.setActiveScreen(new DeckScreen(this));
  }
}
