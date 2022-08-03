package model.screens;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.JsonWriter;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import launcher.BaseGame;
import launcher.ClashRoyale;
import model.actors.BaseActor;
import model.actors.users.User;
import model.utilities.AnimationUtilities;
import model.utilities.Audio;

/**
 * Menu screen implementation.
 */
public class MenuScreen extends BaseScreen {
  private Audio audio;
  private TextureAtlas atlas, atlasLabel;
  private Skin skin, skinLabel;
  private Table table;
  private TextButton buttonPlay, buttonExit, buttonLevel, buttonScore, buttonDeck;
  private Label heading;
  private FileHandle file;
  private User user;
  private Gson gson;
  private Writer writer;
  private static final int SPACE = 15;

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
    this.gson = new Gson();
    this.file = Gdx.files.internal("saves/user.json");
      if (!this.file.exists()) {
        this.user = new User("P"); 
        try {
          writer = new FileWriter(file.file());
          gson.toJson(user, writer);
          writer.close();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      } else {
          try {
            this.user = this.gson.fromJson(new FileReader(this.file.file()), User.class);
          } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
      }

    this.buttonPlay = new TextButton("Play", skin);
    this.buttonPlay.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        audio.stop();
        BaseGame.setActiveScreen(new GameScreen());
      }
    });
    this.buttonPlay.pad(SPACE);

    this.buttonExit = new TextButton("Exit", skin);
    this.buttonExit.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        try {
          writer = new FileWriter(file.file());
          gson.toJson(user, writer);
          writer.close();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        Gdx.app.exit();
      }
    });
    this.buttonExit.pad(SPACE);

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
