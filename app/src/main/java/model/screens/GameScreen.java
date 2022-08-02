package model.screens;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import launcher.BaseGame;
import launcher.ClashRoyale;
import model.actors.Attackable;
import model.actors.BaseActor;
import model.actors.cards.Card;
import model.actors.cards.troops.Troop;
import model.actors.cards.troops.Wizard;
import model.actors.towers.KingTower;
import model.actors.towers.QueenTower;
import model.actors.towers.Tower;
import model.actors.users.Bot;
import model.actors.users.User;
import model.utilities.AnimationUtilities;
import model.utilities.Audio;
import model.utilities.CountDownController;
import model.utilities.ElixirController;
import model.utilities.Pair;
import model.utilities.RectDrawer;
import model.utilities.ingame.BotGameController;
import model.utilities.ingame.GameMap;
import model.utilities.ingame.MapUnit;

/**
 * In-game screen implementation.
 */
public class GameScreen extends BaseScreen {

  private List<Card> wizardsbot;
  private List<Card> wizardsplayer;
  private Bot bot;
  private User user;
  private GameMap map;
  private SpriteBatch sprite;
  private ElixirController elisir;
  private CountDownController count;
  private BitmapFont gamefont;
  private Audio audio;
  private Map<Card, List<Vector2>> spots;
  private BotGameController gameController;

  @Override
  public void initialize() {
    audio = Audio.playBattleMusic();
    elisir = new ElixirController();
    count = new CountDownController();
    sprite = new SpriteBatch();
    gamefont = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"));
    Gdx.input.setInputProcessor(super.getMainStage());
    this.map = new GameMap();
    bot = new Bot();
    this.user = new User("Panini");
    final var arena = new BaseActor(0, 0, super.getMainStage());
    arena.setAnimation(AnimationUtilities.loadTexture("arenas/arena1.png"));
    arena.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
    this.wizardsplayer = List.of(
        Wizard.create(this.user, super.getMainStage(), new Vector2(100, 100)),
        Wizard.create(this.user, super.getMainStage(), new Vector2(300, 100)),
        Wizard.create(this.user, super.getMainStage(), new Vector2(200, 100)),
        Wizard.create(this.user, super.getMainStage(), new Vector2(400, 100)));
    this.wizardsbot = List.of(
        Wizard.create(bot, super.getMainStage(), new Vector2(100, 800)),
        Wizard.create(bot, super.getMainStage(), new Vector2(300, 800)),
        Wizard.create(bot, super.getMainStage(), new Vector2(400, 800)),
        Wizard.create(bot, super.getMainStage(), new Vector2(200, 800)));
    this.gameController = new BotGameController(wizardsplayer, wizardsbot, user, bot, getMainStage());
  }

  private void handleInput(final float dt) {
    move(map.findEnemy(map, this.gameController.getPlayerAttackable(), this.gameController.getBotAttackable()));
    move(map.findEnemy(map, this.gameController.getBotAttackable(), this.gameController.getPlayerAttackable()));
  }

  private void move(final List<Pair<Pair<Attackable, Attackable>, List<Vector2>>> spots) {
      spots.forEach(s -> {
        if (s.getY().size() > 1) {
          if (!s.getX().getX().getClass().equals(QueenTower.class) && !s.getX().getX().getClass().equals(KingTower.class)) {
            if (!Gdx.input.isTouched() || !((Card) s.getX().getX()).isDraggable()) {
              //System.out.println("Classe = " + s.getX().getX().getClass() + "Controllo = " + s.getX().getX().getClass().equals(Card.class));
              ((Troop) s.getX().getX()).setRotation(s.getY().get(1));
              ((Card) s.getX().getX()).setDraggable(false);
              ((Card) s.getX().getX()).moveTo(new Vector2(s.getY().get(1).x - ((Card) s.getX().getX()).getWidth() / 2, s.getY().get(1).y - ((Card) s.getX().getX()).getHeight() / 2));
            }
          }
        }
      });

  }


  @Override
  public void update(final float dt) {
    this.handleInput(dt);
    if (count.getTime() == 0) {
      audio.stop();
      elisir.setRunFalse();
      count.setRunFalse();
      BaseGame.setActiveScreen(new MenuScreen());
    }
  }
  @Override
  public void render(final float dt) {
    super.render(dt);
    sprite.begin();
    gamefont.draw(sprite, "elisir " + elisir.getElixirCount(), 100, 100);
    gamefont.draw(sprite, "durata " + count.getTime(), 100, 200);
    sprite.end();
    RectDrawer.showDebugBoundingBoxes(this.map.getMap().vertexSet().stream().map(MapUnit::getUnitRectangle).collect(Collectors.toList()), Color.BLUE);
    //RectDrawer.showDebugBoundingBoxes(this.map.getMap().vertexSet().stream().filter(v -> v.getType().equals(MapUnit.Type.TERRAIN)).map(MapUnit::getUnitRectangle).collect(Collectors.toList()), Color.BLUE);
    RectDrawer.showDebugBoundingBoxes(this.map.getMap().vertexSet().stream().filter(v -> v.getType().equals(MapUnit.Type.TOWER)).map(MapUnit::getUnitRectangle).collect(Collectors.toList()), Color.RED);
  }
}
