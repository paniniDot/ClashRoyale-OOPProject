package control.controller.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.checkerframework.common.returnsreceiver.qual.This;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import control.controller.Controller;
import control.controller.MenuController;
import model.Model;
import model.actors.Attackable;
import model.actors.cards.Card;
import model.actors.cards.troops.Wizard;
import model.actors.towers.Tower;
import model.actors.users.Bot;
import model.actors.users.User;
import model.utilities.AnimationUtilities;
import model.utilities.Audio;
import model.utilities.CountDownController;
import model.utilities.ElixirController;
import model.utilities.ingame.BotGameModel;
import model.utilities.ingame.GameModel;
import model.utilities.ingame.GameMap;
import view.actors.CardActor;
import view.actors.TowerActor;
import view.screens.GameScreen;

/**
 * Abstract Controller for the game screen.
 */
public abstract class GameController extends Controller {

  /**
   * Used by GameController implementations to load animations. 
   */
  public static final float ANIMATIONS_FRAME_DURATION = (float) 0.017_24 * 10;

  private final ElixirController playerElixir;
  private final CountDownController timer;
  private final GameMap gameMap;

  /**
   * Constructor.
   * 
   * @param model
   *            the logic followed by this controller.
   */
  public GameController(final GameModel model) {
    super(Audio.getBattleMusic());
    this.playerElixir = new ElixirController();
    this.timer = new CountDownController();
    this.gameMap = new GameMap();
    super.registerModel(model);
    super.registerScreen(new GameScreen(this));
    Gdx.input.setInputProcessor(super.getScreen().getMainStage());
  }

  @Override
  public void update(final float dt) {
    if (this.timer.getTime() == 0) {
      this.playerElixir.setRunFalse();
      this.onUpdate();
      this.timer.setRunFalse();
      super.stopMusic();
      new MenuController().setCurrentActiveScreen();
    }
  }

  /**
   * Called from subclasses to extend functionalities when the match is over.
   */
  protected abstract void onUpdate();

  /**
   * 
   *@return the remaining seconds before game ends.
   */
  public int getLeftTime() {
    return this.timer.getTime();
  }

  /**
   * 
   *@return the current elixir owned by the user.
   */
  public int getPlayerCurrentElixir() {
    return this.playerElixir.getElixirCount();
  }

  /**
   * 
   * @return a list of the user attackable entities.
   */
  public List<Attackable> getUserAttackables() {
    return ((GameModel) super.getModel()).getPlayerAttackable();
  }

  /**
   * 
   * @return the current game map.
   */
  protected GameMap getGameMap() {
    return this.gameMap;
  }

  /**
   * Load new card actors in the stage passed as argument, picking them informations from the list passed as argument.
   * 
   * @param list
   *            the list of Cards used to create new actors.
   * @param stage
   *            where actors have to be placed.
   * @param animationName
   *            the animation of the actors.
   * @return 
   *            a list of CardActors.
   */
  protected final List<CardActor> loadCardActorsFrom(final List<Card> list, final Stage stage, final String animationName) {
    final var actors = new ArrayList<CardActor>();
    list.forEach(c -> {
      final var actor = new CardActor(c.getSelfId(), c.getPosition().x, c.getPosition().y, stage);
      actor.setAnimation(AnimationUtilities.loadAnimationFromFiles(c.getAnimationFiles().get(animationName), ANIMATIONS_FRAME_DURATION, true));
      actors.add(actor);
    });
    return actors;
  }

  /**
   * Load card actors in a stage of the screen driven by this controller.
   * 
   * @param stage 
   *              the stage where actors have to be placed.
   * @return 
   *              a list of CardActors owned by the user.
   */
  public final List<CardActor> loadPlayerActors(final Stage stage) {
    return this.loadCardActorsFrom(((GameModel) super.getModel()).getPlayerDeck(), stage, "SELF_MOVING");
  }

  /**
   * Load new tower actors in the stage passed as argument, picking them informations from the list passed as argument.
   * 
   * @param list
   *            the list of Towers used to create new actors.
   * @param stage
   *            where actors have to be placed.
   * @param animationName
   *            the animation of the actors.
   * @return
   *            a list of new Tower Actors.
   */
  protected final List<TowerActor> loadTowerActorsFrom(final List<Tower> list, final Stage stage, final String animationName) {
    final var towers = new ArrayList<TowerActor>();
    list.forEach(t -> {
      final var actor = new TowerActor(t.getSelfId(), t.getPosition().x, t.getPosition().y, stage);
      actor.setAnimation(AnimationUtilities.loadAnimationFromFiles(t.getAnimationFiles().get(animationName), ANIMATIONS_FRAME_DURATION, true));
      towers.add(actor);
    });
    return towers;
  }

  /**
   * Load tower actors in the main stage of the screen driven by this controller.
   * 
   * @param stage 
   *              the stage where towers have to be placed.
   * @return 
   *              a list of the deployed towers.
   */
  public final List<TowerActor> loadPlayerTowers(final Stage stage) {
    return this.loadTowerActorsFrom(((GameModel) super.getModel()).getPlayerActiveTowers(), stage, "SELF");
  }

  public void updatePlayerAttackableAnimations(final List<CardActor> playerCards , final List<TowerActor> playerTowers) { 
    for (final var cardActor : playerCards) {
      for (final var attackable : ((GameModel) super.getModel()).getPlayerAttackable()) {
        for (final var card : ((GameModel) super.getModel()).getPlayerDeck()) {
          if (cardActor.getSelfId().equals(attackable.getSelfId()) && cardActor.getSelfId().equals(card.getSelfId())) {
            cardActor.setAnimation(AnimationUtilities.loadAnimationFromFiles(card.getAnimationFiles().get(attackable.getCurrentTarget().isPresent() ? "SELF_FIGHTING" : "SELF_MOVING"), ANIMATIONS_FRAME_DURATION, false));
          }
        }
      }
    }
    for (final var towerActor : playerTowers) {
      for (final var attackable : ((GameModel) super.getModel()).getPlayerAttackable()) {
        for (final var card : ((GameModel) super.getModel()).getPlayerDeployedCards()) {
            if (towerActor.getSelfId().equals(attackable.getSelfId()) && towerActor.getSelfId().equals(card.getSelfId())) {
              towerActor.setAnimation(AnimationUtilities.loadAnimationFromFiles(card.getAnimationFiles().get(attackable.getCurrentTarget().isPresent() ? "SELF" : "ENEMY"), ANIMATIONS_FRAME_DURATION, false));
            }
        }
      }
    }
  }

  /**
   * Update both player and enemy actors, using cards informations stored inside the model.
   * 
   * @param playerCards
   *                  a list of CardActors owned by the player.
   * @param enemyCards
   *                  a list of CardActors owned by the enemy (whether is a bot or real player).
   */
  public abstract void updateActors(List<CardActor> playerCards, List<CardActor> enemyCards);

}
