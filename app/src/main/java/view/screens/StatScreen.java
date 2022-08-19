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
import control.controller.StatController;
import control.launcher.ClashRoyale;
import model.utilities.AnimationUtilities;
import view.actors.BaseActor;

/**
 * View implementation of the stat screen.
 */
public class StatScreen extends BaseScreen {

  private TextureAtlas atlas;
  private Skin skin;
  private StatController statController;
  private static final int SPACE = 15;
 
  /**
   * Constructor.
   * 
   * @param controller
   *                 {@inheritDoc}.
   */
  public StatScreen(final Controller controller) {
    super(controller);
  }

  @Override
  public void initialize() {
    super.getController().playMusic();
    super.getController().setInputProcessor(super.getMainStage());
    final var background = new BaseActor(0, 0, super.getMainStage(), AnimationUtilities.loadTexture("backgrounds/menuBackground.png"));
    background.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
  }

  @Override
  public void show() {
    super.show();
    Gdx.input.setInputProcessor(super.getUiStage());
    this.atlas = new TextureAtlas("buttons/atlas.pack");
    this.skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), atlas);
    final var table = new Table(skin);
    table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    final var heading = new Label("Statistiche", this.skin);
    this.statController = new StatController();

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
    table.add("Partite giocate: " + statController.getPlays());
    table.row();
    table.add("Vittorie: " + statController.getWins());
    table.row();
    table.add("W/L: " + statController.getRatio());
    table.row();
    table.add("Torri abbattute: " + statController.getTowers());
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
  public void update(final float dt) {
  }

}
