package view.screens.statistics;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import controller.Controller;
import controller.statistics.StatController;

import launcher.ClashRoyale;
import utilities.AnimationUtilities;
import view.actors.BaseActor;
import view.screens.BaseScreen;

/**
 * View implementation of the statistics screen.
 */
public class StatScreen extends BaseScreen {

  private TextureAtlas atlas;
  private Skin skin;
  private final StatController statController;

  private static final int SPACE = 15;
 
  /**
   * Constructor.
   * 
   * @param controller
   *                 {@inheritDoc}.
   */
  public StatScreen(final Controller controller) {
    super(controller);
    this.statController = (StatController) controller;
  }

  @Override
  protected void initialize() {
    super.getController().playMusic();
    super.getController().setInputProcessor(super.getMainStage());
    final var background = new BaseActor(0, 0, super.getMainStage(), AnimationUtilities.loadTexture("backgrounds" + File.separator + "menuBackground.png"));
    background.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
  }

  @Override
  public void show() {
    super.show();
    Gdx.input.setInputProcessor(super.getUiStage());
    this.atlas = new TextureAtlas("buttons" + File.separator + "atlas.pack");
    this.skin = new Skin(Gdx.files.internal("buttons" + File.separator + "menuSkin.json"), atlas);
    final var table = new Table(skin);
    table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    final var heading = new Label("Stats", this.skin);

    final var buttonReturn = new TextButton("RETURN", skin);
    buttonReturn.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        getController().stopMusic();
        statController.returnButton();
      }
    });
    buttonReturn.pad(10);

    table.add().spaceBottom(SPACE).row();
    table.add(heading);
    table.getCell(heading).spaceBottom(100).row();
    table.row();
    table.add("Games played: " + statController.getPlays());
    table.row();
    table.add("Wins: " + statController.getWins());
    table.row();
    table.add("W/L: " + statController.getRatio());
    table.row();
    table.add("Destroyed towers: " + statController.getTowers());
    table.row();
    table.add(buttonReturn).spaceTop(SPACE).right().expandX().expandY();

    super.getUiStage().addActor(table);
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
