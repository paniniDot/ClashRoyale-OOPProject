package view.screens;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
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
import model.utilities.AnimationUtilities;
import view.actors.BaseActor;

/**
 * Desck screen implementation.
 */
public class DeckScreen extends BaseScreen {

  private TextureAtlas atlas;
  private Skin skin;
  private Table table, tableDeck, tableCards; 
  private List list, cards, deck;
  private ArrayList<String> cardsList, deckList; 
  private ScrollPane scrollPaneDeck, scrollPaneCards;
  private TextButton buttonAdd, buttonRemove, buttonExit;
  private Label heading, headingDeck, headingCards;
  private Gson gson;
  private static final int SPACE = 15;
  private static final int HEIGHTSCROLLPANE = 350;
  private static final int WIDTHSCROLLPANE = 400;
  private static final int DIMDECK = 4;
  private Texture texture1;
  private Dialog  endDialog;
  private JFrame frame;

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
    cardsList = new ArrayList<>();
    deckList = new ArrayList<>();


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

    String read;
    FileInputStream file, file2;
    try {
      String pathCards = "C:\\Users\\Giulia\\Desktop/cardsList.txt";
      String pathDeck = "C:\\Users\\Giulia\\Desktop/deckList.txt";
      file = new FileInputStream  (pathCards);
      file2 = new FileInputStream  (pathDeck);
      Scanner sc = new Scanner(file);
      Scanner sc2 = new Scanner(file2);
      while (sc.hasNextLine()) {
        read = sc.nextLine();
        cardsList.add(read);
      }
      while (sc2.hasNextLine()) {
        read = sc2.nextLine();
        deckList.add(read);
      }
      this.heading = new Label("Scelta Deck", this.skin);
      this.headingDeck = new Label("Deck", this.skin);
      this.headingCards = new Label("Mazzo", this.skin);
      this.cards = new List<String>(skin);
      Object[] objectArray = cardsList.toArray();
      cards.setItems(objectArray);
      this.deck = new List<String>(skin);
      objectArray = deckList.toArray();
      deck.setItems(objectArray);

      this.scrollPaneDeck = new ScrollPane(deck);
      this.scrollPaneCards = new ScrollPane(cards);
      this.buttonAdd = new TextButton("ADD", skin);
      this.buttonAdd.pad(SPACE);
      this.buttonAdd.addListener(new ClickListener() {
        @Override
        public void clicked(final InputEvent event, final float x, final float y) {
            if(deckList.size() < DIMDECK) {
            cardsList.remove(cards.getSelected());
            Object[] objectArray = cardsList.toArray();
            cards.setItems(objectArray);
            deckList.add((String) cards.getSelected());
            objectArray = deckList.toArray();
            deck.setItems(objectArray);
            }
            else
            JOptionPane.showMessageDialog(frame, "DECK PIENO(MAX 4 CARTE), RIMUOVERE PRIMA UNA CARTA");

        }
        });
      
      this.buttonRemove = new TextButton("REMOVE", skin);
      this.buttonRemove.pad(SPACE);
      this.buttonRemove.addListener(new ClickListener() {
        @Override
        public void clicked(final InputEvent event, final float x, final float y) {
       
          }
      });
      buttonExit = new TextButton("EXIT", skin);
      this.buttonExit.addListener(new ClickListener() {
        @Override
        public void clicked(final InputEvent event, final float x, final float y) {
//          try {
//            FileOutputStream save = new FileOutputStream(pathCards);
//            for(var entry : cardsMap.entrySet()){
//              String data = entry.getKey()+"\t"+entry.getValue();
//              byte[] array = data.getBytes();
//              save.write(array);
//            }
//          } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//          } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//          }
          Gdx.app.exit();
        }
      });
      buttonExit.pad(10);

      this.tableDeck.add(headingDeck).row();
      this.tableDeck.add(scrollPaneDeck).height(HEIGHTSCROLLPANE).width(WIDTHSCROLLPANE).left().expandY().expandX();
      this.tableDeck.add(buttonRemove).right();
      this.tableCards.add(headingCards).row();
      this.tableCards.add(scrollPaneCards).height(HEIGHTSCROLLPANE).width(WIDTHSCROLLPANE).left().expandY().expandX();
      this.tableCards.add(buttonAdd).right();
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
