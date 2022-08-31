package view.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import controller.Controller;
import controller.menu.MenuController;
import launcher.ClashRoyale;
import model.GlobalData;
import model.utilities.AnimationUtilities;
import view.actors.BaseActor;
import view.screens.BaseScreen;

/**
 * Menu screen implementation.
 */
public class MenuScreen extends BaseScreen {

  private TextureAtlas atlas;
  private Skin skin;

  private static final int SPACE = 15;

  /**
   * Builder.
   *
   * @param controller
   *                {@inheritDoc}
   */
  public MenuScreen(final Controller controller) {
    super(controller);
   }

  @Override
  protected void initialize() {
    Table table;
    Label heading;
    TextButton buttonPlay, buttonExit, buttonLevel, buttonScore, buttonDeck, buttonStat;
    Skin skinLabel;
    TextureAtlas atlasLabel;

    super.getController().playMusic();
    super.getController().setInputProcessor(super.getUiStage());
    final var background = new BaseActor(0, 0, super.getMainStage(), AnimationUtilities.loadTexture("backgrounds/menuBackground.png"));
    background.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);

    atlas = new TextureAtlas("buttons/atlas.pack");
    skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), atlas);
    table = new Table(skin);
    table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    heading = new Label(ClashRoyale.TITLE, skin);
    atlasLabel = new TextureAtlas("buttons/scoreLabel.pack");
    skinLabel = new Skin(Gdx.files.internal("buttons/menuSkinLabel.json"), atlasLabel);

    buttonPlay = new TextButton("Battle", skin);
    buttonPlay.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        getController().stopMusic();
        ((MenuController) getController()).triggerPlay();
      }
    });
    buttonPlay.pad(SPACE);

    buttonDeck = new TextButton("Deck", skin);
    buttonDeck.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        getController().stopMusic();
        ((MenuController) getController()).triggerDeck();
        }
    });
    buttonDeck.pad(SPACE);

    buttonStat = new TextButton("Stats", skin);
    buttonStat.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        getController().stopMusic();
        ((MenuController) getController()).triggerStat();
        }
    });
    buttonStat.pad(SPACE);


    buttonExit = new TextButton("Exit", skin);
    buttonExit.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        getController().saveUser(GlobalData.USER);
        Gdx.app.exit();
      }
    });
    buttonExit.pad(SPACE);

    buttonScore = new TextButton("Score " + GlobalData.USER.getCurrentXP(), skinLabel);
    buttonLevel = new TextButton(GlobalData.USER.getCurrentLevel().toString(), skinLabel);

    table.add(heading);
    table.getCell(heading).spaceBottom(100);
    table.row();
    table.add(buttonPlay);
    table.row();
    table.add(buttonDeck);
    table.row();
    table.add(buttonStat);
    table.row();
    table.add(buttonExit);
    super.getUiStage().addActor(table);
    super.getUiStage().addActor(buttonLevel);
    buttonLevel.setPosition(Gdx.graphics.getWidth() - buttonLevel.getWidth(), Gdx.graphics.getHeight() - buttonLevel.getHeight());
    super.getUiStage().addActor(buttonScore);
    buttonScore.setPosition(0, Gdx.graphics.getHeight() - buttonLevel.getHeight());
  } 

  @Override
  public void dispose() {
    super.dispose();
    this.atlas.dispose();
    this.skin.dispose();
  }

  @Override
  protected void update(final float dt) {
  }
}
