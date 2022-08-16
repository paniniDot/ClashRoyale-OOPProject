package view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import control.controller.Controller;
import control.controller.MenuController;
import control.launcher.ClashRoyale;
import model.utilities.AnimationUtilities;
import view.actors.BaseActor;

/**
 * Menu screen implementation.
 */
public class MenuScreen extends BaseScreen {

  private TextureAtlas atlas, atlasLabel;
  private Skin skin, skinLabel;
  private Table table;
  private TextButton buttonPlay, buttonExit, buttonLevel, buttonScore, buttonDeck;
  private Label heading;
  private MenuController menuController;
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
  public void initialize() {
    super.getController().playMusic();
    super.getController().setInputProcessor(super.getUiStage());
    final var background = new BaseActor(0, 0, super.getMainStage(), AnimationUtilities.loadTexture("backgrounds/menuBackground.png"));
    background.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
    this.menuController = new MenuController();
    this.atlas = new TextureAtlas("buttons/atlas.pack");
    this.skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), this.atlas);
    this.table = new Table(this.skin);
    this.table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    this.heading = new Label(ClashRoyale.TITLE, this.skin);
    this.atlasLabel = new TextureAtlas("buttons/scoreLabel.pack");
    this.skinLabel = new Skin(Gdx.files.internal("buttons/menuSkinLabel.json"), this.atlasLabel);
    this.buttonPlay = new TextButton("Play", skin);
    this.buttonPlay.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        getController().stopMusic();
        menuController.triggerPlay();
      }
    });
    this.buttonPlay.pad(SPACE);
    this.buttonDeck = new TextButton("Deck", skin);
    this.buttonDeck.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        getController().stopMusic();
        menuController.triggerDeck();
        }
    });
    this.buttonDeck.pad(SPACE);

    this.buttonExit = new TextButton("Exit", skin);
    this.buttonExit.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        Controller.save();
        Gdx.app.exit();
      }
    });
    this.buttonExit.pad(SPACE);

    this.buttonScore = new TextButton("Score " + String.valueOf(menuController.getUser().getCurrentXP()), this.skinLabel);
    this.buttonLevel = new TextButton(menuController.getUser().getCurrentLevel().toString(), this.skinLabel);

    this.table.add(this.heading);
    this.table.getCell(this.heading).spaceBottom(100);
    this.table.row();
    this.table.add(this.buttonPlay);
    this.table.row();
    this.table.add(this.buttonDeck);
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
