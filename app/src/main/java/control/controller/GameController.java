package control.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import model.Model;
import model.actors.Attackable;
import model.actors.cards.Card;
import model.actors.cards.troops.Wizard;
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
  private final List<CardActor> playerCardActors;
  private final List<CardActor> botCardActors;
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
    this.playerCardActors = new ArrayList<>();
    this.botCardActors = new ArrayList<>();
    this.gameMap = new GameMap();
    this.logic = new BotGameLogic(
        List.of(Wizard.create(this.user, new Vector2(100, 100)),
            Wizard.create(this.user, new Vector2(300, 100)),
            Wizard.create(this.user, new Vector2(200, 100)),
            Wizard.create(this.user, new Vector2(400, 100))), 
        List.of(Wizard.create(this.bot, new Vector2(100, 800)),
            Wizard.create(this.bot, new Vector2(300, 800)),
            Wizard.create(this.bot, new Vector2(200, 800)),
            Wizard.create(this.bot, new Vector2(400, 800))), 
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
    this.updateAttackablePositions();
    this.updateActorPositions();
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

  /**
   * Load card actors in the main stage of the screen driven by this controller.
   */
  public void loadActors() {
    this.logic.getPlayerDeck().forEach(c -> {
      final var actor = new CardActor(c.getSelfId(), c.getPosition().x, c.getPosition().y, super.getScreen().getMainStage());
      actor.setAnimation(AnimationUtilities.loadAnimationFromFiles(c.getAnimationFiles().get("SELF_MOVING"), ANIMATIONS_FRAME_DURATION, true));
      this.playerCardActors.add(actor);
    });

    this.logic.getBotDeck().forEach(c -> {
      final var actor = new CardActor(c.getSelfId(), c.getPosition().x, c.getPosition().y, super.getScreen().getMainStage());
      actor.setAnimation(AnimationUtilities.loadAnimationFromFiles(c.getAnimationFiles().get("ENEMY_MOVING"), ANIMATIONS_FRAME_DURATION, true));
      this.botCardActors.add(actor);
    });
  }

  /**
   * Load tower actors in the main stage of the screen driven by this controller.
   */
  public void loadTowers() {
    this.logic.getPlayerActiveTowers().forEach(t -> {
      final var actor = new TowerActor(t.getSelfId(), t.getPosition().x, t.getPosition().y, super.getScreen().getMainStage());
      actor.setAnimation(AnimationUtilities.loadAnimationFromFiles(t.getAnimationFiles().get("SELF"), ANIMATIONS_FRAME_DURATION, true));
    });
    this.logic.getBotActiveTowers().forEach(t -> {
      final var actor = new TowerActor(t.getSelfId(), t.getPosition().x, t.getPosition().y, super.getScreen().getMainStage());
      actor.setAnimation(AnimationUtilities.loadAnimationFromFiles(t.getAnimationFiles().get("ENEMY"), ANIMATIONS_FRAME_DURATION, true));
    });
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

  private void updateActorPositions() {
    this.playerCardActors.forEach(actor -> {
      this.getUserAttackables().forEach(attackable -> {
        if (actor.getSelfId().equals(attackable.getSelfId())) {
          actor.moveTo(attackable.getPosition());
        }
      });
    });
  }
}
