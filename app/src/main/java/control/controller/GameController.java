package control.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

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
import model.utilities.Pair;
import model.utilities.ingame.BotGameLogic;
import model.utilities.ingame.GameMap;
import view.actors.CardActor;
import view.actors.TowerActor;
import view.screens.GameScreen;

/**
 * Controller implementation for the game screen.
 */
public class GameController extends Controller {

  private static final float ANIMATIONS_FRAME_DURATION = (float) 0.017_24 * 10;

  private final ElixirController elixir;
  private final CountDownController count;
  private final User user;
  private final Bot bot;
  private final GameMap gameMap;
  private final BotGameLogic logic;

  /**
   * Constructor.
   */
  public GameController() {
    super(Audio.getBattleMusic());
    this.elixir = new ElixirController();
    this.count = new CountDownController();
    this.user = new User("panini");
    this.bot = new Bot();
    this.gameMap = new GameMap();
    this.logic = new BotGameLogic(
        List.of(Wizard.create(this.user, new Vector2(100, 100)),
            Wizard.create(this.user, new Vector2(300, 100)),
            Wizard.create(this.user, new Vector2(200, 100)),
            Wizard.create(this.user, new Vector2(400, 100)),
            Wizard.create(this.user, new Vector2(500, 100)),
            Wizard.create(this.user, new Vector2(600, 100))),
        List.of(Wizard.create(this.bot, new Vector2(100, 800)),
            Wizard.create(this.bot, new Vector2(300, 800)),
            Wizard.create(this.bot, new Vector2(200, 800)),
            Wizard.create(this.bot, new Vector2(400, 800)),
            Wizard.create(this.bot, new Vector2(500, 800))),
        this.user, this.bot);
    super.registerScreen(new GameScreen(this));
    super.registerModel(new Model());
    Gdx.input.setInputProcessor(super.getScreen().getMainStage());
  }

  @Override
  public void update(final float dt) {
    if (this.count.getTime() == 0) {
      this.elixir.setRunFalse();
      this.count.setRunFalse();
      super.stopMusic();
      new MenuController().setCurrentActiveScreen();
    }
    //this.updateAttackablePositions();
    //this.updateActorPositions();
  }
  /**
   *@return the remaining seconds before game ends.
   */
  public int getLeftTime() {
    return this.count.getTime();
  }
  /**
   *@return the current elixir owned.
   */
  public int getCurrentElixir() {
    return this.elixir.getElixirCount();
  }

  /**
   * 
   * @return a list of the user attackable entities.
   */
  public List<Attackable> getUserAttackables() {
    return this.logic.getPlayerAttackable();
  }

  /**
   * 
   * @return a list of bot attackable entities.
   */
  public List<Attackable> getBotAttackables() {
    return this.logic.getBotAttackable();
  }

  private List<CardActor> loadActorsFrom(final List<Card> list, final Stage stage, final String animationName) {
    final var actors = new ArrayList<CardActor>();
    list.forEach(c -> {
      final var actor = new CardActor(c.getSelfId(), c.getPosition().x, c.getPosition().y, stage);
      actor.setAnimation(AnimationUtilities.loadAnimationFromFiles(c.getAnimationFiles().get(animationName), ANIMATIONS_FRAME_DURATION, true));
      actors.add(actor);
    });
    return actors;
  }

  /**
   * Load card actors in the main stage of the screen driven by this controller.
   * 
   * @param stage 
   *              the stage where actors have to be placed.
   *
   * @return a list of CardActors owned by the user.
   */
  public final List<CardActor> loadPlayerActors(final Stage stage) {
    return this.loadActorsFrom(this.logic.getPlayerDeck(), stage, "SELF_MOVING");
  }

  /**
   * Load card actors in the main stage of the screen driven by this controller.
   * 
   * @param stage 
   *              the stage where actors have to be placed.
   *
   * @return a list of CardActors owned by the user.
   */
  public final List<CardActor> loadBotActors(final Stage stage) {
    return this.loadActorsFrom(this.logic.getBotDeck(), stage, "SELF_MOVING");
  }

  private List<TowerActor> loadTowersFrom(final List<Tower> list, final Stage stage, final String animationName) {
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
   *
   * @return a list of the deployed towers.
   */
  public final List<TowerActor> loadPlayerTowers(final Stage stage) {
    return this.loadTowersFrom(this.logic.getPlayerActiveTowers(), stage, "SELF");
  }

  /**
   * Load tower actors in the main stage of the screen driven by this controller.
   * 
   * @param stage 
   *              the stage where towers have to be placed.
   *
   * @return a list of the deployed towers.
   */
  public final List<TowerActor> loadBotTowers(final Stage stage) {
    return this.loadTowersFrom(this.logic.getBotActiveTowers(), stage, "ENEMY");
  }

  private void updateAttackablePositions() {
    final var playerAttackablesPos = this.gameMap.findEnemy(this.getUserAttackables(), this.getBotAttackables());
    final var botAttackablesPos = this.gameMap.findEnemy(this.getBotAttackables(), this.getUserAttackables());
    this.logic.getPlayerAttackable().forEach(p -> playerAttackablesPos.forEach(a -> {
      if (p.equals(a.getX().getX())) {
        p.setPosition(a.getY().get(1));
      }
    }));
    this.logic.getBotAttackable().forEach(p -> botAttackablesPos.forEach(a -> {
      if (p.equals(a.getX().getX())) {
        p.setPosition(a.getY().get(1));
      }
    }));
  }

  private void updateActorPositions(final List<CardActor> playerCards, final List<CardActor> botCards) {
    playerCards.forEach(actor -> {
      this.getUserAttackables().forEach(attackable -> {
        if (actor.getSelfId().equals(attackable.getSelfId())) {
          if (actor.isDraggable()) {
            actor.setDraggable(false);
            attackable.setPosition(actor.getPosition());
          } else {
            actor.moveTo(attackable.getPosition());
          }
        }
      });
    });
  }
}
