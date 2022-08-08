package control.controller;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import model.Model;
import model.actors.Attackable;
import model.actors.cards.Card;
import model.actors.cards.troops.Wizard;
import model.actors.users.Bot;
import model.actors.users.User;
import model.utilities.Audio;
import model.utilities.CountDownController;
import model.utilities.ElixirController;
import model.utilities.ingame.BotGameLogic;
import view.actors.CardActor;
import view.screens.GameScreen;

/**
 * Controller implementation for the game screen.
 */
public class GameController extends Controller {
  private final ElixirController elixir;
  private final CountDownController count;
  private final User user;
  private final Bot bot;
  private final BotGameLogic logic;
  /**
   * Constructor.
   */
  public GameController() {
    super(Audio.getBattleMusic());
    super.registerScreen(new GameScreen(this));
    super.registerModel(new Model());
    this.elixir = new ElixirController();
    this.count = new CountDownController();
    this.user = new User("panini");
    this.bot = new Bot();
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
   * Load actors in the main stage of the screen drived by this controller.
   */
  public void loadActors() {
    this.logic.getPlayerAttackable().forEach(a -> {
      final var actor = new CardActor(a.getSelfId(), a.getPosition().x, a.getPosition().y, super.getScreen().getMainStage());
      super.getScreen().getMainStage().addActor(actor); 
    });
  }
}
