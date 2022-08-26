package control.controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import control.BaseGame;
import control.controller.game.AudioController;
import control.controller.game.BotGameController;
import model.Model;
import model.actors.users.User;
import view.screens.MenuScreen;

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
   BaseGame.setActiveScreen(new MenuScreen(this));
  }
}
