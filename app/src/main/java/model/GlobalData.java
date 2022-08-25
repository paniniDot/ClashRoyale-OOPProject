package model;

import model.actors.users.User;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import control.controller.Controller;
import model.actors.cards.Card;
import model.actors.cards.buildings.InfernoTower;
import model.actors.cards.spells.FireBall;
import model.actors.cards.troops.Barbarian;
import model.actors.cards.troops.Giant;
import model.actors.cards.troops.Wizard;
import model.actors.users.Bot;

/**
 * Class used to easily provide istances of User, Bot and their decks.
 */
public final class GlobalData {

  /**
   * Provides a user instance.
   */
  public static final User USER = Controller.getUser();

  /**
   * Provides a bot.
   */
  public static final Bot BOT = new Bot();

  /**
   * Provides the User deck.
   */
  public static final List<Card> USER_DECK = List.of(
      Wizard.create(USER, new Vector2(100, 100)), 
      Giant.create(USER, new Vector2(200, 100)), 
      Barbarian.create(USER, new Vector2(300, 100)), 
      Wizard.create(USER, new Vector2(400, 100)), 
      Wizard.create(USER, new Vector2(500, 100)),
      Wizard.create(USER, new Vector2(600, 100)));

  /**
   * Provides the Bot deck.
   */
  public static final List<Card> BOT_DECK = List.of(
      Wizard.create(BOT, new Vector2(100, 800)), 
      Giant.create(BOT, new Vector2(200, 800)), 
      Barbarian.create(BOT, new Vector2(300, 800)), 
      Wizard.create(BOT, new Vector2(400, 800)), 
      Wizard.create(BOT, new Vector2(500, 800)),
      Wizard.create(BOT, new Vector2(600, 800)));

  private GlobalData() {
  }


}
