package view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.google.gson.Gson;

import control.controller.Controller;
import control.launcher.ClashRoyale;
import model.actors.BaseActor;
import model.utilities.AnimationUtilities;

/**
 * Desck screen implementation.
 */
public class DeckScreen extends BaseScreen {

  private TextureAtlas atlas;
  private Skin skin;
  private Table table;
  private List list;
  private ScrollPane scrollPane;
  private TextButton buttonAdd, buttonRemove, buttonExit;
  private Label heading;
  private Gson gson;
  private static final int SPACE = 15;

  /**
   * Constructor.
   * 
   * @param controller
   *                 {@inheritDoc}.
   */
  public DeckScreen(final Controller controller) {
    super(controller);
  }

  @Override
  public void initialize() {
    final var background = new BaseActor(0, 0, super.getMainStage());
    background.setAnimation(AnimationUtilities.loadTexture("backgrounds/menuBackground.png"));
    background.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
    Gdx.input.setInputProcessor(super.getUiStage());
    this.atlas = new TextureAtlas("buttons/button.pack");
    this.skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), atlas);
    this.table = new Table(skin);
    this.table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    this.heading = new Label("Scelta Deck", this.skin);
  //  this.list = new List<String>(skin, "small");
  //  list.setItems(new String[] {"one", "two", "three"}); 
  //  this.scrollPane = new ScrollPane(list, skin);
    buttonAdd = new TextButton("ADD", skin);
    buttonAdd.pad(SPACE);
    buttonRemove = new TextButton("REMOVE", skin);
    buttonRemove.pad(SPACE);
    buttonExit = new TextButton("EXIT", skin);
    buttonExit.pad(10);
    this.table.add(this.heading);
    super.getUiStage().addActor(this.table);
    this.table.getCell(this.heading).spaceBottom(100);
    this.table.row();
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
