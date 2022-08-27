package control.controller.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.GlobalData;
import model.actors.Attackable;
import model.actors.cards.Card;
import model.actors.towers.Tower;
import model.actors.users.Bot;
import model.utilities.ingame.BotGameModel;
import view.actors.CardActor;
import view.actors.TowerActor;

/**
 * 
 * Game controller implementation Simple Bot mode.
 */
public class BotGameController extends GameController {

  private final ElixirController botElixir;
  private Map<CardActor, Card> botCardsMap;
  private Map<TowerActor, Tower> botTowersMap;

  /**
   * Constructor.
   */
  public BotGameController() { 
    super(new BotGameModel(GlobalData.DECK.cardList(), GlobalData.BOT_DECK, GlobalData.USER, GlobalData.BOT));
    this.botElixir = new ElixirController();
    this.botCardsMap = new HashMap<>();
    this.botTowersMap = new HashMap<>();
  }

  @Override 
  protected boolean checkEnemyLose() {
    return ((BotGameModel) super.getModel()).getBotActiveTowers().size() == 0;
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
    this.botCardsMap = super.loadCardActorsFrom(((BotGameModel) super.getModel()).getBotDeck(), stage, "ENEMY_MOVING");
  }

  @Override
  protected void onLoadTowers(final Stage stage) {
    this.botTowersMap = super.loadTowerActorsFrom(((BotGameModel) super.getModel()).getBotActiveTowers(), stage, "ENEMY");
  }

  @Override
  protected void onUpdateActorAnimations() {
    super.updateCardAnimations(this.botCardsMap, "ENEMY_MOVING", "ENEMY_FIGHTING");
    super.updateTowerAnimations(this.botTowersMap, "ENEMY");
  }

  private void updateAttackablePosition(final Attackable attackable, final List<Attackable> enemies) {
    attackable.setPosition(this.getGameMap().getNextPosition(attackable, enemies));
  }

  private void updateActorPositions(final Map<CardActor, Card> cardActors, final List<Attackable> enemyAttackables) {
    final var card = new ArrayList<Card>();
    cardActors.entrySet().stream()
      .filter(e -> ((Attackable) e.getValue()).getCurrentTarget().isEmpty())
      .forEach(e -> {
        if (!Gdx.input.isTouched()) {
          if (super.getGameMap().containsPosition(e.getKey().getCenter())) {
            if (e.getKey().isDraggable()) { //Carta non schierata
              if (e.getValue().getOwner() instanceof Bot && e.getValue().getCost() <= botElixir.getElixirCount()) {
                this.deployBotCard(e.getValue());
                e.getKey().setDraggable(false);
                e.getValue().setPosition(e.getKey().getCenter());
              } else if (e.getValue().getCost() <= super.getPlayerCurrentElixir()) {
                card.add(e.getValue());
                super.deployPlayerCard(e.getValue());
                e.getKey().setDraggable(false);
                e.getValue().setPosition(e.getKey().getCenter());
              } else {
                e.getKey().setPosition(e.getKey().getOrigin().x, e.getKey().getOrigin().y);
              }
            } else if (this.castedToIntPosition(e.getKey().getCenter()).equals(this.castedToIntPosition(e.getValue().getPosition()))) {
              this.updateAttackablePosition((Attackable) e.getValue(), enemyAttackables);
              e.getKey().setRotation(e.getValue().getPosition());
              e.getKey().moveTo(e.getValue().getPosition());
            }
          } else {
            e.getKey().setPosition(e.getKey().getOrigin().x, e.getKey().getOrigin().y);
          }
        } 
      });
    if (!card.isEmpty()) {
    card.forEach(e ->  super.deployPlayerActor(e));
    }
    final var actor = new ArrayList<CardActor>();
    cardActors.entrySet().stream()
      .filter(e -> ((Attackable) e.getValue()).getCurrentTarget().isPresent())
      .forEach(e -> {
        e.getKey().setRotation(((Attackable) e.getValue()).getCurrentTarget().get().getPosition());
      });
    cardActors.entrySet().stream()
    .forEach(e -> {
      if (((Attackable) e.getValue()).isDead()) {
        actor.add(e.getKey());
      }
    });
    if (!actor.isEmpty()) {
    super.updateCardsMap(actor);
    }
  }
  private void placeBotActor() {
    Vector2 v = new Vector2(350, 550);
    this.botCardsMap.entrySet().stream().forEach(e -> {
      if (super.getGameMap().containsPosition(v) && e.getKey().isDraggable() && e.getValue().getOwner() instanceof Bot
          && e.getValue().getCost() <= botElixir.getElixirCount()) {
        this.deployBotCard(e.getValue());
        e.getKey().setDraggable(false);
        e.getValue().setPosition(v);
        e.getKey().setPosition(v.x, v.y);
      }
    });
  }

  private void deployBotCard(final Card card) {
    ((BotGameModel) super.getModel()).deployBotCard(card);
    this.botElixir.decrementElixir(card.getCost());
  }

  private Vector2 castedToIntPosition(final Vector2 pos) {
    return new Vector2((int) pos.x, (int) pos.y);
  }

  @Override
  protected void onUpdateActors() {
    this.placeBotActor();
    this.updateActorPositions(super.getPlayerActorsMap(), this.getBotAttackables());
    this.updateActorPositions(this.botCardsMap, super.getUserAttackables());
  }

  /**
   * 
   * @return the current towers destroyed by the user.
   */
  public long getPlayerScore() {
    return botTowersMap.entrySet().stream().filter(e -> e.getValue().isDead()).count();
  }
}
