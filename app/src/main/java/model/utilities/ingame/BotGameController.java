package model.utilities.ingame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.actors.Attackable;
import model.actors.cards.Card;
import model.actors.towers.KingTower;
import model.actors.towers.QueenTower;
import model.actors.towers.Tower;
import model.actors.users.Bot;
import model.actors.users.User;
import model.utilities.AnimationUtilities;
import model.utilities.ElixirController;

/**
 * An implementation of GameController in which the user plays 
 * against a bot.
 */
public class BotGameController extends GameController {

  private final List<Card> botCards;
  private final List<Card> botDeployedCards;
  private final List<Card> botChoosableCards;
  private final List<Tower> botActiveTowers;
  private final ElixirController elixirController;

  /**
   * 
   * @param playerCards
   *              the player deck.
   * @param botCards
   *              {@inheritDoc}.
   */
  public BotGameController(final List<Card> playerCards, final List<Card> botCards, final User player, final Bot bot, final Stage stage) {
    super(playerCards, player, stage);
    this.botCards = botCards.stream().collect(Collectors.toList());
    this.botDeployedCards = new ArrayList<>();
    this.botChoosableCards = new ArrayList<>();
    IntStream.range(0, GameController.CHOOSABLE_CARDS).forEach(i -> this.botChoosableCards.add(this.botCards.remove(0)));
    this.botActiveTowers = this.getBotTowers(bot, stage);
    this.elixirController = new ElixirController();
  }

  /* logica per la posizione delle torri mancante */
  private List<Tower> getBotTowers(final Bot bot, final Stage stage) {
    final List<Tower> towers = new ArrayList<>();
    towers.add(QueenTower.create(bot, stage, new Vector2(205, 613)));
    towers.add(QueenTower.create(bot, stage, new Vector2(415, 613)));
    towers.add(KingTower.create(bot, stage, new Vector2(300, 640)));
    towers.forEach(t -> {
      if (t.getClass() == QueenTower.class) {
        t.setAnimation(AnimationUtilities.loadTexture("towers/enemy/queen_tower.png"));
      } else {
        t.setAnimation(AnimationUtilities.loadTexture("towers/enemy/king_tower.png"));
      }
    });
    return towers;
  }

  /**
   * Deploys a card of the bot.
   * @param card
   *           the card to be deployed.
   */
  public void deployBotCard(final Card card) {
    if (this.botChoosableCards.contains(card)) {
      this.botChoosableCards.remove(card);
      this.elixirController.decrementElixir(card.getCost());
      this.botDeployedCards.add(card);
      this.botCards.add(card);
    }
  }

  /**
   * Removes a card from the map.
   * @param card
   *           the card to be removed.
   */
  public void removeBotCardFromMap(final Card card) {
    if (this.botDeployedCards.contains(card)) {
      this.botDeployedCards.remove(card);
    }
  }

  /**
   * 
   * @return the currently active towers of the bot.
   */
  public List<Tower> getPlayerActiveTowers() {
    return Collections.unmodifiableList(this.botActiveTowers);
  }

  /**
   * If not already, destroys a bot tower.
   * 
   * @param tower
   *            the tower to be destroyed.
   */
  public void destroyBotTower(final Tower tower) {
    if (this.botActiveTowers.contains(tower)) {
      this.botActiveTowers.remove(tower);
    }
  }
  /**
   * 
   * @return the current elixir left to the bot.
   */
  public int getBotElixirLeft() {
    return this.elixirController.getElixirCount();
  }

  /**
   * 
   * @return a list of attackable elements of the bot.
   */
  public List<Attackable> getBotAttackable() {
    /* ricorda di sostituire con botDeployedCards */
    return Stream.concat(this.botChoosableCards.stream().map(c -> (Attackable) c), this.botActiveTowers.stream().map(t -> (Attackable) t)).collect(Collectors.toList());
  }
}
