package controller.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import controller.Controller;
import controller.CountDownController;
import controller.ElixirController;
import controller.FileManager;
import controller.menu.MenuController;
import launcher.ClashRoyale;
import model.entities.Attackable;
import model.entities.cards.Card;
import model.entities.towers.Tower;
import model.utilities.AnimationUtilities;
import model.utilities.ingame.GameModel;
import model.utilities.ingame.GameMap;
import view.actors.cards.CardActor;
import view.actors.towers.TowerActor;
import view.screens.game.GameScreen;

/**
 * Abstract Controller for the game screen.
 */
public abstract class GameController extends Controller {

  /**
   * How much each frame has to last during the animation.
   */
  protected static final float ANIMATIONS_FRAME_DURATION = (float) 0.017_24 * 10;

  private final ElixirController playerElixir;
  private final CountDownController timer;
  private Map<CardActor, Card> playerCardsMap;
  private Map<TowerActor, Tower> playerTowersMap;
  private final GameMap gameMap;

  /**
   * Constructor.
   * 
   * @param model the logic followed by this controller.
   */
  public GameController(final GameModel model) {
    super(new AudioGameController());
    this.playerElixir = new ElixirController();
    this.timer = new CountDownController();
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
      this.updateStats();
      this.onUpdate();
      this.timer.setRunFalse();
      super.stopMusic();
      new MenuController().setCurrentActiveScreen();
    }
    this.updateActors();
    this.updateActorAnimations();
  }

  private void updateStats() {
    final var fileManager = new FileManager();
    fileManager.addPlays();
    if (this.checkEnemyLose()) {
      fileManager.addWin();
    }
    fileManager.addTowersDestroyed(getDestoryedTowers());
    fileManager.save();
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
   * 
   * @return the number of destryedTowers.
   */
  protected abstract int getDestoryedTowers();

  /**
   * Called from subclasses to extend functionalities when the match is over.
   */
  protected abstract void onUpdate();

  /**
   * 
   * @return the remaining seconds before game ends.
   */
  public int getLeftTime() {
    return this.timer.getTime();
  }

  /**
   * 
   * @return the current elixir owned by the user.
   */
  public int getPlayerCurrentElixir() {
    return this.playerElixir.getElixirCount();
  }

  /**
   * 
   * @return a list of the user attackable entities.
   */
  protected List<Attackable> getUserAttackables() {
    return ((GameModel) super.getModel()).getPlayerAttackable();
  }

  /**
   * Load new card actors in the stage passed as argument, picking them
   * informations from the list passed as argument.
   * 
   * @param list          the list of Cards used to create new actors.
   * @param stage         where actors have to be placed.
   * @param animationName the animation of the actors.
   * @return a list of CardActors.
   */
  protected final Map<CardActor, Card> loadCardActorsFrom(final List<Card> list, final Stage stage, final String animationName) {
    final var actors = new HashMap<CardActor, Card>();
    list.forEach(c -> {
      final var actor = new CardActor(c.getPosition().x, c.getPosition().y, stage, AnimationUtilities.loadAnimationFromFiles(c.getAnimationFiles().get(animationName), ANIMATIONS_FRAME_DURATION, true));
      actors.put(actor, c);
    });
    return actors;
  }

  /**
   * Load card actors in a stage of the screen driven by this controller.
   * 
   * @param stage the stage where actors have to be placed.
   */
  public final void loadActors(final Stage stage) {
    this.playerCardsMap = this.loadCardActorsFrom(((GameModel) super.getModel()).getPlayerChoosableCards(), stage, "AS_CARD");
    this.onLoadActors(stage);
  }

  /**
   * Template method used to allow subclasses to load their actors.
   * 
   * @param stage where actors have to be placed.
   */
  protected abstract void onLoadActors(Stage stage);

  /**
   * Load new tower actors in the stage passed as argument, picking them
   * informations from the list passed as argument.
   * 
   * @param list          the list of Towers used to create new actors.
   * @param stage         where actors have to be placed.
   * @param animationName the animation of the actors.
   * @return a list of new Tower Actors.
   */
  protected final Map<TowerActor, Tower> loadTowerActorsFrom(final List<Tower> list, final Stage stage, final String animationName) {
    final var towers = new HashMap<TowerActor, Tower>();
    list.forEach(t -> {
      final var actor = new TowerActor(t.getPosition().x, t.getPosition().y, stage, AnimationUtilities.loadAnimationFromFiles(t.getAnimationFiles().get(animationName), ANIMATIONS_FRAME_DURATION, true));
      actor.setPosition(actor.getPosition().x, actor.getPosition().y);
      towers.put(actor, t);
    });
    return towers;
  }

  /**
   * Load tower actors in the main stage of the screen driven by this controller.
   * 
   * @param stage the stage where towers have to be placed.
   */
  public final void loadTowers(final Stage stage) {
    this.playerTowersMap = this.loadTowerActorsFrom(((GameModel) super.getModel()).getPlayerActiveTowers(), stage, "SELF");
    this.onLoadTowers(stage);
  }

  /**
   * Template method used to allow subclasses to load their actors.
   * 
   * @param stage where towers have to be laced.
   */
  protected abstract void onLoadTowers(Stage stage);

  /**
   * Update a user (whether is a bot or real player) card actor animations based
   * on their status.
   * 
   * @param playerCardsMap a map that associate each card actor to its own card.
   * @param moving         the name of the files used for moving animations.
   * @param fighting       the name of the files used for fighting animations.
   */
  protected void updateCardAnimations(final Map<CardActor, Card> playerCardsMap, final String moving, final String fighting) {
    playerCardsMap.entrySet().stream().forEach(e -> {
      if (this.getGameMap().containsPosition(e.getKey().getCenter()) && !e.getKey().isDraggable()) {
        e.getKey().setAnimation(AnimationUtilities.loadAnimationFromFiles(e.getValue().getAnimationFiles().get(((Attackable) e.getValue()).getCurrentTarget().isPresent() ? fighting : moving), ANIMATIONS_FRAME_DURATION, true));
      }
    });

  }

  @Override
  public void setCurrentActiveScreen() {
    ClashRoyale.setActiveScreen(new GameScreen(this));
  }

  /**
   * Update a user (whether is a bot or real player) tower actor animations based
   * on their status.
   * 
   * @param playerTowersMap a map that associate each tower actor to its own
   *                        tower.
   * @param standing        the name of files used for standing animation.
   */
  protected void updateTowerAnimations(final Map<TowerActor, Tower> playerTowersMap, final String standing) {
    playerTowersMap.entrySet().stream().forEach(e -> {
      if (((Attackable) e.getValue()).isDead()) {
        e.getKey().setAngle(0);
        e.getKey().setAnimation(AnimationUtilities.loadAnimationFromFiles(e.getValue().getAnimationFiles().get("DESTROYED"), ANIMATIONS_FRAME_DURATION, true));
      } else if (((Attackable) e.getValue()).getCurrentTarget().isPresent()) {
        e.getKey().setRotation(e.getValue().getCurrentTarget().get().getPosition());
        e.getKey().setAnimation(AnimationUtilities.loadAnimationFromFiles(e.getValue().getAnimationFiles().get("FIGHTING"), ANIMATIONS_FRAME_DURATION, true));
      } else {
        e.getKey().setAngle(0);
        e.getKey().setAnimation(AnimationUtilities.loadAnimationFromFiles(e.getValue().getAnimationFiles().get(standing), ANIMATIONS_FRAME_DURATION, true));
      }
    });
  }

  private void updateActorAnimations() {
    this.updateCardAnimations(this.playerCardsMap, "SELF_MOVING", "SELF_FIGHTING");
    this.updateTowerAnimations(this.playerTowersMap, "SELF");
    this.onUpdateActorAnimations();
  }

  /**
   * Template method used to allow subclasses update their actor animations.
   */
  protected abstract void onUpdateActorAnimations();

  private void updateActors() {
    ((GameModel) super.getModel()).findAttackableTargets();
    ((GameModel) super.getModel()).handleAttackTargets();
    this.onUpdateActors();
  }

  /**
   * Perform an update of both model and elixir controller after a card has been
   * deployed.
   * 
   * @param card
   */
  protected void deployPlayerCard(final Card card) {
    ((GameModel) super.getModel()).deployPlayerCard(card);
    this.playerElixir.decrementElixir(card.getCost());
  }

  /**
   * add bot troops in map.
   * 
   * @param elements list of card.
   */
  protected void deployPlayerActor(final List<Card> elements) {
    elements.stream().forEach(card -> {
      CardActor c = null;
      for (final Entry<CardActor, Card> entry : this.playerCardsMap.entrySet()) {
        if (entry.getValue().equals(card)) {
          c = entry.getKey();
          this.deployPlayerCard(card);
        }
      }
      if (c != null) {
      final var nextCard = ((GameModel) super.getModel()).getPlayerNextQueuedCard(c.getOrigin());
      if (nextCard.isPresent()) {
        this.playerCardsMap.put(
            new CardActor(c.getOrigin().x, c.getOrigin().y, c.getStage(), AnimationUtilities.loadAnimationFromFiles(nextCard.get().getAnimationFiles().get("AS_CARD"), ANIMATIONS_FRAME_DURATION, true)),
            nextCard.get());

      }
      }
    });
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
  public long getBotScore() {
    return playerTowersMap.entrySet().stream().filter(s -> s.getValue().isDead()).count();
  }

  /**
   * 
   * update Cards Map.
   * 
   * @param elements list of card.
   */
  protected void updateCardsMap(final List<CardActor> elements) {
    elements.stream().peek(Actor::remove).forEach(c -> this.playerCardsMap.remove(c));
  }
}

