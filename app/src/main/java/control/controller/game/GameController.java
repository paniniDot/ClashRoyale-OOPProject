package control.controller.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import control.BaseGame;
import control.controller.Controller;
import control.controller.MenuController;
import model.actors.Attackable;
import model.actors.cards.Card;
import model.actors.cards.spells.Spell;
import model.actors.towers.Tower;
import model.utilities.AnimationUtilities;
import model.utilities.Audio;
import model.utilities.CountDownController;
import model.utilities.ElixirController;
import model.utilities.ScoreController;
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
  private final ScoreController playerScore;
  private Map<CardActor, Card> playerCardsMap;
  private Map<TowerActor, Tower> playerTowersMap;
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
    this.playerScore = new ScoreController();
    this.playerCardsMap = new HashMap<>();
    this.playerTowersMap = new HashMap<>();
    this.gameMap = new GameMap();
    super.registerModel(model);
  }

  /**
   * 
   * @return the current game map.
   */
  protected GameMap getGameMap() {
    return this.gameMap;
  }

  @Override
  public void update(final float dt) {
    if (this.timer.getTime() == 0 || this.checkUserLose() || this.checkEnemyLose()) {
      this.playerElixir.setRunFalse();
      this.onUpdate();
      this.timer.setRunFalse();
      super.stopMusic();
      new MenuController().setCurrentActiveScreen();
    }
    this.updateActors();
    this.updateActorAnimations();
  } 

  private boolean checkUserLose() {
    return ((GameModel) super.getModel()).getPlayerActiveTowers().size() == 0;
  }

  /**
   * 
   * @return if the enemy, whether is a bot or a real player, lost the match.
   */
  protected abstract boolean checkEnemyLose();

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
  protected final Map<CardActor, Card> loadCardActorsFrom(final List<Card> list, final Stage stage, final String animationName) {
    final var actors = new HashMap<CardActor, Card>();
    list.forEach(c -> {
      final var actor = new CardActor(c.getSelfId(), c.getPosition().x, c.getPosition().y, stage, AnimationUtilities.loadAnimationFromFiles(c.getAnimationFiles().get(animationName), ANIMATIONS_FRAME_DURATION, true));
      actors.put(actor, c);
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
    this.playerCardsMap = this.loadCardActorsFrom(((GameModel) super.getModel()).getPlayerChoosableCards(), stage, "SELF_MOVING");
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
  protected final Map<TowerActor, Tower> loadTowerActorsFrom(final List<Tower> list, final Stage stage, final String animationName) {
    final var towers = new HashMap<TowerActor, Tower>();
    list.forEach(t -> {
      final var actor = new TowerActor(t.getSelfId(), t.getPosition().x, t.getPosition().y, stage, AnimationUtilities.loadAnimationFromFiles(t.getAnimationFiles().get(animationName), ANIMATIONS_FRAME_DURATION, true));
      actor.setPosition(actor.getPosition().x, actor.getPosition().y);
      towers.put(actor, t);
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
    this.playerTowersMap = this.loadTowerActorsFrom(((GameModel) super.getModel()).getPlayerActiveTowers(), stage, "SELF");
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
   * @param playerCardsMap
   *                    a map that associate each card actor to its own card.
   * @param moving 
   *                    the name of the files used for moving animations.
   * @param fighting
   *                    the name of the files used for fighting animations.
   */
  protected void updateCardAnimations(final Map<CardActor, Card> playerCardsMap, final String moving, final String fighting) { 
    playerCardsMap.entrySet()
      .stream()
      .filter(e -> !e.getValue().getClass().equals(Spell.class))
      .forEach(e -> e.getKey().setAnimation(AnimationUtilities.loadAnimationFromFiles(e.getValue().getAnimationFiles().get(((Attackable) e.getValue()).getCurrentTarget().isPresent() ? fighting : moving), ANIMATIONS_FRAME_DURATION, true)));

  }

  @Override
  public void setCurrentActiveScreen() {
    BaseGame.setActiveScreen(new GameScreen(this));
  }

  /**
   * Update a user (whether is a bot or real player) tower actor animations based on their status.
   * 
   * @param playerTowersMap
   *                    a map that associate each tower actor to its own tower.
   * @param standing
   *                    the name of files used for standing animation.
   * @param fighting
   *                    the name of files used for fighting animation.
   */
  protected void updateTowerAnimations(final Map<TowerActor, Tower> playerTowersMap, final String standing, final String fighting) {
    playerTowersMap.entrySet()
    .stream()
    .forEach(e -> e.getKey().setAnimation(AnimationUtilities.loadAnimationFromFiles(e.getValue().getAnimationFiles().get(e.getValue().getCurrentTarget().isPresent() ? standing : fighting), ANIMATIONS_FRAME_DURATION, true)));
  }

  /**
   * Update both card and tower actors animations of the player.
   */
  public void updateActorAnimations() {
    this.updateCardAnimations(this.playerCardsMap, "SELF_MOVING", "SELF_FIGHTING");
    this.updateTowerAnimations(this.playerTowersMap, "SELF", "SELF");
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
   * Perform an update of both model and elixir controller after a card has been deployed.
   * @param card
   */
  protected void deployPlayerCard(final Card card) {
    ((GameModel) super.getModel()).deployPlayerCard(card);
    this.playerElixir.decrementElixir(card.getCost());
  }

  /**
   * Template method implemented by subclasses to update actor positions.
   */
  protected abstract void onUpdateActors();

  /**
   * @return a copy of player card actors.
   */
  protected Map<CardActor, Card> getPlayerActorsMap() {
    return this.playerCardsMap;
  }

  /**
   * @return a copy of player tower actors.
   */
  protected Map<TowerActor, Tower> getPlayerTowersMap() {
    return this.playerTowersMap;
  }

  /**
   * 
   * @return the current towers destroyed by the user.
   */
  public int getPlayerScore() {
    return this.playerScore.getScore();
  }

  /**
   * 
   * @return the current elixir left to the player.
   */
  protected ElixirController getPlayerElixirController() {
    return this.playerElixir;
  }

  protected void updateCardsMap(List<CardActor> elements) {
    elements.stream()
      .peek(Actor::remove)
      .forEach(e -> this.playerCardsMap.remove(e));
    System.out.println(playerCardsMap);
  }
}

