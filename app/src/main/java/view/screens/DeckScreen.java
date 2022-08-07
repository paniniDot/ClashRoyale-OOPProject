package view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
import model.utilities.AnimationUtilities;
import view.actors.BaseActor;

/**
 * Desck screen implementation.
 */
public class DeckScreen extends BaseScreen {

  private TextureAtlas atlas;
  private Skin skin;
  private Table table, tableDeck, tableCards; 
  private List list,cards, deck;
  private ScrollPane scrollPaneDeck, scrollPaneCards;
  private TextButton buttonAdd, buttonRemove, buttonExit;
  private Label heading, headingDeck, headingCards;
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


    this.table.debug();
    
    this.heading = new Label("Scelta Deck", this.skin);
    this.headingDeck = new Label("Mazzo", this.skin);
    this.headingCards = new Label("Deck", this.skin);
    this.cards = new List<String>(skin);
    cards.setItems(new String[] {"arciere", "cavalihdfjhufdjere","mago", "fata", "angelo", "priscilla", "lancillotto",});
    
    this.deck = new List<String>(skin);
    deck.setItems(new String[] {"Simone n1", "Fjgfkghfjjgore","anna", "Serena", "angelo", "fata", "sandro",});
    
    scrollPaneDeck = new ScrollPane(deck);
    scrollPaneCards = new ScrollPane(cards);
    buttonAdd = new TextButton("ADD", skin);
    buttonAdd.pad(SPACE);
    buttonRemove = new TextButton("REMOVE", skin);
    buttonRemove.pad(SPACE);
    buttonExit = new TextButton("EXIT", skin);
    buttonExit.pad(10);
    
    this.tableDeck.add(headingDeck).row();
    this.tableDeck.add(scrollPaneDeck).height(350).width(400).left().expandY().expandX();
    this.tableDeck.add(buttonAdd).right();
    this.tableCards.add(headingCards).row();
    this.tableCards.add(scrollPaneCards).height(350).width(400).left().expandY().expandX();
    this.tableCards.add(buttonRemove).right();
    this.table.add().spaceBottom(SPACE).row();
    this.table.add(heading);
    this.table.getCell(this.heading).spaceBottom(100).row();
    this.table.add(tableDeck).left().row();
    this.table.add(tableCards).left().row();
    this.table.add(this.buttonExit).right();

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
