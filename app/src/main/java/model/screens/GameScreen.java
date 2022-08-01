package model.screens;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import launcher.BaseGame;
import launcher.ClashRoyale;
import model.actors.BaseActor;
import model.actors.cards.Card;
import model.actors.cards.troops.Wizard;
import model.actors.users.Bot;
import model.actors.users.User;
import model.utilities.AnimationUtilities;
import model.utilities.Audio;
import model.utilities.CountDownController;
import model.utilities.ElixirController;
import model.utilities.Pair;
import model.utilities.RectDrawer;
import model.utilities.ingame.GameMap;

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
        Wizard.create(this.user, super.getMainStage(), new Vector2(200, 100)));
    this.wizardsplayer.forEach(w -> w.setAnimation(AnimationUtilities.loadAnimationFromFiles(new String[]{"wizard/selfWizard/walking/1.png",
        "wizard/selfWizard/walking/2.png", "wizard/selfWizard/walking/3.png", "wizard/selfWizard/walking/4.png"}, (float) 0.01724 * 10, true)));
    this.wizardsbot = List.of(
        Wizard.create(bot, super.getMainStage(), new Vector2(100, 800)),
        Wizard.create(bot, super.getMainStage(), new Vector2(200, 800)));
    this.wizardsbot.forEach(w -> w.setAnimation(AnimationUtilities.loadAnimationFromFiles(new String[]{"wizard/selfWizard/walking/1.png",
        "wizard/selfWizard/walking/2.png", "wizard/selfWizard/walking/3.png", "wizard/selfWizard/walking/4.png"}, (float) 0.01724 * 10, true)));
  }

  private void handleInput(final float dt) {
    move(map.findEnemy(map, wizardsplayer, wizardsbot), wizardsplayer);
    move(map.findEnemy(map, wizardsbot, wizardsplayer), wizardsbot);
  }

  private void move(final List<Pair<Pair<Card, Card>, List<Vector2>>> spots, final List<Card> card) {
    card.forEach(c -> {
      spots.forEach(s -> {
        if (s.getX().getX().getIdentifier() == c.getIdentifier() && s.getY().size() > 1) {
          if (s.getY().size() < 3) {
            c.setAnimation(AnimationUtilities.loadAnimationFromFiles(
                new String[] { "wizard/selfWizard/walking/1.png", "wizard/selfWizard/walking/2.png",
                    "wizard/selfWizard/walking/3.png", "wizard/selfWizard/walking/4.png" },
                (float) 0.01724 * 10, true));
          }
          if (!Gdx.input.isTouched() || !c.isDraggable()) {
            c.setDraggable(false);
            c.moveTo(new Vector2(s.getY().get(1).x - c.getWidth() / 2, s.getY().get(1).y - c.getHeight() / 2));
          }
        }
      });
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
    RectDrawer.showDebugBoundingBoxes(this.map.getMap().vertexSet().stream().map(v -> v.getUnitRectangle()).collect(Collectors.toList()), Color.BLUE);
  }
}
