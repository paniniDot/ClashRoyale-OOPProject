package control.controller.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.GlobalData;
import model.actors.Attackable;
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
  private List<CardActor> botCards;
  private List<TowerActor> botTowers;
  private static final BotGameModel BOT_GAME_MODEL = new BotGameModel(GlobalData.USER_DECK, GlobalData.BOT_DECK, GlobalData.USER, GlobalData.BOT);;

  /**
   * Constructor.
   */
  public BotGameController() { 
    super(BOT_GAME_MODEL);
    this.botElixir = new ElixirController();
    this.botCards = new ArrayList<>();
    this.botTowers = new ArrayList<>();
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

  @Override
  protected void onLoadActors(final Stage stage) {
    this.botCards = super.loadCardActorsFrom(((BotGameModel) super.getModel()).getBotDeck(), stage, "ENEMY_MOVING");
  }

  @Override
  protected void onLoadTowers(final Stage stage) {
    this.botTowers = super.loadTowerActorsFrom(((BotGameModel) super.getModel()).getBotActiveTowers(), stage, "ENEMY");
  }

  @Override
  protected void onUpdateActorAnimations() {
    super.updateCardAnimations(this.botCards, this.getBotAttackables(), "ENEMY_MOVING", "ENEMY_FIGHTING");
    super.updateTowerAnimations(this.botTowers, this.getBotAttackables(), "ENEMY", "ENEMY");
  }

  private void updateAttackablePosition(final Attackable attackable, final List<Attackable> enemies) {
    attackable.setPosition(this.getGameMap().getNextPosition(attackable, enemies));
  }

  private void updateActorPositions(final List<CardActor> cards, final List<Attackable> selfAttackables, final List<Attackable> enemyAttackables) {
    cards.forEach(c -> {
      selfAttackables.stream().filter(a -> a.getCurrentTarget().isEmpty()).forEach(a -> {
        if (!Gdx.input.isTouched() && c.getSelfId().equals(a.getSelfId())) {
          if (super.getGameMap().containsPosition(c.getCenter())) {
            if (c.isDraggable()) {
              c.setDraggable(false);
              a.setPosition(c.getCenter());
            } else if (this.castedToIntPosition(c.getCenter()).equals(this.castedToIntPosition(a.getPosition()))) {
              this.updateAttackablePosition(a, enemyAttackables);
              c.setRotation(a.getPosition());
              c.moveTo(a.getPosition());
            } 
          } else {
            c.setPosition(c.getOrigin().x, c.getOrigin().y);
          }
        }
      });
      selfAttackables.stream().filter(a -> a.getCurrentTarget().isPresent()).forEach(a -> {
        c.setRotation(a.getCurrentTarget().get().getPosition());
      });
    });
  }

  private Vector2 castedToIntPosition(final Vector2 pos) {
    return new Vector2((int) pos.x, (int) pos.y);
  }

  @Override
  protected void onUpdateActors() {
    this.updateActorPositions(super.getPlayerActors(), super.getUserAttackables(), this.getBotAttackables());
    this.updateActorPositions(this.botCards, this.getBotAttackables(), super.getUserAttackables());
  }

}
