package model.screens;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Gdx;
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
import model.utilities.inGameUtilities.GameMap;

/**
 * In-game screen implementation.
 */
public class GameScreen extends BaseScreen {

  private Card wizard;
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
    this.wizard = Wizard.create(new User("Panini"), super.getMainStage(), new Vector2(336, 596));
    this.wizard.setAnimation(AnimationUtilities.loadAnimationFromFiles(new String[]{"wizard/selfWizard/walking/1.png",
        "wizard/selfWizard/walking/2.png", "wizard/selfWizard/walking/3.png", "wizard/selfWizard/walking/4.png"}, (float) 0.01724 * 10, true));

  }

  private void handleInput(final float dt) {
    final List<Vector2> coords = new ArrayList<>();
    coords.add(new Vector2(0, 0));
    if (Gdx.input.justTouched()) {
      this.wizard.moveBy(0, 10);
      //System.out.println(this.map.getPath(new Vector2(this.wizard.getX(), this.wizard.getY()), new Vector2(450, 395)));
      System.out.println(this.wizard.getPosition());
    }
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
  }
}
