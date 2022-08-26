package model.utilities.ingame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.badlogic.gdx.math.Vector2;

import model.actors.Attackable;
import model.actors.cards.Card;
import model.actors.towers.KingTower;
import model.actors.towers.QueenTower;
import model.actors.towers.Tower;
import model.actors.users.Bot;
import model.actors.users.User;
import model.utilities.VectorsUtilities;

/**
 * An implementation of GameController in which the user plays 
 * against a bot.
 */
public class BotGameModel extends GameModel {

  private final List<Card> botCards;
  private final List<Card> botCardQueue;
  private final List<Card> botDeployedCards;
  private final List<Card> botChoosableCards;
  private final List<Tower> botActiveTowers;
 
  /**
   * 
   * @param playerCards
   *              the player deck.
   * @param botCards
   *              {@inheritDoc}.
   */
  public BotGameModel(final List<Card> playerCards, final List<Card> botCards, final User player, final Bot bot) {
    super(playerCards, player);
    this.botCards = botCards.stream().collect(Collectors.toList());
    this.botCardQueue = botCards.stream().collect(Collectors.toList());
    this.botDeployedCards = new ArrayList<>();
    this.botChoosableCards = new ArrayList<>();
    IntStream.range(0, GameModel.CHOOSABLE_CARDS).forEach(i -> this.botChoosableCards.add(this.botCardQueue.remove(0)));
    this.botActiveTowers = this.getBotTowers(bot);
  }

  /* logica per la posizione delle torri mancante */
  private List<Tower> getBotTowers(final Bot bot) {
    final List<Tower> towers = new ArrayList<>();
    towers.add(QueenTower.create(bot, new Vector2(205 + 33, 613 + 44)));
    towers.add(QueenTower.create(bot, new Vector2(415 + 33, 613 + 44)));
    towers.add(KingTower.create(bot, new Vector2(300 + 44, 640 + 66)));
    return towers;
  }

  /**
   * 
   * @return a list of every card used from the bot during the match.
   */
  public List<Card> getBotDeck() {
    return Collections.unmodifiableList(this.botCards);
  }

  /**
   * 
   * @return a list of bot currently deployed cards.
   */
  public List<Card> getBotDeployedCards() {
    return Collections.unmodifiableList(this.botDeployedCards);
  }

  /**
   * 
   * @return a list of bot currently choosable cards.
   */
  public List<Card> getBotChoosableCards() {
    return Collections.unmodifiableList(this.botChoosableCards);
  }

  /**
   * Deploys a card of the bot.
   * @param card
   *           the card to be deployed.
   */
  public void deployBotCard(final Card card) {
    if (this.botChoosableCards.contains(card)) {
      this.botChoosableCards.remove(card);
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
  public List<Tower> getBotActiveTowers() {
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
   * @return a list of attackable elements of the bot.
   */
  public List<Attackable> getBotAttackable() {
    /* ricorda di sostituire con botDeployedCards */
    return Stream.concat(this.botDeployedCards.stream().map(c -> (Attackable) c), this.botActiveTowers.stream().map(t -> (Attackable) t)).collect(Collectors.toList());
  }

  private void findTargets(final List<Attackable> selfAttackables, final List<Attackable> enemyAttackables) {
    selfAttackables
      .stream()
      .filter(selfAttackable -> selfAttackable.getCurrentTarget().isEmpty())
      .forEach(selfAttackable -> enemyAttackables
          .stream()
          .filter(enemyAttackable -> this.isInRange(selfAttackable, enemyAttackable))
          .findAny()
          .ifPresent(enemyAttackable -> selfAttackable.setCurrentTarget(enemyAttackable)));
  }

  private boolean isInRange(final Attackable selfAttackable, final Attackable enemyAttackable) {
    return VectorsUtilities.euclideanDistance(selfAttackable.getPosition(), enemyAttackable.getPosition()) <= selfAttackable.getRange();
  }

  @Override
  public void findAttackableTargets() {
    this.findTargets(super.getPlayerAttackable(), this.getBotAttackable());
    this.findTargets(this.getBotAttackable(), super.getPlayerAttackable());
  }

  private void attackTargets(final List<Attackable> selfAttackables) {
    selfAttackables.forEach(attackable -> {
      attackable.attackCurrentTarget();
      if (attackable.getCurrentTarget().isPresent()) {
        final var currentTarget = attackable.getCurrentTarget().get();
        if (currentTarget.isDead()) {
          if (isUserTheOwner(currentTarget)) {
            super.removeUserAttackableFromArena(currentTarget);
          } else {
            this.removeBotAttackableFromArena(currentTarget);
          }
          attackable.resetCurrentTarget();
        }
      }
    });
  }

  private void removeBotAttackableFromArena(final Attackable target) {
    if (super.isTower(target)) {
      this.destroyBotTower((Tower) target);
    } else {
      this.removeBotCardFromMap((Card) target);
    }
  }

  @Override
  public void handleAttackTargets() {
    this.attackTargets(super.getPlayerAttackable());
    this.attackTargets(this.getBotAttackable());
  }
}
