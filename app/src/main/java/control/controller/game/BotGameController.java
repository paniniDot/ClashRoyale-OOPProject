package control.controller.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.GlobalData;
import model.actors.Attackable;
import model.actors.cards.Card;
import model.actors.towers.Tower;
import model.actors.users.Bot;
import model.actors.users.User;
import model.deck.PlayersDeck;
import model.utilities.AnimationUtilities;
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
    super(new BotGameModel(PlayersDeck.getInstance().cardList(), GlobalData.BOT_DECK, GlobalData.USER, GlobalData.BOT));
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
   * @return the current elixir owned by the bot.
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
    final var actor = new ArrayList<CardActor>();
    cardActors.entrySet().stream().forEach(e -> {
      if (e.getKey().isDraggable() && !Gdx.input.isTouched()) {
        if (e.getValue().getOwner() instanceof User && e.getValue().getCost() <= super.getPlayerCurrentElixir() && super.getGameMap().containsPosition(e.getKey().getCenter())) {
          card.add(e.getValue());
          super.deployPlayerCard(e.getValue());
          e.getKey().setDraggable(false);
          e.getValue().setPosition(e.getKey().getCenter());
        } else {
          e.getKey().setPosition(e.getKey().getOrigin().x, e.getKey().getOrigin().y);
        }
      } else if (!e.getKey().isDraggable() && this.roundedPosition(e.getKey().getCenter()).equals(this.roundedPosition(e.getValue().getPosition()))) {
        this.updateAttackablePosition((Attackable) e.getValue(), enemyAttackables);
        e.getKey().setRotation(e.getValue().getPosition());
        e.getKey().moveTo(e.getValue().getPosition());
      }
      if (((Attackable) e.getValue()).getCurrentTarget().isPresent()) {
        e.getKey().setRotation(((Attackable) e.getValue()).getCurrentTarget().get().getPosition());
      }
      if (((Attackable) e.getValue()).isDead()) {
        actor.add(e.getKey());
      }
    });
    if (!card.isEmpty()) {
      super.deployPlayerActor(card);
    }
    if (!actor.isEmpty()) {
      super.updateCardsMap(actor);
    }
  }

  private void placeBotActor() {
    final Vector2 v = new Vector2(350, 550);
    CardActor c = null;
    for (final Entry<CardActor, Card> e : this.botCardsMap.entrySet()) {
      if (e.getKey().isDraggable() && e.getValue().getOwner() instanceof Bot && e.getValue().getCost() <= botElixir.getElixirCount()) {
        e.getValue().setPosition(v);
        e.getKey().setPosition(v.x, v.y);
        c = e.getKey();
        e.getKey().setDraggable(false);
        this.deployBotCard(e.getValue());
      }
    }
    if (c != null) {
    final var nextCard = ((BotGameModel) super.getModel()).getBotNextQueuedCard(c.getOrigin());
    if (nextCard.isPresent()) {
      this.botCardsMap.put(
          new CardActor(c.getOrigin().x, c.getOrigin().y, c.getStage(), AnimationUtilities.loadAnimationFromFiles(nextCard.get().getAnimationFiles().get("ENEMY_MOVING"), ANIMATIONS_FRAME_DURATION, true)),
          nextCard.get());
    }
    }
  }

  private void deployBotCard(final Card card) {
    ((BotGameModel) super.getModel()).deployBotCard(card);
    this.botElixir.decrementElixir(card.getCost());
  }

  private Vector2 roundedPosition(final Vector2 pos) {
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
