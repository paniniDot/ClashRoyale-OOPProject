package model.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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

import launcher.BaseGame;
import launcher.ClashRoyale;
import model.actors.BaseActor;
import model.utilities.AnimationUtilities;
import model.utilities.Audio;

/**
 * Menu screen implementation.
 */
public class MenuScreen extends BaseScreen {
  private Audio audio;
  private TextureAtlas atlas;
  private Skin skin;
  private Table table;
  private TextButton buttonPlay, buttonExit;
  private Label heading;

  @Override
  public void initialize() {
    audio = Audio.playMenuMusic();
    final var background = new BaseActor(0, 0, super.getMainStage());
    background.setAnimation(AnimationUtilities.loadTexture("backgrounds/menuBackground.png"));
    background.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
    Gdx.input.setInputProcessor(super.getUiStage());

    atlas = new TextureAtlas("buttons/button.pack");
    skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), atlas);
    table = new Table(skin);
    table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    //creating heading
    heading = new Label(ClashRoyale.TITLE, skin);

    //creating buttons
    buttonPlay = new TextButton("Play", skin);
    buttonPlay.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        BaseGame.setActiveScreen(new GameScreen());
      }
    });
    buttonPlay.pad(15);

    buttonExit = new TextButton("Exit", skin);
    buttonExit.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        Gdx.app.exit();
      }
    });  

    buttonExit.pad(15);

    table.add(heading);
    table.getCell(heading).spaceBottom(100);
    table.row();
    table.add(buttonPlay);
    table.row();
    table.add(buttonExit);
    super.getUiStage().addActor(table);
  }

  @Override
  public void update(final float dt) {
    if (Gdx.input.isKeyJustPressed(Keys.S)) {
      audio.stop();
      BaseGame.setActiveScreen(new GameScreen());
    }

  }
  @Override
  public void dispose() {
    super.dispose();
    this.atlas.dispose();
    this.skin.dispose();
  }



}
