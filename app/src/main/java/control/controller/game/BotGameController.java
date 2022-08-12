package control.controller.game;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.GlobalData;
import model.actors.Attackable;
import model.actors.users.Bot;
import model.utilities.ElixirController;
import model.utilities.ingame.BotGameModel;
import view.actors.CardActor;
import view.actors.TowerActor;

/**
 * 
 * Game controller implementation Simple Bot mode.
 */
public class BotGameController extends GameController {

  private final ElixirController botElixir;

  /**
   * Constructor.
   */
  public BotGameController() {
    super(new BotGameModel(GlobalData.USER_DECK, GlobalData.BOT_DECK, GlobalData.USER, GlobalData.BOT));
    this.botElixir = new ElixirController();
  }

  @Override
  protected void onUpdate() {
    this.botElixir.setRunFalse();
  }

  /**
   *@return the current elixir owned by the bot.
   */
  public int getBotCurrentElixir() {
    return this.botElixir.getElixirCount();
  }

  /**
   * 
   * @return a list of the bot attackable entities.
   */
  public List<Attackable> getBotAttackables() {
    return ((BotGameModel) super.getModel()).getBotAttackable();
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
    return super.loadActorsFrom(((BotGameModel) super.getModel()).getBotDeck(), stage, "SELF_MOVING");
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
    return super.loadTowersFrom(((BotGameModel) super.getModel()).getBotActiveTowers(), stage, "ENEMY");
  }

  private void updateAttackablePosition(final Attackable attackable, final List<Attackable> enemies) {
    final var playerAttackablePos = super.getGameMap().findEnemy(List.of(attackable), enemies);
    playerAttackablePos.forEach(a -> {
      if (a.getY().size() > 1) {
        attackable.setPosition(a.getY().get(1));
      }
    });
  }

  private void updateActorPosition(final List<CardActor> cards, final List<Attackable> selfAttackables, final List<Attackable> enemyAttackables) {
    cards.forEach(c -> {
      selfAttackables.forEach(a -> {
        if (!Gdx.input.isTouched() && c.getSelfId().equals(a.getSelfId()) && super.getGameMap().containsPosition(c.getPosition())) {
          System.out.println(c.getPosition() + " " + a.getPosition());
          if (c.isDraggable()) {
            c.setDraggable(false);
            a.setPosition(c.getPosition());
          } else if (c.getPosition().equals(a.getPosition())) {
            this.updateAttackablePosition(a, enemyAttackables);
            c.moveTo(a.getPosition()); 
            c.setRotation(a.getPosition());
          }
        }
      });
    });
  }
 
  @Override
  public void updateActorPositions(final List<CardActor> playerCards, final List<CardActor> botCards) {
    this.updateActorPosition(playerCards, this.getUserAttackables(), this.getBotAttackables());
    this.updateActorPosition(botCards, this.getBotAttackables(), this.getUserAttackables()); 
  }

}
