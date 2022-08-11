package control.controller;

import model.utilities.Audio;

import com.badlogic.gdx.Gdx;

import control.controller.game.BotGameController;
import model.Model;
import view.screens.MenuScreen;

/**
 * Controller implementation for the Menu Screen.
 */
public class MenuController extends Controller {

  /**
   * Constructor.
   */
  public MenuController() {
    super(Audio.getMenuMusic());
    super.registerScreen(new MenuScreen(this));
    super.registerModel(new Model());
    Gdx.input.setInputProcessor(super.getScreen().getUiStage());
  }

  @Override
  public void update(final float dt) {
    // TODO Auto-generated method stub

  }

  /**
   * Istanciate a new GameController which takes control of the application.
   */
  public void triggerPlay() {
    new BotGameController().setCurrentActiveScreen();
  }

  /**
   * Istanciate a new DeckController which takes control of the application.
   */
  public void triggerDeck() {
    new DeckController().setCurrentActiveScreen();
  }

  /**
   * Close the application.
   */
  public void triggerQuit() {
    Gdx.app.exit();
  }

}
