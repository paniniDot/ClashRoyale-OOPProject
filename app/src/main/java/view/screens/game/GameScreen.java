package view.screens.game;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import controller.Controller;
import controller.game.BotGameController;
import controller.game.GameController;
import launcher.ClashRoyale;
import model.utilities.AnimationUtilities;

import view.actors.BaseActor;
import view.screens.BaseScreen;

/**
 * In-game screen implementation.
 */
public class GameScreen extends BaseScreen {

  private static final int TIMER_X = 10;
  private static final int TIMER_Y = 50;
  private static final int ELISIR_X = 10;
  private static final int ELISIR_Y = 100;
  private static final int POINT_X = 10;
  private static final int POINT_Y = 950;

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
  protected void initialize() {
    super.getController().playMusic();
    super.getController().setInputProcessor(getMainStage());
    sprite = new SpriteBatch();
    gamefont = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"));
    final var arena = new BaseActor(0, 0, getMainStage(), AnimationUtilities.loadTexture("arenas/arena1.png"));
    arena.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
    ((GameController) super.getController()).loadActors(getMainStage());
    ((GameController) super.getController()).loadTowers(getMainStage());
  }

  @Override
  protected void update(final float dt) {
    super.getController().update(dt);
  }

  @Override
  public void render(final float dt) {
    super.render(dt);
    sprite.begin();
    gamefont.draw(sprite, "Elixir " + ((GameController) super.getController()).getPlayerCurrentElixir(), GameScreen.ELISIR_X, GameScreen.ELISIR_Y);
    gamefont.draw(sprite, "Time left " + ((GameController) super.getController()).getLeftTime(), GameScreen.TIMER_X, GameScreen.TIMER_Y);
    gamefont.draw(sprite, "Score: Player->" + ((BotGameController) super.getController()).getPlayerScore() + " Bot->" + ((GameController) super.getController()).getBotScore() ,GameScreen.POINT_X, GameScreen.POINT_Y);
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
