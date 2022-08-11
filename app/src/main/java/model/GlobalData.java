package model;

import model.actors.users.User;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import model.actors.cards.Card;
import model.actors.cards.troops.Wizard;
import model.actors.users.Bot;

/**
 * Class used to easily access istances of User, Bot and their decks.
 */
public final class GlobalData {

  /**
   * The user.
   */
  public static final User USER = new User("Panini");

  /**
   * The bot.
   */
  public static final Bot BOT = new Bot();

  /**
   * User deck.
   */
  public static final List<Card> USER_DECK = List.of(
      Wizard.create(USER, new Vector2(100, 100)), 
      Wizard.create(USER, new Vector2(200, 100)), 
      Wizard.create(USER, new Vector2(300, 100)), 
      Wizard.create(USER, new Vector2(400, 100)), 
      Wizard.create(USER, new Vector2(500, 100)),
      Wizard.create(USER, new Vector2(600, 100)),
      Wizard.create(USER, new Vector2(700, 100)));

  /**
   * Bot deck.
   */
  public static final List<Card> BOT_DECK = List.of(
      Wizard.create(BOT, new Vector2(100, 800)), 
      Wizard.create(BOT, new Vector2(200, 800)), 
      Wizard.create(BOT, new Vector2(300, 800)), 
      Wizard.create(BOT, new Vector2(400, 800)), 
      Wizard.create(BOT, new Vector2(500, 800)),
      Wizard.create(BOT, new Vector2(600, 800)),
      Wizard.create(BOT, new Vector2(700, 800)));

  private GlobalData() {
  }

}
