package control.controller;

import javax.swing.JFrame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import control.BaseGame;
import model.Model;
import model.utilities.Audio;
import view.screens.StatScreen;

/**
 * Controller implementation of the stat screen.
 */
public class StatController extends Controller {

  private final int plays;
  private final int wins;
  private final int towers;
  
  /**
   * Constructor.
   */
  public StatController() {
    super(Audio.getMenuMusic());
    super.registerModel(new Model());
    this.plays = 5;
    this.wins = 5;
    this.towers = 5;
    /*this.atlas = new TextureAtlas("buttons/atlas.pack");
    this.skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), atlas);*/
  }

  /**
   * @return matches played.
   */
  public int getPlays() {
    return plays;
  }

/**
 * @return matches win.
 */
  public int getWins() {
    return wins;
  }

/**
 * @return towers destroyed.
 */
  public int getTowers() {
    return towers;
  }

  /**
   * @return win/lose rateo.
   */
  public float getRateo() {
    return wins / ((plays - wins) == 0 ? 1 : plays - wins);
  }

  /**
   * Istanciate a new MenuController which takes control of the application.
   */
  public void triggerMenu() {
    new MenuController().setCurrentActiveScreen();
  }

  @Override
  public void setCurrentActiveScreen() {
    BaseGame.setActiveScreen(new StatScreen(this));
  }

  @Override
  public void update(final float dt) {
  }

  /**
   * Return to menu screen.
   */
  public void returnButton() {
    triggerMenu();
  }

}
