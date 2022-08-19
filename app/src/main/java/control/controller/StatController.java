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

/*  private TextureAtlas atlas;
  private Skin skin;
  private JFrame frame;
*/
  /**
   * Constructor.
   */
  public StatController() {
    super(Audio.getMenuMusic());
    super.registerModel(new Model());
    /*this.atlas = new TextureAtlas("buttons/atlas.pack");
    this.skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), atlas);*/
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
