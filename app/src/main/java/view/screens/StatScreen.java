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
  private Table table, tableDeck, tableCards; 
  private TextButton buttonReturn;
  private Label heading, headingDeck, headingCards;
  private StatController statController;
  private static final int SPACE = 15;
 /* private static final int HEIGHTSCROLLPANE = 350;
  private static final int WIDTHSCROLLPANE = 400;
*/
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
    this.table = new Table(skin);
    this.tableDeck = new Table(skin);
    this.tableCards = new Table(skin);
    this.table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    this.heading = new Label("Scelta Deck", this.skin);
    this.headingDeck = new Label("Deck", this.skin);
    this.headingCards = new Label("Mazzo", this.skin);
    this.statController = new StatController();

    buttonReturn = new TextButton("RETURN", skin);
    this.buttonReturn.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        statController.returnButton();
      }
    });
    buttonReturn.pad(10);

    this.tableDeck.add(headingDeck).row();
    this.tableCards.add(headingCards).row();
    this.table.add().spaceBottom(SPACE).row();
    this.table.add(heading);
    this.table.getCell(this.heading).spaceBottom(100).row();
    this.table.add(tableDeck).left().row();
    this.table.add(tableCards).left().row();
    this.table.add(this.buttonReturn).right();

    super.getUiStage().addActor(this.table);
  }

  @Override
  public void dispose() {
    super.dispose();
    this.atlas.dispose();
    this.skin.dispose();
  }

  @Override
  public void update(final float dt) {
    // TODO Auto-generated method stub

  }

}
