package view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
  private Table table,container,table1,table2,table3, table4;
  private List list;
  private ScrollPane scrollpane;
  private TextButton buttonAdd, buttonRemove, buttonExit;
  private Label heading;
  private Gson gson;
  private static final int SPACE = 15;
  private Texture texture1, texture2, texture3, texture4;


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
    this.atlas = new TextureAtlas("buttons/atlas.pack");
    this.skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), atlas);
    this.table = new Table(skin);
    this.table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    this.table.debug();
    this.heading = new Label("Scelta Deck", this.skin);
    this.list = new List<String>(skin);
    list.setItems(new String[] {"arciere", "cavaliere","mago"}); 
    texture1 = new Texture(Gdx.files.internal("Cards/BarbariansCard.png"));
    texture2 = new Texture(Gdx.files.internal("Cards/FireballCard.png"));
    texture3 = new Texture(Gdx.files.internal("Cards/GiantCard.png"));
    texture4 = new Texture(Gdx.files.internal("Cards/InfernoTowerCard.png"));

//    this.scrollPane = new ScrollPane(list, skin);
    //inner table that is used as a makeshift list.
    
    // table that holds the scroll pane
    container = new Table();
    container.setWidth(320f);
    container.setHeight(300f);
    this.container.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    
    // tables that hold the data you want to display
    table1 = new Table(skin);
    table1.add(new Image(texture1)).expandY().fillY();
    table1.add(new Label("", skin)).width(10f).expandY().fillY();// a spacer
    table1.add(new Label("Figa!", skin)).expandY().fillY();

    table2 = new Table(skin);
    table2.add(new Image(texture2)).expandY().fillY();
    table2.add(new Label("", skin)).width(10f).expandY().fillY();// a spacer
    table2.add(new Label("Capra!", skin)).expandY().fillY();

    table3 = new Table(skin);
    table3.add(new Image(texture3)).expandY().fillY();
    table3.add(new Label("", skin)).width(10f).expandY().fillY();// a spacer
    table3.add(new Label("Rana!", skin)).expandY().fillY();
    
    table4 = new Table(skin);
    table4.add(new Image(texture4)).expandY().fillY();
    table4.add(new Label("", skin)).width(10f).expandY().fillY();// a spacer
    table4.add(new Label("Pluto", skin)).expandY().fillY();
    
    
    //inner table that is used as a makeshift list.
    Table innerContainer = new Table();
    innerContainer.add(table1).expand().fill();
    innerContainer.row();
    innerContainer.add(table2).expand().fill();
    innerContainer.row();
    innerContainer.add(table3).expand().fill();
    innerContainer.row();
    innerContainer.add(table4).expand().fill();
   

    // create the scrollpane
    scrollpane = new ScrollPane(innerContainer);
    //add the scroll pane to the container
    container.add(scrollpane).fill().expand();
    
    
//    buttonAdd = new TextButton("ADD", skin);
//    buttonAdd.pad(SPACE);
//    buttonRemove = new TextButton("REMOVE", skin);
//    buttonRemove.pad(SPACE);
//    buttonExit = new TextButton("EXIT", skin);
//    buttonExit.pad(10);
//    this.table.add(heading);
//    this.table.getCell(this.heading).spaceBottom(100).row();
//    this.table.add(scrollPane);
//    this.table.add(buttonAdd);
//    this.table.add(buttonRemove);

    
    super.getUiStage().addActor(this.container);
    Gdx.input.setInputProcessor(getUiStage());
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
