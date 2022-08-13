package control.controller.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import control.controller.Controller;
import control.controller.MenuController;
import model.actors.Attackable;
import model.actors.cards.Card;
import model.actors.towers.Tower;
import model.utilities.AnimationUtilities;
import model.utilities.Audio;
import model.utilities.CountDownController;
import model.utilities.ElixirController;
import model.utilities.ingame.GameModel;
import model.utilities.ingame.GameMap;
import view.actors.CardActor;
import view.actors.TowerActor;
import view.screens.GameScreen;

/**
 * Abstract Controller for the game screen.
 */
public abstract class GameController extends Controller {

  private static final float ANIMATIONS_FRAME_DURATION = (float) 0.017_24 * 10;

  private final ElixirController playerElixir;
  private final CountDownController timer;
  private final GameMap gameMap;
  private List<CardActor> playerCards;
  private List<TowerActor> playerTowers;

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
    this.playerCards = new ArrayList<>();
    this.playerTowers = new ArrayList<>();
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
   */
  public final void loadActors(final Stage stage) {
    this.playerCards = this.loadCardActorsFrom(((GameModel) super.getModel()).getPlayerDeck(), stage, "SELF_MOVING");
    this.onLoadActors(stage);
  }

  /**
   * Template method used to allow subclasses to load their actors.
   * 
   * @param stage
   *             where actors have to be placed.
   */
  protected abstract void onLoadActors(Stage stage);

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
   */
  public final void loadTowers(final Stage stage) {
    this.playerTowers = this.loadTowerActorsFrom(((GameModel) super.getModel()).getPlayerActiveTowers(), stage, "SELF");
    this.onLoadTowers(stage);
  }

  /**
   * Template method used to allow subclasses to load their actors.
   * 
   * @param stage
   *              where towers have to be laced.
   */
  protected abstract void onLoadTowers(Stage stage);

  /**
   * Update a user (whether is a bot or real player) card actor animations based on their status.
   * 
   * @param playerCards
   *                    a list of user cards.
   * @param playerAttackables
   *                    a list of user attackables.
   * @param moving 
   *                    the name of the files used for moving animations.
   * @param fighting
   *                    the name of the files used for fighting animations.
   */
  protected void updateCardAnimations(final List<CardActor> playerCards, final List<Attackable> playerAttackables, final String moving, final String fighting) { 
    for (final var cardActor : playerCards) {
      for (final var attackable : ((GameModel) super.getModel()).getPlayerAttackable()) {
        if (cardActor.getSelfId().equals(attackable.getSelfId()) && attackable instanceof Card) {
            cardActor.setAnimation(AnimationUtilities.loadAnimationFromFiles(((Card) attackable).getAnimationFiles().get(attackable.getCurrentTarget().isPresent() ? fighting : moving), ANIMATIONS_FRAME_DURATION, true));
        }
      }
    }
  }

  /**
   * Update a user (whether is a bot or real player) tower actor animations based on their status.
   * 
   * @param playerTowers
   *                    a list of user towers.
   * @param playerAttackables
   *                    a list of user attackables.
   * @param standing
   *                    the name of files used for standing animation.
   * @param fighting
   *                    the name of files used for fighting animation.
   */
  protected void updateTowerAnimations(final List<TowerActor> playerTowers, final List<Attackable> playerAttackables, final String standing, final String fighting) {
    for (final var towerActor : playerTowers) {
      for (final var attackable : ((GameModel) super.getModel()).getPlayerAttackable()) {
        if (towerActor.getSelfId().equals(attackable.getSelfId()) && attackable instanceof Tower) {
            towerActor.setAnimation(AnimationUtilities.loadAnimationFromFiles(((Tower) attackable).getAnimationFiles().get(attackable.getCurrentTarget().isPresent() ? "SELF" : "SELF"), ANIMATIONS_FRAME_DURATION, true));
        }
      }
    }
  }

  /**
   * Update both card and tower actors animations of the player.
   */
  public void updateActorAnimations() {
    this.updateCardAnimations(this.playerCards, this.getUserAttackables(), "SELF_MOVING", "SELF_FIGHTING");
    this.updateTowerAnimations(this.playerTowers, this.getUserAttackables(), "SELF", "SELF");
    this.onUpdateActorAnimations();
  }

  /**
   * Template method used to allow subclasses update their actor animations.
   */
  protected abstract void onUpdateActorAnimations();

  /**
   * Update both player and enemy actors, using cards informations stored inside the model.
   * 
   */
  public void updateActors() {
    ((GameModel) super.getModel()).findAttackableTargets();
    ((GameModel) super.getModel()).handleAttackTargets();
    this.onUpdateActors();
  }

  /**
   * Template method implemented by subclasses to update actor positions.
   */
  protected abstract void onUpdateActors();

  /**
   * @return a copy of player card actors.
   */
  protected List<CardActor> getPlayerActors() {
    return Collections.unmodifiableList(this.playerCards);
  }

  /**
   * @return a copy of player tower actors.
   */
  protected List<TowerActor> getPlayerTowers() {
    return Collections.unmodifiableList(this.playerTowers);
  }
}
