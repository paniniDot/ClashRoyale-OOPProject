package controller.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import controller.ElixirController;

import model.GlobalData;
import model.entities.Attackable;
import model.entities.cards.Card;
import model.entities.towers.Tower;
import model.entities.users.Bot;
import model.entities.users.User;
import model.deck.PlayersDeck;
import model.utilities.AnimationUtilities;
import model.utilities.ingame.BotGameModel;
import model.utilities.ingame.MapUnit;
import view.actors.cards.CardActor;
import view.actors.towers.TowerActor;

/**
 * 
 * Game controller implementation Simple Bot mode.
 */
public class BotGameController extends GameController {

  private final ElixirController botElixir;
  private Map<CardActor, Card> botCardsMap;
  private Map<TowerActor, Tower> botTowersMap;
  private final Random rand = new Random();
  private static final int SIDE = 500;
  private final JFrame frame;

  /**
   * Constructor.
   */
  public BotGameController() {
    super(new BotGameModel(PlayersDeck.getInstance().cardList(), GlobalData.BOT_DECK, GlobalData.USER, GlobalData.BOT));
    this.botElixir = new ElixirController();
    this.botCardsMap = new HashMap<>();
    this.botTowersMap = new HashMap<>();
    this.frame = new JFrame();
  }

  @Override
  protected boolean checkEnemyLose() {
    return ((BotGameModel) super.getModel()).getBotActiveTowers().size() == 0;
  }

  @Override
  protected int getDestoryedTowers() {
    return 3 - ((BotGameModel) super.getModel()).getBotActiveTowers().size();
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
    final var actor = new ArrayList<CardActor>();
    cardActors.entrySet().stream().forEach(e -> {
      if (!e.getKey().isDraggable() && e.getKey().getCenter().equals(e.getValue().getPosition())) {
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
    if (!actor.isEmpty()) {
      super.updateCardsMap(actor);
    }
  }

  private void placeBotActor() {
    final Vector2 v = new Vector2(this.randomPosition(150, 550), this.randomPosition(500, 700));
    CardActor c = null;
    for (final Entry<CardActor, Card> e : this.botCardsMap.entrySet()) {
      if (this.checkposition(v, e.getValue()) && e.getKey().isDraggable() && e.getValue().getCost() <= this.botElixir.getElixirCount()) {
        c = e.getKey();
        this.deployBotCard(e.getValue());
        e.getKey().setPosition(v.x, v.y);
        e.getValue().setPosition(e.getKey().getCenter());
        e.getKey().setDraggable(false);
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

  private void placePlayeActor() {
    final var card = new ArrayList<Card>();
    super.getPlayerActorsMap().entrySet().stream().forEach(e -> {
      if (e.getKey().isDraggable() && !Gdx.input.isTouched()) {
        if (this.checkposition(e.getKey().getCenter(), e.getValue()) && e.getValue().getCost() <= super.getPlayerCurrentElixir()) {
          card.add(e.getValue());
          super.deployPlayerCard(e.getValue());
          e.getKey().setDraggable(false);
          e.getValue().setPosition(e.getKey().getCenter());
        } else {
          e.getKey().setPosition(e.getKey().getOrigin().x, e.getKey().getOrigin().y);
        }
      }
    });
    if (!card.isEmpty()) {
      super.deployPlayerActor(card);
    }
  }

  private boolean checkposition(final Vector2 v, final Card c) {
    if (super.getGameMap().containsPosition(v) && super.getGameMap().getMapUnitFromPosition(v).getType().equals(MapUnit.Type.TERRAIN)) {
      if (c.getOwner() instanceof User && v.y < SIDE) {
        return true;
      } else if (c.getOwner() instanceof Bot && v.y > SIDE) {
        return true;
      }
    }
    return false;
  }

  private void deployBotCard(final Card card) {
    ((BotGameModel) super.getModel()).deployBotCard(card);
    this.botElixir.decrementElixir(card.getCost());
  }

  private int randomPosition(final int min, final int max) {
    return rand.nextInt((max - min) + 1) + min;
  }

  @Override
  protected void onUpdateActors() {
    this.placeBotActor();
    this.placePlayeActor();
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

  @Override
  public boolean checkForwinner() {
    if (getPlayerScore() == 3) {
      JOptionPane.showMessageDialog(frame, "Hai Perso");
      return true;
    }
    if (super.getBotScore() == 3) {
      JOptionPane.showMessageDialog(frame, "Hai Vinto");
      return true;
    }
    return false;   
  }
}
