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
import model.utilities.RectDrawer;
import model.utilities.ingame.GameMap;

/**
 * In-game screen implementation.
 */
public class GameScreen extends BaseScreen {

  private List<Card> wizardsbot;
  private List<Card> wizardsplayer;
  private Bot bot;
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
    final var arena = new BaseActor(0, 0, super.getMainStage());
    arena.setAnimation(AnimationUtilities.loadTexture("arenas/arena1.png"));
    arena.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
    this.wizardsplayer = List.of(
        Wizard.create(new User("Panini"), super.getMainStage(), new Vector2(100, 100)),
        Wizard.create(new User("Panini"), super.getMainStage(), new Vector2(200, 100)),
        Wizard.create(new User("Panini"), super.getMainStage(), new Vector2(300, 100)));
    this.wizardsplayer.forEach(w -> w.setAnimation(AnimationUtilities.loadAnimationFromFiles(new String[]{"wizard/selfWizard/walking/1.png",
        "wizard/selfWizard/walking/2.png", "wizard/selfWizard/walking/3.png", "wizard/selfWizard/walking/4.png"}, (float) 0.01724 * 10, true)));
    this.wizardsbot = List.of(
        Wizard.create(new Bot(), super.getMainStage(), new Vector2(100, 800)),
        Wizard.create(new Bot(), super.getMainStage(), new Vector2(200, 800)),
        Wizard.create(new Bot(), super.getMainStage(), new Vector2(300, 800)),
        Wizard.create(new Bot(), super.getMainStage(), new Vector2(400, 800)));
    this.wizardsbot.forEach(w -> w.setAnimation(AnimationUtilities.loadAnimationFromFiles(new String[]{"wizard/selfWizard/walking/1.png",
        "wizard/selfWizard/walking/2.png", "wizard/selfWizard/walking/3.png", "wizard/selfWizard/walking/4.png"}, (float) 0.01724 * 10, true)));

  }

  private void handleInput(final float dt) {
    if (!Gdx.input.isTouched()) {
      spots = bot.findEnemy(map, wizardsplayer, wizardsbot);
      for (final var entry : spots.entrySet()) {
        this.wizardsbot.forEach(w -> {
          if (entry.getKey().equals(w) && entry.getValue().size() > 1) {
            if (entry.getValue().size() < 3) {
              w.setAnimation(AnimationUtilities.loadAnimationFromFiles(new String[]{"wizard/selfWizard/walking/1.png",
                  "wizard/selfWizard/walking/2.png", "wizard/selfWizard/walking/3.png", "wizard/selfWizard/walking/4.png"}, (float) 0.01724 * 10, true));
            }
            w.moveTo(new Vector2(entry.getValue().get(1).x - w.getWidth() / 2, entry.getValue().get(1).y - w.getHeight() / 2));
          }
          });
      }
      spots = bot.findEnemy(map, wizardsbot, wizardsplayer);
      for (final var entry : spots.entrySet()) {
        this.wizardsplayer.forEach(w -> {
          if (entry.getKey().equals(w) && entry.getValue().size() > 1) {
            if (entry.getValue().size() < 3) {
              w.setAnimation(AnimationUtilities.loadAnimationFromFiles(new String[]{"wizard/selfWizard/walking/1.png",
                  "wizard/selfWizard/walking/2.png", "wizard/selfWizard/walking/3.png", "wizard/selfWizard/walking/4.png"}, (float) 0.01724 * 10, true));
            }
            w.moveTo(new Vector2(entry.getValue().get(1).x - w.getWidth() / 2, entry.getValue().get(1).y - w.getHeight() / 2));
          }
          });
      }
    }
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
