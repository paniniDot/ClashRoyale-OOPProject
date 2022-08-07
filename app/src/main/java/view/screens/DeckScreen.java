package view.screens;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
  private Table table, tableDeck, tableCards; 
  private List list, cards, deck;
  private Map<String, Integer> cardsMap, deckMap; 
  private ScrollPane scrollPaneDeck, scrollPaneCards;
  private TextButton buttonAdd, buttonRemove, buttonExit;
  private Label heading, headingDeck, headingCards;
  private Gson gson;
  private static final int SPACE = 15;
  private Texture texture1;


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
    cardsMap = new HashMap<>();
    deckMap = new HashMap<>();
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

    //come faccio a leggere riga per riga?   FileHandle file = Gdx.files.local("Cards/cardsList.txt");
    String[] read;
    //   System.out.println(read);
    FileInputStream file, file2;
    try {
      file = new FileInputStream  ("C:\\Users\\Giulia\\Desktop/cardsList.txt");
      file2 = new FileInputStream  ("C:\\Users\\Giulia\\Desktop/deckList.txt");
      Scanner sc = new Scanner(file);
      Scanner sc2 = new Scanner(file2);
      while (sc.hasNextLine()) {
        read = sc.nextLine().split("\t");
        cardsMap.put(read[0], Integer.parseInt(read[1]));
      }
      while (sc2.hasNextLine()) {
        read = sc2.nextLine().split("\t");
        deckMap.put(read[0], Integer.parseInt(read[1]));
      }
      this.heading = new Label("Scelta Deck", this.skin);
      this.headingDeck = new Label("Mazzo", this.skin);
      this.headingCards = new Label("Deck", this.skin);
      this.cards = new List<String>(skin);
      Object[] objectArray = cardsMap.entrySet().toArray();
      cards.setItems(objectArray);
      this.deck = new List<String>(skin);
      objectArray = deckMap.entrySet().toArray();
      deck.setItems(objectArray);

      this.scrollPaneDeck = new ScrollPane(deck);
      this.scrollPaneCards = new ScrollPane(cards);
      this.buttonAdd = new TextButton("ADD", skin);
      this.buttonAdd.pad(SPACE);
      this.buttonAdd.addListener(new ClickListener() {
        @Override
        public void clicked(final InputEvent event, final float x, final float y) {
          boolean check = true;
          if (!deckMap.isEmpty() && check) {
            String key = deck.getSelected().toString();
            key = key.substring( 0, key.indexOf("=") );
            if (deckMap.get(key).intValue() >1) {
              int numCards = deckMap.get(key).intValue() - 1;
              deckMap.put(key, numCards);
            }
            else
            { deckMap.remove(key);}
            Object[] objectArray = deckMap.entrySet().toArray();
            deck.setItems(objectArray);
            check =deckMap.containsKey(key);
            if(cardsMap.isEmpty() || !cardsMap.containsKey(key)) {
              cardsMap.put(key, 1);
            }
            else {
              int numCards = cardsMap.get(key).intValue() + 1;
              cardsMap.put(key, numCards);
            }
            objectArray = cardsMap.entrySet().toArray();
            cards.setItems(objectArray);
          }
          deck.setSelected(false);
          }
      });
      this.buttonRemove = new TextButton("REMOVE", skin);
      this.buttonRemove.pad(SPACE);
      this.buttonRemove.addListener(new ClickListener() {
        @Override
        public void clicked(final InputEvent event, final float x, final float y) {
          boolean check = true;
          if (!cardsMap.isEmpty() && check) {
            String key = cards.getSelected().toString();
            key = key.substring( 0, key.indexOf("=") );
            if (cardsMap.get(key).intValue() >1) {
              int numCards = cardsMap.get(key).intValue() - 1;
              cardsMap.put(key, numCards);
            }
            else
            {cardsMap.remove(key);}
            Object[] objectArray = cardsMap.entrySet().toArray();
            cards.setItems(objectArray);
            check = cardsMap.containsKey(key);
            if (deckMap.isEmpty() || !deckMap.containsKey(key)) {
              deckMap.put(key, 1);
            }
            else {
              int numCards = deckMap.get(key).intValue() + 1;
              deckMap.put(key, numCards);
            }
            objectArray = deckMap.entrySet().toArray();
            deck.setItems(objectArray);
          }
          cards.setSelected(false);
          }
      });
      buttonExit = new TextButton("EXIT", skin);
      this.buttonExit.addListener(new ClickListener() {
        @Override
        public void clicked(final InputEvent event, final float x, final float y) {
          Gdx.app.exit();
        }
      });
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
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


    //  String[] elenco = read.split("\n");
    //  cards.setItems(elenco);



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
