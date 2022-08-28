package model;

import model.actors.users.User;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import control.controller.game.SaveController;
import model.actors.cards.Card;
import model.actors.cards.troops.Barbarian;
import model.actors.cards.troops.Giant;
import model.actors.cards.troops.Wizard;
import model.actors.users.Bot;

/**
 * Class used to easily provide istances of User and Bot.
 */
public class GlobalData {
  /**
   * Provides a user instance.
   */
  public static final User USER = SaveController.loadUser();

//  /**
//   * Provides a user instance.
//   */
//  public static Deck DECK = new Deck();


  /**
   * Provides a bot.
   */
  public static final Bot BOT = new Bot();

  /**
   * Provides the Bot deck.
   */
  public static final List<Card> BOT_DECK = List.of(
      Wizard.create(BOT, new Vector2(100, 800)), 
      Barbarian.create(BOT, new Vector2(200, 800)), 
      Giant.create(BOT, new Vector2(300, 800)), 
      Wizard.create(BOT, new Vector2(400, 800)));

  private GlobalData() {    
  }
}
