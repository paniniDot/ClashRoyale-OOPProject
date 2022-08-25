package control.controller.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.GlobalData;
import model.actors.Attackable;
import model.actors.cards.Card;
import model.actors.cards.buildings.InfernoTower;
import model.actors.cards.spells.FireBall;
import model.actors.cards.spells.Spell;
import model.actors.users.Bot;
import model.utilities.ElixirController;
import model.utilities.VectorsUtilities;
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

  /**
   * Constructor.
   */
  public BotGameController() { 
    super(new BotGameModel(GlobalData.USER_DECK, GlobalData.BOT_DECK, GlobalData.USER, GlobalData.BOT));
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
            if (c.isDraggable()) { //Carta non schierata
              if (a.getOwner() instanceof Bot && getBotElixirController().getElixirCount() > ((Card) a).getCost()) {
                ((BotGameModel) super.getModel()).deployBotCard((Card) a);
                getBotElixirController().decrementElixir(((Card) a).getCost());
                c.setDraggable(false);
                a.setPosition(c.getCenter());
              } else if (getPlayerElixirController().getElixirCount() > ((Card) a).getCost()) {
                ((BotGameModel) super.getModel()).deployPlayerCard((Card) a);
                getPlayerElixirController().decrementElixir(((Card) a).getCost());
                c.setDraggable(false);
                a.setPosition(c.getCenter());
              } else {
                c.setPosition(c.getOrigin().x, c.getOrigin().y);
              }
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

  private boolean isInRange(final Spell card, final Attackable attackable) {
    return VectorsUtilities.euclideanDistance(card.getPosition(), attackable.getPosition()) <= card.getRange();
  }

  private boolean isNotBuilding(final CardActor c) {
    return !getActorMap().get(c).getClass().equals(InfernoTower.class);
  }
 
  private boolean isUserTheOwner(final Card card) {
    return card.getOwner().equals(GlobalData.USER);
>>>>>>> 008b4756572ceb49742a01e5d449419cb8191fd9
  }

  private ElixirController getBotElixirController() {
    return this.botElixir;
  }

  private Vector2 castedToIntPosition(final Vector2 pos) {
    return new Vector2((int) pos.x, (int) pos.y);
  }

  @Override
  protected void onUpdateActors() {
    this.updateActorPositions(getPlayerActors(), super.getUserAttackables(), this.getBotAttackables());
    this.updateActorPositions(this.botCards, this.getBotAttackables(), super.getUserAttackables());
  }

}
