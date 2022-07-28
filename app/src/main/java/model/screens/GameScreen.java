package model.screens;

import java.util.List;
import java.util.stream.Collectors;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import launcher.ClashRoyale;
import model.actors.BaseActor;
import model.actors.cards.Card;
import model.actors.cards.troops.Wizard;
import model.actors.users.User;
import model.utilities.AnimationUtilities;
import model.utilities.Audio;
import model.utilities.CountDownController;
import model.utilities.ElixirController;
import model.utilities.RectDrawer;
import model.utilities.inGameUtilities.GameMap;

/**
 * In-game screen implementation.
 */
public class GameScreen extends BaseScreen {

  private List<Card> wizards;
  //private Card wizard;
  private GameMap map;
  private SpriteBatch sprite;
  private ElixirController elisir;
  private CountDownController count;
  private BitmapFont gamefont;

  @Override
  public void initialize() {
    Audio.playBattleMusic();
    elisir = new ElixirController();
    count = new CountDownController();
    sprite = new SpriteBatch();
    gamefont = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"));
    Gdx.input.setInputProcessor(super.getMainStage());
    this.map = new GameMap();
    final var arena = new BaseActor(0, 0, super.getMainStage());
    arena.setAnimation(AnimationUtilities.loadTexture("arenas/arena1.png"));
    arena.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
    this.wizards = List.of(
        Wizard.create(new User("Panini"), super.getMainStage(), new Vector2(336, 596)),
        Wizard.create(new User("Panini"), super.getMainStage(), new Vector2(300, 500)),
        Wizard.create(new User("Panini"), super.getMainStage(), new Vector2(350, 550)),
        Wizard.create(new User("Panini"), super.getMainStage(), new Vector2(380, 596)));
    //this.wizard = Wizard.create(new User("Panini"), super.getMainStage(), new Vector2(297, 303));
    this.wizards.forEach(w -> w.setAnimation(AnimationUtilities.loadAnimationFromFiles(new String[]{"wizard/selfWizard/walking/1.png",
        "wizard/selfWizard/walking/2.png", "wizard/selfWizard/walking/3.png", "wizard/selfWizard/walking/4.png"}, (float) 0.01724 * 10, true)));
    //this.wizard.setAnimation(AnimationUtilities.loadAnimationFromFiles(new String[]{"wizard/selfWizard/walking/1.png",
     //   "wizard/selfWizard/walking/2.png", "wizard/selfWizard/walking/3.png", "wizard/selfWizard/walking/4.png"}, (float) 0.01724 * 10, true));

  }

  private void handleInput(final float dt) {
    this.wizards.forEach(w -> {
    final var spots = this.map.getPath(w.getCenter(), new Vector2(450, 395));
    if(!spots.isEmpty()) {
      System.out.println("lista " + spots);
      w.moveTo(new Vector2(spots.get(1).x-w.getWidth()/2,spots.get(1).y-w.getHeight()/2));
    }
    });
    //this.wizards.forEach(w -> w.moveTo(this.map.getPath(w.getPosition(), new Vector2(450, 395)).get(1)));
    //final var spots = this.map.getPath(this.wizard.getCenter(), new Vector2(300, 567));
    //System.out.println("lista " + spots);
    //this.wizard.moveTo(new Vector2(spots.get(1).x - (int) this.wizard.getWidth() / 2, spots.get(1).y - (int) this.wizard.getHeight() / 2));
//    if (Gdx.input.justTouched()) {
//      this.wizard.moveTo(spots.get(1));
//      //System.out.println("posizione di gamescreen: " + this.wizard.getPosition());
//    }
      //this.wizard.moveBy(0, 10);
      //System.out.println("input: " + Gdx.input.getX() + ", " + (Gdx.graphics.getHeight() - Gdx.input.getY()));
    //this.wizard.moveBy(coords.get(0).x - this.wizard.getPosition().x , coords.get(0).y - this.wizard.getPosition().y);
    //coords.remove(0);
  }

  @Override
  public void update(final float dt) {
    this.handleInput(dt);
    //this.wizard.moveBy(0, 1);
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
