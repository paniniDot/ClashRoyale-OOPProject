package view.screens;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import control.controller.Controller;
import control.controller.game.BotGameController;
import control.controller.game.GameController;
import control.launcher.ClashRoyale;

import model.utilities.AnimationUtilities;

import view.actors.BaseActor;

/**
 * In-game screen implementation.
 */
public class GameScreen extends BaseScreen {

  private SpriteBatch sprite;
  private BitmapFont gamefont;

  /**
   * Constructor.
   * 
   * @param controller
   *                  {@inheritDoc}.
   */
  public GameScreen(final Controller controller) {
    super(controller);
  }

  @Override
  public void initialize() {
    super.getController().playMusic();
    super.getController().setInputProcessor(super.getMainStage());
    sprite = new SpriteBatch();
    gamefont = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"));
    final var arena = new BaseActor(0, 0, super.getMainStage(), AnimationUtilities.loadTexture("arenas/arena1.png"));
    arena.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
    ((GameController) super.getController()).loadActors(super.getMainStage());
    ((GameController) super.getController()).loadTowers(super.getMainStage());
  }

  @Override
  public void update(final float dt) {
    super.getController().update(dt);
    ((GameController) super.getController()).updateActors();
    ((GameController) super.getController()).updateActorAnimations();
  }

  @Override
  public void render(final float dt) {
    super.render(dt);
    sprite.begin();
    gamefont.draw(sprite, "elisir " + ((BotGameController) super.getController()).getPlayerCurrentElixir(), 100, 100);
    gamefont.draw(sprite, "durata " + ((BotGameController) super.getController()).getLeftTime(), 100, 200);
    sprite.end();
    //RectDrawer.showDebugBoundingBoxes(this.map.getMap().vertexSet().stream().map(MapUnit::getUnitRectangle).collect(Collectors.toList()), Color.BLUE);
    //RectDrawer.showDebugBoundingBoxes(this.map.getMap().vertexSet().stream().filter(v -> v.getType().equals(MapUnit.Type.TERRAIN)).map(MapUnit::getUnitRectangle).collect(Collectors.toList()), Color.BLUE);
    //RectDrawer.showDebugBoundingBoxes(this.map.getMap().vertexSet().stream().filter(v -> v.getType().equals(MapUnit.Type.TOWER)).map(MapUnit::getUnitRectangle).collect(Collectors.toList()), Color.RED);
  }

  /**
   * Show a dialog message telling you win.
   * 
   * @param playerPoints the points made by the player.
   * 
   * @param botPoints the points made by the bot.
   */
  public void winDialog(final int playerPoints, final int botPoints) {
    JOptionPane.showMessageDialog(new JFrame(), "HAI VINTO: " + playerPoints + " - " + botPoints);
  }

  /**
   * Show a dialog message telling you loose.
   * 
   * @param playerPoints the points made by the player.
   * 
   * @param botPoints the points made by the bot.
   */
  public void looseDialog(final int playerPoints, final int botPoints) {
    JOptionPane.showMessageDialog(new JFrame(), "HAI PERSO: " + playerPoints + " - " + botPoints);
  }
}
