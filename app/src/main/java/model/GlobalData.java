package model;

import model.actors.users.User;
import model.utilities.Deck;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import control.controller.Controller;
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
  public static final User USER = SaveController.getInstance().loadUser();

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
      Giant.create(BOT, new Vector2(200, 800)), 
      Barbarian.create(BOT, new Vector2(300, 800)), 
      Wizard.create(BOT, new Vector2(400, 800)), 
      Wizard.create(BOT, new Vector2(500, 800)),
      Wizard.create(BOT, new Vector2(600, 800)));

  private GlobalData() {    
  }
}
