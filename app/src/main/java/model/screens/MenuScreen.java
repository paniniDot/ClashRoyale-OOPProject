package model.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

import launcher.BaseGame;
import launcher.ClashRoyale;
import model.actors.BaseActor;
import model.actors.users.User;
import model.actors.users.UserLevel;
import model.utilities.AnimationUtilities;
import model.utilities.Audio;

/**
 * Menu screen implementation.
 */
/**
 * @author Giulia
 *
 */
public class MenuScreen extends BaseScreen {

  /**
   * for save file user.
   */
  public static class UserDatabase {
    public int currentXP;
    public UserLevel currentLevel;

    public User createUser() {
      User user = new User("P");
      user.setCurrentLevel(currentLevel);
      user.setCurrentXP(currentXP);
      return user;
    }
  }

  private Audio audio;
  private TextureAtlas atlas, atlasLabel;
  private Skin skin, skinLabel;
  private Table table;
  private TextButton buttonPlay, buttonExit, buttonLevel, buttonScore;
  private Label heading, level;
  private Json json;
  private FileHandle file;
  private UserDatabase desc;
  private User user;
  private final int space = 15;
  /**
   * 
   * @return descr
   */
  public UserDatabase newUserDatabase() {
    this.desc = new UserDatabase();
    this.desc.currentXP = 10;
    this.desc.currentLevel = UserLevel.LVL2;
    return desc;
  }

  @Override
  public void initialize() {
    this.audio = Audio.playMenuMusic();
    final var background = new BaseActor(0, 0, super.getMainStage());
    background.setAnimation(AnimationUtilities.loadTexture("backgrounds/menuBackground.png"));
    background.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
    Gdx.input.setInputProcessor(super.getUiStage());
    this.atlas = new TextureAtlas("buttons/button.pack");
    this.skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), this.atlas);
    this.table = new Table(this.skin);
    this.table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    this.heading = new Label(ClashRoyale.TITLE, this.skin);
    this.atlasLabel = new TextureAtlas("buttons/scoreLabel.pack");
    this.skinLabel = new Skin(Gdx.files.internal("buttons/menuSkinLabel.json"), this.atlasLabel);
    this.file = Gdx.files.local("bin/desc.json");
    this.json = new Json();
    if (this.user == null) {
      this.desc = newUserDatabase();
      this.user = desc.createUser();
    }
    if (this.file != null) {
      load();
    }

    this.buttonPlay = new TextButton("Play", skin);
    this.buttonPlay.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        audio.stop();
        BaseGame.setActiveScreen(new GameScreen());
      }
    });
    this.buttonPlay.pad(space);

    this.buttonExit = new TextButton("Exit", skin);
    this.buttonExit.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        save(desc);
        Gdx.app.exit();
      }
    });
    this.buttonExit.pad(space);

    this.buttonScore = new TextButton("Score " + String.valueOf(this.user.getCurrentXP()), this.skinLabel);
    this.buttonLevel = new TextButton(this.user.getCurrentLevel().toString(), this.skinLabel);

    this.table.add(this.heading);
    this.table.getCell(this.heading).spaceBottom(100);
    this.table.row();
    this.table.add(this.buttonPlay);
    this.table.row();
    this.table.add(this.buttonExit);
    super.getUiStage().addActor(this.table);
    super.getUiStage().addActor(this.buttonLevel);
    this.buttonLevel.setPosition(Gdx.graphics.getWidth() - this.buttonLevel.getWidth(), Gdx.graphics.getHeight() - this.buttonLevel.getHeight());
    super.getUiStage().addActor(buttonScore);
    this.buttonScore.setPosition(0, Gdx.graphics.getHeight() - this.buttonLevel.getHeight());


  }
  /**
   * 
   * @param desc
   */
  public void save(final UserDatabase desc) {
    this.json.setOutputType(OutputType.json);
    this.file.writeString(json.prettyPrint(desc), false);
  }
  /**
   * 
   */
  public void load() {
    this.desc = this.json.fromJson(UserDatabase.class, this.file);
    this.user = this.desc.createUser();
  }

  @Override
  public void dispose() {
    super.dispose();
    this.atlas.dispose();
    this.skin.dispose();
  }


  @Override
  public void update(final float dt) {
  }
}
