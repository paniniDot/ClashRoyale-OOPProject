package controller.menu;

import com.badlogic.gdx.Gdx;

import controller.AudioController;
import controller.Controller;
import controller.deck.DeckController;
import controller.game.BotGameController;
import controller.statistics.StatController;
import launcher.ClashRoyale;
import model.Model;
import view.screens.menu.MenuScreen;

/**
 * Controller implementation for the Menu Screen.
 */
public class MenuController extends Controller {

  /**
   * Constructor.
   */
  public MenuController() {
    super(AudioController.getMenuMusic());
    super.registerModel(new Model());
  }

  @Override
  public void update(final float dt) {
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
   * Istanciate a new StatController which takes control of the application. 
   */
  public void triggerStat() {
    new StatController().setCurrentActiveScreen();
  }
  /**
   * Close the application.
   */
  public void triggerQuit() {
    Gdx.app.exit();
  }

  @Override
  public void setCurrentActiveScreen() {
   ClashRoyale.setActiveScreen(new MenuScreen(this));
  }
}
