package view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import control.controller.Controller;
import control.controller.DeckController;
import control.launcher.ClashRoyale;
import model.utilities.AnimationUtilities;
import view.actors.BaseActor;

/**
 * Deck screen implementation.
 */
public class DeckScreen extends BaseScreen {

  private TextureAtlas atlas;
  private Skin skin;
  private List<String> cards, deck;
  private DeckController deckController;

  private static final int SPACE = 15;
  private static final int HEIGHTSCROLLPANE = 350;
  private static final int WIDTHSCROLLPANE = 400;


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
    super.getController().playMusic();
    super.getController().setInputProcessor(super.getMainStage());
    final var background = new BaseActor(0, 0, super.getMainStage(), AnimationUtilities.loadTexture("backgrounds/menuBackground.png"));
    background.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
  }
  @Override
  public void show() {
    Table table, tableDeck, tableCards;
    ScrollPane scrollPaneDeck, scrollPaneCards;
    TextButton buttonAdd, buttonRemove, buttonReturn;
    Label heading, headingDeck, headingCards;

    super.show();
    Gdx.input.setInputProcessor(super.getUiStage());
    this.atlas = new TextureAtlas("buttons/atlas.pack");
    this.skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), atlas);
    this.deckController = new DeckController();
    this.cards = this.deckController.setCards();
    this.deck = this.deckController.setDeck();

    table = new Table(skin);
    table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    tableDeck = new Table(skin);
    tableCards = new Table(skin);
    heading = new Label("Scelta Deck", this.skin);
    headingDeck = new Label("Deck", this.skin);
    headingCards = new Label("Mazzo", this.skin);
    scrollPaneDeck = new ScrollPane(deck);
    scrollPaneCards = new ScrollPane(cards);

    buttonAdd = new TextButton("ADD", skin);
    buttonAdd.pad(SPACE);
    buttonAdd.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        if (deckController.full()) {
        final String select = cards.getSelected();
        cards = deckController.removeCard(select);
        deck = deckController.addDeck(select);
        }
      }
    });

    buttonRemove = new TextButton("REMOVE", skin);
    buttonRemove.pad(SPACE);
    buttonRemove.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        if (deckController.empty()) {
        final String select = (String) deck.getSelected();
        deck = deckController.removeDeckCard(select);
        cards = deckController.addCard(select);
        }
      }
    });

    buttonReturn = new TextButton("RETURN", skin);
    buttonReturn.addListener(new ClickListener() {
      @Override
      public void clicked(final InputEvent event, final float x, final float y) {
        getController().stopMusic();
        deckController.returnButton();
      }
    });
    buttonReturn.pad(10);

    tableDeck.add(headingDeck).row();
    tableDeck.add(scrollPaneDeck).height(HEIGHTSCROLLPANE).width(WIDTHSCROLLPANE).left().expandY().expandX();
    tableDeck.add(buttonRemove).right();
    tableCards.add(headingCards).row();
    tableCards.add(scrollPaneCards).height(HEIGHTSCROLLPANE).width(WIDTHSCROLLPANE).left().expandY().expandX();
    tableCards.add(buttonAdd).right();
    table.add().spaceBottom(SPACE).row();
    table.add(heading);
    table.getCell(heading).spaceBottom(100).row();
    table.add(tableDeck).left().row();
    table.add(tableCards).left().row();
    table.add(buttonReturn).right();

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
